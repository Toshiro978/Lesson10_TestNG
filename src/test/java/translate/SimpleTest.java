package translate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Test;


public class SimpleTest {

    @Test
    public void myFirstTest(){
        System.out.println("Hello world!");
    }

    @Test
    public void top250MoviesPageTitle() {
        open("https://www.imdb.com/chart/top/");
        $("h1.ipc-title__text").shouldHave(text("IMDb Top 250 Movies"));
    }
}