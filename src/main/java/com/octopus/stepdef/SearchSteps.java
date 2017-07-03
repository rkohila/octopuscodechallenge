package com.octopus.stepdef;

import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import com.octopus.pages.OurPeopleSearchPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static junit.framework.Assert.assertTrue;


public class SearchSteps {
    
    private WebDriver driver;
    OurPeopleSearchPage ourPeopleSearchPage;
    private static final String URL = "https://octopusinvestments.com/adviser/about-us/our-people";

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Given("^the user opens the octopus our people link$")
    public void the_user_opens_the_octopus_our_people_link() throws Throwable {
        driver.get(URL);
        ourPeopleSearchPage = PageFactory.initElements(driver,OurPeopleSearchPage.class);
        ourPeopleSearchPage.acceptCookieModal();
        waitForPageLoad(driver);


    }

    @When("^the user enters \"([^\"]*)\" in search text$")
    public void the_user_enters_in_search_text(String searchtext) throws Throwable {
        ourPeopleSearchPage.basicSearch(searchtext);
    }

    static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

            @Override
            public boolean apply(WebDriver input) {
                return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
            }

        };
        wait.until(pageLoaded);
    }


    @Then("^the user expects to see Adam's name$")
    public void the_user_expects_to_see_Adam_s_name() throws Throwable {
        List<String> nameList = ourPeopleSearchPage.searchResults();
        assertTrue("Name list doesn't contains text - Adam", validateNameList(nameList, "adam"));
    }

    @When("^the user selects (.*) from teams$")
    public void the_user_selects_Business_development_team_from_teams(String team) throws Throwable {
          ourPeopleSearchPage.selectATeam(team);
    }

    @Then("^the user expects to see names containing (.*)$")
    public void the_user_expects_to_see_names_containing_Ab(String text) throws Throwable {
        List<String> nameList = ourPeopleSearchPage.searchResults();
        assertTrue("Name list doesn't contains text - " + text, validateNameList(nameList, text));
    }

    private boolean validateNameList(List<String> nameList,String text) {
        boolean retValue = true;
        for(String name : nameList){
            if(!name.toLowerCase().contains(text.toLowerCase())){
                retValue=false;
            }
        }
        return retValue;
    }

    @And("^the names in descending alphabetical order$")
    public void the_names_in_descending_alphabetical_order() throws Throwable {
        List<String> nameList = ourPeopleSearchPage.searchResults();
        assertTrue(Ordering.natural().reverse().isOrdered(nameList));
    }

    @And("^the user selects the sort by option as descending order$")
    public void the_user_selects_the_sort_by_option_as_descending_order() throws Throwable {
        ourPeopleSearchPage.sortBy("desc");
    }

    @Then("^the user expects to display search by postcode$")
    public void the_user_expects_to_display_search_by_postcode() throws Throwable {
       assertTrue("Search by Postcode id not displayed : ", ourPeopleSearchPage.isPostCodeDisplayed());
    }

    @When("^the user enters the postcode as (.*)$")
    public void the_user_enters_the_postcode_as_EC_N(String postCode) throws Throwable {
        ourPeopleSearchPage.searchByPostcode(postCode);
    }

    @Then("^the user expects to see team members of that postcode$")
    public void the_user_expects_to_see_team_members_of_that_postcode() throws Throwable {
        List<String> nameList = ourPeopleSearchPage.searchResults();
        assertTrue(nameList.size()>0);
    }

    @Then("^the user expects to see team members of (.*)$")
    public void the_user_expects_to_see_team_members_of_Business_development_team(String teamName) throws Throwable {
        List<String> searchResultTeamName = ourPeopleSearchPage.searchResultTeamNames();
        assertTrue("Name list doesn't contains text - " + teamName, validateNameList(searchResultTeamName, teamName));

    }

    @When("^the user clicks on the Adam's picture$")
    public void the_user_clicks_on_the_Adam_s_picture() throws Throwable {
         ourPeopleSearchPage.selectSearchResult();
    }

    @Then("^the user expects to see Adam's page$")
    public void the_user_expects_to_see_Adam_s_page() throws Throwable {
          assertTrue(ourPeopleSearchPage.getCurrentUrl().contains("adam"));
    }
}
