package com.selenium.util;

import org.apache.commons.io.FileUtils;
import com.selenium.base.TestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReusableFunctions extends TestBase {

    static Logger logger = Logger.getLogger(ReusableFunctions.class.getName());

    private int timeout;

    public int timeoutValue(){
        try{
           timeout = Integer.parseInt(prop.getProperty("Timeout"));
        }catch (Exception Ex) {
            logger.error("Exception Occurred While Getting Timeout Property.");
        }
        return timeout;
    }

    public boolean waitForElementPresent(WebDriver driver, By locator){
        WebDriverWait wait = new WebDriverWait(driver, timeoutValue());
        boolean flag = true;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch(Exception Ex) {
            flag = false;
            logger.error("Exception Occurred While Locating The Element: " + Ex.getMessage());
        }
        return flag;
    }

    public boolean waitForElementPresent(WebDriver driver, WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, timeoutValue());
        boolean flag = true;
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception Ex) {
            flag = false;
            logger.error("Exception Occurred While Locating The Element: " + Ex.getMessage());
        }
        return flag;
    }

    public boolean verifyElementsLocated(WebDriver driver, ArrayList<By> arrayList){
        boolean flag = true;
        try{
            for (By locator : arrayList){
                WebElement element = driver.findElement(locator);
                if (waitForElementPresent(driver, element)){
                    logger.info(element.toString()+": element is displayed.");
                }else{
                    logger.error(element.toString()+": element isn't displayed.");
                    flag=false;
                }
            }
        }catch (Exception Ex){
            logger.error("Exception Occurred While Locating The Elements: " + Ex.getMessage());
        }
        return flag;
    }

    public boolean waitForElementClickable(WebDriver driver, By locator){
        WebDriverWait wait = new WebDriverWait(driver, timeoutValue());
        boolean flag = true;
        try{
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch(Exception Ex) {
            flag = false;
            logger.error("Exception Occurred While Locating The Element: " + Ex.getMessage());
        }
        return flag;
    }

    public void enterText(WebDriver driver, By locator, String value){
        try{
            if (waitForElementClickable(driver, locator)) {
                WebElement element = driver.findElement(locator);
                element.sendKeys(value);
            }
        }catch(Exception Ex) {
            logger.error("Exception Occurred While Entering The Text: " + Ex.getMessage());
        }

    }

    public void click(WebDriver driver, By locator){
        try{
            if (waitForElementClickable(driver, locator)){
                WebElement element = driver.findElement(locator);
                element.click();
            }
        }catch(Exception Ex) {
            logger.error("Exception Occurred While Clicking The Element: " + Ex.getMessage());
        }

    }

    public String getTextByAttributeValue(WebDriver driver, By locator){
        String text = null;
        try{
            if (waitForElementClickable(driver, locator)) {
                WebElement element = driver.findElement(locator);
                text = element.getAttribute("value");
            }
        }catch(Exception Ex) {
            logger.error("Exception Occurred While Getting The Text: " + Ex.getMessage());
        }
        return text;
    }

    public String getTextByInnerText(WebDriver driver, By locator){
        String text = null;
        try{
            if (waitForElementClickable(driver, locator)) {
                WebElement element = driver.findElement(locator);
                text = element.getText();
            }
        }catch(Exception Ex) {
            logger.error("Exception Occurred While Getting The Text: " + Ex.getMessage());
        }
        return text;
    }

    public static String takeScreenShot(WebDriver driver, String screenshotName) {
        String destination = null;
        try{
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            destination = System.getProperty("user.dir") + "/Screenshots/"+screenshotName+dateName+".png";
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);

            destination = finalDestination.getAbsolutePath();
            logger.info("Saving screenshot to failed repo: " + destination);

        }catch (Exception Ex){
            logger.error("Exception Occurred While Getting The Text: " + Ex.getMessage());
        }
        return destination;
    }


    public static boolean verifyTextMatch(WebDriver driver, String actualText, String expectedText){
        boolean flag = false;
        try {
            logger.info("Actual Text From Application Web UI --> :: " + actualText);
            logger.info("Expected Text From Application Web UI --> :: " + expectedText);

            if(actualText.equals(expectedText)){
                logger.info("### VERIFICATION TEXT MATCHED !!!");
                flag = true;
            }else{
                logger.error("### VERIFICATION TEXT DOES NOT MATCHED !!!");
            }

        }catch (Exception Ex){
            logger.error("Exception Occurred While Verifying The Text Match: " + Ex.getMessage());
        }
        return flag;
    }
}

