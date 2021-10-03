package com.yc.qa.titles.test;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
import com.codeborne.selenide.Condition;

import com.util.BaseConfig;
import com.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static com.codeborne.selenide.Condition.ownText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;



public class TitleValidationEasyTest {

	@BeforeClass(alwaysRun = true)
	public final void beforeClass() {

		SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

		// or for fine-tuning:
		//SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(true) );

		/*
		* similarity to Factory design pattern
		 * */
		final String driverType = BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP);

		if(driverType == null || Constants.CHROME_SHORT.equalsIgnoreCase(driverType) || Constants.CHROME_LONG.equalsIgnoreCase(driverType)){
			WebDriverManager.chromedriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
		}
		else if(Constants.FIREFOX_SHORT.equalsIgnoreCase(driverType) || Constants.FIREFOX_LONG.equalsIgnoreCase(driverType)){
			WebDriverManager.firefoxdriver().driverVersion(BaseConfig.getProperty(Constants.DRIVER_VER_PROP)).setup();
		}
	}//beforeClass

	@DataProvider(parallel = false)
	public Object[][] dp() {
		//site, Expected Title
		return new Object[][] {
			    {"indeed", "Job Search | Indeed"},
			    {"ontada", "Oncology Insights & Technology | Ontada"},
			    {"blaBla", "Bla title"} // Implicitly will fail
		};
	}

    @Features({ @Feature("SEARCH"), @Feature("FILTER") })
	@Issues({ @Issue("GA-001"), @Issue("GTA-002") })
	@Stories({ @Story("Stories: CIR-098"), @Story("Stories: CIR-099") })
	@Epics({ @Epic("Epic03"), @Epic("Epic04") })
	@TmsLinks({ @TmsLink("1234"), @TmsLink("4321") })
	@Links({ @Link(name = "Link#1", url = "https://knowledge.bla.com/confluence"), @Link(name = "Link#2", url = "https://bitbucket.bla.com:8443/projects/bla") })
	@Lead("Yurii Chukhrai")
	@Flaky
	@Severity(SeverityLevel.NORMAL)
	@Description("Title assertion")
	@Owner("Yurii Chukhrai")
	@Test(priority = 0, enabled = true, groups = "DEFAULT", dataProvider = "dp")
	public void titleTest(final String site, final String title) {

       open("http://google.com");
        $(byName("q")).shouldBe(Condition.visible).sendKeys(site + Keys.ENTER);
		$$(byXpath(String.format("(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]", site))).first().shouldBe(Condition.visible).click();

		//$("title").shouldHave(attribute("text", title));

		$("title").shouldHave(ownText(title));

		//assertThat( $., describedAs(String.format("Expected title [%s].", title), containsString(title)));
		//Utils.makeScreenShot("Partial. Google page", driver);
		//$("#hplogo").shouldBe(Condition.visible);
	}
}

//		webDriverWait.until(ExpectedConditions.titleContains(title));
//
//		assertThat(driver.getTitle(), describedAs(String.format("Expected title [%s].", title), containsString(title)));
//		Utils.makeScreenShot(String.format("Partial. Site [%s], Title [%s].", site, title), driver);
//		Utils.makeScreenAsShot(String.format("Full. Site [%s], Title [%s].", site, title), true, driver);


