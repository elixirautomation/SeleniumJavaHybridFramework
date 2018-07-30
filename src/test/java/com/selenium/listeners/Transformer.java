package com.selenium.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.selenium.base.TestBase;
import com.selenium.testdata.DataReader;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class Transformer implements IAnnotationTransformer {
    public static HashMap<String, HashMap<String, String>> runmodeData = DataReader.testDataMappedToTestName(TestBase.prop.getProperty("TestDataExcelFileName"), TestBase.prop.getProperty("RunModeSheetName"));

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
            annotation.setEnabled(DataReader.isRunnable(testMethod.getName(), runmodeData));
    }
}
