Feature: Octopus Search

  As a new user trying to search  a page

  @run
  Scenario: Search by name and expect to see the person
    Given the user opens the octopus our people link
    When the user enters "Adam" in search text
    Then the user expects to see Adam's name
    When the user clicks on the Adam's picture
    Then the user expects to see Adam's page

  @run
  Scenario: Search text, sort by descending order and verify the order
    Given the user opens the octopus our people link
    When the user enters "ab" in search text
    And the user selects the sort by option as descending order
    Then the user expects to see names containing ab
    And the names in descending alphabetical order

  @run
  Scenario: Select Business development team and verify postcode
    Given the user opens the octopus our people link
    When the user selects Business development team from teams
    Then the user expects to display search by postcode
    When the user enters the postcode as EC1N
    Then the user expects to see team members of Business development team


  Scenario: Select Business development team, search text, search postcode and verify the results

    Given the user opens the octopus our people link
    When the user selects Business development team from teams
    Then the user expects to display search by postcode
    When the user enters "ab" in search text
    And  the user enters the postcode as EC1
    And the user selects the sort by option as descending order
    Then the user expects to see the team members in descending order

  Scenario: Select Client relations team and verify the post code should not present
    Given the user opens the octopus our people link
    When the user selects Client relations team from teams
    Then the user not expect to see search by postcode
    When the user enters "ab" in search text
    And the user selects the sort by option as descending order
    Then the user expects to see the team members in descending order
    Then the user expects to see team members of Client relations team

  Scenario: Select two teams and expect the results from both
    Given the user opens the octopus site
    When the user selects Sales support team
    And the user selects the Multi manager team
    Then the user expects to see both the team members

  Scenario Outline: : Invalid search should returns no search results
    Given the user opens the octopus our people link
    When the user enters <inputdata> in search text
    Then  the user expects to see No Results found
    Examples:
      | inputdata |
      | "1234"    |
      | "A12A     |
      | "A!&A     |




  Scenario: Select Business development team with invalid post code and expects no search results

    Given the user opens the octopus our people link
    When the user selects Business development team from teams
    Then the user expects to display search by postcode
    When the user enters the postcode as AA1
    Then the user expects to see No Results found


  Scenario: Search by Name Adam Said is not returning any results
    Given the user opens the octopus site
    When the user enters the search text as Adam Said
    Then  the user expects to see No Results found















