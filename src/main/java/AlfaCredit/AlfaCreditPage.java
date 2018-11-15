package AlfaCredit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

class AlfaCreditPage {

    private WebDriver driver;

    AlfaCreditPage(WebDriver driver) {
        this.driver = driver;
    }

    private By headingText = By.xpath("//div[@class='top__info']/h2");
    private By lastNameField = By.xpath("//input[@name='lastName']");
    private By firstNameField = By.xpath("//input[@name='firstName']");
    private By middleNameField = By.xpath("//input[@name='middleName']");
    private By mobilePhoneField = By.xpath("//input[@name='mobilePhone']");
    private By emailField = By.xpath("//input[@name='email']");
    private By workRegionField = By.xpath("//input[@name='workRegionCode']");
    private By continueButton = By.xpath("//span[text()='Продолжить']");
    private By lastNameError = By.xpath("//input[@name='lastName']/parent::span/following-sibling::span/div");
    private By firstNameError = By.xpath("//input[@name='firstName']/parent::span/following-sibling::span/div");
    private By middleNameError = By.xpath("//input[@name='middleName']/parent::span/following-sibling::span/div");
    private By genderFieldError = By.xpath("//span[@class='radio-group__sub']//div");
    private By mobilePhoneError = By.xpath("//input[@name='mobilePhone']/parent::span/following-sibling::span/div");
    private By eMailError = By.xpath("//input[@name='email']/parent::span/following-sibling::span/div");
    private By workRegionError = By.xpath("//input[@name='workRegionCode']/parent::span/following-sibling::span/div");

    void continueWithValidValues(String lastName, String firstName, String middleName, Boolean gender, String mobilePhone, String email, String workRegion) {
        this.typeLastName(lastName);
        this.typeFistName(firstName);
        this.typeMiddleName(middleName);
        this.typeGender(gender);
        this.typePhoneNumber(mobilePhone);
        this.typeEmail(email);
        this.typeWorkRegion(workRegion);
        this.clickContinueButton();
        new AlfaCreditPage(driver);
    }

    private void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    String getHeadingText() {
        return driver.findElement(headingText).getText();
    }

    private void typeLastName(String lastName) {
        if (lastName != null) {
            driver.findElement(lastNameField).sendKeys(lastName);
        }
    }

    private void typeFistName(String firstName) {
        if (firstName != null) {
            driver.findElement(firstNameField).sendKeys(firstName);
        }
    }

    private void typeMiddleName(String middleName) {
        if (middleName != null) {
            driver.findElement(middleNameField).sendKeys(middleName + Keys.TAB);
        }
    }

    private void chooseGender(Boolean male) {
        if (male != null) {
            if (male) {
                driver.findElement(By.xpath("//span[text()='Мужской']"));
            } else {
                driver.findElement(By.xpath("//span[text()='Женский']"));
            }
        }
    }

    private void typeGender(Boolean gender) {
        if (gender != null) {
            try {
                if (gender) {
                    driver.findElement(By.xpath("//span[text()='Мужской']")).click();
                } else {
                    driver.findElement(By.xpath("//span[text()='Женский']")).click();
                }
            } catch (Exception e) {
                System.out.println("Не удалось найти radioButton выбора пола");
            }
        }
    }

    private void typePhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            driver.findElement(mobilePhoneField).sendKeys(phoneNumber);
        }
    }

    private void typeEmail(String email) {
        if (email != null) {
            driver.findElement(emailField).sendKeys(email);
        }
    }

    private void typeWorkRegion(String workRegion) {
        if (workRegion != null) {
            String workRegionPath = String.format("//span[text()='%s']", workRegion);
            driver.findElement(workRegionField).click();
            try {
                driver.findElement(By.xpath(workRegionPath)).click();
            } catch (Exception e) {
                System.out.println("Не удалось найти рабочий регион");
            }
        }
    }

    AlfaCreditPage continueWithInvalidValues(String lastName, String firstName, String middleName, String mobilePhone, String email, String workRegion) {
        this.typeLastName(lastName);
        this.typeFistName(firstName);
        this.typeMiddleName(middleName);
        this.typePhoneNumber(mobilePhone);
        this.typeEmail(email);
        this.typeWorkRegion(workRegion);
        this.clickContinueButton();
        return new AlfaCreditPage(driver);
    }

    AlfaCreditPage continueWithInvalidValues(String lastName, String firstName, String middleName, Boolean gender, String mobilePhone, String email, String workRegion) {
        this.typeLastName(lastName);
        this.typeFistName(firstName);
        this.typeMiddleName(middleName);
        this.chooseGender(gender);
        this.typePhoneNumber(mobilePhone);
        this.typeEmail(email);
        this.typeWorkRegion(workRegion);
        this.clickContinueButton();
        return new AlfaCreditPage(driver);
    }

    String getLastNameError() {
        return driver.findElement(lastNameError).getText();
    }

    String getFirstNameError() {
        return driver.findElement(firstNameError).getText();
    }

    String getMiddleNameError() {
        return driver.findElement(middleNameError).getText();
    }

    String getGenderFieldError() {
        return driver.findElement(genderFieldError).getText();
    }

    String getPhoneNumberError() {
        try {
            return driver.findElement(mobilePhoneError).getText();
        } catch (Exception e){
            return null;
        }

    }

    String getEmailError() {
        return driver.findElement(eMailError).getText();
    }

    String getWorkRegionError() {
        return driver.findElement(workRegionError).getText();
    }
}
