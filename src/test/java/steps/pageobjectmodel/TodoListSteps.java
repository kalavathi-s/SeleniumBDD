package steps.pageobjectmodel;

import core.drivers.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.pages.TodoListPage;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class TodoListSteps {
    private WebDriver driver;
    private TodoListPage todoListPage;

    public TodoListSteps(){
        driver=DriverManager.getDriver();
        todoListPage = new TodoListPage(driver);
    }
    // Scenario 1: Adding a new todo item
    @Given("I am on the todo list page")
    public void iAmOnTheTodoListPage() {
        //
    }

    @When("I type {string} into the todo input field")
    public void iTypeIntoTheTodoInputField(String todoItem) {
        WebElement inputField = driver.findElement(By.id("todo-input"));
        inputField.sendKeys(todoItem);  // Type the todo item
    }

    @And("I press Enter")
    public void iPressEnter() {
        WebElement inputField = driver.findElement(By.id("todo-input"));
        inputField.sendKeys(Keys.RETURN);  // Simulate pressing Enter to submit the task
    }

    @Then("{string} should appear in the todo list")
    public void shouldAppearInTheTodoList(String todoItem) {

        assertTrue("todo items Count not matched",todoListPage.verifyItemscount()==todoListPage.getTodoCount());


        assertTrue("The todo item '" + todoItem + "' was not found in the list",
                todoListPage.isTodoItemVisible(todoItem));

    }

    // Scenario 2: Completing a todo item
    @Given("I have a todo item {string} in the list")
    public void iHaveATodoItemInTheList(String todoItem) {

        todoListPage.addTodoItem(todoItem);
    }

    @When("I click the checkbox next to {string}")
    public void iClickTheCheckboxNextTo(String todoItem) {
        todoListPage.toggleTodoItem(todoItem);
    }

    @Then("{string} should be marked as complete")
    public void shouldBeMarkedAsComplete(String todoItem) {
        assertTrue(todoListPage.isTodoItemCompleted(todoItem));
    }

    @Then("{string} should be marked as Active")
    public void shouldBeMarkedAsActive(String todoItem) {
        assertTrue("Active items count not matched",todoListPage.getTodoCount()==todoListPage.verifyItemscount());
        assertTrue(todoListPage.isTodoItemVisible(todoItem));
    }

    // Scenario 3: Uncompleting a todo item
    @Given("I have a completed todo item {string} in the list")
    public void iHaveACompletedTodoItemInTheList(String todoItem) {

        todoListPage.addTodoItem(todoItem);
        todoListPage.toggleTodoItem(todoItem);
    }

    @Then("{string} should be marked as incomplete")
    public void shouldBeMarkedAsIncomplete(String todoItem) {
        assertFalse(todoListPage.isTodoItemCompleted(todoItem));
    }

    // Scenario 4: Filtering for active, completed, and all todo items
    @Given("I have todo items {string} \\(active) and {string} \\(completed)")
    public void iHaveTodoItemsActiveAndCompleted(String activeItem, String completedItem) {

        todoListPage.addTodoItem(activeItem);
        todoListPage.addTodoItem(completedItem);
        todoListPage.toggleTodoItem(completedItem); // Mark completed item
    }

    @When("I click the {string} filter button")
    public void iClickTheFilterButton(String filterName) {
        todoListPage.clickFilter(filterName);  // Click the appropriate filter (e.g., Active, Completed, or All)
    }

    @Then("only {string} should be visible in the list")
    public void onlyShouldBeVisibleInTheList(String visibleItem) {
        boolean isVisible = todoListPage.getVisibleTodoItems().stream()
                .anyMatch(item -> item.getText().equals(visibleItem));
        assertTrue("The item '" + visibleItem + "' was not visible", isVisible);
    }

    @Then("both {string} and {string} should be visible in the list")
    public void bothShouldBeVisibleInTheList(String firstItem, String secondItem) {
        boolean firstItemVisible = todoListPage.getVisibleTodoItems().stream()
                .anyMatch(item -> item.getText().equals(firstItem));
        boolean secondItemVisible = todoListPage.getVisibleTodoItems().stream()
                .anyMatch(item -> item.getText().equals(secondItem));

        assertTrue("The item '" + firstItem + "' was not visible", firstItemVisible);
        assertTrue("The item '" + secondItem + "' was not visible", secondItemVisible);
    }

    // Scenario: Clearing completed todo items
    @Given("I have todo items {string} \\(active) and {string} \\(completed) for clearing")
    public void iHaveTodoItemsActiveAndCompletedForClearing(String activeItem, String completedItem) {

        todoListPage.addTodoItem(activeItem);
        todoListPage.addTodoItem(completedItem);
        todoListPage.toggleTodoItem(completedItem);  // Mark completed item
    }

    @When("I click the {string} button")
    public void iClickTheButtonForClearing(String buttonName) {
        todoListPage.clickClearCompletedButton();  // Assuming there's a method to click the "Clear completed" button
    }

    @Then("{string} should be removed from the list")
    public void shouldBeRemovedFromTheList(String itemToRemove) {
        boolean isRemoved = todoListPage.getVisibleTodoItems().stream()
                .noneMatch(item -> item.getText().equals(itemToRemove));
        assertTrue("The item '" + itemToRemove + "' was not removed", isRemoved);
    }

    @Then("{string} should still be in the list")
    public void shouldStillBeInTheList(String itemToRemain) {
        boolean isVisible = todoListPage.getVisibleTodoItems().stream()
                .anyMatch(item -> item.getText().equals(itemToRemain));
        assertTrue("The item '" + itemToRemain + "' was not visible", isVisible);
    }

    // Scenario: Deleting a todo item
    @When("I click the delete button next to {string}")
    public void iClickTheDeleteButtonNextTo(String todoItem) {
        // Wait until the delete button is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        actions.moveToElement(todoListPage.getVisibleTodoItems().get(0))  // Hover over the element
                .moveToElement(driver.findElement(By.xpath("//label[text()='" + todoItem + "']/following-sibling::button[@class='destroy' and @data-testid='todo-item-button']"))) // Move the mouse to the clickable element
                .click()                      // Perform the click action
                .build()
                .perform();

    }

    @Then("{string} should be removed from the todo list")
    public void shouldBeRemovedFromTheTodoList(String todoItem) {
        // Ensure the todo item is removed from the list
        boolean isRemoved = todoListPage.getVisibleTodoItems().stream()
                .noneMatch(item -> item.getText().equals(todoItem));  // Ensure the item is no longer visible
        assertTrue("The todo item '" + todoItem + "' was not removed", isRemoved);
    }


    // Scenario: Editing a todo item



    @When("I double-click {string}")
    public void i_double_click(String string) {
        Actions actions = new Actions(driver);
        actions.doubleClick(todoListPage.getVisibleTodoItems().get(0));

    }

    @When("I change the text to {string}")
    public void i_change_the_text_to(String string) throws InterruptedException {
        WebElement ele=todoListPage.getVisibleTodoItems().get(0);
        Actions actions=new Actions(driver);
        System.out.println(ele.toString());
        actions.doubleClick(ele).sendKeys(string).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
    }

    @Then("the todo item should now be {string}")
    public void the_todo_item_should_now_be(String string) {
        assertTrue("Item is not updated with new value",
                todoListPage.getVisibleTodoItems().get(0).getText().equals(string));
    }

    @Then("the todo items should be empty")
    public void the_todo_list_is_empty() {
     assertTrue("todo list is Not empty",todoListPage.verifyFooter());
    }

    @When("I mark All the items as completed")
    public void the_todo_list_is_completed() {
        todoListPage.setSelectAll();
    }

    @Then("the todo items should be empty1")
    public void the_todo_list_is_empty1() {

        assertTrue("todo list is Not empty",todoListPage.verifyFooter());
    }
    @When("I add list of task to todo list")
    public void i_add(DataTable dataTable) {
        List<String> items = dataTable.asList(String.class);
        for (String item : items) {
            WebElement inputField = driver.findElement(By.id("todo-input"));
            inputField.sendKeys(item);  // Type the todo item
            inputField.sendKeys(Keys.RETURN);  // Simulate pressing Enter to submit the task
        }
    }

    @Then("All the items should be marked as completed and Active todo items count should be 0")
    public void shouldAppearInTheTodoList1() {
        assertTrue("todo items Count not matched",todoListPage.verifyItemscount()==0);
    }

}
