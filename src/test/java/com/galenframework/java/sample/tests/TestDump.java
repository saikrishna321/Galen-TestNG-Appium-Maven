package com.galenframework.java.sample.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.galenframework.api.Galen;

public class TestDump{

WebDriver driver = null;
	

	@BeforeMethod
	public void setBrowser(){
		driver = new FirefoxDriver();
		driver.get("http://www.google.com");
	}
	@Test
	public void contactPageShouldLookGood_device() throws IOException {
		Galen.dumpPage(driver, "HomePage", "/specs/google.spec", 
				"/Users/saikrisv/Downloads/galen-sample-java-tests-master_2/src/test/resources/contactPage-dump");
		
	}
	
	@AfterMethod
	public void quite(){
		driver.close();
		driver.quit();
	}


}
