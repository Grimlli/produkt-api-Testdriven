package com.example.produktapi;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Selenium {
    @Test
    public void cheakTitle(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        WebElement h1Text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1"));

        assertEquals("Testdriven utveckling - projekt",h1Text.getText(),"Test FAILD");

        driver.quit();
    }


    @Test
    public void numberOfProductsShouldBeTwenty(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        // Temp: Slows the code down so the site can load. Did not figure out how to do it with list.
        WebElement waiter = new WebDriverWait(driver,
                Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated((By.className("productItem"))));

        List<WebElement> product = driver.findElements(By.className("productItem"));

        assertEquals(20,product.size());

        driver.quit();
    }

    @Test
    public void assertThePriceForTheProductIsRight(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");
        String[] expected = {"109.95","22.3","55.99","15.99","695","168"};
        for(int i = 1; i<6; i++){
            WebElement waiter = new WebDriverWait(driver,
                    Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated((By.xpath(
                            "//*[@id=\"productsContainer\"]/div/div["+i+"]/div/div/p"))));
            String[] result = waiter.getText().split("\\R");

            assertEquals(expected[i-1],result[1]);
        }

        driver.quit();
    }

    // Test Push
}
