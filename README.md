![Selenide](./doc/selenide-logo.png "Java + Selenide") ![Selenium](./doc/selenium-logo.png "Java + Selenium")
# Selenium, Selenide Demo [UI Tests]

This **[repository](https://github.com/YuriiChukhrai/selenium-selenide-demo)** contains [**Selenium**](http://seleniumhq.org/) and [**Selenide**](https://selenide.org/) UI demo tests for the Google and Indeed pages.
Also provided demo how to upload and download files using **Selenium** and **Selenide**. In **Selenide** module also provided demo with build-in proxy server and how to generate **HAR** [(HTTP Archive)](https://en.wikipedia.org/wiki/HAR_(file_format)) file and attache it to the Allure report.

## Introduction
When I am doing an interview of candidates, the previous projects (in git hub or maybe on their machine) are much more critical for me than some fundamental questions about Git, Java, Selenium, Maven, or Jenkins.
We are all people. In an interview, we all can experience anxiety or fear, so I do not believe you can evaluate candidates' many years' background in a couple of hours (even if it is in many panel interviews ~ 8 hours).
I prefer to discuss the candidate demos (explore the code and see how clean it is and what library and why they were used) or discuss their articles in blogs or LinkedIn.


## Dependencies
Make sure you have installed on your operating system:<br/>
1. [JDK. Oracle](http://www.java.com/) OR [OpenJDK](https://openjdk.java.net/)
2. [Git](https://git-scm.com/)
3. [Maven](https://maven.apache.org/)


## Modules
1. **[selenium](./selenium/README.md)** - This module contains the demo on pure **Selenium API** with automatically downloading necessary WebDriver and POM (page object model - the architectural approach to organize the classes).
2. **[selenide](./selenide/README.md)** - This module contains the demo of small portion the **Selenide API**.
3. **shared** - This module contains the shared (reusable) code (processing properties, common interfaces and etc.) for both modules (selenium and selenide).


### Test Suites
The following are valid TestNG test suites for use in the ` -Dtest.suite={test suite name}` parameter: 
1. ` easy `
2. ` advance `
3. ` all `
4. ` fileUploadDonload `


### CLO (Command Line Options)
The following are valid test parameters:
* ` -Dtest.suite` - Which test scenario need to run - TestNG XML file name [all, easy, advance].<br/>
* ` -Ddriver.type` - Which browser need to use, for example `CHROME`, `IE`, `FF`, `SAFARI`, `HTMLUNIT`. Optional, default `CHROME` (case-insensitive).<br/>
* ` -Ddriver.version` -  Which the driver version need to use.<br/>
* ` -Dgroups` - Which test group need to run (TestNg groups) [SEARCH, DEFAULT or others].<br/>
* ` -Preporting` - The Maven profile what enable generation of report (by default reporting disabled).


## Running Tests
The following steps should get you set up for running **Selenium/Selenide** modules locally on your machine:

1. Clone this repository to your local machine:<br/>
    * HTTPS. ` $> git clone https://github.com/YuriiChukhrai/selenium-selenide-demo.git `
    * SSH. ` $> git clone git@github.com:YuriiChukhrai/selenium-selenide-demo.git `<br/><br/>
2. All commands must be run from the required module (**selenium** or **selenide**) directory, cloned during setup process above.<br/><br/>
3. ` $ {project.base.dir}> mvn clean compile -Dmaven.test.skip=true install` - Clean up the `target` directory, upload all necessary dependencies, compile java classes and install shared module to the local **Maven** repository.
4. ` $ {project.base.dir}\{module}> mvn clean compile ` - Clean up the `target` directory, upload all necessary dependencies and compile java classes.
### CHROME
5. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=easy -Ddriver.type=chrome -Ddriver.version=94.0.4606.71 -Preporting` - Run test suite `easy` (without POM and additional layers) for Chrome browser version **94.0.4606.71**  (or you can provide your version) and generate reports ( Surefire Report; Allure; JaCoCo ).<br/>
   **NOTE!** The script will automatically download required WebDriver due too `-Ddriver.type` and `-Ddriver.version`.<br/><br/>
6. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=advance -Dgroups=SEARCH -Ddriver.type=chrome -Ddriver.version=94.0.4606.71 `<br/><br/>
7. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=all -Ddriver.type=chrome -Ddriver.version=94.0.4606.71 `<br/><br/>
8. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=fileUploadDonload -Ddriver.type=chrome -Ddriver.version=94.0.4606.71 `<br/><br/>
### FIREFOX (FF)
9. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=easy -Ddriver.type=ff -Ddriver.version=0.30.0 `<br/><br/>
10. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=advance -Dgroups=SEARCH -Ddriver.type=FIREFOX -Ddriver.version=0.30.0 `<br/><br/>
11. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=all -Ddriver.type=ff -Ddriver.version=0.30.0 `<br/><br/>
12. ` $ {project.base.dir}\{module}> mvn clean site -Dtest.suite=fileUploadDonload -Ddriver.type=ff -Ddriver.version=0.30.0 `<br/><br/>
    **NOTE!** Please, do not forgot to use the Maven profile for report generation ` -Preporting` if you would like to have SureFire, Allure and JaCoCo reports.<br/><br/>

### Browsers
The following are valid for use in the ` -Ddriver.type ` parameter:

* CHROME - do not forgot provide the driver version
* FF - do not forgot provide the driver version
* IE - [TBD]
* Safari - [TBD]
* HTMLUNIT - [TBD]


### Static Analysis
Static code analysis is a method of debugging by examining source code before a program is run. It’s done by analyzing a set of code against a set (or multiple sets) of coding rules.<br><br>
This type of analysis addresses weaknesses in source code that might lead to vulnerabilities. Of course, this may also be achieved through manual code reviews. But using automated tools is much more effective.<br>
It’s often used for complying with industry standards — such as [ISO 26262](https://www.iso.org/standard/43464.html).

1. ` $ {project.base.dir}> mvn clean site -Pstatic-analysis -Dmaven.test.skip=true ` - Run all static analysis frameworks (PMD, CPD, SpotBugs, Checkstyle) and generate the HTML report.<br>
Report location: ` {project.base.dir}\{module}\target\site\surefire-report.html `


# Reports
In project exist 4 kind of reports (location: `{project.base.dir}\{module}\target\site\index.html`):
- [TestNG](http://testng.org/doc/documentation-main.html) produce ‘index.html‘ report, and it resides in the same test-output folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output.<br/>
- [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build life-cycle to execute the unit tests of an application.<br/>
- [Allure 2](https://docs.qameta.io/allure/) report. An open-source framework designed to create test execution reports clear to everyone in the team.<br/>
  **NOTE!** To run the report (HTML + JS) in Firefox You need leverage the restriction by going to `about:config` url and then **uncheck** `privacy.file_unique_origin` **boolean** value.
- [JaCoCo](https://www.jacoco.org/) report. JaCoCo is a free code coverage library for Java, which has been created by the EclEmma team based on the lessons learned from using and integration existing libraries for many years.<br/>


# Wishlist (TODO)
1. [Accept Cookies] function on the landing page