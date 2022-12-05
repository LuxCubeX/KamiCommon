package com.kamikazejamplugins.kamicommon.config;

import com.kamikazejamplugins.kamicommon.config.annotation.ConfigValue;
import com.kamikazejamplugins.kamicommon.config.data.ConfigComment;
import com.kamikazejamplugins.kamicommon.config.data.KamiConfig;
import com.kamikazejamplugins.kamicommon.util.StringUtil;
import com.kamikazejamplugins.kamicommon.yaml.YamlHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class KamiConfigManager {
    public static void parse(KamiConfig... kamiConfigs) {
        for (KamiConfig kamiConfig : kamiConfigs) {
            try {
                parse(kamiConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void parse(KamiConfig kamiConfig) throws Exception {
        YamlHandler yamlHandler = new YamlHandler(kamiConfig.getFile());
        YamlHandler.YamlConfiguration config = yamlHandler.loadConfig(false);

        List<ConfigComment> comments = new ArrayList<>();

        for (Field field : kamiConfig.getClass().getDeclaredFields()) {
            ConfigValue annotation;

            field.setAccessible(true);
            if (!field.isAnnotationPresent(ConfigValue.class)) { continue; }
            annotation = field.getAnnotation(ConfigValue.class);

            // Set the config values
            if (!annotation.key().isEmpty()) {
                if (!config.contains(annotation.key())) {
                    config.set(annotation.key(), field.get(kamiConfig));
                }
            }

            // Add to the comments list for post processing
            if (annotation.above().length != 0) {
                int i = 0;
                String[] parts = annotation.key().split("\\.");

                for (String c : annotation.above()) {
                    if (!c.isEmpty()) {
                        String key = StringUtil.combine(StringUtil.subList(parts, 0, i+1), ".");
                        comments.add(new ConfigComment(key, c, true));
                    }
                    i++;
                }
            }
            if (!annotation.inline().isEmpty()) {
                comments.add(new ConfigComment(annotation.key(), annotation.inline(), false));
            }
        }
        // Save the FileConfiguration (without comments)
        config.save();

        // Add the comments to the file
        for (ConfigComment comment : comments) {
            addComment(kamiConfig.getFile(), comment);
        }
    }

    private static void addComment(File file, ConfigComment comment) throws IOException {
        String[] parts = comment.getKey().split("\\.");
        int searchingFor = 0;

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String start = StringUtil.repeat("  ", searchingFor) + parts[searchingFor] + ":";

            if (line.startsWith(start)) {
                if (searchingFor == parts.length - 1) {

                    // We've found the key we're looking for
                    if (comment.isAbove()) {
                        lines.add(i, StringUtil.repeat("  ", searchingFor) + "# " + comment.getComment());
                    }else {
                        lines.set(i, line + " # " + comment.getComment());
                    }
                    break;
                } else {
                    searchingFor++;
                }
            }
        }

        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
    }
}
