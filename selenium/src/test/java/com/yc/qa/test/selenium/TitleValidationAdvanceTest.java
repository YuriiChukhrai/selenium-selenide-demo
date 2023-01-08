package com.yc.qa.test.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.util.WebDriverFactory;
import com.yc.qa.util.*;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yc.qa.google.search.SearchPageImpl;
import com.yc.qa.indeed.pom.LandingPageImpl;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Listeners(BaseListener.class)
public class TitleValidationAdvanceTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public final void setUpMethod() {
		final WebDriver wd = WebDriverFactory.createInstance(DriverManagerType.valueOf(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP)), BaseConfig.getProperty(Constants.DRIVER_VER_PROP), null);
		wd.manage().window().maximize();
		wd.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); //Implicitly

		ThreadStoreLocal.setWebDriver(wd);
	}


	@DataProvider(parallel = true)
	public Iterator<String> dp() {
		// site, Expected Title
		return new ArrayList<String>(Arrays.asList("indeed", "ontada")).iterator();
	}

	@Features({ @Feature(TestGroups.SEARCH), @Feature("FILTER") })
	@Issues({ @Issue("GA-001"), @Issue("GTA-002") })
	@Stories({ @Story("Stories: CIR-001"), @Story("Stories: CIR-002") })
	@Epics({ @Epic("Epic01"), @Epic("Epic02") })
	@TmsLinks({ @TmsLink("12345"), @TmsLink("54321") })
	@Links({ @Link(url="https://github.com/YuriiChukhrai/selenium-selenide-demo", name="GitHub"), @Link(url="https://www.linkedin.com/in/yurii-c-b55aa6174/", name="LinkedIn") })
	@Lead("Yurii Chukhrai")
	@Flaky
	@Severity(SeverityLevel.NORMAL)
	@Description("Upload resume")
	@Owner("Yurii Chukhrai")
	@Test(enabled = true, groups = TestGroups.SEARCH, dataProvider = "dp")
	public void resumeTest01(final String site) {

		Allure.feature( BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP) );

		ObjectSupplier.$(SearchPageImpl.class)
						.searchContent(site)
						.goToFirstLink(LandingPageImpl.class);
	}
}
