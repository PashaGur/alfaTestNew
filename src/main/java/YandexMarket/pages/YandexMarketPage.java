package YandexMarket.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class YandexMarketPage extends PageFactory {

    private WebDriver driver;

    public YandexMarketPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Кнопка "Маркет"
     */
    private By yaMarket = By.cssSelector("[data-id=market]");

    /**
     * Кнопка Электоника
     */
    private By electronic = By.cssSelector("li[data-department=Электроника]");

    /**
     * Кнопка Мобильные телефоны
     */
    private By mobilePhones = By.xpath("//div[@class='theme_light']//a[text()='Мобильные телефоны']");


    private By headPhones = By.xpath("//div[@class='theme_light']//a[contains(text(),'Наушники')]");
    /**
     * Кнопка Все фильтры
     */
    private By allFilters = By.xpath("//span[text()='Все фильтры']");

    /**
     * Поле ввода нижнего порога цены
     */
    private By startPriceField = By.cssSelector("#glf-pricefrom-var");
    /**
     * Кнопка Показать подходящие
     */
    private By showSuitable = By.cssSelector(".button_action_show-filtered");

    private By filteredElements = By.cssSelector("div.n-snippet-cell2_type_product");

    private String firstFilteredElem;

    private By searchField = By.cssSelector("#header-search");

    private By searchKey = By.cssSelector("button[type=submit]");

    private By sortByPrice = By.xpath("//a[text()='по цене']");


    public void sleep() {
        try {
            wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод перехода в раздел Маркет
     */
    public void goToYandexMarket() {
        driver.findElement(yaMarket).click();
    }

    /**
     * Метод перехода в раздел Электроники
     */
    public void goToElectronic() {
        driver.findElement(electronic).click();
    }

    /**
     * Метод перехода в раздел Мобильные Телефоны
     */
    public void goToMobilePhones() {
        driver.findElement(mobilePhones).click();
    }

    public void goToHeadPhones() {
        driver.findElement(headPhones).click();
    }

    /**
     * Метод перехода в расширенный поиск
     */
    public void goToAllFiltersSearch() {
        driver.findElement(allFilters).click();
    }

    /**
     * Ввод в стартовую сумму значения 'sum'
     */
    public void typeStartingPrice(String sum) {
        driver.findElement(startPriceField).sendKeys(sum);
    }

    /**
     * Метод выбора производителя
     */
    public void selectManufacturer(String manufacturer) {
        driver.findElement(By.xpath("//label[text()='" + manufacturer + "']")).click();
    }

    /**
     * Метод нажатия Показать Подходящие
     */
    public void clickAcceptSearchParamsKey() {
        driver.findElement(showSuitable).click();
    }

    public void checkCount(int elementsCountToCheck) {
        List<WebElement> webElements = driver.findElements(filteredElements);
        checkElementsSize(webElements, elementsCountToCheck);
    }

    private static void checkElementsSize(List<WebElement> webElements, int elementsCountToCheck) {
        if (webElements.size() == elementsCountToCheck) {
            System.out.println("There are 12 elements on check page");
        } else {
            System.out.println("Elements of check page != 12, actual size = " + webElements.size());
        }
    }

    public void setFirstFilteredElem() {
        firstFilteredElem = driver.findElements(filteredElements).get(0).getText().split("\n")[1];
    }

    public void fillElementToSearchField() {
        driver.findElement(searchField).sendKeys(firstFilteredElem);
    }

    public void pressSearchKey() {
        driver.findElement(searchKey).click();
    }

    public void checkFirstElem() {
        checkFirstElem(firstFilteredElem);
        driver.quit();
    }

    private void checkFirstElem(String firstElemName) {
        try {
            driver.findElement(By.xpath("//a[@title='" + firstElemName + "']"));
            System.out.println(firstElemName + " was found by search");
        } catch (Exception e) {
            System.out.println("Failed to find " + firstElemName);
        }
    }

    public void chooseSortByPrice() {
        driver.findElement(sortByPrice).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkSorting() {
        List<WebElement> webElements =
                driver.findElements(By.xpath("//div[starts-with(@class,'n-snippet')]//div[@class='price']"));

        List<Integer> pricesList = new ArrayList<>();
        for (WebElement element : webElements) {
            pricesList.add(Integer.parseInt(element.getText().replaceAll("\\s[ \u20BD]", "")));
        }
        checkIfElementsSorted(pricesList);
        driver.quit();
    }

    private static void checkIfElementsSorted(List<Integer> pricesList) {
        boolean sorted = true;
        for (int i = 1; i < pricesList.size(); i++) {
            if (pricesList.get(i - 1) > pricesList.get(i)) {
                sorted = false;
                break;
            }
        }
        System.out.println("Are prices sorted? " + sorted);
    }
}

