package quru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selenide.*;

public class HomeToolsQaPageTest extends TestBase {

    @Test
    @Tag("SMOKE")
    @DisplayName("Тест успешной загрузки стартовой страницы")
    void homePageDisplayedTest() {
        open("");

        $(".new-training__heading").shouldHave(text("Selenium Online Trainings"));
        $(".navbar").shouldBe(visible);
        $(".container-fluid").shouldBe(visible);
        $(".subscribe__container").shouldBe(visible);
        $("[class='youtube__promote d-flex flex-column align-items-center']").shouldBe(visible);
        $("[class='row align-items-md-center']").shouldBe(visible);
    }

    @ParameterizedTest(name = "Проверка элемента {0} в header")
    @Tag("SMOKE")
    @ValueSource(strings = {"Home", "Selenium Training", "Demo Site", "About"})
    void headerNavigationElementsVisibleTest(String string) {
        open("");
        $$(".navbar").findBy(text(string)).shouldBe(visible);
    }

    @ParameterizedTest(name = "Работа поиска в header по search query = {0}")
    @Tag("SMOKE")
    @CsvSource(value =
            {
                    "Selenium , Top Cross Browser Testing Tools For 2024, Selenium Waits Commands",
                    "junit, Junit Tutorial, Getting started with Rest Assured"
            })
    void headerSearchFunctionalTest(String searchString, String checkResult1, String checkResult2) {
        open("");
        $("input[class='navbar__search--input']").setValue(searchString).pressEnter();

        $$(".articles").findBy(text(checkResult1)).shouldBe(clickable);
        $$(".articles").findBy(text(checkResult2)).shouldBe(clickable);
    }

    @ParameterizedTest(name = "Проверка перехода на страницу учебника {0}")
    @Tag("SMOKE")
    @CsvFileSource(resources = "/test_data/navigateToTutorialBySelectedTool.csv")
    void navigateToTutorialBySelectedToolTest(String tools, String expectedBook) {
        open("");
        $$(".category__name")
                .findBy(text(tools))
                .scrollIntoView(instant().block(center))
                .click();

        $(".article-meta-data").shouldHave(text(expectedBook));

    }
}