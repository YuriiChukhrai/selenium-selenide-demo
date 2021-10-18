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

2. **Build-in proxy. Browsermob/[BrowserUp](https://selenide.org/2019/12/18/advent-calendar-network-logs-with-proxy/) proxy. Work with traffic**
    * manipulate HTTP requests and responses
    * capture HTTP content
    * export performance data as a **HAR** [(HTTP Archive)](https://en.wikipedia.org/wiki/HAR_(file_format)) file
    * works well as a standalone proxy serve
    * especially useful when embedded in Selenium tests

3. New API for download files(w/o proxy, with proxy, with interceptor)

4. Build in Explicit wait conditions


## HAR Viewer in the Allure report
The build-in proxy ([BrowserUp (Selenide)](https://selenide.org/2019/12/18/advent-calendar-network-logs-with-proxy/)) can generate HAR file. You can attach it to the Allure report in two different ways:
1. Like regular `text/plain` file (in demo it's file `{project.base.dir}/selenide/target/harFile.har`). And after, you should use any HAR Viewer tools to open and analyze it (for Example: [Google HAR Analyzer](https://toolbox.googleapps.com/apps/har_analyzer/) OR [HAR Viewer](http://www.softwareishard.com/blog/har-viewer/) ). 
2. Or you can use the [HAR Viewer Allure plugin](https://github.com/kolsys/allure-harviewer)

In Selenide demo was implemented both ways. But to enable the second one, we should proceed with the next steps:

1. Install [HAR Viewer Allure plugin](https://github.com/kolsys/allure-harviewer)
   1. Go to the directory `/selenium-selenide-demo/selenide/.allure/allure-{version}/plugins/`
   2. Clone the plugin repository: `git https://github.com/kolsys/allure-harviewer.git`
   3. Enable this plugin in the Allure configuration.
      1. Open file in any text editor: `/selenium-selenide-demo/selenide/.allure/allure-{version}/config/allure.yml`
      2. Add new plugin **allure-harviewer** to the configuration: 
```yaml
plugins:
 - junit-xml-plugin
 - xunit-xml-plugin
 - trx-plugin
 - behaviors-plugin
 - packages-plugin
 - screen-diff-plugin
 - xctest-plugin
 - jira-plugin
 - xray-plugin
 - allure-harviewer
```

2. The java code for attaching and using HAR Viewer Allure plugin should (file name should be _harFile.har_). [Allure DOC](https://docs.qameta.io/allure-report/frameworks/java/testng#attachments):
```java
@Attachment(fileExtension = ".har", value = "{0}", type = "")
public static byte[] addHar(final String filename, final File file) {
...
```
3. Attach the HAR file inside the Test method (current implementation in the _HAR Viewer Allure_ plugin)

[Screenshot. HAR attachment in Allure](../doc/allure-har-file.png)<br>
[Screenshot. HAR Viewer in Allure](../doc/har-viewer.png)<br><br>

**NOTE!** If you would like to attach the HAR file outside the test method (AfterClass or AfterSuite, for example) you should modify the plugin source code.
   1. Open file `/selenium-selenide-demo/selenide/.allure/allure-{version}/plugins/allure-harviewer/static/index.js` in any text editor.
   2. Modify the code block. FROM:
```jql
    template: function (data) {
      if (!data.testStage) {
        return;
      }
      var links = [];
      for (var i in data.testStage.attachments) {
        var attach = data.testStage.attachments[i];
        if (attach.name.match(/\.har$/)) {
          links.push({url: 'data/attachments/'+attach.source, name: attach.name});
        }
      }
```
   TO:
```jql
    template: function (data) {
      if (!data.testStage && !data.afterStages) {
        return;
      }

      var links = [];

      if(data.afterStages) {
        var afterStagesArray = data.afterStages 
          for (var i in afterStagesArray) {
            for (var j in afterStagesArray[i].attachments) {
              var attach = afterStagesArray[i].attachments[j];
              if (attach.name.match(/\.har$/)) { 
                links.push({url: 'data/attachments/' + attach.source, name: attach.name}); 
              }
            }
          }
      } 
      if(data.afterStages) {
            for (var i in data.testStage.attachments) {
              var attach = data.testStage.attachments[i];
              if (attach.name.match(/\.har$/)) {
                links.push({url: 'data/attachments/' + attach.source, name: attach.name});
              }
            }
        }
```


## References
1. [Selenide](https://selenide.org/) - Official website
2. [Selenide Wiki](https://github.com/selenide/selenide/wiki)
3. [BrowserUp proxy](https://selenide.org/2019/12/18/advent-calendar-network-logs-with-proxy/)
4. [HAR Viewer](http://www.softwareishard.com/blog/har-viewer/)
5. [HAR Viewer Allure plugin](https://github.com/kolsys/allure-harviewer)