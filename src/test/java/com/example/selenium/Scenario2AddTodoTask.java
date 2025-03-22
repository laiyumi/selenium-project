package com.example.selenium;

import com.example.selenium.utils.ConfigReader;
import com.example.selenium.utils.ExcelReader;
import com.example.selenium.utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public class Scenario2AddTodoTask extends BaseTest{

    // Prepare the data
    @DataProvider(name = "todoTaskData")
    public Iterator<Object[]> getTestData() {
        List<String[]> testData = ExcelReader.readData("data/Scenario2Data.xlsx");

        System.out.println("Reading test data...");
        for (String[] row : testData) {
            System.out.println("Title: " + row[0] + ", Date: " + row[1] + ", Time: " + row[2] + ", Calendar: " + row[3] + ", Details: " + row[4]);
        }

        return testData.stream().map(data -> new Object[]{data[0], data[1], data[2], data[3], data[4]}).iterator();
    }

    // Run the test
    @Test(dataProvider = "todoTaskData")
    public void testScenario2AddTodoTask(String title, String date, String time, String calendar, String details) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        test = extent.createTest("Add To Do: " + title);

        // Step 1: Open Canvas login page
        driver.get("https://northeastern.instructure.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("loginfmt")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "1_before_typing_account");

        // Step 2: Enter account email
        driver.findElement(By.name("loginfmt")).sendKeys(ConfigReader.getUsername());
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "1_after_typing_account");

        // Step 3: Click Next
        driver.findElement(By.id("idSIButton9")).click();

        // Step 4: Wait for password field
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwd")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "2_before_typing_password");

        // Step 5: Enter password
        driver.findElement(By.name("passwd")).sendKeys(ConfigReader.getPassword());
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "2_after_typing_password");

        // Step 6: Click Sign In
        driver.findElement(By.id("idSIButton9")).click();

        // Step 7: Wait for DUO verification to complete (we wait for a known element after DUO)
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "3_before_verification");

        WebElement trustCheckbox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("trust-this-browser-label"))
        );
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "3_after_verification");

        // Step 8: Trust this browser
        driver.findElement(By.id("trust-browser-button")).click();

        // Step 9: Wait for the "Stay signed in?" page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idSIButton9")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "4_before_stay_signed_in");

        // Step 10: Click Yes
        driver.findElement(By.id("idSIButton9")).click();

        // Step 11: Wait for Dashboard
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "4_after_stay_signed_in");

        // Step 12: Login Assertion
        Assert.assertTrue(driver.getTitle().equals("Dashboard"), "Dashboard page does not load after login");
        test.pass("Verified Dashboard page loaded.");

        // Step 13: Navigate to the Calendar
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "5_before_navigate_to_calendar");
        driver.findElement(By.id("global_nav_calendar_link")).click();
        wait.until(ExpectedConditions.titleContains("Calendar"));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "5_after_navigate_to_calendar");

        // Step 14: Click "+" button
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "6_before_click_add_button");
        driver.findElement(By.id("create_new_event_link")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit_event")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "6_after_click_add_button");

        // Step 15: Click "My To Do" tab
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "7_before_click_my_to_do_tab");
        driver.findElement(By.id("ui-id-5")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("event-details-content")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "7_after_click_my_to_do_tab");

        // Step 16: Entering the title
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "8_before_entering_title");
        driver.findElement(By.id("planner_note_title")).sendKeys(title);

        WebElement titleInput = driver.findElement(By.id("planner_note_title"));
        Assert.assertEquals(titleInput.getAttribute("value"), title, "Title input mismatch");
        test.pass("Verified title input matches expected value");
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "8_after_entering_title");

        // Step 17: Entering the date
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "9_before_choosing_date");

        driver.findElement(By.id("planner_note_date")).clear();
        driver.findElement(By.id("planner_note_date")).sendKeys(date);

        WebElement dateInput = driver.findElement(By.id("planner_note_date"));
        Assert.assertEquals(dateInput.getAttribute("value"), date, "Date input mismatch");
        test.pass("Verified date input matches expected value");
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "9_after_choosing_date");

        // Step 18: Entering the time
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "10_before_entering_time");

        driver.findElement(By.id("planner_note_time")).sendKeys(time);

        WebElement timeInput = driver.findElement(By.id("planner_note_time"));
        Assert.assertEquals(timeInput.getAttribute("value"), time, "Time input mismatch");
        test.pass("Verified time input matches expected value");
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "10_after_entering_time");

        // Step 19: Picking the calendar
        driver.findElement(By.id("planner_note_context")).click();
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "11_before_choosing_calendar");

        WebElement dropdown = driver.findElement(By.id("planner_note_context"));
        Select select = new Select(dropdown);
        select.selectByVisibleText(calendar);

        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, calendar, "Calendar selection mismatch");
        test.pass("Calendar selection verified");
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "11_after_choosing_calendar");

        // Step 20: Entering the details
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "12_before_entering_details");
        driver.findElement(By.id("details_textarea")).sendKeys(details);
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "12_after_entering_details");

        // Step 21: Click "Submit" button
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "13_before_submit_todo");
        driver.findElement(By.cssSelector(".event_button.btn.btn-primary.save_note")).click();
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "13_after_submit_todo");

        test.info("Checking if new To Do item appears on calendar");

        // Assertion: check if the new todo item shows on the page
        // 1. Wait for the calendar view to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fc-view.fc-month-view")));
        ScreenshotUtil.takeScreenshot(driver, "Scenario2", "14_view_new_todo_on_calendar");

        // 2. Look for all todo titles
        List<WebElement> titleSpans = driver.findElements(By.cssSelector(".fc-title"));
        boolean found = false;

        for (WebElement el : titleSpans) {
            List<WebElement> children = el.findElements(By.tagName("span"));

            if (!children.isEmpty()) {
                String prefix = children.get(0).getText().trim(); // "To Do:"
                String fullText = el.getText().trim();            // "To Do: test"
                String actualTitle = fullText.replace(prefix, "").trim(); // "test"

                if (prefix.equals("To Do:") && actualTitle.equals(title)) {
                    found = true;
                    break;
                }
            }
        }

        Assert.assertTrue(found, "To Do with title '" + title + "' was not found.");

        if (found) {
            test.pass("<b>Expected:</b> To Do with title '" + title + "' should be on calendar");
            test.pass("<b>Actual:</b> To Do with title '" + title + "' found on calendar");
        } else {
            test.fail("<b>Expected:</b> To Do with title '" + title + "' should be on calendar");
            test.fail("<b>Actual:</b> Not found");
        }
    }
}
