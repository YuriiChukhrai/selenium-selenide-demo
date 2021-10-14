package com.yc.qa.test.selenium;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.util.*;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

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

		driver = WebDriverFactory.createDriver();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // Implicitly

		webDriverWait = new WebDriverWait(driver, 15); // Explicitly
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
	@Links({ @Link(name = "Link#1", url = "https://knowledge.bla.com/confluence"), @Link(name = "Link#2", url = "https://bitbucket.bla.com:8443/projects/bla") })
	@Lead("Yurii Chukhrai")
	@Flaky
	@Severity(SeverityLevel.NORMAL)
	@Description("Title assertion")
	@Owner("Yurii Chukhrai")
	@Test(priority = 0, enabled = true, groups = TestGroups.DEFAULT, dataProvider = "dp")
	public void titleTest(final String site, final String title) {

		Allure.feature(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP));

		driver.get("http://googl.com");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys(site + Keys.ENTER);

		By links = By.xpath(String.format("(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]", site));

		webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(links));
		Utils.makeScreenShot("Partial. Google page", driver);
		Utils.makeScreenAsShot("Full. Google page", true, driver);

		driver.findElements(links).stream().findFirst().get().click();
		webDriverWait.until(ExpectedConditions.titleContains(title));
		
		assertThat(driver.getTitle(), describedAs(String.format("Expected title [%s].", title), containsString(title)));
		Utils.makeScreenShot(String.format("Partial. Site [%s], Title [%s].", site, title), driver);
		Utils.makeScreenAsShot(String.format("Full. Site [%s], Title [%s].", site, title), true, driver);
	}
}

/* Inner class. Just to keep all in one place. for demo. */
final class Utils {

	@Attachment(value = "{0}", type = "image/png")
	@Step("Partial. Make makeScreenshot [{0}]")
	public static byte[] makeScreenShot(String fileName, WebDriver webDriver) {
		byte[] rawImage = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);

		return rawImage;
	}

	/* Implement AsHoT - Screenshot with scrolling */
	@Attachment(value = "{0}", type = "image/png")
	@Step("Full [{1}]. Make makeScreenshot [{0}]")
	public static byte[] makeScreenAsShot(String fileNames, boolean fullPage, final WebDriver webDriver) {

		int scrollTimeout = 500;
		int headerToCut = 2;
		int footerToCut = 0;
		float dpr = 2.0F;
		ShootingStrategy shootingStrategy;

		if (fullPage) {
			// Scroll to top of page
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 10)");

			shootingStrategy = isOs("MAC")
					? ShootingStrategies.viewportRetina(scrollTimeout, headerToCut, footerToCut, dpr)
					: ShootingStrategies.viewportNonRetina(scrollTimeout, headerToCut, footerToCut);
		} else {
			shootingStrategy = ShootingStrategies.simple();
		}

		final Screenshot screenShot = new AShot().shootingStrategy(shootingStrategy).takeScreenshot(webDriver);

		if (fullPage) {
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 10)");
		}

		byte[] imageInByte = null;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			ImageIO.write(screenShot.getImage(), "PNG", baos); // Does not work for "JPG"
			baos.flush();
			imageInByte = baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageInByte;
	}

	/* Highlight elements on the page */
	@Step("Highlight HTML element [{1}]")
	public static WebElement highlight(final WebDriver driver, final WebElement element) {

		if (element.isDisplayed()) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor = 'rgb(0,200,0)'", element);
		}

		return element;
	}

	@Step("Check the expected OS [{0}]")
	public static boolean isOs(final String osNameExpected) {
		final String osNameActual = System.getProperty("os.name").trim().toUpperCase();

		return osNameActual.contains(osNameExpected.toUpperCase());
	}
}
