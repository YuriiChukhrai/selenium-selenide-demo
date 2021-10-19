package com.yc.qa.test.selenide;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import com.util.BaseUtils;
import com.util.TestGroups;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.files.FileFilters.withName;
import static com.util.BaseUtils.makeScreenAsShot;

/**
 * @author limit (Yurii Chukhrai)
 */
public class UploadDownloadFileTest extends BaseTest  {

    BrowserUpProxy bmp;

    @Override
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {

        Configuration.downloadsFolder = "./target/downloads";
        Configuration.fileDownload = FileDownloadMode.HTTPGET;
        Configuration.proxyEnabled = true;

        super.beforeClass();
    }

    /*
     * In the current moment the plugin [allure-harviewer] support attachment inside the test method
     *
     * */
//    @AfterClass(alwaysRun = true)
//    public void afterClass() throws IOException {
//
//        if(bmp != null){
//            final File harFile = new File("harFile.har");
//            bmp.getHar().writeTo(harFile);
//            bmp.stop();
//            BaseUtils.addHar(harFile.getName(), harFile);
//        }
//    }


    @Features({ @Feature("UPLOAD"), @Feature("DOWNLOAD") })
    @Issues({ @Issue("GA-011"), @Issue("GTA-012") })
    @Stories({ @Story("Stories: CIR-098"), @Story("Stories: CIR-099") })
    @Epics({ @Epic("Epic05"), @Epic("Epic06") })
    @TmsLinks({ @TmsLink("1234"), @TmsLink("4321") })
    @Links({ @Link(name = "Link#1", url = "https://knowledge.bla.com/confluence"), @Link(name = "Link#2", url = "https://bitbucket.bla.com:8443/projects/bla") })
    @Lead("Yurii Chukhrai")
    @Flaky
    @Severity(SeverityLevel.NORMAL)
    @Description("Upload and Download files.")
    @Owner("Yurii Chukhrai")
    @Test(priority = 0, enabled = true, groups = TestGroups.DEFAULT)
    public void uploadDownloadFileTest() throws IOException {

        final String fileName01 = "selenide-logo.png";
        final String fileName02 = "selenium-logo.png";

        final String uploadPath = "../doc/%s";
        final String uploadedFileXpath = "//a[text()='%s']";
        final String downloadPath = "//a[@title='%1$s']/img[contains(@src,'%1$s')]/parent::a";//img[contains(@src,'%1$s')]/parent::a

        open("https://blueimp.github.io/jQuery-File-Upload/");

        // After the proxy started - get it
        bmp = WebDriverRunner.getSelenideProxy().getProxy();

        // remember body of requests (body is not stored by default because it can be large)
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());

        // remember both requests and responses
        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // start recording!
        bmp.newHar("test.com");


        //Upload
        $(By.xpath("//input[@type='file']")).uploadFile(Paths.get(String.format(uploadPath, fileName01)).toFile(), Paths.get(String.format(uploadPath, fileName02)).toFile());
        $(By.xpath("//button//span[contains(text(),'Start upload')]")).shouldBe(Condition.visible).click();

        makeScreenAsShot("Partial. Landing Page", false, WebDriverRunner.getWebDriver());

        //Assertions
        $(By.xpath(String.format(uploadedFileXpath, fileName01))).shouldBe(Condition.visible);
        $(By.xpath(String.format(uploadedFileXpath, fileName02))).shouldBe(Condition.visible);

        final File file01 = $(By.xpath(String.format(downloadPath, fileName01))).download(withName(fileName01));
        final File file02 = $(By.xpath(String.format(downloadPath, fileName02))).download(withName(fileName02));

        //Attach files to the report
        BaseUtils.attachImageFile(fileName01, file01);
        BaseUtils.attachImageFile(fileName02, file02);

        //Download
        Assert.assertTrue(file01.exists());
        Assert.assertTrue(file02.exists());

        /*
        * If Proxy working:
        *   a) pull the HAR file from proxy
        *   b) save HAR file
        *   c) stop Proxy
        *   d) attach HAR file to the Allure report
        * */
        if(bmp != null){
            final File harFile = new File("./target/harFile.har");
            bmp.getHar().writeTo(harFile);
            bmp.stop();
            BaseUtils.addHar(harFile.getName(), harFile);
        }
    }
}
