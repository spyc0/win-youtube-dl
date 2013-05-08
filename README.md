win-youtube-dl
==============

A very simple GUI wrapper for youtube-dl (http://rg3.github.io/youtube-dl/download.html) Python script.
This application only works on 64-bit Windows (I'm lazy to make it work on 32-bit Windows).
Technically speaking, this application can work on Linux and Mac OSX, but I don't see a point of
having this application run those two OSes since Linux or Mac OSX users should be familar with
using a command line.

TL;DR: This application (https://sourceforge.net/projects/winyoutubedl/files/latest/download) is for non-technies.
       Power users are encouraged to use the real script (http://rg3.github.io/youtube-dl/download.html).

How to build
------------
win-youtube-dl uses Gradle build system (http://www.gradle.org/) and launch4j (http://launch4j.sourceforge.net/)
It also requires at least Java 7 (http://java.com/en/download/index.jsp).

    gradle -Plaunch4j=<launch4j_directory>

How to run
--------------
    Unzip win-youtube-dl.zip at https://sourceforge.net/projects/winyoutubedl/files/latest/download
    Double click on win-youtube-dl.exe

Screenshot
----------
![win-youtube-dl screenshot](https://raw.github.com/fredyw/win-youtube-dl/master/win-youtube-dl.png)    
