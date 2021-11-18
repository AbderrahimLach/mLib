# mLib
A simple Library for minecraft spigot plugins

This library includes the following utilities:
  1) Menus (for inventories and guis)
  2) Commands (supports subcommands trees)
  3) Files and config files
  4) Other crucial utilities (e.g: ItemBuilder, MessagesBuilder)


## Using mLib
There are currently two ways of using it as a dependency in your project
- Artifact Dependency
- Maven Dependency

### Artifact Dependency
You can download the jar directly from [here](../../releases/tag/1.1.4) , and use
it as an artifact

### Maven Dependency
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
            <groupId>com.github.Mqzn</groupId>
            <artifactId>mLib</artifactId>
            <version>1.1.6</version>
            <scope>provided</scope> 
        </dependency>
      </dependencies>
```
[![](https://jitpack.io/v/Mqzn/mLib.svg)](https://jitpack.io/#Mqzn/mLib)

## Using mLib on your server
mLib works as an independent plugin so that it register it's listeners on it's own
making it easiers for developers to use and to prevent duplication of code

You can download the jar directly from [here](../../releases/tag/1.1.6) , and place it in your plugins folder
then just restart the server and here you go !

## Utilities Guides
- [Menus](../../wiki/Menus)
- [Commands](../../wiki/Commands)

