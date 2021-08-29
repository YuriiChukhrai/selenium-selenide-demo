package com.yc.qa.indeed.pom;

import org.openqa.selenium.By;

/**
*
* @author limit (Yurii Chukhrai)
*/
public interface LandingPage {

	By uploadResumeLink = By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'upload your resume')]");
	By uploadResumeInput = By.xpath("//input[@id='upload-resume-button']");
	By uploadedLabel = By.xpath("//div[@class='emailBanner']");
	
	HomePage signIn();
	
	LandingPage findJobs(final String jobTitle);
	
	LandingPage uploadResume();
}
