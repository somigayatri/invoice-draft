Feature: Quotes

  Scenario: User goes to Create a New Quote page
    Given User is on Xero Dashboard
    And clicks Business tab on the navbar menu
    And clicks Quotes option from the dropdown menu
    And User clicks on New Quote Button
    When User is navigated to the New Quote Page
    Then User creates a new quote with required params

  Scenario: User accepts a quote sent on behalf of client
    Given User is on Xero Dashboard
    And clicks Business tab on the navbar menu
    And clicks Quotes option from the dropdown menu
    When clicks Sent tab to view quotes that are already sent
    Then accepts all sent quotes

  Scenario: User creates a draft invoice from an accepted quote
    Given User is on Xero Dashboard
    And clicks Business tab on the navbar menu
    And clicks Quotes option from the dropdown menu
    And clicks Accepted tab to view quotes which are accepted
    When selects the recently accepted quote
    Then creates invoice from the quote

