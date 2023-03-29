import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Automation3 {
    @Test
    public void AutomateCargurusSite() throws InterruptedException {

        //  1. Navigate to cargurus.com
        WebDriver driver = new EdgeDriver();
        System.out.println("Testing starts");
        driver.manage().window().maximize();
        driver.get("https://www.cargurus.com");


        //  2. Click on Buy Used
        driver.findElement(By.className("ft-homepage-search__tabs__used-car")).click();

//  3. Verify that the default selected option in Makes dropdown is All Makes (Use Assert
//methods for all verifications

        String actualMake = driver.findElement(By.cssSelector("#carPickerUsed_makerSelect > option")).getText();
        String exeptedMake = "All Makes";
        Assert.assertEquals(actualMake, exeptedMake);

//  4. In Makes dropdown, choose Lamborghini
        driver.findElement(By.id("carPickerUsed_makerSelect")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/optgroup[2]/option[53]")).click();

//  5. Verify that the default selected option in Models dropdown is All Models

        String actualModel = driver.findElement(By.cssSelector("#carPickerUsed_modelSelect > option")).getText();
        String exeptedModel = "All Models";
        Assert.assertEquals(actualModel, exeptedModel);

//  6. Verify that Models dropdown options are [All Models, Aventador, Huracan, Urus,
//400GT, Centenario, Countach, Diablo, Espada, Gallardo, Murcielago

        Select select = new Select(driver.findElement(By.id("carPickerUsed_modelSelect")));
        List<WebElement> options = select.getOptions();
        List<String> expectedOptions = Arrays.asList("All Models", "Aventador", "Gallardo", "Huracan", "Urus", "400GT", "Centenario", "Countach", "Diablo", "Espada", "Murcielago");
        List<String> actualOptions = new ArrayList<String>();
        for (WebElement option : options) {
            actualOptions.add(option.getText());
        }
        Assert.assertEquals(actualOptions, expectedOptions);

////  7. In Models dropdown, choose Gallardo

        select.selectByVisibleText("Gallardo");

////  8. Enter 22031 for zip and hit search

        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031");
        driver.findElement(By.id("dealFinderForm_0")).click();
        Thread.sleep(2000);


//  9. In the results page, verify that there are 15 search results, excluding the first sponsored
//      result: To isolate the 15 results, excluding the first sponsored one, you can use a custom xpath which excludes the first result. For example:
////a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))] (find all <a> elements that have a common data-cg-ft attribute 'car-blade-link' (matche 16 results) and whose href attribute does not contain text 'FEATURED'
        List<WebElement> searchResults = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
        Assert.assertEquals(searchResults.size(), 15);
//  10. Verify that all 15 result's title text contains "Lamborghini Gallardo
        for (WebElement result : searchResults) {
            String title = result.findElement(By.xpath("//*[@id=\"cargurus-listing-search\"]/div/div/div[2]/div[2]/div[1]/div/div")).getText();
            Assert.assertTrue(title.contains("Lamborghini Gallardo"));
        }
//  11. From the dropdown on the left corner choose “Lowest price first” option and verify that
//    all 15 results are sorted from lowest to highest. You should exclude the first result since
//    it will not be a part of sorting logic.
//    To verify correct sorting, collect all 15 prices into a list, create a copy of it and sort in
//    ascending order and check the equality of the sorted copy with the original

        List<WebElement> prices = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-price']"));
        List<String> originalPrices = new ArrayList<String>();
        for (WebElement price : prices) {
            originalPrices.add(price.getText());
        }
        List<String> sortedPrices = new ArrayList<String>(originalPrices);
        Collections.sort(sortedPrices);
        Assert.assertEquals(originalPrices, sortedPrices);
//  12. From the dropdown menu, choose “Highest mileage first” option and verify that all 15
//    results are sorted from highest to lowest. You should exclude the first result since it will
//    not be a part of sorting logic

        List<WebElement> mileage = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-mileage']"));
        List<String> originalMileage = new ArrayList<String>();
        for (WebElement mile : mileage) {
            originalMileage.add(mile.getText());
        }
        List<String> sortedMileage = new ArrayList<String>(originalMileage);
        Collections.sort(sortedMileage);
        Collections.reverse(sortedMileage);
        Assert.assertEquals(originalMileage, sortedMileage);

//  13. On the left menu, click on Coupe AWD checkbox and verify that all results on the page
//    contains “Coupe AWD”


        WebElement coupeAwdCheckbox = driver.findElement(By.cssSelector("#cargurus-listing-search > div > div > div.Km1Vfz > div.hhhhHl > div.Fu6p_D > div:nth-child(2) > fieldset:nth-child(5) > ul > li:nth-child(1)"));
        coupeAwdCheckbox.click();
        Thread.sleep(2000);
        boolean allResultsContainCoupeAwd = true;
        for (WebElement result : driver.findElements(By.xpath("//div[@class='cg-listingCard']"))) {
            if (!result.getText().contains("Coupe AWD")) {
                allResultsContainCoupeAwd = false;
                break;
            }
        }

        // Print the result
        if (allResultsContainCoupeAwd) {
            System.out.println("All results contain Coupe AWD.");
        } else {
            System.out.println("Not all results contain Coupe AWD.");
        }
//  14. Click on the last result (get the last result dynamically, i.e., your code should click on the
//            last result regardless of how many results are there
        List<WebElement> lastResult = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
        lastResult.get(lastResult.size() - 1).click();
        Thread.sleep(2000);

//  15. Once you are in the result details page go back to the results page and verify that the
//            clicked result has “Viewed” text on it
        driver.navigate().back();
        Thread.sleep(2000);
        List<WebElement> viewed = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]//span[@class='cg-dealFinder-result-viewed']"));
        for (WebElement view : viewed) {
            String viewText = view.getText();
            Assert.assertTrue(viewText.contains("Viewed"));
        }
        driver.quit();


//     Remember: 90% of the exceptions and issues that you encounter can be resolved by
//     synchronizing your code, i.e., by observing what is your code trying to do and what is
//     actually happening on the UI and adjust your code by adding necessary waits. Make sure
//     to set an implicit wait and use Thread.sleep() as needed

    }
}
