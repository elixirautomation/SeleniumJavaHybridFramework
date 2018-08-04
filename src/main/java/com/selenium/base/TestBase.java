package com.selenium.base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    static Logger logger;

    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/selenium/config/config.properties");
            prop.load(inputStream);

            PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resource/log4j.properties");
            logger = Logger.getLogger(TestBase.class.getName());

        } catch (FileNotFoundException Ex) {
            logger.info("File not found: " + Ex.getMessage());

        } catch (IOException Ex) {
            logger.info("Exception occurred: " + Ex.getMessage());
        }
    }

    public static void initialization() throws MalformedURLException {
        String browserName = prop.getProperty("Browser");
        String environment = prop.getProperty("Environment");

        switch (environment) {

            case "local":
                switch (browserName) {
                    case "chrome":
                        logger.info("Starting tests on chrome browser.");
                        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//ExternalDrivers//chromedriver.exe");
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--start-maximized");
                        options.addArguments("--disable-extensions");
                        driver = new ChromeDriver(options);
                        break;

                    case "firefox":
                        logger.info("Starting tests on firefox browser.");
                        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//ExternalDrivers//geckodriver.exe");
                        driver = new FirefoxDriver();
                        break;

                    default:
                        logger.info("Browser not defined.");
                        break;
                }
                break;

            case "grid":
                switch (browserName) {
                    case "chrome":
                        logger.info("Starting tests on chrome browser.");
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--start-maximized");
                        options.addArguments("--disable-extensions");
                        options.setCapability("platform", "LINUX");
                        driver = new RemoteWebDriver(new URL(prop.getProperty("GridServer")), options);
                        logger.info("Setting Up Selenium Grid.");
                        break;

                    case "firefox":
                        logger.info("Starting tests on firefox browser.");
                        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//ExternalDrivers//geckodriver.exe");
                        driver = new FirefoxDriver();
                        break;

                    default:
                        logger.info("Browser not defined.");
                        break;
                }
                break;


            case "browser_stack":
                String username = prop.getProperty("bs_username");
                String automate_key = prop.getProperty("bs_key");
                String url = "http://" + username + ":" + automate_key + "@hub.browserstack.com:80/wd/hub";

                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("browser", prop.getProperty("browser"));
                caps.setCapability("browser_version", prop.getProperty("browser_version"));
                caps.setCapability("os", prop.getProperty("os"));
                caps.setCapability("os_version", prop.getProperty("os_version"));
                caps.setCapability("resolution", prop.getProperty("resolution"));
                caps.setCapability("project", prop.getProperty("project"));
                caps.setCapability("name", prop.getProperty("name"));
                caps.setCapability("browserstack.debug", prop.getProperty("browserstack.debug"));
                caps.setCapability("browserstack.networkLogs", prop.getProperty("browserstack.networkLogs"));

                driver = new RemoteWebDriver(new URL(url), caps);

                logger.info("Setting Up Browser Stack Grid.");
                break;

            default:
                logger.info("No environment defined.");
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(prop.getProperty("Timeout")), TimeUnit.SECONDS);
        driver.get(prop.getProperty("Url"));

        logger.info("Driver initialization completed.");
    }

}
