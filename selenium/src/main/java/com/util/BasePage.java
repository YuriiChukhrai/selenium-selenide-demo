package com.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
public abstract class BasePage {

	abstract WebElement findElementImplicit(final By locator);

	abstract WebElement findElement(final By locator);

	abstract List<WebElement> findElements(final By locator);

	abstract com.yc.qa.util.BasePage sendKeys(final Keys keys);

	abstract com.yc.qa.util.BasePage sendKeys(final By locator, final CharSequence keysToSend);
	
	abstract com.yc.qa.util.BasePage sendKeysCustom(final By locator, final CharSequence text);

	abstract com.yc.qa.util.BasePage openURL(final String url);

	abstract com.yc.qa.util.BasePage refreshPage();

	abstract boolean isElementDisplayed(final By locator);

	abstract Actions actionMoveToElement(final By locator);

	abstract com.yc.qa.util.BasePage click(final By locator);

	abstract com.yc.qa.util.BasePage click(final WebElement webElement);

	abstract com.yc.qa.util.BasePage clickLocatorJs(final By locator);
	
	abstract com.yc.qa.util.BasePage clickJavaScript(final WebElement webElement);

	abstract String getText(final By locator);

	abstract com.yc.qa.util.BasePage setRadioSelected(final By locator);

	abstract com.yc.qa.util.BasePage selectVisibleTextFromDropDownListSelect(final By locator, final String text);

	abstract com.yc.qa.util.BasePage selectValueFromDropDownListSelect(final By locator, final String value);

	abstract com.yc.qa.util.BasePage scrollToWebElement(final By locator);

	abstract com.yc.qa.util.BasePage scrollToWebElement(final WebElement webElement);

	abstract com.yc.qa.util.BasePage scrollToBottomPage();

	abstract com.yc.qa.util.BasePage scrollToNextView();

	abstract com.yc.qa.util.BasePage scrollToPreviousView();

	abstract com.yc.qa.util.BasePage openLinkTab(final String link);

	abstract com.yc.qa.util.BasePage zoomPage(final int zoomExpected);

	abstract com.yc.qa.util.BasePage scrollToTopPage();

	abstract String getTitle(final String expectedTitle);

	abstract String getTitle();

	abstract WebElement highlight(final WebElement webElement);

	abstract com.yc.qa.util.BasePage setAttributeValue(final By locator, final String value);

	abstract com.yc.qa.util.BasePage removeAttribute(final By locator, final String attributeName, final String attributeValue);

	abstract com.yc.qa.util.BasePage setStyleValue(final By locator, final String value);

	abstract boolean isTextPresent(final String text);

	abstract String getSRC();

	abstract String alertAccept();

	abstract String alertDismiss();

	abstract com.yc.qa.util.BasePage switchToFrame(final int frameID);

	abstract com.yc.qa.util.BasePage switchToFrame(final By locator);

	abstract com.yc.qa.util.BasePage switchToDefaultFrame();

	abstract com.yc.qa.util.BasePage switchToBrowserTab(final int tabNumber);

	abstract com.yc.qa.util.BasePage closeTab(final int tabNumber);

	abstract String getAllCookies();

	abstract String getUrl();

	abstract com.yc.qa.util.BasePage makeScreenAsShot(final String fileNames, final boolean fullPage);
}
