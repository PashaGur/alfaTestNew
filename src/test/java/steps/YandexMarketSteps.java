package steps;


import YandexMarket.pages.YandexMarketPage;
import cucumber.api.PendingException;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import hooks.Hooks;
import static hooks.Hooks.driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class YandexMarketSteps {

    private YandexMarketPage yandexMarketPage;

    private final String CHROME = "chrome";
    private final String FIREFOX = "firefox";
    private final String EDGE = "edge";


    @Допустим("Были прогружены данные о тестовом браузере \"([^\"]*)\"$")
    public void БылиПрогруженыДанныеОТестовомБраузере(String browser) {
        switch (browser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        yandexMarketPage = new YandexMarketPage(driver);
    }


    @Когда("^пользователь зашел на сайт \"([^\"]*)\"$")
    public void пользовательЗашелНаСайт(String site) {
        driver.get(site);
    }

    @Когда("^пользователь открыл браузер и развернул его на весь экран$")
    public void пользовательОткрылБраузерИРазвернулЕгоНаВесьЭкран() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @И("^пользователь перешел в яндекс маркет$")
    public void пользовательПерешелВЯндексМаркет() {
        yandexMarketPage.goToYandexMarket();
    }

    @И("^пользователь перешел в раздел 'Электроника'$")
    public void пользовательПерешелВРазделЭлектроника() {
        yandexMarketPage.goToElectronic();
    }

    @И("^пользователь перешел в раздел 'Мобильные телефоны'$")
    public void пользовательПерешелВРазделМобильныеТелефоны() {
        yandexMarketPage.goToMobilePhones();
    }

    @И("^пользователь перешел в раздел 'Наушники'$")
    public void пользовательПерешелВРазделНаушники() {
        yandexMarketPage.goToHeadPhones();

    }

    @И("^пользователь перешел в расширенный поиск$")
    public void пользовательПерешелВРасширенныйПоиск() {
        yandexMarketPage.goToAllFiltersSearch();
    }

    @И("^пользователь задал начальную сумму поиска \"([^\"]*)\" рублей$")
    public void пользовательЗадалНачальнуюСуммуПоискаРублей(String sum) {
        yandexMarketPage.typeStartingPrice(sum);
    }

    @И("^пользователь выбрал производителя \"([^\"]*)\"$")
    public void пользовательВыбралПроизводителя(String manufacturer) {
        yandexMarketPage.selectManufacturer(manufacturer);
    }

    @И("^пользователь нажимает на кнопку 'Показать подходящие'$")
    public void пользовательНажимаетНаКнопкуПоказатьПодходящие() {
        yandexMarketPage.clickAcceptSearchParamsKey();
    }

    @Тогда("^система проверяет, что элементов \"([^\"]*)\"$")
    public void системаПроверяетЧтоЭлементов(String elementsCountToCheck) {
        yandexMarketPage.checkCount(Integer.parseInt(elementsCountToCheck));
    }

    @И("^пользователь запоминает первый элемент в списке$")
    public void пользовательЗапоминаетПервыйЭлементВСписке() {
        yandexMarketPage.setFirstFilteredElem();
    }

    @И("^пользователь вводит в поиск запомненное значение$")
    public void пользовательВводитВПоискЗапомненноеЗначение() {
        yandexMarketPage.fillElementToSearchField();
    }

    @И("^пользователь нажимает кнопку 'Найти'$")
    public void пользовательНажимаетКнопкуНайти() {
        yandexMarketPage.pressSearchKey();
    }

    @Тогда("^наименование товара соответствует запомненому значению$")
    public void наименованиеТовараСоответствуетЗапомненомуЗначению() {
        yandexMarketPage.checkFirstElem();
    }

    @И("^пользователь выбрал сортировку по цене$")
    public void пользовательВыбралСортировкуПоЦене() {
        yandexMarketPage.chooseSortByPrice();
    }

    @Тогда("^элементы отсортированы верно$")
    public void элементыОтсортированыВерно() {
        yandexMarketPage.checkSorting();
    }
}

