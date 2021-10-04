package com.yc.qa;

//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Listeners;

//import com.util.BaseListener;
//import com.util.ThreadStoreLocal;
//import com.util.WebDriverFactory;


import com.codeborne.selenide.logevents.SelenideLogger;
import com.util.BaseConfig;
import com.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

//@Listeners(BaseListener.class)
public class BaseTest {

	@BeforeClass(alwaysRun = true)
	public final void beforeClass() {

        /*
         * Integration with Allure report (saveScreenshots and page)
         * */
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        /*
         * some kind of Factory design pattern
         * */
        final String driverType = BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP);

        if(driverType == null || Constants.CHROME_SHORT.equalsIgnoreCase(driverType) || Constants.CHROME_LONG.equalsIgnoreCase(driverType)){
            WebDriverManager.chromedriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
        }
        else if(Constants.FIREFOX_SHORT.equalsIgnoreCase(driverType) || Constants.FIREFOX_LONG.equalsIgnoreCase(driverType)){
            WebDriverManager.firefoxdriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
        }
	}

//	@AfterClass(alwaysRun = true)
//	public final void afterClass() {
//
//		if (ThreadStoreLocal.getWebDriver() != null) {
//			ThreadStoreLocal.getWebDriver().quit();
//		}
//		ThreadStoreLocal.getWebDriverContainer().remove();
//	}
//
//	@BeforeMethod(alwaysRun = true)
//	public final void setUpMethod() {
//		ThreadStoreLocal.setWebDriver(WebDriverFactory.createDriver());
//	}
//
//	@AfterMethod(alwaysRun = true)
//	public final void tearDownMethod() {
//
//		if (ThreadStoreLocal.getWebDriver() != null) {
//			ThreadStoreLocal.getWebDriver().quit();
//		}
//		ThreadStoreLocal.getWebDriverContainer().remove();
//	}
//
}
