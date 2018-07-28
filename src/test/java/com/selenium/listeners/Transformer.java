package com.selenium.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.selenium.base.TestBase;
import com.selenium.testdata.DataReader;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class Transformer implements IAnnotationTransformer {
    public static DataReader reader = new DataReader();
    public static TestBase testBase = new TestBase();
    public static HashMap<String, HashMap<String, String>> runmodeData = reader.testDataMappedToTestName(testBase.prop.getProperty("TestDataExcelFileName"), testBase.prop.getProperty("RunModeSheetName"));

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (DataReader.isRunnable(testMethod.getName(), runmodeData)) {
            annotation.setEnabled(true);
        }else if (! DataReader.isRunnable(testMethod.getName(), runmodeData)) {
            annotation.setEnabled(false);
        }
    }
}
