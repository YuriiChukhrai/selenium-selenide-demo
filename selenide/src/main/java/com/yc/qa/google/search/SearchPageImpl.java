package com.yc.qa.google.search;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.util.BaseUtils.makeScreenAsShot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.Keys;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public final class SearchPageImpl implements SearchPage {

	private final String inputFieldXpath ="//input[@name='q']";
	private final String linksXpathTemplate = "(//div[contains(@data-async-context,'%1$s')]//a[contains(@href,'%1$s')])[1]";

	private final String searchEngineUrl = "http://google.com";
	private String searchContent;

	@Step("Search content [{0}]")
	@Override
	public SearchPage searchContent(String searchContent) {
		this.searchContent = searchContent;

        open(searchEngineUrl);
		$(byXpath(inputFieldXpath)).shouldBe(Condition.visible).sendKeys(this.searchContent + Keys.ENTER);

		return this;
	}

	@Step("Go to first link. And return page [{0}]")
	@Override
	public <T> T goToFirstLink(Class<T> pageObject) {

		$$(byXpath(String.format(linksXpathTemplate, this.searchContent))).first().shouldBe(Condition.visible).click();

		makeScreenAsShot("Partial. Landing Page", false, WebDriverRunner.getWebDriver());
		makeScreenAsShot("Full. Landing Page", true, WebDriverRunner.getWebDriver());

		return ConstructorAccess.get(pageObject).newInstance();
	}
}
