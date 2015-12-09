# wiiusej
A Java API for Wiimotes, fork of [guiguito's wiiusej](https://github.com/guiguito/wiiusej), originally on [Google Code](https://code.google.com/p/wiiusej/)

## Dependencies
wiiusej includes binaries from
[wiiuse](http://www.macs.hw.ac.uk/~ruth/year4VEs/Labs/wiiuse.html),
a native Wiimote library by Michael Laforest.

## Changes from original version
- Project splitted into Maven modules
- Detecting operating system automatically
- Unpacking appropriate native libraries from JAR file to temp directory

## Installation
You can either:
- download a [released JAR](../../releases) and add it to the build path of your project
- use a dependency management tool like [Maven](https://maven.apache.org) or [Gradle](http://gradle.org/)

For the second option, there is a free service called [JitPack](https://jitpack.io/) that allows adding a project hosted on GitHub as a dependency. It downloads source code, builds JARs and serves them to you.

To use wiiusej on a Maven project, add this to your pom.xml:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.awvalenti.wiiusej</groupId>
    <artifactId>wiiusej-lib</artifactId>
    <version>v0.13</version>
  </dependency>
    <!--
      Here goes either a git tag correponding to a released version,
      a commit hash or the string "-SNAPSHOT" (without the quotes,
      with the hyphen). The example below uses a git tag.
    -->
    <version>v0.13</version>
  </dependency>
</dependencies>
```

## License
[GPL v3](http://www.gnu.org/licenses/gpl-3.0.en.html)

```
wiiusej - Java API for Wiimotes
Copyright (C) 2015  Andre Willik Valenti
Copyright (C) 2008  Guilhem Duche

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

### Former readme
```
WiiuseJ is a java API to use wiimotes on your computer. This API is built on top of an excellent API call Wiiuse using JNI.

WiiuseJ intent to be a very simple, easy to use and lightweight java API for wiimotes.

Some of the great aspects of this API are :

    * The compatibility which is as large as Wiiuse's API compatibility 

    * Cross-platform : Windows and linux (like Wiiuse) 

    * Easy to use 
    
    
WiiuseJ is made by Guilhem Duché : guiguito1@hotmail.com.
You can contact me if you need more information.    
    
Go to : http://wiiusej.googlecode.com/   for further documentations.   



CHANGELOG :

wiiusej 0.12b :
- make wiiusej compatible with java 1.5.
- fixed a bug that made Null pointer exception happen when disconnecting several wiimotes.

wiiusej 0.12a :
- added guitar hero controller support.
- added classic controller support.

wiiusej 0.12 :
- added nunchuk support.
- all new stuffs from wiiuse 0.12.
- added timeout method.
- added IR sensitivity.
- updated to be able to reconnect wiimotes.
- new architecture behind the scene.
- cleaning of the project.

wiiusej 0.11 :
- updated with new stuffs from wiiuse 0.11.
- Linux version.
- selectionnable rumble on wiimotes connection. 

wiiusej 0.1 :
- first version on wiiuse 0.1.
```
