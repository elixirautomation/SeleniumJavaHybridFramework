package com.selenium.pages;

import com.selenium.base.TestBase;
import com.selenium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class MainPage extends TestBase {

    static Logger logger = Logger.getLogger(MainPage.class.getName());
    ReusableFunctions _reusableFunc = new ReusableFunctions();

    // Web App

    public final By user_message_input = By.xpath("//input[@id='user-message']");
    public final By show_message_button = By.xpath("//button[text()='Show Message']");
    public final By show_message_text = By.xpath("//div[@id='user-message']//following::span[@id='display']");
    public final By enter_first_value = By.xpath("//input[@id='sum1']");
    public final By enter_second_value = By.xpath("//input[@id='sum2']");
    public final By get_total_button = By.xpath("//button[text()='Get Total']");
    public final By show_total_text = By.xpath("//span[@id='displayvalue']");

    public boolean verifyMainScreenElements(WebDriver driver) {
        boolean flag = true;
        try{
            logger.info("Verifying Main Page Elements.");
            ArrayList<By> locators = new ArrayList<By>();

            locators.add(user_message_input);
            locators.add(show_message_button);
            locators.add(enter_first_value);
            locators.add(enter_second_value);
            locators.add(get_total_button);

            if (_reusableFunc.verifyElementsLocated(driver, locators)){
                logger.info("All Main Page Elements Displayed.");
            }else {
                flag = false;
                logger.error("Main Page Elements Not Displayed.");
            }

        }catch(Exception Ex){
            flag = false;
            logger.error("Exception Occurred While Verifying Main Page Elements: "+Ex.getMessage());
        }
        return flag;
    }

    public boolean verifyValidUserInput(WebDriver driver, String userInput){
        boolean flag = false;

        try {
            logger.info("Verifying Valid User Input.");

            _reusableFunc.enterText(driver, user_message_input, userInput);
            _reusableFunc.click(driver, show_message_button);
            String message = _reusableFunc.getTextByInnerText(driver, show_message_text);
            flag = ReusableFunctions.verifyTextMatch(driver, message, userInput);

        }catch(Exception Ex){
            logger.error("Exception Occurred While Verifying Valid User Input: "+Ex.getMessage());
        }
        return flag;
    }

    public boolean verifyAdditionFunctionality(WebDriver driver, String numA, String numB, String expected){
        boolean flag = false;

        try {
            logger.info("Verifying Addition Functionality.");

            _reusableFunc.enterText(driver, enter_first_value, numA);
            _reusableFunc.enterText(driver, enter_second_value, numB);
            _reusableFunc.click(driver, get_total_button);
            String addition = _reusableFunc.getTextByInnerText(driver, show_total_text);
           flag = ReusableFunctions.verifyTextMatch(driver, addition, expected);

        }catch(Exception Ex){
            logger.error("Exception Occurred While Verifying Addition Functionality: "+Ex.getMessage());
        }
        return flag;
    }

}
