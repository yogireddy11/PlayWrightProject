package practice;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class APITesting  {
    Playwright playwright;
    APIRequestContext requestContext;
    APIResponse response;

    @BeforeClass
    public void setup(){

        playwright = Playwright.create();
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json");
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.restful-api.dev")
                .setExtraHTTPHeaders(header)
        );
    }

    @Test(priority = 1, description = "Get Method", enabled = true)
    public void getMethod() {
        response = requestContext.get("/objects");
        System.out.println(response.status());
        System.out.println(response.text());
        assertEquals(response.status(), 200);

        response = requestContext.get("/objects?id=3&id=5&id=10");
        System.out.println(response.status() + " | " + response.statusText());
        System.out.println(response.text());
        assertEquals(response.status(), 200);

        response = requestContext.get("/objects/7");
        System.out.println(response.status() + " | " + response.statusText());
        System.out.println(response.text());
        assertEquals(response.status(), 200);

    }

    @Test(priority = 2, description = "Post Method", enabled = true)
    public void postMethod() {

        response = requestContext.post("/objects", RequestOptions.create().setData("""
                {
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 1849.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB"
                   }
                }"""));
        System.out.println(response.status() + " | " + response.statusText());
        System.out.println(response.text());
        assertEquals(response.status(), 200);
    }

    @Test(priority = 3, description = "Put Method", enabled = true)
    public void putMethod() {

        response = requestContext.put("/objects/7", RequestOptions.create().setData("""
                {
                   "name": "Apple MacBook Pro 16",
                   "data": {
                      "year": 2019,
                      "price": 2049.99,
                      "CPU model": "Intel Core i9",
                      "Hard disk size": "1 TB",
                      "color": "silver"
                   }
                }"""));
        System.out.println(response.text());
        assertEquals(response.status(), 200);
    }

    @Test(priority = 4, description = "patch Method", enabled = true)
    public void patchMethod() {
        response = requestContext.patch("/objects/7", RequestOptions.create().setData("""
                {
                   "name": "Apple MacBook Pro 16 (Updated Name)"
                }"""));
        System.out.println(response.text());
        assertEquals(response.status(), 200);
    }

    @Test(priority = 5, description = "Delete Method", enabled = true)
    public void deleteMethod() {
        response = requestContext.delete("/objects/6");
        System.out.println(response.text());
        System.out.println(response.status());
        assertEquals(response.status(), 200);
    }

    @AfterClass
    public void teardown(){
        response.dispose();
        playwright.close();
    }
}
