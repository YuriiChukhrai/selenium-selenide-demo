package com.yc.qa.titles.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.util.BaseListener;
import com.util.ObjectSupplier;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yc.qa.BaseTest;
import com.yc.qa.google.search.SearchPageImpl;
import com.yc.qa.indeed.pom.LandingPageImpl;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Epics;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Flaky;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import io.qameta.allure.Lead;
import io.qameta.allure.Link;
import io.qameta.allure.Links;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Listeners(BaseListener.class)
public class TitleValidationAdvanceTest extends BaseTest {

	@DataProvider(parallel = true)
	public Iterator<String> dp() {
		// site, Expected Title
		return new ArrayList<String>(Arrays.asList("indeed", "ontada")).iterator();
	}

	@Features({ @Feature("SEARCH"), @Feature("FILTER") })
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
	@Test(enabled = true, groups = "SEARCH", dataProvider = "dp")
	public void resumeTest01(final String site) {
	    
		ObjectSupplier.$(SearchPageImpl.class)
						.searchContent(site)
						.goToFirstLink(LandingPageImpl.class);
	}
}
