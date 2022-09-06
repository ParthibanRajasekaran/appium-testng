package com.parthibanrajasekaran.vodqa.test.android;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// Tests for VodQA App using Actions API (Can be used independent of platform unlike Android / iOS specific gestures)
public class AndroidSpecificGesturesTest {
    public AppiumDriver driver;
    WebDriverWait wait;

    AppiumDriverLocalService service;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        service = AppiumDriverLocalService
            .buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withLogFile(new File(System.getProperty("user.dir")+ "/android-logs.txt"))
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub"));
        service.start();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700000);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/VodQA.apk");
        driver = new AndroidDriver(service.getUrl(), capabilities);
    }

    @Test
    public void DragAndDropTest() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("login"))).click();
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragAndDrop"))).click();

        WebElement ball = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragMe")));
        final Point dropZoneLocation = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dropzone"))).getLocation();

        driver.executeScript("mobile: dragGesture", ImmutableMap.of("elementId",ball, "endX",dropZoneLocation.x+100, "endY", dropZoneLocation.y));
        Thread.sleep(3000);
    }

    @Test
    public void shellTest() {
        String response = (String) driver.executeScript("mobile: shell", ImmutableMap.of(
            "command", "dumpsys",
            "args","cpuinfo"
        ));
        System.out.println(response);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        service.stop();
    }


}
