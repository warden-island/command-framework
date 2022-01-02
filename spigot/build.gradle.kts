repositories {
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
    api(project(":shared"))
}