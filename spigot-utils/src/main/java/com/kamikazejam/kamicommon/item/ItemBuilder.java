package com.kamikazejam.kamicommon.item;

import com.kamikazejam.kamicommon.xseries.XMaterial;
import com.kamikazejam.kamicommon.yaml.spigot.ConfigurationSection;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ItemBuilder extends IBuilder {

    ItemStack base = null;
    private final @NotNull List<XMaterial> materialCycle = new ArrayList<>();

    private ItemBuilder() {} // Private for Clone
    public ItemBuilder(ConfigurationSection section) {
        super(section);
    }
    public ItemBuilder(ConfigurationSection section, OfflinePlayer offlinePlayer) {
        super(section, offlinePlayer);
    }
    public ItemBuilder(XMaterial mat, ConfigurationSection section) {
        super(mat, section);
    }
    public ItemBuilder(XMaterial mat, ConfigurationSection section, OfflinePlayer offlinePlayer) {
        super(mat, section, offlinePlayer);
    }
    public ItemBuilder(ItemStack base, ConfigurationSection section) {
        super(base, section);
    }
    public ItemBuilder(ItemStack base, ConfigurationSection section, OfflinePlayer offlinePlayer) {
        super(base, section, offlinePlayer);
    }
    public ItemBuilder(XMaterial m) {
        super(m);
    }
    public ItemBuilder(int id) {
        super(id);
    }
    public ItemBuilder(int id, short damage) {
        super(id, damage);
    }
    public ItemBuilder(XMaterial m, short damage) {
        super(m, damage);
    }
    public ItemBuilder(int id, int amount) {
        super(id, amount);
    }
    public ItemBuilder(XMaterial m, int amount) {
        super(m, amount);
    }
    public ItemBuilder(int id, int amount, short damage) {
        super(id, amount, damage);
    }
    public ItemBuilder(XMaterial material, int amount, short damage) {
        super(material, amount, damage);
    }
    public ItemBuilder(ItemStack is) {
        super(is);
    }
    public ItemBuilder(ItemStack is, boolean clone) {
        super(is, clone);
    }

    @Override
    public void loadBasicItem(ConfigurationSection config, boolean loadMaterial) {
        // Load the material
        if (loadMaterial && !this.loadMaterial(config)) {
            throw new IllegalStateException("No materials found in config.");
        }

        // Load basic values from config
        this.setAmount(config.getInt("amount", 1));
        this.setDurability(config.getInt("damage", 0));
        this.setName(config.getString("name"));
        this.setLore(config.getStringList("lore"));
    }

    @Override
    public IBuilder clone() {
        return loadClone(new ItemBuilder());
    }
}
