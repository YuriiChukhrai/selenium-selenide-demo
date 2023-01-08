package com.yc.qa.test.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.yc.qa.util.BaseConfig;
import com.yc.qa.util.Constants;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

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
        Configuration.timeout = 10_000L;


        Configuration.browser = BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP).toLowerCase();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP).toLowerCase());
        capabilities.setVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP));

        Configuration.browserCapabilities = capabilities;
	}
}
