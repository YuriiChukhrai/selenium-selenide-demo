package com.yc.qa.util;

import java.util.List;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
public abstract class BasePage {

	@Step("Looking for element [{0}]")
	protected abstract WebElement findElementImplicit(By locator);

	@Step("Looking for element [{0}]")
	protected abstract WebElement findElement(By locator);

	@Step("Looking for elementS [{0}]")
	protected abstract List<WebElement> findElements(By locator);

	@Step("Send Key [{0}]")
	protected abstract BasePage sendKeys(Keys keys);

	@Step("Send Key [{1}] for locator [{0}]")
	protected abstract BasePage sendKeys(By locator, CharSequence keysToSend);

	@Step("Send text [{1}] to field: [{0}]")
	protected abstract BasePage sendKeysCustom(By locator, CharSequence text);

	@Step("Open URI [{0}]")
	protected abstract BasePage openURL(String url);

	@Step("Refresf page")
	protected abstract BasePage refreshPage();

	@Step("Check if the element  [{0}] displayed")
	protected abstract boolean isElementDisplayed(By locator);

	@Step("Action, move to element [{0}]")
	protected abstract Actions actionMoveToElement(By locator);

	@Step("Wait to the element will be click, selector [{0}]")
	protected abstract BasePage click(By locator);

	@Step("Wait to the element will be clickable, selector [{0}]")
	protected abstract BasePage click(WebElement webElement);

	@Step("Wait to the element will be clickable, selector [{0}]")
	protected abstract BasePage clickLocatorJs(By locator);

	@Step("Wait to the element will be clickable for HTML element [{0}]")
	protected abstract BasePage clickJavaScript(WebElement webElement);

	@Step("Get text from element [{0}]")
	protected abstract String getText(By locator);

	@Step("Click on the button [{0}]")
	protected abstract BasePage setRadioSelected(By locator);

	@Step("Select visible text [{1}] from drop down list [{0}]")
	protected abstract BasePage selectVisibleTextFromDropDownListSelect(By locator, String text);

	@Step("Select value [{1}] from drop down list [{0}]")
	protected abstract BasePage selectValueFromDropDownListSelect(By locator, String value);

	@Step("Scroll page to element [{0}]")
	protected abstract BasePage scrollToWebElement(By locator);

	@Step("Scroll page to Web Element [{0}]")
	protected abstract BasePage scrollToWebElement(WebElement webElement);

	@Step("Scroll to bottom of page")
	protected abstract BasePage scrollToBottomPage();

	@Step("Scroll to next View")
	protected abstract BasePage scrollToNextView();

	@Step("Scroll to previous View")
	protected abstract BasePage scrollToPreviousView();

	@Step("Open link in new tab [{0}]")
	protected abstract BasePage openLinkTab(String link);

	@Step("Set up zoom [{0}] on the page")
	protected abstract BasePage zoomPage(int zoomExpected);

	/* This method scroll page to bottom */
	@Step("Scroll to top of page")
	protected abstract BasePage scrollToTopPage();

	@Step("Wait of title [{0}]")
	protected abstract String getTitle(String expectedTitle);

	@Step("Get title of page")
	protected abstract String getTitle();

	@Step("Highlight element [{0}] on the page")
	protected abstract WebElement highlight(WebElement webElement);

	@Step("Set value [{1}] for HTML element attribute [{0}]")
	protected abstract BasePage setAttributeValue(By locator, String value);

	@Step("Set value [{1}] for HTML element attribute [{0}]")
	protected abstract BasePage removeAttribute(By locator, String attributeName, String attributeValue);

	@Step("Set style [{1}] for HTML element [{0}]")
	protected abstract BasePage setStyleValue(By locator, String value);

	@Step("Check present the text [{0}] in the SRC")
	protected abstract boolean isTextPresent(String text);

	@Step("Get source code of the page")
	protected abstract String getSRC();

	@Step("Handled Alert Exception. Ok")
	protected abstract String alertAccept();

	@Step("Handled Alert Exception. Cancel")
	protected abstract String alertDismiss();

	@Step("Switch to the frame ID [{0}]")
	protected abstract BasePage switchToFrame(int frameID);

	@Step("Switch to the frame location [{0}]")
	protected abstract BasePage switchToFrame(By locator);

	@Step("Frame. Switch to default content.")
	protected abstract BasePage switchToDefaultFrame();

	@Step("Frame. Switch to the tab# [{0}]")
	protected abstract BasePage switchToBrowserTab(int tabNumber);

	@Step("Frame. Close the tab# [{0}]")
	protected abstract BasePage closeTab(int tabNumber);

	@Step("Get Cookies")
	protected abstract String getAllCookies();

	@Step("Get URL")
	protected abstract String getUrl();

	@Step("Create the screenshot of a page with name [{0}]")
	protected abstract BasePage makeScreenShot(String fileNames);
}
