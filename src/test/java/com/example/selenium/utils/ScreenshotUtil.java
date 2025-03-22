package com.example.selenium.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtil {
    public static void takeScreenshot(WebDriver driver, String folderName, String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get("screenshots/" + folderName));
            File destFile = new File("screenshots/" + folderName + "/" + fileName + ".png");
            Files.copy(srcFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
