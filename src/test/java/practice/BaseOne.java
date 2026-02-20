package practice;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;


public class BaseOne {

    Playwright playwright;
    Browser browser;
    Page page;
    public BrowserContext context;

    APIRequestContext requestContext;
    APIResponse response;

    @Parameters("browser")
    @BeforeClass()
    public void setup(@Optional("chrome") String browserName) {
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                throw new RuntimeException("No browser has opened!!");

        }
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = context.newPage();

        page.navigate("https://demoapps.qspiders.com/");
        page.locator("//p[text()='UI Testing Concepts']").waitFor();
        page.locator("//p[text()='UI Testing Concepts']").click();

    }

    @AfterClass
    public void teardown() {
        context.close();
        browser.close();
        playwright.close();

    }
}
