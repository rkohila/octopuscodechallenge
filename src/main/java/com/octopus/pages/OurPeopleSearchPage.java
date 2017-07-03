package com.octopus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;



public class OurPeopleSearchPage {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public OurPeopleSearchPage(WebDriver driver){
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver,30);
    }



    public void acceptCookieModal(){
        boolean displayed = driver.findElement(By.cssSelector(".modal-dialog>.modal-content")).isDisplayed();
        System.out.println("displayed = " + displayed);
        WebElement element = driver.findElement(By.cssSelector(".modal-footer>a"));

        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        actions.click();
        actions.build().perform();

    }

    public void  basicSearch(String searchText){

        focusOnSearchContainer();

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".inputOne")));
        WebElement searchElement = driver.findElement(By.cssSelector(".inputOne"));

        waitForElement();

        Actions actions = new Actions(driver);
        actions.moveToElement(searchElement);
        actions.click();
        actions.sendKeys(searchText);
        actions.build().perform();

    }

    private void waitForElement() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void focusOnSearchContainer() {
        WebElement element = driver.findElement(By.cssSelector(".search-container"));
        Actions searchContainerAction = new Actions(driver);
        searchContainerAction.moveToElement(element);
        searchContainerAction.perform();
    }

    public List<String> searchResults(){
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".row.searchResults.effect-3")));
        List<WebElement> searchResultNames = driver.findElements(By.cssSelector(".row.searchResults.effect-3>a>div>h2"));
        List<String> searchNameList = new ArrayList<String>();
        
        for(WebElement searchResult : searchResultNames){
            System.out.println("searchResult.getText() = " + searchResult.getText());
            searchNameList.add(searchResult.getText().toLowerCase());
        }
        return searchNameList;

    }

    public List<String> searchResultTeamNames(){
        List<WebElement> searchResultTeamNameList = driver.findElements(By.cssSelector(".row.searchResults.effect-3>a>div>p"));
        List<String> searchNameList = new ArrayList<String>();

        for(WebElement searchResult : searchResultTeamNameList){
            searchNameList.add(searchResult.getText().toLowerCase());
        }
        return searchNameList;

    }

    public void selectSearchResult(){
        List<WebElement> searchResultNames = driver.findElements(By.cssSelector(".row.searchResults.effect-3"));
        WebElement webElement = searchResultNames.get(0);
        webElement.click();

    }

    public void sortBy(String order){
        WebElement selectElement = driver.findElement(By.cssSelector(".selectOne>div>select"));
        Select select = new Select(selectElement);

        if(order.equals("desc")) {
            select.selectByValue("Z-A");
        }

    }

    public boolean isPostCodeDisplayed(){
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".inputTwo"))));
        return driver.findElement(By.cssSelector(".inputTwo")).isDisplayed();
    }

    public void searchByPostcode(String postCode){
        WebElement element = driver.findElement(By.cssSelector(".inputTwo"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(postCode);
        actions.build().perform();
    }

    public void selectTeam(String selectTeamName){
        focusOnSearchContainer();

        List<WebElement> elements = driver.findElements(By.cssSelector(".selectTwo>div>div>ul>li"));
        for(WebElement webElement : elements){
            String teamName = webElement.findElement(By.cssSelector("a>span")).getText();
            if(teamName.equalsIgnoreCase(selectTeamName)) {
                 webElement.click();
                 return;
            }
        }

    }

   public void selectATeam(String teamName){
       focusOnSearchContainer();
       WebElement selectElement = driver.findElement(By.cssSelector(".selectTwo>div>select"));
       Select select = new Select(selectElement);
       select.selectByValue(teamName);

   }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }



}