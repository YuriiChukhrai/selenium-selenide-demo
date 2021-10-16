package com.yc.qa.test.selenide;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

import com.util.*;
import com.yc.qa.google.search.SearchPageImpl;
import com.yc.qa.indeed.pom.LandingPageImpl;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.codeborne.selenide.Selenide.page;

@Listeners(BaseListener.class)
public class TitleValidationAdvanceTest extends BaseTest {

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
	@Links({ @Link(name = "Link#1", url = "https://knowledge.bla.com/confluence"),
			@Link(name = "Link#2", url = "https://bitbucket.bla.com:8443/projects/bla") })
	@Lead("Yurii Chukhrai")
	@Flaky
	@Severity(SeverityLevel.NORMAL)
	@Description("Upload resume")
	@Owner("Yurii Chukhrai")
	@Test(enabled = true, groups = TestGroups.SEARCH, dataProvider = "dp")
	public void resumeTest01(final String site) {

		Allure.feature( BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP) );

		page(SearchPageImpl.class)
						.searchContent(site)
						.goToFirstLink(LandingPageImpl.class);
	}
}