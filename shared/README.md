# Selenium demo [UI Tests]

This module contains [Selenium](http://seleniumhq.org/) demo tests for the Google, Indeed pages.


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


## References
1. [Selenium](http://seleniumhq.org/). Official website.