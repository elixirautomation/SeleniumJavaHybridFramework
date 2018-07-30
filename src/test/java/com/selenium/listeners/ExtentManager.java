package com.selenium.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {

    private static ExtentReports extent;
    private static ExtentTest logger;

    public ExtentManager(ExtentReports reports){
        extent = reports;
    }

    public synchronized static ExtentReports getReporter() throws IOException {
        if(extent == null){
            String workingDir = System.getProperty("user.dir");

            FileInputStream inputStream = new FileInputStream(new File(workingDir+"/src/test/resource/extent-config.xml"));
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter( workingDir+"/ExtentReports/ExtentReportResults"+System.getProperty("current.date")+".html");
            htmlReporter.loadConfig(inputStream);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Host Name", "Selenium Test Automation");
            extent.setSystemInfo("Environment", "QA Environment");
            extent.setSystemInfo("User Name", "Abhilash Sharma");

        }
        return extent;
    }

    public synchronized static ExtentTest getLogger(String testCaseName){
        try{
            logger = getReporter().createTest(testCaseName);
        }catch (Exception Ex){
            Ex.printStackTrace();
        }
        return logger;
    }
}
