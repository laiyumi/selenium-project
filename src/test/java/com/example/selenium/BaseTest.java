package com.example.selenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.selenium.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
    // Create the web driver
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    // Set up the report
    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    // Set up the driver
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown (ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("❌ <b>Test Failed</b>");
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("✅ <b>Test Passed</b>");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("⚠️ <b>Test Skipped</b>");
        }
        if (driver != null) {
            // Pause for 5 seconds
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (extent != null) {
            extent.flush();
        }
    }
}
