@CTS
Feature: Todo List Management
@test
  Scenario: Adding a new todo item
    Given I am on the todo list page
    When I type "Buy groceries" into the todo input field
    And I press Enter
    Then "Buy groceries" should appear in the todo list

   Scenario: Completing a todo item
     Given I have a todo item "Pay bills" in the list
     When I click the checkbox next to "Pay bills"
     Then "Pay bills" should be marked as complete

   Scenario: Uncomplete a todo item
     Given I have a completed todo item "Walk the dog" in the list
     When I click the checkbox next to "Walk the dog"
     Then "Walk the dog" should be marked as Active

  Scenario: Filtering for active todo items
    Given I have todo items "Buy groceries" (active) and "Pay bills" (completed)
    When I click the "Active" filter button
    Then only "Buy groceries" should be visible in the list

  Scenario: Filtering for completed todo items
    Given I have todo items "Buy groceries" (active) and "Pay bills" (completed)
    When I click the "Completed" filter button
    Then only "Pay bills" should be visible in the list

  Scenario: Filtering for all todo items
    Given I have todo items "Buy groceries" (active) and "Pay bills" (completed)
    When I click the "All" filter button
    Then both "Buy groceries" and "Pay bills" should be visible in the list

  Scenario: Clearing completed todo items
    Given I have todo items "Buy groceries" (active) and "Pay bills" (completed)
    When I click the "Clear completed" button
    Then "Pay bills" should be removed from the list
    And "Buy groceries" should still be in the list

    Scenario: Deleting a todo item
      Given I have a todo item "Pay bills" in the list
      When I click the delete button next to "Pay bills"
      Then "Pay bills" should be removed from the list

  Scenario: Editing a todo item
    Given I have a todo item "Walk the dog" in the list
    When I double-click "Walk the dog"
    And I change the text to "Walk the cat"
    And I press Enter
    Then the todo item should now be "Walk the cat"
@fail
  Scenario: Mark all the items as completed by clicking on select all button
    Given I am on the todo list page
    When I add list of task to todo list
    | Todo|
    | Pay bills     |
    | Walk the cat   |
    And I mark All the items as completed
    Then All the items should be marked as completed and Active todo items count should be 0
