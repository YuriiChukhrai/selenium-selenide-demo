package com.util;

import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.openqa.selenium.Capabilities;

/**
 * @author: Yurii Chukhrai
 * @see <a href="https://projectlombok.org/features/Builder">Lombok - Builder</a>
 * @see <a href="https://www.w3.org/TR/webdriver1/#capabilities">Capabilities</a>
 * */
@Getter
@Builder
@ToString
public final class WebDriverInstDesc {

    @NonNull
    final private DriverManagerType browserName;

    @NonNull
    final private String browserVersion;

    final private Capabilities capabilities;

    final private boolean isRemoteWebDriver;
    final private String remoteUrl;
}
