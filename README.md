# Resume Sender [UI Tests]

This repository contains [Selenium](http://seleniumhq.org/) demo tests for the Google, Indeed [GitHub](https://github.com/selenium-demo).

## Dependencies
Make sure you have installed on your operating system:<br/>
1. [Java](http://www.java.com/) 
2. [Git](https://git-scm.com/)
3. [Maven](https://maven.apache.org/)


## Running Tests
The following steps should get you set up for running Selenium tests locally on your machine:

1. Clone this repository to your local machine.<br/>
2. All commands must be run from the `\selenium-demo` directory cloned during setup process above.<br/>


### Run Test Suites

##### Search Link
` $> mvn clean site -DtNG={Test Suite} -Dgroups={Group name} -Ddriver.type={Web Driver type} -Ddriver.version={Web Driver version}`
<br/>or<br/>
` $> mvn clean site -DtNG=all -Ddriver.type=chrome -Ddriver.version=92.0.4515.107`<br/>

### Parameters
The following are valid test parameters:

*	`-Ddriver.type` - Which browser need to use, for example `CHROME`, `IE`, `FF`, `SAFARI`, `HTMLUNIT`. Optional, default `CHROME`.<br/>
*	`-DtNG` - Which test scenario need to run - TestNG XML file name [all, easy, advance].<br/>
*	`-Dgroups` - Which test group need to run [JOBS, SKILLS, DEFAULT, UPLOAD].<br/>
*	`-Ddriver.version` -  Which the driver version need to use.<br/>


### Browsers
The following are valid for use in the `-Ddriver.type` parameter:

*	CHROME - do not forgot provide the driver version 
*	FF - do not forgot provide the driver version 
*	IE - [Not Implement yet]
*	Safari - [Not Implement yet]
*	HTMLUNIT - [Not Implement yet]


### Static Analysis

` $> mvn clean site -Pstatic-analysis -Dmaven.test.skip=true ` - Run all static analysis frameworks (PMD, CPD, SpotBugs, Checkstyle) and generate the HTML report.

Report location ` selenium-demo/target/site/surefire-report.html`


# Reports
In project exist 4 kind of reports:

- [TestNG](http://testng.org/doc/documentation-main.html) produce ‘index.html‘ report and it resides in the same test-output folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output.<br/><br/>
- [SureFire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build life-cycle to execute the unit tests of an application.<br/><br/>
- [Allure](https://docs.qameta.io/allure/) report. An open-source framework designed to create test execution reports clear to everyone in the team.<br/><br/>
- [JaCoCo](https://www.jacoco.org/) report. JaCoCo is a free code coverage library for Java, which has been created by the EclEmma team based on the lessons learned from using and integration existing libraries for many years.<br/><br/>