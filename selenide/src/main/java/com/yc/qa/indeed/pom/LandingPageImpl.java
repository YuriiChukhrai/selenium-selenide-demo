package com.yc.qa.indeed.pom;

import com.codeborne.selenide.Condition;
import com.yc.qa.util.BaseConfig;
import com.yc.qa.util.Constants;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
@Log4j
public class LandingPageImpl implements LandingPage {

	@Step("Sign In")
	@Override
	public HomePage signIn() {
		return page(HomePageImpl.class);
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

            $(uploadResumeLink).should(Condition.visible).click();
            $(uploadResumeInput).sendKeys(path);
            $(uploadedLabel).shouldBe(Condition.visible);

            log.info(String.format("The resume uploaded. Path [%s]", path));
        }
        else {
            log.error(String.format("The file [%s] not exists.", url));
        }
		return this;
	}
}
