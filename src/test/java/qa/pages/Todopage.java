package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Todopage {
    private WebDriver driver;
    private By todoInputBy = By.className("new-todo");
    private By todoCountBy = By.className("todo-count");
    private By toggleAllBy = By.className("toggle-all");
    private By tasksBy = By.cssSelector(".todo-list li");

    public Todopage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isTodoInputDisplayed() {
        return driver.findElement(todoInputBy).isDisplayed();
    }

    public String getTodoInputValue() {
        return driver.findElement(todoInputBy).getAttribute("value");
    }

    public void addTodo(String task) {
        WebElement todoInput = driver.findElement(todoInputBy);
        todoInput.sendKeys(task);
        todoInput.sendKeys(Keys.ENTER);
    }

    public String getTodoCountText() {
        return driver.findElement(todoCountBy).getText();
    }

    public void toggleTask(int index) {
        List<WebElement> tasks = driver.findElements(tasksBy);
        tasks.get(index).findElement(By.cssSelector("input.toggle")).click();
    }

    public void editTask(int index, String newTaskName) {
        List<WebElement> tasks = driver.findElements(tasksBy);
        WebElement taskLabel = tasks.get(index).findElement(By.cssSelector("label"));
        WebElement editInput = tasks.get(index).findElement(By.cssSelector(".edit"));
        taskLabel.click();
        editInput.clear();
        editInput.sendKeys(newTaskName);
        editInput.sendKeys(Keys.ENTER);
    }

    public int getNumberOfTasks() {
        return driver.findElements(tasksBy).size();
    }

    public void toggleAllTasks() {
        driver.findElement(toggleAllBy).click();
    }
}