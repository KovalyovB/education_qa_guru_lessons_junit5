package quru.qa;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import quru.qa.data.WikiLanguagesPages;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WikiLanguagesPagesTest extends WikiTestBase {

    static Stream<Arguments> wikiLanguagesPagesTest() {
        return Stream.of(
                Arguments.of(WikiLanguagesPages.valueOf("English"), WikiLanguagesPages.Русский,
                        List.of("Читать", "Просмотр кода", "История")),
                Arguments.of(WikiLanguagesPages.valueOf("Русский"),WikiLanguagesPages.English,
                        List.of("Read", "View source", "View history"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка содержимого правой навигационной панели для языка {1}")
    void wikiLanguagesPagesTest
            (WikiLanguagesPages wikiUrlForTest, WikiLanguagesPages wikiButtons, List<String> expectedButtons) {
        open(wikiUrlForTest.getUrl());
        $("#p-lang-btn-checkbox").click();
        $("[class='row uls-search'] [class ='uls-filterinput uls-languagefilter noime languagefilter']")
                .setValue(wikiButtons.toString());
        $$("[class='row uls-language-block'] a")
                .find(exactText(wikiButtons.toString())).click();

        $$("[id='p-views'] [class='vector-menu-content-list'] a")
                .filter(visible).shouldHave(texts(expectedButtons));
    }
}
