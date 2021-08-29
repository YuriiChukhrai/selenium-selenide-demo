package com.util;

import java.util.logging.Level;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import com.exception.UnknownTypeWebDriverExceptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public final class WebDriverFactory {

	private WebDriverFactory() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	public static synchronized WebDriver createDriver() {
		return WebDriverFactory.createDriver(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP),
				BaseConfig.getProperty(Constants.DRIVER_VER_PROP));
	}

	/**
	 * @param driverType    (CLO: -Ddriver.type=CHROME)
	 * @param driverVersion (CLO: -Ddriver.version=92.0.4515.107)
	 */
	public static synchronized WebDriver createDriver(final String driverType, final String driverVersion) {
		WebDriver driver = null;

		final LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.SEVERE);

		if (!BaseUtils.isEmpty(driverType) && !BaseUtils.isEmpty(driverVersion)) {

			switch (driverType.trim().toUpperCase()) {

			case Constants.FIREFOX_SHORT:
			case Constants.FIREFOX_LONG:

				log.info("Create Gecko WebDriver");
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setLogLevel(FirefoxDriverLogLevel.ERROR);
				//firefoxOptions.setCapability(FirefoxDriver.MARIONETTE, true);
				firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, logs);
				firefoxOptions.setCapability("handlesAlerts", true);
				firefoxOptions.setCapability("acceptInsecureCerts", true);

				firefoxOptions.setCapability("moz:webdriverClick", false);
				firefoxOptions.setCapability("pageLoadStrategy", "normal");
				firefoxOptions.setCapability("javascriptEnabled", true);
				firefoxOptions.setCapability("headless", false);

				WebDriverManager.firefoxdriver().driverVersion(driverVersion)
						.operatingSystem(BaseUtils.isOs("MAC") ? OperatingSystem.MAC : OperatingSystem.WIN).setup();

				driver = new FirefoxDriver(firefoxOptions);
				driver.manage().window().fullscreen();

				break;

			case Constants.CHROME_SHORT:
			case Constants.CHROME_LONG:
				log.info("Create Chrome WebDriver");
				ChromeOptions chromeOptions = new ChromeOptions();

				chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
				chromeOptions.setAcceptInsecureCerts(true);
				chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logs);

				chromeOptions.addArguments("start-maximized"); // Works for Windows but not for MAC
				chromeOptions.addArguments("start-fullscreen");// Works for MAC
				chromeOptions.addArguments("kiosk");// Do not works for LINUX

				chromeOptions.addArguments("verbose");
				chromeOptions.addArguments("--enable-logging --v=1");
				chromeOptions.addArguments("--enable-logging=stderr --v=1");

				chromeOptions.addArguments("no-sandbox");
				chromeOptions.addArguments("disable-infobars");
				chromeOptions.addArguments("ignore-certificate-errors");
				chromeOptions.addArguments("disable-notifications");

				chromeOptions.addArguments("lang=en");
				chromeOptions.addArguments("disable-web-security");

				//chromeOptions.addArguments("user-data-dir=/Users/yurii.chukhrai@mckesson.com/Library/Application Support/Google/Chrome/Default");

				WebDriverManager.chromedriver().driverVersion(driverVersion)
						.operatingSystem(BaseUtils.isOs("MAC") ? OperatingSystem.MAC : OperatingSystem.WIN).setup();
				driver = new ChromeDriver(chromeOptions);

				break;
				
				
				
				//TODO Add support of HTMLUNIT headless browser.

			default:
				throw new UnknownTypeWebDriverExceptions(String.format("Unknown driver type [%s]", driverType));
			}
		} else {
			throw new UnknownTypeWebDriverExceptions(
					String.format("Driver type/version was [null]. Type[%s]. Version[%s].", driverType, driverVersion));
		}

		return driver;
	}
}
