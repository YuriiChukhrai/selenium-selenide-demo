package com.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

//TODO - unit testing

/**
 * @author: Yurii Chukhrai
 * @see <a href="https://bonigarcia.dev/webdrivermanager/">Webdriver Manager</a>
 * @see <a href="https://selenide.org/documentation.html">Selenide</a>
 * @see <a href="https://github.com/bonigarcia/webdrivermanager/blob/master/src/test/java/io/github/bonigarcia/wdm/test/create/ChromeRemoteTest.java">Web-Driver Manager. Remote</a>
 * <p>
 * DriverManagerType browserName:
 * CHROME("org.openqa.selenium.chrome.ChromeDriver")
 * FIREFOX("org.openqa.selenium.firefox.FirefoxDriver")
 * OPERA("org.openqa.selenium.opera.OperaDriver")
 * EDGE("org.openqa.selenium.edge.EdgeDriver")
 * IEXPLORER("org.openqa.selenium.ie.InternetExplorerDriver")
 * CHROMIUM("org.openqa.selenium.chrome.ChromeDriver")
 * SAFARI("org.openqa.selenium.safari.SafariDriver")
 */
@Log4j
public final class WebDriverFactory {

    private WebDriverFactory() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    public static WebDriver createInstance(final WebDriverInstDesc webDriverInstDesc) {

        // Remote mode
        if (webDriverInstDesc.isRemoteWebDriver() && !StringUtils.isEmpty(webDriverInstDesc.getRemoteUrl())) {
            log.info("Remote MODE");

            return WebDriverManager
                    .getInstance(webDriverInstDesc.getBrowserName())
                    .remoteAddress(webDriverInstDesc.getRemoteUrl())
                    .capabilities(webDriverInstDesc.getCapabilities())
                    .create();
        }

        // Local mode
        else {
            log.info("Local MODE");

            return WebDriverManager
                    .getInstance(webDriverInstDesc.getBrowserName())
                    .driverVersion(webDriverInstDesc.getBrowserVersion())
                    .capabilities(webDriverInstDesc.getCapabilities())
                    .create();
        }
    }

    public static WebDriver createInstance(final DriverManagerType browserName, final String browserVersion, Capabilities capabilities) {

        return WebDriverManager
                .getInstance(browserName)
                .driverVersion(browserVersion)
                .capabilities(Objects.nonNull(capabilities) ? capabilities : getDefaultCapabilities(browserName) )
                .create();
    }

    private static Capabilities getDefaultCapabilities(DriverManagerType browserName) {

        switch (browserName) {

            case CHROME: {

                final ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions
                        //.setBrowserVersion("104")
                        //.setPlatformName("Unix")
                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                        .setAcceptInsecureCerts(true)
                        .setPageLoadStrategy(PageLoadStrategy.EAGER)
                        .setStrictFileInteractability(true)
                        .setImplicitWaitTimeout(Duration.ofSeconds(5))
                        .setPageLoadTimeout(Duration.ofSeconds(30))
                        .setScriptTimeout(Duration.ofSeconds(60));

                chromeOptions.setExperimentalOption("detach", false);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("use-fake-ui-for-media-stream");
                chromeOptions.addArguments("disable-user-media-security");
                chromeOptions.addArguments("allow-running-insecure-content");
                chromeOptions.addArguments("use-fake-device-for-media-stream");
                chromeOptions.addArguments("allow-file-access-from-files");

                return chromeOptions;
            }

            case FIREFOX: {

                final FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);

                firefoxOptions.addArguments("--start-maximized");
                        //.AddArgument();

                //firefoxOptions.setCapability(FirefoxDriver., false);
                //firefoxOptions.setCapability("handlesAlerts", true);
                //firefoxOptions.setCapability("acceptInsecureCerts", true);
                //firefoxOptions.addPreference("geo.enabled", false);
                //firefoxOptions.addPreference("dom.webnotifications.serviceworker.enabled", false);
                //firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                //firefoxOptions.setCapability("moz:webdriverClick", false);
                //firefoxOptions.setCapability("pageLoadStrategy", "normal");
                //firefoxOptions.setCapability("javascriptEnabled", true);
                //firefoxOptions.setCapability("headless", false);
                // [handlesAlerts, headless, javascriptEnabled];

                return firefoxOptions;
            }

        }

        return null;
    }
}