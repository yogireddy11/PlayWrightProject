package practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class AutomateSpider extends BaseOne {

    @Test(priority = 1, enabled = false)
    public void registerPage() {
        //SignUp page
        page.locator("#name").fill("Ojas");
        page.locator("#email").fill("gambeera@gmail.com");
        page.locator("#password").fill("gambeera123");
        page.locator("//button[text()='Register']").click();
        Locator status = page.locator("(//div[@role=\"status\"])[1]");
        System.out.println(status.textContent());
        Locator loginPage = page.locator("//h1[text()='Login']");
        loginPage.waitFor();
        assertThat(loginPage).isVisible();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("Login Successful")));
        System.out.println(page.title() + " | " + page.url());
        //Login Page
        page.locator("#email").fill("gambeera@gmail.com");
        page.locator("#password").fill("gambeera123");
        page.locator("//button[text()='Login']").click();
        System.out.println(status.textContent());
    }

    @Test(priority = 2, enabled = false)
    public void handleButtons() {
        page.locator("//section[text()='Button']").click();
        page.locator("#btn").click();
        Locator yesTxt = page.locator("//span[@class=\"text-green-600 p-1 px-2 ms-2 rounded-md\"]");
        System.out.println(yesTxt.textContent());
        page.locator("#btn_two").click();
        System.out.println(yesTxt.textContent());


        Locator rightClick = page.locator("//a[text()='Right Click']");  //Right Click page
        if (rightClick.isVisible()) {
            rightClick.click();
            page.locator("#btn_a").click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
            page.locator("(//div[@class=\"py-1 ps-1 hover:bg-orange-300\"])[1]").click();
            System.out.println(yesTxt.textContent() + " In the right click page!!");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("Right click page")));
        } else {
            throw new RuntimeException();
        }

        Locator doubleClick = page.locator("//a[text()='Double Click']");
        if (doubleClick.isVisible()) {
            doubleClick.click();   // Double click
            page.locator("#btn_b").dblclick();
            System.out.println(yesTxt.textContent() + " Double click");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("Double click page")));
        } else {
            throw new RuntimeException();
        }

        Locator submitClick = page.locator("//a[text()='Submit Click']");
        if (submitClick.isVisible()) {
            submitClick.click();   //Submit Click
            page.locator("#sat_b").click();
            page.locator("#btn_abh").click();
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("Submit page")));

        } else {
            throw new RuntimeException();
        }
    }

    @Test(priority = 3, enabled = false)
    public void handleLinks() {

        page.locator("//section[text() = 'Link']").click();
        System.out.println("Page Title | " + page.title());
        page.waitForSelector("//a[@class=\"text-[14px]\"]");
        Locator link = page.locator("//a[@class=\"text-[14px]\"]");
        int count = link.count();
        System.out.println("Count of the links | " + count);

        for (int i = 0; i < count; i++) {
            String href = link.nth(i).getAttribute("href");
            String text = link.nth(i).innerText().trim();
            System.out.println((i + 1) + ". Text: " + text + " | Href: " + href);
            if ((i + 1) == 3) {
                continue;
            }
            link.nth(i).click();
            page.locator("//button[@class=\"bg-[#f97316] text-white rounded-sm px-3 text-3xl font-bold\"]").click();
        }

        page.locator("//a[text()='Link in New Tab']").click();

        for (int i = 0; i < count; i++) {
            if ((i + 1) == 3) {
                continue;
            }

            int finalI = i;
            Page newTab = page.waitForPopup(() -> {
                link.nth(finalI).click();
            });
            newTab.waitForLoadState();

            Locator img = newTab.locator("//img[@class=\" w-full h-full object-cover z-10 rounded-t-md\"]");
            for (Locator getImg : img.all()) {
                System.out.println(i + " Product URL | " + getImg.getAttribute("src"));
            }
            Locator name = newTab.locator("//h2[@class=\" w-full text-lg py-1\"]");
            for (Locator getName : name.all()) {
                System.out.println(i + " Product Name | " + getName.textContent());
            }
            newTab.close();
            page.bringToFront();
        }
    }

    @Test(priority = 4, enabled = false)
    public void handleCheckBox() throws InterruptedException {
        page.locator("//section[text()='Check Box']").click();
        System.out.println(page.url());
        page.locator("//h1[text()='Checkout Page']").waitFor();
        Locator sm = page.locator("(//main[@class=\"flex gap-10 ml-4\"])[1]/div/input");
        for (Locator checkBox : sm.all()) {
            if (!checkBox.isChecked()) {
                checkBox.check();
            }
            Thread.sleep(100);
        }
        for (Locator unBox : sm.all()) {
            if (unBox.isChecked()) {
                unBox.uncheck();
                ;
            }
            Thread.sleep(100);

        }
    }

    @Test(priority = 5,enabled = false)
    public void handleRadioButton() throws InterruptedException {
        page.locator("//section[text()='Radio Button']").click();
        page.locator("//h1[text()='Checkout Page']").waitFor();
        Locator radio = page.locator("//main[@class=\"flex flex-wrap justify-between w-full\"]/div/input");
        System.out.println("Count of available options | " + radio.count());

        for (int i = 0; i < radio.count(); i++) {
            Locator sinRadio = radio.nth(i);
            if (!sinRadio.isChecked()) {
                sinRadio.check();
                sleep(100);
            }
        }
    }

    @Test(priority = 6)
    public void handleImages(){

    }

    private void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }
}
