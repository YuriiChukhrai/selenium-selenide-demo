![Selenide](../doc/selenide-logo.png "Java + Selenide")
# Selenide demo [UI Tests] 

This repository contains [Selenide](https://selenide.org/) demo tests for the Google, Indeed pages.

**Selenide** is a framework for test automation powered by **Selenium WebDriver** that brings the following advantages:
* Concise fluent API for tests
* Ajax support for stable tests
* Powerful selectors
* Simple configuration
  
You don't need to think how to shut down browser, handle timeouts and **StaleElement Exceptions** or search for relevant log lines, debugging your tests.
  Just focus on your business logic and let **Selenide** do the rest!


## Keynote
1. **Build-in webdrivermanager**
    * checks the version of the browser installed in your machine (e.g.
      Chrome, Firefox).
    * checks the version of the driver (e.g. chromedriver, geckodriver). If
      unknown, it uses the latest version of the driver.
    * downloads the WebDriver binary if it is not present on the
      WebDriverManager cache (~/.m2/repository/webdriver by default).
    * it exports the proper WebDriver Java environment variables required
      by Selenium.

2. **Build-in proxy. Browsermob/BrowserUp proxy. Work with traffic**
    * manipulate HTTP requests and responses
    * capture HTTP content
    * export performance data as a HAR file
    * works well as a standalone proxy serve
    * especially useful when embedded in Selenium tests

3. New API for download files(w/o proxy, with proxy, with interceptor)

4. Build in Explicit wait conditions


## References
1. [Selenide](https://selenide.org/) - Official website
2. [Selenide Wiki](https://github.com/selenide/selenide/wiki)
3. [Selenide: tips and tricks.](https://github.com/britka/qaopen-selenide-examples) - Hillel IT School