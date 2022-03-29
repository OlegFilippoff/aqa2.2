package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private WebDriver driver;
    Locale local = new Locale("ru", "RU");
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    Date currentDate = new Date();
    String newDate;

    @BeforeEach
    void availableDate() {
        long time = currentDate.getTime();
        long anotherDate = +4;
        time = time + (60 * 60 * 24 * 1000 * anotherDate);
        String newDate = sdf.format(time);
        this.newDate = newDate;
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");

    }

    @Test
    void shouldTestHappyPath() {

        $x("//input[@placeholder=\"Город\"]").val("Иркутск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(newDate);
        $x("//input[@name=\"name\"]").val("Григорий Ефимов-Пахомов");
        $x("//*[@data-test-id=\"phone\"]/span/span/input").val("+79125357174");
        $x("//*[@class=\"checkbox__text\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[@data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));

    }

}
