package com.yc.qa.indeed.pom;

import org.openqa.selenium.By;

/**
*
* @author limit (Yurii Chukhrai)
*/
public interface LandingPage {

	String uploadResumeLink = "//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'upload your resume')]";
	String uploadResumeInput = "//input[@id='upload-resume-button']";
	String uploadedLabel = "//div[@class='emailBanner']";

	HomePage signIn();
	LandingPage findJobs(final String jobTitle);
	LandingPage uploadResume();
}
