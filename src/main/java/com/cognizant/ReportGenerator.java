package com.cognizant;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportGenerator 
{
	Object object;
	ExtentTest test;
	
	public ReportGenerator()
	{
		ExtentReports extent = new ExtentReports();
		object=extent;
		ExtentSparkReporter sp=new ExtentSparkReporter("GeneratedReport.html");
		((ExtentReports) object).attachReporter(sp);
	}
	
	public void testName(String testName)
	{
		test = ((ExtentReports) object).createTest(testName);
	}
	
	public void logsInfo(String log)
	{
		test.log(Status.INFO,log);
	}
	
	public void flushReport() throws IOException
	{
		((ExtentReports) object).flush();
		 Desktop.getDesktop().browse(new File("GeneratedReport.html").toURI());
	}
	
}
