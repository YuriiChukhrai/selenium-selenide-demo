package com.yc.qa.test.selenide;

/**
 * @author limit (Yurii Chukhrai)
 */
import com.codeborne.selenide.Condition;

import com.yc.qa.util.BaseConfig;
import com.yc.qa.util.Constants;
import com.yc.qa.util.TestGroups;
import io.qameta.allure.*;

import org.openqa.selenium.Keys;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.ownText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class TitleValidationEasyTest extends BaseTest {

	@DataProvider(parallel = false)
	public Object[][] dp() {
		//site, Expected Title
		return new Object[][] {
			    {"indeed", "Job Search | Indeed"},
			    {"ontada", "Oncology Insights & Technology | Ontada"},
			    {"blaBla", "Bla title"} // Implicitly will fail
		};
	}

    @Features({ @Feature(TestGroups.SEARCH), @Feature("FILTER") })
	@Issues({ @Issue("GA-001"), @Issue("GTA-002") })
	@Stories({ @Story("Stories: CIR-098"), @Story("Stories: CIR-099") })
	@Epics({ @Epic("Epic03"), @Epic("Epic04") })
	@TmsLinks({ @TmsLink("1234"), @TmsLink("4321") })
	@Links({ @Link(url="https://github.com/YuriiChukhrai/selenium-selenide-demo", name="GitHub"), @Link(url="https://www.linkedin.com/in/yurii-c-b55aa6174/", name="LinkedIn") })
	@Lead("Yurii Chukhrai")
	@Flaky
	@Severity(SeverityLevel.NORMAL)
	@Description("Title assertion")
	@Owner("Yurii Chukhrai")
	@Test(priority = 1, enabled = true, groups = TestGroups.DEFAULT, dataProvider = "dp")
	public void titleTest(final String site, final String title) {

		Allure.feature(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP));

        open("https://google.com");
        $(byName("q")).shouldBe(Condition.visible).sendKeys(site + Keys.ENTER);
		$$(byXpath(String.format("(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]", site)))
				.first()
				.shouldBe(Condition.visible)
				.click();

		$("title").shouldHave(ownText(title));
	}
}
