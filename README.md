# Program Design II

The repo for final project of program design II in CCU.

# How to install

You couldn't compile from the source code, because there are some missing art resource. Credit to [Pixymoon](https://pixymoon.itch.io) for creating such beautiful art. If you own [Cute RPG World](https://pixymoon.itch.io/cute-rpg-world) asset pack, you may move the folder under assets and rename it to `Cute RPG World`. Then, it should be good to compile from the source.

If you don't want to buy it, you may go to releases page and download pre-build jar file.

# How to run

## Pre-build jar file 

Download it, and cd to where the file located. Then run:

```
java -jar game.jar // add -XstartOnFirstThread if you are using mac
```

## Compile from the source

Make sure you have buy [Cute RPG World](https://pixymoon.itch.io/cute-rpg-world) asset pack and put the folder the right place, have jdk(21 or above) installed, and not afraid of command-line.

Download the source.
```
git clone https://github.com/simanglam/Program_Design_II.git
```

Compile it.
```
./gradlew desktop:dist // mac or unix-like system

.\gradlew.bat desktop:dist // windows
```

The Jar file should be found under `Path-To-Project/desktop/build/lib/desktop-{version}.jar`.

# Guideline

![image](https://github.com/simanglam/Program_Design_II/blob/map/assets/guideline1.png)

![image](https://github.com/simanglam/Program_Design_II/blob/map/assets/guideline2.png)

![image](https://github.com/simanglam/Program_Design_II/blob/map/assets/guideline3.png)

# Team member

| github 帳號 | 名字 | 學號 |
| ----- | ----- | ----- |
| simanglam | 周造麟 | 412910010 |
| jhenyulin | 林振宇 | 412910027 | 
| Poki1246 | 汪柏昱 | 412910030 | 
| xup6m4c06 | 林育函 | 412530036 | 
| WillieHuang | 黃偉宸 | 411315011 | 
| Brightchao | 趙柏榕 | 411315044 | 