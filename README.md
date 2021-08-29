# Selenium, Selenide demo [UI Tests]

This **[repository](https://github.com/YuriiChukhrai/selenium-selenide-demo)** contains [Selenium](http://seleniumhq.org/) and [Selenide](https://selenide.org/) demo tests for the Google and Indeed pages.


## Dependencies
Make sure you have installed on your operating system:<br/>
1. [JDK. Oracle](http://www.java.com/) OR [OpenJDK](https://openjdk.java.net/)
2. [Git](https://git-scm.com/)
3. [Maven](https://maven.apache.org/)


## Running Tests
The following steps should get you set up for running Selenium/Selenide tests locally on your machine:

1. Clone this repository to your local machine:<br/>
    * HTTPS. ` $> git clone https://github.com/YuriiChukhrai/selenium-selenide-demo.git `
    * SSH. ` $> git clone git@github.com:YuriiChukhrai/selenium-selenide-demo.git `<br/><br/>
2. All commands must be run from the ` \selenium-selenide-demo ` directory, cloned during setup process above.<br/><br/>
3. ` $> mvn clean compile ` - Clean up the `target` directory, upload all necessary dependencies and compile java classes.


## Modules
1. [Selenium](./selenium/README.md) - This module contains the demo on pure **Selenium API** with automatically downloading necessary WebDriver and POM (page object model - the architectural approach to organize the classes).
2. [Selenide](./selenide/README.md) - This module contains the demo on **Selenide API**. 


### Static Analysis

Static code analysis is a method of debugging by examining source code before a program is run. It’s done by analyzing a set of code against a set (or multiple sets) of coding rules.<br><br>
This type of analysis addresses weaknesses in source code that might lead to vulnerabilities. Of course, this may also be achieved through manual code reviews. But using automated tools is much more effective.<br>
It’s often used for complying with industry standards — such as [ISO 26262](https://www.iso.org/standard/43464.html).

1. ` $> mvn clean site -Pstatic-analysis -Dmaven.test.skip=true ` - Run all static analysis frameworks (PMD, CPD, SpotBugs, Checkstyle) and generate the HTML report. Report location ` {project.base.dir}/target/site/surefire-report.html `


# Reports

In project exist 4 kind of reports (location: `{project.dir}\target\site\index.html`):
- [TestNG](http://testng.org/doc/documentation-main.html) produce ‘index.html‘ report, and it resides in the same test-output folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output.<br/>
- [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build life-cycle to execute the unit tests of an application.<br/>
- [Allure 2](https://docs.qameta.io/allure/) report. An open-source framework designed to create test execution reports clear to everyone in the team.<br/>
  **NOTE!** To run the report (HTML + JS) in Firefox You need leverage the restriction by going to `about:config` url and then **uncheck** `privacy.file_unique_origin` **boolean** value.
- [JaCoCo](https://www.jacoco.org/) report. JaCoCo is a free code coverage library for Java, which has been created by the EclEmma team based on the lessons learned from using and integration existing libraries for many years.<br/>
