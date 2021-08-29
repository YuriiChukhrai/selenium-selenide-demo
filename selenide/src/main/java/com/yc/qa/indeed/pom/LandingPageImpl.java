package com.yc.qa.indeed.pom;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.util.BaseConfig;
import com.util.BasePageImpl;
import com.util.Constants;
import com.util.ObjectSupplier;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public class LandingPageImpl extends BasePageImpl implements LandingPage {

	@Step("Sign In")
	@Override
	public HomePage signIn() {

		return ObjectSupplier.$(HomePageImpl.class);
	}

	@Step("Find job [{0}]")
	@Override
	public LandingPage findJobs(final String jobTitle) {
		throw new RuntimeException("Not implemented yet");

		// return this;
	}

	@Step("Upload resume")
	@Override
	public LandingPage uploadResume() {

		final URL url = Thread.currentThread().getContextClassLoader().getResource(BaseConfig.getProperty(Constants.RESOURCE_NAME));
		
		//final URL url = ClassLoader.getSystemClassLoader().getResource(BaseConfig.getProperty(Constants.RESOURCE_NAME));
		
		
		/*
		 * ClassLoader.getSystemClassLoader().getResource(BaseConfig.getProperty(Constants.RESOURCE_NAME))
		 * OR
		 * Thread.currentThread().getContextClassLoader().getSystemResource(BaseConfig.getProperty(Constants.RESOURCE_NAME))
		 * OR
		 * ClassLoader.getSystemClassLoader().getResource(BaseConfig.getProperty(Constants.RESOURCE_NAME))
		 * OR
		 * Thread.currentThread().getContextClassLoader().getSystemResource(BaseConfig.getProperty(Constants.RESOURCE_NAME))
		 * */


		if(url != null && Files.exists(Paths.get(url.getPath()))) {
			
			final String path = Paths.get(url.toString()).toString();
			
			click(uploadResumeLink);
			findElement(uploadResumeInput).sendKeys(path);
			findElement(uploadedLabel);
			makeScreenAsShot("Upload resume", false);

			log.info(String.format("The resume uploaded. Path [%s]", path));
		}
		else {
			log.error(String.format("The file [%s] not exists.", url));
		}
		
		return this;
	}
}
