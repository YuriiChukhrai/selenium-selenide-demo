package com.yc.qa.test.selenide;

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
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.files.FileFilters.withName;
import static com.util.BaseUtils.makeScreenAsShot;

/**
 * @author limit (Yurii Chukhrai)
 */
public class UploadDownloadFileTest extends BaseTest  {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        Configuration.downloadsFolder = "./target/downloads";
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.HTTPGET;
    }

    @Features({ @Feature("UPLOAD"), @Feature("DOWNLOAD") })
    @Issues({ @Issue("GA-011"), @Issue("GTA-012") })
    @Stories({ @Story("Stories: CIR-098"), @Story("Stories: CIR-099") })
    @Epics({ @Epic("Epic05"), @Epic("Epic06") })
    @TmsLinks({ @TmsLink("1234"), @TmsLink("4321") })
    @Links({ @Link(name = "Link#1", url = "https://knowledge.bla.com/confluence"), @Link(name = "Link#2", url = "https://bitbucket.bla.com:8443/projects/bla") })
    @Lead("Yurii Chukhrai")
    @Flaky
    @Severity(SeverityLevel.NORMAL)
    @Description("Upload and Download")
    @Owner("Yurii Chukhrai")
    @Test(priority = 0, enabled = true, groups = TestGroups.DEFAULT)
    public void uploadDownloadFileTest() throws FileNotFoundException {

        final String fileName01 = "selenide-logo.png";
        final String fileName02 = "selenium-logo.png";

        final String uploadPath = "../doc/%s";
        final String uploadedFileXpath = "//a[text()='%s']";
        final String downloadPath = "//a[@title='%1$s']/img[contains(@src,'%1$s')]/parent::a";//img[contains(@src,'%1$s')]/parent::a

        open("https://blueimp.github.io/jQuery-File-Upload/");

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
        BaseUtils.makeScreenAsShot(fileName01, file01);
        BaseUtils.makeScreenAsShot(fileName02, file02);

        //Download
        Assert.assertTrue(file01.exists());
        Assert.assertTrue(file02.exists());
    }
}
