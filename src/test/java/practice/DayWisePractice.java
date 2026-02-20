package practice;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DayWisePractice extends BaseOne{


    @Test(enabled = false)
    public void switchesImproved() {

        page.navigate("https://demoapps.qspiders.com/ui/frames?sublist=0");

        FrameLocator frame = page.frameLocator("iframe");
        frame.locator("#username").fill("abc");
        frame.locator("#password").fill("123");

        System.out.println(frame.locator("#username").inputValue());

        frame.locator("#submitButton").click();

        // ALERT HANDLING
        Page page1 = browser.newPage();
        page1.navigate("https://demo.automationtesting.in/Alerts.html");

        page1.onDialog(dialog -> {
            System.out.println("Alert Message: " + dialog.message());
            dialog.accept();
        });

        page1.locator(".btn-danger").click();

        // Confirm alert
        page1.onDialog(Dialog::dismiss);

        page1.getByText("Alert with OK & Cancel ").click();

        // Prompt alert
        page1.onDialog(dialog -> {
            dialog.accept("Im learning Playwright");
        });

        page1.getByText("Alert with Textbox ").click();

        // WINDOW HANDLING
        page1.navigate("https://demo.automationtesting.in/Windows.html");

        Page newTab = page1.waitForPopup(() -> {
            page1.locator("(//button[@class='btn btn-info'])[1]").click();
        });

        System.out.println("New Tab Title: " + newTab.title());

        newTab.close();
        page1.close();
    }


    @Test(enabled = false)
    public void assertionsDay5() {
        page.navigate("https://www.saucedemo.com/");
        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");
        page.keyboard().press("Enter");
        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
        Locator allItems = page.locator("//div[@id=\"inventory_container\" and @class=\"inventory_container\"]/div/div");
        assertThat(allItems).hasCount(6);
        page.locator("#add-to-cart-sauce-labs-bike-light").click();
        page.locator("#add-to-cart-sauce-labs-bolt-t-shirt").click();
        Locator itemCount = page.locator("//div[@id=\"shopping_cart_container\"]/a/span");
        System.out.println("Count of Add to card | " + itemCount.textContent());
        assertThat(itemCount).hasText("2");
        itemCount.click();
        Locator item1 = page.locator("//a[@id=\"item_0_title_link\"]/div");
        assertThat(item1).hasText("Sauce Labs Bike Light");
        Locator item2 = page.locator("//a[@id=\"item_1_title_link\"]/div");
        assertThat(item2).hasText("Sauce Labs Bolt T-Shirt");

        assertThat(page).hasTitle("Swag Labs");
    }

    @Test(enabled = true,priority = 1)
    public void verifyLoginPageDay2() {

        page.navigate("https://www.saucedemo.com/");
        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        System.out.println(page.title());
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(page.title()))); //Take screenshot
        Locator product = page.locator(".inventory_item");
        System.out.println(product.count());
        for (int i = 0; i < product.count(); i++) {
            System.out.println(product.nth(i).innerText());
        }
    }

    @Test(enabled = true, priority = 2)
    public void actionsClassDay3() {

        page.navigate("https://www.saucedemo.com/");
        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");
        page.keyboard().press("Enter");
        //page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        page.getByText("Sauce Labs Backpack").click();
        page.locator("#add-to-cart").click();
        page.locator(".shopping_cart_link").hover();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("AddToCard.png")));
        Locator addToCart = page.locator(".shopping_cart_link");
        addToCart.click();
        System.out.println(addToCart.count());
    }

    @Test(enabled = false)
    public void waitsDay3() {
        page.navigate("https://yogireddy-trials719.orangehrmlive.com/");
        page.locator("#txtUsername").fill("admin");
        page.locator("#txtPassword").fill("X@l8b2OJcU");
        page.keyboard().press("Enter");
        //  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        page.waitForSelector("#menu-content");
        System.out.println("Page Title | " + page.title() + "\n Page URL | " + page.url());
        page.locator("#menu-content li").nth(1).click();
        page.waitForSelector(".floating-add-btn");
        page.locator("//div[@class=\"fixed-action-btn floating-add-btn tooltipped\"]").click();
        page.locator("#selectedEmployee_value").fill("sysAdmin");
        page.locator("#user_name").fill("admin2");
        page.locator("//div[@id=\"essrole\"]").click();
        page.locator("#password").fill("Admin@123");
        page.locator("#confirmpassword").fill("Admin@123");
        page.locator("#modal-save-button").click();
    }
}
