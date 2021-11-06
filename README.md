# mLib
A simple Library for minecraft spigot plugins

This library includes the following utilities:
  1) Menus (for inventories and guis)
  2) Commands (supports subcommands trees)
  3) Files and config files
  4) Other crucial utilities (e.g: ItemBuilder, MessagesBuilder)


## Using mLib
There are currently two ways of using it
- Artifact Dependency
- Maven Dependency

#Artifact Dependency
You can download the jar directly from [here](../../releases/tag/1.0.3) , and use
it as an artifact

#Maven Dependency
Firstly, you must inject jitpack dependency in your pom.xml by adding 
the jitpack repository in your repositories **AND** the dependency of jitpack it's self
Moreover, you must add the mLib dependency from github
**Example:**
```xml
      <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
      </repositories>

      <dependencies>
        <dependency>
            <groupId>com.github.jitpack</groupId>
            <artifactId>maven-simple</artifactId>
            <version>0.1</version>
        </dependency>
        <dependency>
            <groupId>com.github.Mqzn</groupId>
            <artifactId>mLib</artifactId>
            <version>1.0.3</version>
            <scope>provided</scope>
        </dependency>
      </dependencies>
```

***Some Guides on how to use each utility***
- [Menus](../../wiki/Menus)
- [Commands](../../wiki/Commands)

