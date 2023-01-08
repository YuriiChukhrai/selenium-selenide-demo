package com.yc.qa.test.selenium;


import com.yc.qa.util.BaseConfig;
import com.yc.qa.util.BaseUtils;
import com.yc.qa.util.Constants;
import com.yc.qa.util.TestGroups;
import com.util.WebDriverFactory;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.yc.qa.util.BaseUtils.makeScreenShot;

/**
 * @author limit (Yurii Chukhrai)
 */
public class UploadDownloadFileTest {

    public static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static Actions actions;
    private static Wait<WebDriver> wait;

    @BeforeClass
    public void beforeClass() {

        driver = WebDriverFactory.createInstance(DriverManagerType.valueOf(BaseConfig.getProperty(Constants.DRIVER_TYPE_PROP)), BaseConfig.getProperty(Constants.DRIVER_VER_PROP), null);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // Implicitly

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Explicitly
        wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        actions = new Actions(driver);

    }

    @AfterClass
    public void afterClass() {
        if (!BaseUtils.isWebDriverDead(driver)) {
            driver.quit();
        }
    }

    @DataProvider(parallel = false)
    public Iterator<String> dp() {
        //File name
        return new ArrayList<String>(Arrays.asList("selenide-logo.png", "selenium-logo.png")).iterator();
    }

    @Features({ @Feature("UPLOAD"), @Feature("DOWNLOAD") })
    @Issues({ @Issue("GA-011"), @Issue("GTA-012") })
    @Stories({ @Story("Stories: CIR-098"), @Story("Stories: CIR-099") })
    @Epics({ @Epic("Epic05"), @Epic("Epic06") })
    @TmsLinks({ @TmsLink("1234"), @TmsLink("4321") })
    @Links({ @Link(url="https://github.com/YuriiChukhrai/selenium-selenide-demo", name="GitHub"), @Link(url="https://www.linkedin.com/in/yurii-c-b55aa6174/", name="LinkedIn") })
    @Lead("Yurii Chukhrai")
    @Flaky
    @Severity(SeverityLevel.NORMAL)
    @Description("Upload and Download files.")
    @Owner("Yurii Chukhrai")
    @Test(priority = 0, enabled = true, groups = TestGroups.DEFAULT, dataProvider = "dp")
    public void uploadDownloadFileTest(final String fileName) throws IOException, URISyntaxException {

        final String uploadPath = "../doc/%s";
        final String uploadedFileXpath = "//a[text()='%s']";
        final String downloadPath = "//a[@title='%1$s']/img[contains(@src,'%1$s')]/parent::a";

        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        //Upload
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(Paths.get(String.format(uploadPath, fileName)).toAbsolutePath().normalize().toString());
        driver.findElement(By.xpath("//button//span[contains(text(),'Start upload')]")).click();

        makeScreenShot("Partial. Landing Page", driver);

        //Assertions
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(uploadedFileXpath, fileName))));
        String imageLink = driver.findElement(By.xpath(String.format(downloadPath, fileName))).getAttribute("href");

        //Download ( Using only build in JDK tools ;) )
        final URL resource = new URL(imageLink);
        final Path downloadedResources = Paths.get(String.format("./target/downloaded_%s", fileName));
        Files.copy(resource.openStream(), downloadedResources);

        //Attach files to the report
        BaseUtils.attachImageFile(fileName, downloadedResources.toFile());

        Assert.assertTrue(Files.exists(downloadedResources));
    }
}
