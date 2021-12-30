package com.yc.qa.test.selenium;

import com.util.ThreadStoreLocal;
import com.util.WebDriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.Objects;

/**
 * @author limit (Yurii Chukhrai)
 */

public class BaseTest {

	@AfterClass(alwaysRun = true)
	public final void afterClass() {
		cleanUp ();
	}

	@BeforeMethod(alwaysRun = true)
	public final void setUpMethod() {
		ThreadStoreLocal.setWebDriver(WebDriverFactory.createDriver());
	}

	@AfterMethod(alwaysRun = true)
	public final void tearDownMethod() {
		cleanUp ();
	}


	private void cleanUp (){
		if (Objects.nonNull(ThreadStoreLocal.getWebDriver())) {
			ThreadStoreLocal.getWebDriver().quit();
		}
		ThreadStoreLocal.  getWebDriverContainer().remove();
	}
}
