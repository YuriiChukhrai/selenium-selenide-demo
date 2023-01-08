package com.yc.qa.util;

import org.openqa.selenium.WebDriver;

import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public final class ThreadStoreLocal {

	private ThreadStoreLocal() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	private static final ThreadLocal<WebDriver> WEB_DRIVER_CONTAINER = new ThreadLocal<>();

	public static WebDriver getWebDriver() {
		log.debug(String.format("TID [%d] - getWebDriver()", Thread.currentThread().getId()));
		return WEB_DRIVER_CONTAINER.get();
	}

	public static synchronized void setWebDriver(WebDriver driver) {

		if (driver != null) {
			WEB_DRIVER_CONTAINER.set(driver);
		}

		log.info(String.format("TID [%d] - setWebDriver()", Thread.currentThread().getId()));
	}

	public static ThreadLocal<WebDriver> getWebDriverContainer() {
		log.debug(String.format("TID [%d] - getWebDriverContainer() - ", Thread.currentThread().getId()));
		return WEB_DRIVER_CONTAINER;
	}
}
