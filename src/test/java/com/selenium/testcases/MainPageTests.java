package com.selenium.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.selenium.base.TestBase;
import com.selenium.listeners.ExtentManager;
import com.selenium.pages.MainPage;
import com.selenium.testdata.DataReader;
import com.selenium.util.ReusableFunctions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainPageTests extends TestBase {

    public static MainPage _mainPage;
    public static SoftAssert _softAssert;

    public static String testCaseName = null;
    public static ExtentTest logger = null;
    public static ExtentManager extManager = null;
    public static ExtentReports extent = null;

    public static HashMap<String, HashMap<String, String>> tcData = DataReader.testDataMappedToTestName(prop.getProperty("TestDataExcelFileName"), prop.getProperty("TestDataSheetName"));

    public MainPageTests(){
        super();
    }

    @BeforeMethod
    public static void setUp(Method method) throws IOException {
        initialization();
        _mainPage = new MainPage();
        _softAssert = new SoftAssert();

        testCaseName = method.getName();
        extManager = new ExtentManager(extent);
        extent = ExtentManager.getReporter();
        logger = ExtentManager.getLogger(testCaseName);
    }

    @Test(priority = 1)
    public void verifyMainScreenElements() {

        if (!_mainPage.verifyMainScreenElements(driver)) {
            _softAssert.fail("Element not present on the page");
        }
        _softAssert.assertAll();
    }

    @Test(priority = 2)
    public void verifyValidUserInput() {
        String userInput = tcData.get(testCaseName).get("Text_Message");

        if (!_mainPage.verifyValidUserInput(driver, userInput)) {
            _softAssert.fail("Not a valid user input: " + userInput);
        }
        _softAssert.assertAll();
    }

    @Test(priority = 3)
    public void verifyValidAddition() {
        String numA = tcData.get(testCaseName).get("Number_A");
        String numB = tcData.get(testCaseName).get("Number_B");
        String expected = tcData.get(testCaseName).get("Expected");

        if (!_mainPage.verifyAdditionFunctionality(driver, numA, numB, expected)) {
            _softAssert.fail("Not a valid addition: " + expected);
        }
        _softAssert.assertAll();
    }

    @Test(priority = 4)
    public void verifyInvalidAddition() {
        String numA = tcData.get(testCaseName).get("Number_A");
        String numB = tcData.get(testCaseName).get("Number_B");
        String expected = tcData.get(testCaseName).get("Expected");

        if (!_mainPage.verifyAdditionFunctionality(driver, numA, numB, expected)) {
            _softAssert.fail("Not a valid addition: " + expected);
        }
        _softAssert.assertAll();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception{

        if(result.getStatus() == ITestResult.FAILURE){
            String screenShotPath = ReusableFunctions.takeScreenShot(driver, "SeleniumTestScreen");
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            logger.fail(result.getThrowable());
            logger.fail("Snapshot below: " + logger.addScreenCaptureFromPath(screenShotPath, testCaseName));

        }else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test case SKIPPED due to below issues:", ExtentColor.GREY));
            logger.skip(result.getThrowable());

        }else if(result.getStatus() == ITestResult.SUCCESS){
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test case PASSED.", ExtentColor.GREEN));
        }

        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.close();
        }
    }
    @AfterTest
    public void tearDown(){
        extent.flush();
    }
}
