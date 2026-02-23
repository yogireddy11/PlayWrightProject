package practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test(priority = 5, enabled = false)
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

    @Test(priority = 6, enabled = false)
    public void handleImages() {
        page.locator("//section[text()='Image']").click();
        Locator img = page.locator("//img[@title=\"Image tooltip\"]");
        img.waitFor();
        int naturalWidth = (int) img.evaluate("img => img.naturalWidth");
        int naturalHeight = (int) img.evaluate("img => img.naturalHeight");
        System.out.println("Natural Width: " + naturalWidth);
        System.out.println("Natural Height: " + naturalHeight);

        page.locator("//a[text()='Clickable Image']").click();
        Locator clickableImg = page.locator("//a[@title=\"Mens Clothing\"]");
//        int naturalWidth1 = (int) clickableImg.evaluate("img => img.naturalWidth");
//        int naturalHeight1 = (int) clickableImg.evaluate("img => img.naturalHeight");
//        System.out.println("Natural Width: " + naturalWidth1);
//        System.out.println("Natural Height: " + naturalHeight1);
        clickableImg.click();
        Locator getItemName = page.locator("//h2[@class=\"font-bold text-xl pb-3\"]");
        System.out.println(getItemName.textContent());

        page.locator("//a[text()='Broken Image']").click();
        Locator brokenImg = page.locator("//section[@class=\"flex gap-6\"]/figure/img");
        page.locator("(//figure[@class=\"border-[1px] basis-[22%] shadow\"])[4]").waitFor();

        for (int i = 0; i < brokenImg.count(); i++) {
            Locator getImg = brokenImg.nth(i);

            int naturalWidth2 = (int) getImg.evaluate("img => img.naturalWidth");

            if (naturalWidth2 == 0) {
                System.out.println("Broken Image Found: " + getImg.getAttribute("src"));
            } else {
                System.out.println("Image loaded: " + getImg.getAttribute("src"));

            }

        }

    }

    @Test(priority = 7, enabled = false)
    public void handleDropdown() throws InterruptedException {
        page.locator("//section[text()='Dropdown']").click();

        Locator selCountry = page.locator("#select3");
        for (int i = 0; i < selCountry.count(); i++) {
            Locator getItems = selCountry.nth(i);
            System.out.print(getItems.textContent());
        }
        System.out.println();
        selCountry.selectOption(new SelectOption().setLabel("India"));
        sleep(500);

        Locator selState = page.locator("#select5");
        for (int i = 0; i < selState.count(); i++) {
            Locator getItems = selState.nth(i);
            System.out.print(" | " + getItems.textContent());
        }
        System.out.println();
        selState.selectOption(new SelectOption().setValue("Andhra Pradesh"));
        sleep(500);

        Locator selCity = page.locator("(//select[contains(@class, 'transition-all duration-150')])[4]");
        for (int i = 0; i < selCity.count(); i++) {
            Locator getItems = selCity.nth(i);
            System.out.println(getItems.textContent());
        }
        System.out.println();
        selCity.selectOption(new SelectOption().setIndex(14));
        sleep(500);
        page.locator("#continuebtn").click();

        //Multi Select Dropdown
        page.locator("//a[text()='Multi Select']").click();
        Locator multiSelect = page.locator("#select-multiple-native");
        multiSelect.selectOption(new SelectOption().setLabel("Solid Gold Petite Mi..."));
        sleep(500);
        multiSelect.selectOption(new SelectOption().setLabel("Mens Casual Slim Fit..."));
        sleep(500);
        page.locator("//button[@class=\"bg-orange-500 p-2 text-white rounded w-[150px]\"]").click();

    }

    @Test(priority = 8, enabled = false)
    public void handleWebTables() throws InterruptedException {

        page.locator("text=Web Table").click();
        sleep(500);
        Locator table = page.locator("table.w-full");
        // Headers
        Locator headers = table.locator("thead th");
        for (int i = 0; i < headers.count(); i++) {
            System.out.print(headers.nth(i).innerText() + " | ");
        }
        System.out.println();
        // Rows
        Locator rows = table.locator("tbody tr");
        for (int i = 0; i < rows.count(); i++) {
            Locator cells = rows.nth(i).locator("td");
            for (int j = 0; j < cells.count(); j++) {
                System.out.print(cells.nth(j).innerText() + " | ");
            }
            System.out.println();
        }
    }

    @Test(priority = 9, enabled = false)
    public void handleFormValidation() throws InterruptedException {

        page.locator("text=FormValidation").click();
        Locator subBtn = page.locator("//button[@class=\"bg-orange-600 rounded-md text-white py-2 px-4 mx-2 ms-0\"]");
        subBtn.click();
        Locator error = page.locator("//p[@class=\"text-red-500 text-sm\"]");
        int errorCount = error.count();
        for (int i = 0; i < errorCount; i++) {
            Locator getTxt = error.nth(i);
            System.out.println((i + 1) + " | " + getTxt.textContent());
        }
        page.locator("#fullName").fill("Peddi");
        final String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final String PHONE_REGEX = "^[6-9]\\d{9}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher("peddi123@gmail.com");
        page.locator("#emailId").fill("peddi123@gmail.com");
        matcher.matches();
        page.locator("#password").fill("Peddi@123");
        Pattern phnPattern = Pattern.compile(PHONE_REGEX);
        Matcher phnMatcher = phnPattern.matcher("9875463210");
        phnMatcher.matches();
        page.locator("#mobile").fill("9875463210");
        Locator dropdown = page.locator("#city");
        dropdown.selectOption(new SelectOption().setValue("hyd"));
        page.setInputFiles("#resume", Paths.get("C:\\Users\\yogireddy\\Downloads\\XPATH.pdf"));

        Locator multiSel = page.locator("//select[contains(@class, 'MuiNativeSelect-select MuiNativeSelect-outlined')]");
        multiSel.selectOption(new String[]{"CSS", "HTML", "Javascript"});
        subBtn.click();
        System.out.println("Error count | " + errorCount);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("Form Registration!!.png")));


    }

    @Test(priority = 9, enabled = true)
    public void scrollThePage() throws InterruptedException {
        page.locator("(//section[text()=\"Scroll\"])[1]").click();
        page.locator("(//section[text()=\"Scroll\"])[2]").click();
        Page newTab = page.waitForPopup(() ->
                page.locator("//a[@href=\"/ui/scroll/newTabVertical\"]").click()
        );
       // newTab.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        newTab.keyboard().press("End");
        Locator checkbox =   newTab.locator("//input[@type=\"checkbox\"]");
        checkbox.scrollIntoViewIfNeeded();
        checkbox.check();
        newTab.locator("//button[text()='Accept Our Policy']").click();
        newTab.close();

        page.locator("//a[@href=\"/ui/scroll/newHorizontal\"]").click();

        Page horTab = page.waitForPopup(() ->
                page.locator("//a[@href=\"/ui/scroll/newTabHorizontal\"]").click()
                );
        horTab.evaluate("window.scrollBy(1000,0)");
        sleep(2000);
        horTab.close();
    }

    private void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }
}
