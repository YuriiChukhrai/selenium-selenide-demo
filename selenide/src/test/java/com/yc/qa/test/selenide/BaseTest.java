package com.yc.qa.test.selenide;

import com.codeborne.selenide.Configuration;
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
	public void beforeClass() {

        /*
         * Integration with Allure report (saveScreenshots and page)
         * */
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        /*
         * some kind of Factory design pattern
         * */
        final String driverType = BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP);
        Configuration.startMaximized = true;

        if(driverType == null || Constants.CHROME_SHORT.equalsIgnoreCase(driverType) || Constants.CHROME_LONG.equalsIgnoreCase(driverType)){
            WebDriverManager.chromedriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
        }
        else if(Constants.FIREFOX_SHORT.equalsIgnoreCase(driverType) || Constants.FIREFOX_LONG.equalsIgnoreCase(driverType)){
            WebDriverManager.firefoxdriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
        }
	}
}
