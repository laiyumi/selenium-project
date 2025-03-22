package com.example.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String getUsername() {
        return props.getProperty("account");
    }

    public static String getPassword() {
        return props.getProperty("password");
    }

    public static String getChromeDriverPath(){ return props.getProperty("chromedriver.path");
    }
}