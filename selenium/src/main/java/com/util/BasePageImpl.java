package com.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.yc.qa.util.BasePage;
import com.yc.qa.util.BaseUtils;
import com.yc.qa.util.ThreadStoreLocal;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public class BasePageImpl extends BasePage {

	private EventFiringWebDriver eventFiringWebDriver;
	private final WebDriverWait wait;
	private final Actions actions;
	private final WebDriverEventListener webDriverEventListener;
	private final JavascriptExecutor javascriptExecutor;

	protected BasePageImpl(WebDriverEventListener webDriverEventListener) {

		this.webDriverEventListener = webDriverEventListener;

		eventFiringWebDriver = new EventFiringWebDriver(ThreadStoreLocal.getWebDriver());
		eventFiringWebDriver.register(this.webDriverEventListener);
		wait = new WebDriverWait(eventFiringWebDriver, Duration.ofSeconds(30));
		actions = new Actions(eventFiringWebDriver);
		javascriptExecutor = eventFiringWebDriver;
	}

	protected BasePageImpl() {

		this.webDriverEventListener = new BasePageWebDriverEventListenerHandler();

		eventFiringWebDriver = new EventFiringWebDriver(ThreadStoreLocal.getWebDriver());
		eventFiringWebDriver.register(this.webDriverEventListener);
		wait = new WebDriverWait(eventFiringWebDriver, Duration.ofSeconds(30));
		actions = new Actions(eventFiringWebDriver);
		javascriptExecutor = eventFiringWebDriver;
	}

	// --------

	@Override
	@Step("Looking for element [{0}]")
	protected WebElement findElementImplicit(final By locator) {
		return eventFiringWebDriver.findElement(locator);
	}

	@Override
	@Step("Looking for element [{0}]")
	protected WebElement findElement(final By locator) {

		log.info(String.format("TID [%d] - Try to find WebElement [%s].", Thread.currentThread().getId(),
				locator.toString()));

		return highlight(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
	}

	@Override
	@Step("Looking for elementS [{0}]")
	protected List<WebElement> findElements(final By locator) {

		List<WebElement> webElements = eventFiringWebDriver.findElements(locator);
		log.info(String.format("TID [%d] - Was found [%d]. Locator [%s].", Thread.currentThread().getId(),
				webElements.size(), locator.toString()));

		return webElements;
	}

	@Override
	@Step("Send Key [{0}]")
	protected BasePageImpl sendKeys(Keys keys) {
		actions.sendKeys(keys).build().perform();
		return this;
	}

	@Override
	@Step("Send Key [{1}] for locator [{0}]")
	protected BasePageImpl sendKeys(By locator, CharSequence keysToSend) {
		findElement(locator).sendKeys(keysToSend);
		return this;
	}

	@Override
	@Step("Open URI [{0}]")
	protected BasePageImpl openURL(final String url) {
		log.info(String.format("TID [%d] - Open URL [%s].", Thread.currentThread().getId(), url));
		eventFiringWebDriver.get(url);
		return this;
	}

	@Override
	@Step("Refresf page")
	protected BasePageImpl refreshPage() {
		eventFiringWebDriver.navigate().refresh();
		log.info(String.format("TID [%d] - Page URL [%s] was refreshed.", Thread.currentThread().getId(),
				eventFiringWebDriver.getCurrentUrl()));
		return this;
	}

	@Override
	@Step("Check if the element  [{0}] displayed")
	protected boolean isElementDisplayed(final By locator) {
		return eventFiringWebDriver.findElement(locator).isDisplayed();
	}

	/* probably do not work */

	@Override
	@Step("Action, move to element [{0}]")
	protected Actions actionMoveToElement(final By locator) {
		log.info(String.format("TID [%d] - Actions [%s].", Thread.currentThread().getId(), locator.toString()));
		return actions.moveToElement(findElement(locator));
	}

	@Override
	@Step("Send text [{1}] to field: [{0}]")
	protected BasePageImpl sendKeysCustom(final By locator, final CharSequence text) {

		if (text != null && !text.equals("null" + Keys.TAB)) {

			WebElement webElement = highlight(wait.until(ExpectedConditions.elementToBeClickable(locator)));
			webElement.clear();
			webElement.sendKeys(text);

			log.info(String.format("TID [%d] - Set text [%s] in field [%s].", Thread.currentThread().getId(), text,
					locator.toString()));
		} else {
			log.warn(
					String.format("TID [%d] - Can't provide enter of the text in the field, Input parameter was empty.",
							Thread.currentThread().getId()));
		}
		return this;
	}

	@Override
	@Step("Wait to the element will be click, selector [{0}]")
	protected BasePageImpl click(final By locator) {

		log.info(String.format("TID [%d] - Click on the WebElement [%s].", Thread.currentThread().getId(),
				locator.toString()));
		highlight(wait.until(ExpectedConditions.elementToBeClickable(locator))).click();
		return this;
	}

	@Override
	@Step("Wait to the element will be clickable, selector [{0}]")
	protected BasePageImpl click(final WebElement webElement) {

		log.info(String.format("TID [%s] - Click on the WebElement [%s].", Thread.currentThread().getId(),
				webElement.toString()));
		webElement.click();
		return this;
	}

	@Override
	@Step("Wait to the element will be clickable, selector [{0}]")
	protected BasePageImpl clickLocatorJs(final By locator) {

		log.info(String.format("TID [%s] - Click on the Locator [%s].", Thread.currentThread().getId(),
				locator.toString()));

		clickJavaScript(eventFiringWebDriver.findElement(locator));
		return this;
	}

	@Override
	@Step("Get text from element [{0}]")
	protected String getText(final By locator) {

		String textFromSelector = findElement(locator).getText().trim();
		log.info(String.format("TID [%d] - Get text from WebElement [%s]. Text [%s].", Thread.currentThread().getId(),
				locator.toString(), textFromSelector));

		return textFromSelector;
	}

	@Override
	@Step("Click on the button [{0}]")
	protected BasePageImpl setRadioSelected(final By locator) {
		log.info(
				String.format("TID [%d] - Set Radio Button [%s].", Thread.currentThread().getId(), locator.toString()));
		
		findElement(locator).click();
		return this;
	}

	@Override
	@Step("Select visible text [{1}] from drop down list [{0}]")
	protected BasePageImpl selectVisibleTextFromDropDownListSelect(final By locator, final String text) {
		log.info(String.format("TID [%d] - Select Visible Text [%s].", Thread.currentThread().getId(),
				locator.toString()));
		
		new Select(findElement(locator)).selectByVisibleText(text);
		return this;
	}

	@Override
	@Step("Select value [{1}] from drop down list [{0}]")
	protected BasePageImpl selectValueFromDropDownListSelect(final By locator, final String value) {
		log.info(String.format("TID [%d] - Select Value [%s].", Thread.currentThread().getId(), locator.toString()));
		new Select(findElement(locator)).selectByValue(value);
		return this;
	}

	@Override
	@Step("Scroll page to element [{0}]")
	protected BasePageImpl scrollToWebElement(final By locator) {
		log.info(String.format("TID [%d] - Scroll to WebElement [%s].", Thread.currentThread().getId(),
				locator.toString()));

		javascriptExecutor.executeScript("arguments[0].scrollIntoView();", findElementImplicit(locator));
		return this;
	}

	@Override
	@Step("Scroll page to Web Element [{0}]")
	protected BasePageImpl scrollToWebElement(final WebElement webElement) {
		log.info(String.format("TID [%d] - Scroll to WebElement [%s].", Thread.currentThread().getId(),
				webElement.toString()));

		javascriptExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
		return this;
	}

	@Override
	@Step("Scroll to bottom of page")
	protected BasePageImpl scrollToBottomPage() {
		log.info(String.format("TID [%d] - Scroll to Bottom Page.", Thread.currentThread().getId()));

		javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		return this;
	}

	@Override
	@Step("Scroll to next View")
	protected BasePageImpl scrollToNextView() {
		log.info(String.format("TID [%d] - Scroll to next View.", Thread.currentThread().getId()));

		javascriptExecutor.executeScript("window.scrollBy(0,150);");
		return this;
	}

	@Override
	@Step("Scroll to previous View")
	protected BasePageImpl scrollToPreviousView() {
		log.info(String.format("TID [%d] - Scroll to previous View.", Thread.currentThread().getId()));

		javascriptExecutor.executeScript("window.scrollBy(50,0);");
		return this;
	}

	@Override
	@Step("Open link in new tab [{0}]")
	protected BasePageImpl openLinkTab(String link) {
		log.info(String.format("TID [%d] - Open link [%s] in new tab. Home Handle [%s]", Thread.currentThread().getId(),
				link, eventFiringWebDriver.getWindowHandle()));

		javascriptExecutor.executeScript("window.open('" + link + "', '_blank');");
		return this;
	}

	@Override
	@Step("Set up zoom [{0}] on the page")
	protected BasePageImpl zoomPage(int zoomExpected) {
		log.info(String.format("TID [%d] - Zoom Page [%d] %%.", Thread.currentThread().getId(), zoomExpected));

		javascriptExecutor.executeScript("document.body.style.zoom='" + zoomExpected + "%'");
		return this;
	}

	/* This method scroll page to bottom */
	@Override
	@Step("Scroll to top of page")
	protected BasePageImpl scrollToTopPage() {
		log.info(String.format("TID [%d] - Scroll to Top of Page.", Thread.currentThread().getId()));

		javascriptExecutor.executeScript("window.scrollTo(0, 10)");
		return this;
	}

	@Override
	@Step("Wait of title [{0}]")
	protected String getTitle(final String expectedTitle) {
		wait.until(ExpectedConditions.titleIs(expectedTitle));
		log.info(String.format("TID [%d] - Get title from page [%s]. Expected title [%s].",
				Thread.currentThread().getId(), eventFiringWebDriver.getCurrentUrl(), expectedTitle));

		return eventFiringWebDriver.getTitle();
	}

	@Override
	@Step("Get title of page")
	protected String getTitle() {
		log.info(String.format("TID [%d] - Get title from page [%s].", Thread.currentThread().getId(),
				eventFiringWebDriver.getCurrentUrl()));

		return eventFiringWebDriver.getTitle();
	}

	@Override
	@Step("Highlight element [{0}] on the page")
	protected WebElement highlight(final WebElement webElement) {

		String RGB_HIGHLIGHT_COLOR = "rgb(0,200,0)";

		if (webElement.isDisplayed()) {
			javascriptExecutor.executeScript("arguments[0].style.backgroundColor = '" + RGB_HIGHLIGHT_COLOR + "'",
					webElement);
		}

		return webElement;
	}

	@Override
	@Step("Set value [{1}] for HTML element attribute [{0}]")
	protected BasePageImpl setAttributeValue(final By locator, final String value) {

		javascriptExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", eventFiringWebDriver.findElement(locator));
		return this;
	}

	@Override
	@Step("Set value [{1}] for HTML element attribute [{0}]")
	protected BasePageImpl removeAttribute(final By locator, final String attributeName, final String attributeValue) {

		javascriptExecutor.executeScript(
				"arguments[0].removeAttribute('" + attributeName + "','" + attributeValue + "')", findElement(locator));
		return this;
	}

	@Override
	@Step("Set style [{1}] for HTML element [{0}]")
	protected BasePageImpl setStyleValue(final By locator, final String value) {

		javascriptExecutor.executeScript("arguments[0].setAttribute('style', '" + value + "')",
				eventFiringWebDriver.findElement(locator));
		log.info(String.format("TID [%d] - Set style value [%s] for element [%s].", Thread.currentThread().getId(),
				value, locator.toString()));
		return this;
	}

	@Override
	@Step("Check present the text [{0}] in the SRC")
	protected boolean isTextPresent(final String text) {
		boolean isTextFound = false;

		if (eventFiringWebDriver.getPageSource().contains(text)) {
			isTextFound = true;
			log.info(String.format("TID [%d] - The text [%s] is present on the page [%s].",
					Thread.currentThread().getId(), text, eventFiringWebDriver.getCurrentUrl()));
		}
		return isTextFound;
	}

	@Override
	@Step("Get source code of the page")
	protected String getSRC() {
		log.info(String.format("TID [%d] - Get 'src' of page [%s].", Thread.currentThread().getId(),
				eventFiringWebDriver.getCurrentUrl()));

		return eventFiringWebDriver.getPageSource();
		// org.apache.commons.text.StringEscapeUtils.escapeJava(driver.getPageSource());
	}

	@Override
	@Step("Handled Alert Exception. Ok")
	protected String alertAccept() {
		String msg = null;

		try {
			msg = eventFiringWebDriver.switchTo().alert().getText();
			log.info(String.format("TID [%d] - Handled Alert exception. Accept. MSG [%s].",
					Thread.currentThread().getId(), msg));

			eventFiringWebDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			log.warn(String.format("TID [%d] - Alert was not present.", Thread.currentThread().getId()));
		}
		return msg;
	}

	@Override
	@Step("Handled Alert Exception. Cancel")
	protected String alertDismiss() {
		String msg = null;

		try {
			msg = eventFiringWebDriver.switchTo().alert().getText();
			log.info(String.format("TID [%d] - Handled Alert exception. Dismiss. MSG [%s].",
					Thread.currentThread().getId(), msg));

			eventFiringWebDriver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			log.warn(String.format("TID [%d] - Alert was not present.", Thread.currentThread().getId()));
		}
		return msg;
	}

	@Override
	@Step("Switch to the frame ID [{0}]")
	protected BasePageImpl switchToFrame(int frameID) {
		this.eventFiringWebDriver = (EventFiringWebDriver) eventFiringWebDriver.switchTo().frame(frameID);
		log.info(String.format("TID [%d] - Switched to frame, id [%s].", Thread.currentThread().getId(), frameID));
		return this;
	}

	@Override
	@Step("Switch to the frame location [{0}]")
	protected BasePageImpl switchToFrame(final By locator) {

		log.info(
				String.format("TID [%d] - Switched to frame, selector [%s].", Thread.currentThread().getId(), locator));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator)).switchTo();
		return this;
	}

	@Override
	@Step("Frame. Switch to default content.")
	protected BasePageImpl switchToDefaultFrame() {
		this.eventFiringWebDriver = (EventFiringWebDriver) eventFiringWebDriver.switchTo().defaultContent();
		log.info(String.format("TID [%d] - Frame. Switch to default content.", Thread.currentThread().getId()));
		return this;
	}

	@Override
	@Step("Frame. Switch to the tab# [{0}]")
	protected BasePageImpl switchToBrowserTab(int tabNumber) {

		final ArrayList<String> allHandles = new ArrayList<String>(eventFiringWebDriver.getWindowHandles());
		log.info(String.format("TID [%d] - Switch to browser tab [%d], URL [%s], Handle [%s].",
				Thread.currentThread().getId(), tabNumber, eventFiringWebDriver.getCurrentUrl(),
				allHandles.get(tabNumber)));
		this.eventFiringWebDriver.switchTo().window(allHandles.get(tabNumber));
		// this.driver.register((WebDriverEventListener)
		// driver.switchTo().window(allHandles.get(tabNumber)));
		// = driver.switchTo().window(allHandles.get(tabNumber));

		// (EventFiringWebDriver)
		// setWebDriver(driver.switchTo().window(allHandles.get(tabNumber)));

		return this;
	}

	@Override
	@Step("Frame. Close the tab# [{0}]")
	protected BasePageImpl closeTab(int tabNumber) {

		final ArrayList<String> allHandles = new ArrayList<String>(eventFiringWebDriver.getWindowHandles());
		// this.driver = (EventFiringWebDriver)
		eventFiringWebDriver.switchTo().window(allHandles.get(tabNumber));
		this.eventFiringWebDriver.close();
		// this.driver = (EventFiringWebDriver)
		eventFiringWebDriver.switchTo().window(allHandles.get(0));
		// setWebDriver(driver.switchTo().window(allHandles.get(tabNumber)));
		log.info(String.format("TID [%d] - Switch to browser tab [%d], URL [%s].", Thread.currentThread().getId(),
				tabNumber, eventFiringWebDriver.getCurrentUrl()));

		return this;
	}

	@Override
	@Step("Get Cookies")
	protected String getAllCookies() {

		StringBuilder sb = new StringBuilder();

		// loop for getting the cookie inString.formation
		for (Cookie ck : eventFiringWebDriver.manage().getCookies()) {
			sb.append(ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
					+ ck.getExpiry() + ";" + ck.isSecure());
			sb.append("\n");

		}
		return sb.toString();
	}

	@Override
	@Step("Get URL")
	protected String getUrl() {
		final String currUrl = eventFiringWebDriver.getCurrentUrl();
		log.info(String.format("TID [%d] - Get URL [%s].", Thread.currentThread().getId(), currUrl));
		return currUrl != null ? currUrl : "N/A - URL";
	}

	@Override
	@Step("Create the screenshot of a page with name [{0}], isFull page [{1}]")
	protected BasePageImpl makeScreenShot(String fileNames) {

		BaseUtils.makeScreenShot(fileNames, eventFiringWebDriver);

		return this;
	}

	@Override
	@Step("Wait to the element will be clickable for HTML element [{0}]")
	protected BasePageImpl clickJavaScript(final WebElement webElement) {

		log.info(String.format("TID [%s] - Click on the WebElement [%s].", Thread.currentThread().getId(),
				webElement.toString()));

		javascriptExecutor.executeScript("arguments[0].click();", webElement);

		return this;
	}

}


@Log4j
final class BasePageWebDriverEventListenerHandler implements WebDriverEventListener {

	@Override
	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		BaseUtils.waitRandom(250, 1000);
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
	}
}
