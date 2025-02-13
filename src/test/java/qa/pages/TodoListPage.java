package qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class TodoListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "new-todo")
    private WebElement todoInput;

    @FindBy(css = ".todo-list li")
    private List<WebElement> todoItems;

    @FindBy(xpath = "//*[@id='toggle-all']")
    private WebElement selectAll;

    @FindBy(css = ".filters li a")
    private List<WebElement> filterButtons;

    @FindBy(css = ".clear-completed")
    private WebElement clearCompletedButton;

    @FindBy(css="ul.filters")
    private  WebElement footer;


    @FindBy(xpath="//*[@id='root']/footer/span")
    private  WebElement footer_itemcount;

    public TodoListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Method to add a todo item
    public void addTodoItem(String itemText) {
        todoInput.sendKeys(itemText);
        todoInput.sendKeys(Keys.ENTER);
    }

    // Method to toggle (mark as completed) a todo item
    public void toggleTodoItem(String itemText) {
        for (WebElement item : todoItems) {
            if (item.getText().equals(itemText)) {
                WebElement checkbox = item.findElement(By.cssSelector("input.toggle"));
                checkbox.click();
                break;
            }
        }
    }

    // Method to get the current todo item count
    public int getTodoCount() {
        return todoItems.size();  // Return the number of todo items
    }

    // Method to click the filter (e.g., All, Active, Completed)
    public void clickFilter(String filterName) {
        for (WebElement filter : filterButtons) {
            if (filter.getText().equalsIgnoreCase(filterName)) {
                filter.click();
                break;
            }
        }
    }

    // Method to clear completed todo items
    public void clearCompleted() {
        clearCompletedButton.click();
    }

    // Method to check if a todo item is visible
    public boolean isTodoItemVisible(String itemText) {
        for (WebElement item : todoItems) {
            if (item.getText().equals(itemText)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if a todo item is completed
    public boolean isTodoItemCompleted(String itemText) {
        for (WebElement item : todoItems) {
            if (item.getText().equals(itemText)) {
                WebElement checkbox = item.findElement(By.cssSelector("input.toggle"));
                return checkbox.isSelected();
            }
        }
        return false;
    }

    // Method to complete a todo item by clicking the checkbox
    public void completeTodoItem(String itemText) {
        for (WebElement item : todoItems) {
            if (item.getText().equals(itemText)) {
                WebElement checkbox = item.findElement(By.cssSelector("input.toggle"));
                if (!checkbox.isSelected()) {
                    checkbox.click();  // Mark as completed if it's not already completed
                }
                break;
            }
        }
    }

    // Method to click the "Clear completed" button
    public void clickClearCompletedButton() {
        WebElement clearButton = wait.until(ExpectedConditions.elementToBeClickable(clearCompletedButton));
        clearButton.click();  // Click the "Clear completed" button
    }

    // New method to get visible todo items after filtering
    public List<WebElement> getVisibleTodoItems() {
        return todoItems;  // Return the list of todo items visible on the page
    }

    //Method to verify footer is loaded
    public boolean verifyFooter(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.invisibilityOf(footer));
    }

    //Method to select all the todo items to mark it as complete
    public void setSelectAll(){
        selectAll.click();
    }

    public String getfooter_itemCount(){
        return footer_itemcount.getText();
    }
    public int verifyItemscount(){
        String todo_count=getfooter_itemCount();
        String numberStr = todo_count.replaceAll("[^0-9]", ""); // This removes all non-digit characters
        return Integer.parseInt(numberStr);
    }
}
