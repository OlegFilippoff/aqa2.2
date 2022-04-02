package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private WebDriver driver;
    private int days;

    public String availableDate(int days) {
        return
                LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestHappyPath() {
        String planningDate = availableDate(3);

        $x("//input[@placeholder=\"Город\"]").val("Иркутск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Григорий Ефимов-Пахомов");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
        $x("//*[@class='notification__content']").
                shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldTestEnglishName() {
        String planningDate = availableDate(3);

        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Bruce Willis");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+142536789101");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("неверно")).should(visible, Duration.ofSeconds(5));

    }

    @Test
    void shouldTestUnhappyDatePath() {
        String planningDate = availableDate(1);

        $x("//input[@placeholder=\"Город\"]").val("Иркутск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Степан Чижиков");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("невозможен")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestYoLetterAndI() {
        String planningDate = availableDate(5);

        $x("//input[@placeholder=\"Город\"]").val("Иркутск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Йоаким Ёжиков");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("неверно")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestPhoneWithoutPlusSign() {
        String planningDate = availableDate(5);

        $x("//input[@placeholder=\"Город\"]").val("Иркутск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Станис ");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("89125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("11 цифр")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestPhoneLessThen11Digits() {
        String planningDate = availableDate(5);

        $x("//input[@placeholder=\"Город\"]").val("Санкт-Петербург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Станис ");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+7912535717");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("11 цифр")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestCityInEnglish() {
        String planningDate = availableDate(5);

        $x("//input[@placeholder=\"Город\"]").val("Washington D.C.");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Степан Чижиков");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("Доставка в выбранный город недоступна")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestEnglishCity() {
        String planningDate = availableDate(5);

        $x("//input[@placeholder=\"Город\"]").val("Вашингтон");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(planningDate);
        $x("//input[@name=\"name\"]").val("Степан Чижиков");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $(withText("Доставка в выбранный город недоступна")).should(visible, Duration.ofSeconds(5));
    }

}
