package com.yc.qa.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.qameta.allure.Attachment;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public final class BaseUtils {

	private BaseUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}

	public static boolean isOs(final String osNameExpected) {
		final String osNameActual = System.getProperty("os.name").trim().toUpperCase();
		log.info(String.format("Detected OS [%s]", osNameActual));
		
		return osNameActual.contains(osNameExpected.toUpperCase());
	}

	/* This method make Text attachment for Allure report */
	@Attachment(value = "{0}", type = "text/plain")
	public static synchronized String attachText(final String nameOfAttachment, final String bodyOfMessage) {

		log.info(String.format("TID [%d] - Attached to allure file [%s].", Thread.currentThread().getId(),
				nameOfAttachment));

		return bodyOfMessage;
	}

	@Attachment(value = "{0}", type = "text/html")
	public static synchronized String attachHtml(final String nameOfAttachment, final String bodyOfMessage) {

		log.info(String.format("TID [%d] - Attached to allure file [%s].", Thread.currentThread().getId(),
				nameOfAttachment));

		return bodyOfMessage;
	}

	public static void waitRandom(int min, int max) {
		long wait = ThreadLocalRandom.current().nextLong(min, max);
		try {
			log.info(String.format("TID [%d] - Random wait [%s].", Thread.currentThread().getId(), wait));
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("Can't wait random " + e.getMessage());
		}
	}

	@Attachment(fileExtension = ".har", value = "{0}", type = "")
	public static byte[] addHar(final String filename, final File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Attachment(value = "{0}", type = "image/png")
	public static byte[] attachImageFile(String fileNames, final File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Can't attache file " +file.getName() + ". Error: " + e.getMessage());
		}
		return null;
	}

	@Attachment(value = "{0}", type = "image/png")
	public static byte[] makeScreenShot(String fileName, WebDriver webDriver) {
		return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
	}

	public static boolean isEmpty(final String s) {
		return Objects.isNull(s) || s.isEmpty();
	}
	
	public static synchronized boolean isWebDriverDead(final WebDriver webDriver) {
		return Objects.isNull(webDriver) || (((RemoteWebDriver) webDriver).getSessionId() == null);
	}
}
