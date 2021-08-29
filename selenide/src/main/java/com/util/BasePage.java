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

	abstract BasePage sendKeys(final Keys keys);

	abstract BasePage sendKeys(final By locator, final CharSequence keysToSend);

	abstract BasePage sendKeysCustom(final By locator, final CharSequence text);

	abstract BasePage openURL(final String url);

	abstract BasePage refreshPage();

	abstract boolean isElementDisplayed(final By locator);

	abstract Actions actionMoveToElement(final By locator);

	abstract BasePage click(final By locator);

	abstract BasePage click(final WebElement webElement);

	abstract BasePage clickLocatorJs(final By locator);

	abstract BasePage clickJavaScript(final WebElement webElement);

	abstract String getText(final By locator);

	abstract BasePage setRadioSelected(final By locator);

	abstract BasePage selectVisibleTextFromDropDownListSelect(final By locator, final String text);

	abstract BasePage selectValueFromDropDownListSelect(final By locator, final String value);

	abstract BasePage scrollToWebElement(final By locator);

	abstract BasePage scrollToWebElement(final WebElement webElement);

	abstract BasePage scrollToBottomPage();

	abstract BasePage scrollToNextView();

	abstract BasePage scrollToPreviousView();

	abstract BasePage openLinkTab(final String link);

	abstract BasePage zoomPage(final int zoomExpected);

	abstract BasePage scrollToTopPage();

	abstract String getTitle(final String expectedTitle);

	abstract String getTitle();

	abstract WebElement highlight(final WebElement webElement);

	abstract BasePage setAttributeValue(final By locator, final String value);

	abstract BasePage removeAttribute(final By locator, final String attributeName, final String attributeValue);

	abstract BasePage setStyleValue(final By locator, final String value);

	abstract boolean isTextPresent(final String text);

	abstract String getSRC();

	abstract String alertAccept();

	abstract String alertDismiss();

	abstract BasePage switchToFrame(final int frameID);

	abstract BasePage switchToFrame(final By locator);

	abstract BasePage switchToDefaultFrame();

	abstract BasePage switchToBrowserTab(final int tabNumber);

	abstract BasePage closeTab(final int tabNumber);

	abstract String getAllCookies();

	abstract String getUrl();

	abstract BasePage makeScreenAsShot(final String fileNames, final boolean fullPage);
}
