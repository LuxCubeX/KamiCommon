dependencies {
    // Unique dependencies for this module
    compileOnly("org.spigotmc:spigot-server:1.11.2-R0.1-SNAPSHOT")
    compileOnly(files(project(":spigot-nms:api")
        .dependencyProject.layout.buildDirectory.dir("unpacked-shadow"))
    )
}
