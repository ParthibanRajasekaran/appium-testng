package com.parthibanrajasekaran.vodqa.test.ios;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// Tests for VodQA App using Actions API
public class ActionsApiTest {
    public AppiumDriver driver;
    WebDriverWait wait;

    AppiumDriverLocalService service;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        service = AppiumDriverLocalService
            .buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withLogFile(new File(System.getProperty("user.dir")+ "/iOS-logs.txt"))
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub"));
        service.start();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700000);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.UDID, "A4EA7479-D8B0-406C-97F5-AA4624B08BA5");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/vodqa.zip");
        driver = new IOSDriver(service.getUrl(), capabilities);
    }

    @Test
    public void SliderTest() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("login"))).click();
        Thread.sleep(3000);
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("slider1"))).click();
        Thread.sleep(3000);
        WebElement slider = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("slider")));

        // getting initial location of the pointer in the slider
        final Point location = slider.getLocation();

        final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        final Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
            location.x, location.y));

        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence.addAction(finger.createPointerDown(MouseButton.MIDDLE.asArg()));

        sequence.addAction(new Pause(finger, Duration.ofMillis(600)));

        sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
            location.x+50, location.y));
        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence.addAction(finger.createPointerUp(MouseButton.MIDDLE.asArg()));

        driver.perform(Collections.singletonList(sequence));

        Thread.sleep(8000);
    }

    @Test
    public void DragAndDropTest() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("login"))).click();
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragAndDrop"))).click();
        Thread.sleep(3000);
        WebElement ball = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragMe")));
        WebElement destination = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dropzone")));

        final int originX = ball.getLocation().x;
        final int originY = ball.getLocation().y;

        final int DestinationX = destination.getLocation().x;
        final int DestinationY = destination.getLocation().y;
        final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        final Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),originX, originY));

        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence.addAction(finger.createPointerDown(MouseButton.MIDDLE.asArg()));

        sequence.addAction(new Pause(finger, Duration.ofMillis(600)));

        sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
            DestinationX+400, DestinationY+50));

        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence.addAction(finger.createPointerUp(MouseButton.MIDDLE.asArg()));

        driver.perform(Collections.singletonList(sequence));
        Thread.sleep(8000);
    }

    @Test
    public void DoubleFingerSwipeTest() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("login"))).click();
        Thread.sleep(3000);
        wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("slider1"))).click();
        Thread.sleep(3000);

        WebElement slider1 = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("slider")));
        WebElement slider2 = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("slider1")));

        // getting initial location of the pointer in the slider
        final Point slider1Location = slider1.getLocation();
        final Point slider2Location = slider2.getLocation();

        final PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        final PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        final Sequence sequence1 = new Sequence(finger1, 1);
        final Sequence sequence2 = new Sequence(finger2, 1);

        sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
            slider1Location.x, slider1Location.y));
        sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
            slider2Location.x, slider2Location.y));

        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence1.addAction(finger1.createPointerDown(MouseButton.MIDDLE.asArg()));
        sequence2.addAction(finger2.createPointerDown(MouseButton.MIDDLE.asArg()));

        sequence1.addAction(new Pause(finger1, Duration.ofMillis(600)));
        sequence2.addAction(new Pause(finger2, Duration.ofMillis(600)));

        sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
            slider1Location.x+50, slider1Location.y));
        sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
            slider2Location.x+50, slider2Location.y));


        // Dummy sequence - Had to be added as a consequence of reusing the selenium endpoints as well as W3C protocol expects this interaction
        sequence1.addAction(finger1.createPointerUp(MouseButton.MIDDLE.asArg()));
        sequence2.addAction(finger2.createPointerUp(MouseButton.MIDDLE.asArg()));

        ArrayList<Sequence> sequenceArrayList = new ArrayList<>();
        sequenceArrayList.add(sequence1);
        sequenceArrayList.add(sequence2);

        driver.perform(sequenceArrayList);

        Thread.sleep(8000);


    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        service.stop();
    }


}
