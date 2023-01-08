package com.yc.qa.test.selenium;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;


import com.util.*;
import com.yc.qa.util.*;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
*
* @author limit (Yurii Chukhrai)
*/
public class TitleValidationEasyTest {

	public static WebDriver driver;
	private static WebDriverWait webDriverWait;
	private static Actions actions;
	private static Wait<WebDriver> wait;

	@BeforeClass
	public void beforeClass() {

		driver = WebDriverFactory.createInstance(DriverManagerType.valueOf(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP)), BaseConfig.getProperty(Constants.DRIVER_VER_PROP), null);

		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // Implicitly

		webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Explicitly
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);
		actions = new Actions(driver);

	}

	@AfterClass
	public void afterClass() {
		if (!BaseUtils.isWebDriverDead(driver)) {
			driver.quit();
		}
	}

	@DataProvider(parallel = false)
	public Object[][] dp() {
		//site, Expected Title
		return new Object[][] {
			{"indeed", "Job Search | Indeed"},
			{"ontada", "Oncology Insights & Technology | Ontada"},
			{"blaBla", "Bla title"} // Implicitly will fail
		};
	}
	
	@Features({ @Feature(TestGroups.DEFAULT), @Feature("FILTER") })
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
	@Test(priority = 0, enabled = true, groups = TestGroups.DEFAULT, dataProvider = "dp")
	public void titleTest(final String site, final String title) {

		//final String linkLocatorTemplate = "//div[@class='g']/link[contains(@href,'%1$s')]/following-sibling::*//a[contains(@href,'%1$s')]";
		final String linkLocatorTemplate = "(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]";
		Allure.feature(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP));

		driver.get("http://google.com");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys(site + Keys.ENTER);

		final By firstLinkLocator = By.xpath(String.format(linkLocatorTemplate, site));

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(firstLinkLocator));
		Utils.makeScreenShot("Partial. Google page", driver);

		driver.findElement(firstLinkLocator).click();
		webDriverWait.until(ExpectedConditions.titleContains(title));
		
		assertThat(driver.getTitle(), describedAs(String.format("Expected title [%s].", title), containsString(title)));
		Utils.makeScreenShot(String.format("Partial. Site [%s], Title [%s].", site, title), driver);
	}
}

/* Inner class. Just to keep all in one place. for demo. */
final class Utils {

	@Attachment(value = "{0}", type = "image/png")
	@Step("Partial. Make makeScreenshot [{0}]")
	public static byte[] makeScreenShot(String fileName, WebDriver webDriver) {
		return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
	}
}
