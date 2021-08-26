# Selenium demo [UI Tests]

This repository contains [Selenium](http://seleniumhq.org/) demo tests for the Google, Indeed [GitHub](https://github.com/YuriiChukhrai/selenium-demo).

## Dependencies
Make sure you have installed on your operating system:<br/>
1. [Java. JDK](http://www.java.com/) 
2. [Git](https://git-scm.com/)
3. [Maven](https://maven.apache.org/)


## Running Tests
The following steps should get you set up for running Selenium tests locally on your machine:

1. Clone this repository to your local machine:<br/>
   * HTTPS. ` $> git clone https://github.com/YuriiChukhrai/allure-jmeter.git `
   * SSH. ` $> git clone git@github.com:YuriiChukhrai/allure-jmeter.git `<br/><br/>
2. All commands must be run from the ` \selenium-demo ` directory, cloned during setup process above.<br/><br/>
3. ` $> mvn clean compile ` - Clean up the `target` directory, upload all necessary dependencies and compile java classes.
### CHROME
4. ` $> mvn clean site -Dtest.suite=easy -Ddriver.type=chrome -Ddriver.version=92.0.4515.107 ` - Run test suite `easy` (without POM and additional layers) for Chrome browser version **92.0.4515.107**  (or you can provide your version) and generate reports ( Surefire Report; Allure; JaCoCo ).<br/>
**NOTE!** The script will automatically download required WebDriver due too `-Ddriver.type` and `-Ddriver.version`.<br/><br/>
5. ` $> mvn clean site -Dtest.suite=advance -Dgroups=SEARCH -Ddriver.type=chrome -Ddriver.version=92.0.4515.107 `<br/><br/>
6. ` $> mvn clean site -Dtest.suite=all -Ddriver.type=chrome -Ddriver.version=92.0.4515.107 `<br/><br/>
### FIREFOX (FF)
7. ` $> mvn clean site -Dtest.suite=easy -Ddriver.type=ff -Ddriver.version=0.29.1 `<br/><br/>
8. ` $> mvn clean site -Dtest.suite=advance -Dgroups=SEARCH -Ddriver.type=FIREFOX -Ddriver.version=0.29.1 `<br/><br/>
9. ` $> mvn clean site -Dtest.suite=all -Ddriver.type=ff -Ddriver.version=0.29.1 `<br/><br/>


### Parameters
The following are valid test parameters:

*	` -Dtest.suite` - Which test scenario need to run - TestNG XML file name [all, easy, advance].<br/>
*	` -Ddriver.type` - Which browser need to use, for example `CHROME`, `IE`, `FF`, `SAFARI`, `HTMLUNIT`. Optional, default `CHROME` (case-insensitive).<br/>
*	` -Ddriver.version` -  Which the driver version need to use.<br/>
*	` -Dgroups` - Which test group need to run (TestNg group) [SEARCH, DEFAULT].<br/>


### Browsers
The following are valid for use in the `-Ddriver.type` parameter:

*	CHROME - do not forgot provide the driver version
*	FF - do not forgot provide the driver version
*	IE - [TBD]
*	Safari - [TBD]
*	HTMLUNIT - [TBD]


### Static Analysis

` $> mvn clean site -Pstatic-analysis -Dmaven.test.skip=true ` - Run all static analysis frameworks (PMD, CPD, SpotBugs, Checkstyle) and generate the HTML report.

Report location ` selenium-demo/target/site/surefire-report.html`


# Reports

In project exist 4 kind of reports (location: `{project.dir}\target\site\index.html`):
- [TestNG](http://testng.org/doc/documentation-main.html) produce ‘index.html‘ report and it resides in the same test-output folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output.<br/>
- [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build life-cycle to execute the unit tests of an application.<br/>
- [Allure 2](https://docs.qameta.io/allure/) report. An open-source framework designed to create test execution reports clear to everyone in the team.<br/>
  **NOTE!** To run the report (HTML + JS) in Firefox You need leverage the restriction by going to `about:config` url and then **uncheck** `privacy.file_unique_origin` **boolean** value.
- [JaCoCo](https://www.jacoco.org/) report. JaCoCo is a free code coverage library for Java, which has been created by the EclEmma team based on the lessons learned from using and integration existing libraries for many years.<br/>
