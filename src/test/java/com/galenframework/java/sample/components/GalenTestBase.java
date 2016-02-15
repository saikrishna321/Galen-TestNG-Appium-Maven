package com.galenframework.java.sample.components;

import com.galenframework.testng.GalenTestNgTestBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class GalenTestBase extends GalenTestNgTestBase {

	private static final String ENV_URL = "http://testapp.galenframework.com";
	AppiumDriverLocalService appiumDriverLocalService;
	@Override
	public WebDriver createDriver(Object[] args) {

		TestDevice device = (TestDevice) args[0];
		if (device.name == "android") {
			try {
				setUp();
			} catch (ExecuteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AndroidDriver<WebElement> driver = null;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "10.4.20.62:5555");
			capabilities.setCapability("platformVersion", "5.0.1");
			capabilities.setCapability("browserName", "Chrome");
			driver = new AndroidDriver<WebElement>(appiumDriverLocalService.getUrl(), capabilities);
			return driver;
		} else if (device.name == "ios") {
			try {
				setUp();
			} catch (ExecuteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			IOSDriver<WebElement> driver = null;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "iPhone 6");
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("platformVersion", "8.4");
			capabilities.setCapability("browserName", "safari");
			driver = new IOSDriver<WebElement>(appiumDriverLocalService.getUrl(), capabilities);
			return driver;
		}
		{
			WebDriver driver = new FirefoxDriver();
			if (args.length > 0) {
				if (args[0] != null && args[0] instanceof TestDevice) {

					if (device.getScreenSize() != null) {
						driver.manage().window().setSize(device.getScreenSize());
					}
				}
			}
			return driver;
		}
	}

	public void load(String uri) {
		getDriver().get(ENV_URL + uri);
	}

	@DataProvider(name = "devices")
	public Object[][] devices() {
		return new Object[][] {{ new TestDevice("ios", null, asList("ios")) },
				{ new TestDevice("android", null, asList("android")) },
				{ new TestDevice("tablet", new Dimension(750, 800), asList("tablet")) },
				{ new TestDevice("desktop", new Dimension(1024, 800), asList("desktop")) } };
	}


	@Override
	@AfterMethod
	public void quitDriver() {
		System.out.println("Driver Quit");
		getDriver().quit();
		appiumDriverLocalService.stop();
	}

	public static class TestDevice {
		private final String name;
		private final Dimension screenSize;
		private final List<String> tags;

		public TestDevice(String name, Dimension screenSize, List<String> tags) {
			this.name = name;
			this.screenSize = screenSize;
			this.tags = tags;
		}

		public String getName() {
			return name;
		}

		public Dimension getScreenSize() {
			return screenSize;
		}

		public List<String> getTags() {
			return tags;
		}
	}

	public void setUp() throws ExecuteException, IOException, InterruptedException {
		AppiumServiceBuilder builder = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/bin/appium.js"))
				.withArgument(GeneralServerFlag.LOG_LEVEL, "info").usingAnyFreePort(); /*and so on*/;
		appiumDriverLocalService = builder.build();
		appiumDriverLocalService.start();

	}

	public void tearDown() throws IOException {
		String[] command = { "/usr/bin/killall", "-KILL", "node" };
		Runtime.getRuntime().exec(command);
		System.out.println("Appium server stop");
	}
}
