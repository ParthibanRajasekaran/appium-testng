# Appium2 Sample Project

## Pre Requisites
* Make sure you follow the installation instruction [here](https://github.com/saikrishna321/VodQa_MobileAutomationWorkShop) and configure all the necessary dependencies required.
    * Java 17
    * Maven
    * Node 16+ installed
    * Android Studio + Android Emulators created
    * Make sure PATH variables are configured.
    * Appium Doctor and run the safety check
    * Appium 2 and it's driver's installed
    * Intellij IDE, etc

## Steps to execute
1. Start Appium server manually in terminal using `appium`
2. Start an Android emulator and confirm device connection using `adb devices` command
3. Open another terminal and navigate to the project
4. Run command `mvn clean test` 

## Install Basic Drivers
To install **uiautomator2** / _Run android tests_ : `appium driver install uiautomator2`
To install **xcuitest** / _Run iOS tests_ : `appium driver install uiautomator2 `
