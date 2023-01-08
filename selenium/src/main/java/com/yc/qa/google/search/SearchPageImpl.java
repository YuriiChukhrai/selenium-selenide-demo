package com.yc.qa.google.search;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.util.BasePageImpl;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public final class SearchPageImpl extends BasePageImpl implements SearchPage {

	protected SearchPageImpl() {
		super();
	}

	protected SearchPageImpl(WebDriverEventListener webDriverEventListener) {
		super(webDriverEventListener);
	}

	private final By inputFieldXpath = By.xpath("//input[@name='q']");
	private final String linksXpathTemplate = "(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]";

	private final String searchEngineUrl = "http://google.com";
	private String searchContent;

	@Step("Search content [{0}]")
	@Override
	public SearchPage searchContent(String searchContent) {
		this.searchContent = searchContent;

		openURL(searchEngineUrl);
		findElement(inputFieldXpath).clear();
		sendKeys(inputFieldXpath, searchContent + Keys.ENTER);
		
		return this;
	}

	@Step("Go to first link. And return page [{0}]")
	@Override
	public <T> T goToFirstLink(Class<T> pageObject) {

		findElements(By.xpath(String.format(linksXpathTemplate, this.searchContent)))
		.stream()
		.findFirst()
		.get()
		.click();

		makeScreenShot("Partial. Landing Page");

		return ConstructorAccess.get(pageObject).newInstance();
	}

//	//private BasePage basePageApi = new BasePage();
//	 
//	@Step("Search content [{0}]")
//	@Override
//	public SearchPage searchContent(String content) {
//		
//		LOG.info(String.format("Search content [%s].", content));
//		
//		basePageApi
//		.openURL("http://google.com")
//		.findElement(By.xpath("//input[@name='q']"))
//		.sendKeys(content + Keys.ENTER);
//		
//		basePageApi.makeScreenAsShot("Google Search part page", false);
//		basePageApi.makeScreenAsShot("Google Search full page", true);
//		
//		return this;
//	}
//
//	@Step("Select first Ads")
//	@Override
//	public <T> T goToFirstAds(Class<T> pageObject) {
//
//		basePageApi.findElement(adsLinks);
//		
//		basePageApi.findElements(adsLinks).stream().findFirst().get().click();
//		
//		basePageApi.makeScreenAsShot("Landing Page", false);
//		basePageApi.makeScreenAsShot("Landing Full Page", true);
//		
//		return ConstructorAccess.get(pageObject).newInstance();
//	}
}
