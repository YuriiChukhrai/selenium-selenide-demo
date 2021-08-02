package com.yc.qa;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.util.BaseListener;
import com.util.ThreadStoreLocal;
import com.util.WebDriverFactory;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

@Listeners(BaseListener.class)
public class BaseTest {

	@BeforeClass(alwaysRun = true)
	public final void beforeClass() {
	}

	@AfterClass(alwaysRun = true)
	public final void afterClass() {

		if (ThreadStoreLocal.getWebDriver() != null) {
			ThreadStoreLocal.getWebDriver().quit();
		}
		ThreadStoreLocal.getWebDriverContainer().remove();
	}

	@BeforeMethod(alwaysRun = true)
	public final void setUpMethod() {
		ThreadStoreLocal.setWebDriver(WebDriverFactory.createDriver());
	}

	@AfterMethod(alwaysRun = true)
	public final void tearDownMethod() {

		if (ThreadStoreLocal.getWebDriver() != null) {
			ThreadStoreLocal.getWebDriver().quit();
		}
		ThreadStoreLocal.getWebDriverContainer().remove();
	}

}
