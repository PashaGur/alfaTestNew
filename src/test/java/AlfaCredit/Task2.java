package AlfaCredit;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Task2 {

    private WebDriver driver;
    private AlfaCreditPage alfaCreditPage;

    /**
     * URL следующей формы по заполнению заявки на кредит
     */
    private final static String CORRECT_NEXT_URL = "https://anketa.alfabank.ru/alfaform-refpil/step2";

    /**
     * Ошибка обязательности поля
     */
    private final static String ERROR_MANDATORY_FIELD = "Поле обязательно для заполнения";

    /**
     * Фамилия для теста
     */
    private final static String LAST_NAME = "Гурин";
    /**
     * Имя для теста
     */
    private final static String FIRST_NAME = "Павел";
    /**
     * Отчество для теста
     */
    private final static String MIDDLE_NAME = "Павлович";
    /**
     * Пол м-true, ж-false
     */
    private final static Boolean GENDER = true;
    /**
     * Тестовый e-mail
     */
    private final static String EMAIL = "email@yandex.ru";
    /**
     * Тестовый номер телефона
     */
    private final static String PHONE_NUMBER = "9101234567";
    /***/
    private final static String WORK_REGION = "Санкт-Петербург";

    /**
     * Предустановка драйвера для теста
     */
    @Before
    public void setUp() {

        System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "src\\main\\resources\\MicrosoftWebDriver.exe");

        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
        //driver = new EdgeDriver();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://anketa.alfabank.ru/alfaform-refpil/step1");
        alfaCreditPage = new AlfaCreditPage(driver);
    }


    /**
     * Тест заголовка страницы
     */
    @Test
    public void headingTest() {
        String heading = alfaCreditPage.getHeadingText();
        Assert.assertEquals("Заявка на кредит наличными с возможностью рефинансирования", heading);
    }

    /**
     * Тест с номером телефона 1234567890
     */
    @Test
    public void oneToTenNumberTest() {
        String testingNumber = "1234567890";
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, testingNumber, EMAIL, WORK_REGION);
        waitASecond();
        System.out.println("Testing phoneNumber = " + testingNumber + ", Error text = " + page.getPhoneNumberError());
        Assert.assertEquals(CORRECT_NEXT_URL, driver.getCurrentUrl());
    }

    /**
     * Тест с номером короче и длиннее реально существующих
     */
    @Test
    public void shortAndLongNumberTest() {
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, "12345", EMAIL, WORK_REGION);
        Assert.assertNotNull(page.getPhoneNumberError());
        driver.navigate().refresh();

        AlfaCreditPage page2 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, "910123456789", EMAIL, WORK_REGION);
        System.out.println("Error text = " + page.getPhoneNumberError() + ", but testing number was cut after 10 digit");
        Assert.assertNotNull(page2.getPhoneNumberError());
    }

    /**
     * Тест с несуществующим форматом e-mail
     */
    @Test
    public void wrongEmailTest() {
        String emailError = "Указан недопустимый email";
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, "someText", WORK_REGION);
        Assert.assertEquals(emailError, page.getEmailError());
        driver.navigate().refresh();

        AlfaCreditPage page2 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, "email@iamnotevenexist", WORK_REGION);
        Assert.assertEquals(emailError, page2.getEmailError());
        driver.navigate().refresh();

        AlfaCreditPage page3 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, "00000@00000000.00", WORK_REGION);
        Assert.assertEquals(emailError, page3.getEmailError());
        driver.navigate().refresh();

        //        AlfaCredit page4 = AlfaCredit.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, "email@iamnotevenexist.somedomain", WORK_REGION);
        //        System.out.println();
        //        Assert.assertEquals(emailError, page4.getEmailError());
        //        driver.navigate().refresh();
    }

    @Test
    public void stringToPhoneNumberTest() {
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, "string", EMAIL, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page.getPhoneNumberError());
    }

    @Test
    public void emptyMandatoryFields() {
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(null, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page.getLastNameError());
        driver.navigate().refresh();

        AlfaCreditPage page2 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, null, MIDDLE_NAME, PHONE_NUMBER, EMAIL, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page2.getFirstNameError());
        driver.navigate().refresh();

        AlfaCreditPage page3 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, null, PHONE_NUMBER, EMAIL, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page3.getMiddleNameError());
        driver.navigate().refresh();

        AlfaCreditPage page4 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, null, EMAIL, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page4.getPhoneNumberError());
        driver.navigate().refresh();

        AlfaCreditPage page5 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, null, WORK_REGION);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page5.getEmailError());
        driver.navigate().refresh();

        AlfaCreditPage page6 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, null);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page6.getWorkRegionError());
        driver.navigate().refresh();

        AlfaCreditPage page7 = alfaCreditPage.continueWithInvalidValues("Гу", "Па", "Па", null, PHONE_NUMBER, EMAIL, null);
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page7.getGenderFieldError());
    }


    @Test
    public void wrongWorkRegionTest() {
        AlfaCreditPage page = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, "Несуществующий");
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page.getWorkRegionError());
        driver.navigate().refresh();
        AlfaCreditPage page2 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, "123");
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page2.getWorkRegionError());
        driver.navigate().refresh();
        AlfaCreditPage page3 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL,
                "яяяяяяяяяя_ яяяяяяяяяяяя3123123яяяяяяяяяяяяяяяяяяяяяяяяяяasdadsяяяяяяяяяя");
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page3.getWorkRegionError());
        driver.navigate().refresh();
        AlfaCreditPage page4 = alfaCreditPage.continueWithInvalidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, "САНКТ");
        Assert.assertEquals(ERROR_MANDATORY_FIELD, page4.getWorkRegionError());
        driver.navigate().refresh();
    }

    @Test
    public void shortNamesTest() {
        alfaCreditPage.continueWithValidValues("Гу", "Па", "Па", GENDER, PHONE_NUMBER, EMAIL, WORK_REGION);
        waitASecond();
        Assert.assertEquals(CORRECT_NEXT_URL, driver.getCurrentUrl());
    }

    @Test
    public void validValuesTest() {
        alfaCreditPage.continueWithValidValues(LAST_NAME, FIRST_NAME, MIDDLE_NAME, GENDER, PHONE_NUMBER, EMAIL, WORK_REGION);
        waitASecond();
        Assert.assertEquals(CORRECT_NEXT_URL, driver.getCurrentUrl());
    }

    @Test
    public void validTestWrongKeyboardLang() {
        alfaCreditPage.continueWithInvalidValues("Uehby", "Gfdtk", "Gfdkjdbx", PHONE_NUMBER, EMAIL, WORK_REGION);
        waitASecond();
        Assert.assertEquals(CORRECT_NEXT_URL, driver.getCurrentUrl());
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    private void waitASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
