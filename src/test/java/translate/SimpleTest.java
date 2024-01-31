package translate;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import config.Browser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleTest {
    @DataProvider(name = "languages")
    public Object[][] languages() {
        return new Object[][] {
                {"en", "I'll study TESTNG cool."},
                {"es", "Estudiaré testng genial."},
                {"da", "Jeg studerer testng cool."},
                {"it", "Studierò Testng Cool."},
                {"de", "Ich werde testng cool studieren."},
                {"bg", "Ще проуча Testng Cool."},
                {"nso", "Ke tla ithuta testng cool."},
                {"ca", "Estudiaré Testng Cool."},
                {"sr", "Проучићу тестнг цоол."},
                {"tk", "Testngo-ny gowy öwrenerin."},
                {"ee", "Masrɔ̃ nu tso testng cool ŋu."},
                {"fr", "Je vais étudier le test."},
                {"mg", "Handalina ny hatsiaka aho."},
                {"hu", "Tanulok a tesztng hűvösen."},
                {"ka", "ტესტს ვსწავლობ მაგარი."},
                {"lv", "Es studēšu testng atdzist."},
                {"pl", "Badam testng fajnie."},
                {"ro", "Voi studia testng cool."},
                {"sk", "Budem študovať Testng Cool."},
                {"sv", "Jag studerar testng cool."},
        };
    }
    @BeforeTest
    public void setUpBrowser() {
        Browser.setBrowser();
    }
    @Test(dataProvider = "languages")
    public void myFirstTest(String baseUrl, String response){
        String dynamicURL = "https://translate.google.com/?sl=uk&tl=" + baseUrl + "&text=Я%20круто%20вивчу%20TestNG.&op=translate";
        open(dynamicURL);
        $("[jsname='W297wb']").should(Condition.visible);
        String text = $("[jsname='W297wb']").getAttribute("textContent");
        Assert.assertEquals(text, response);
        System.out.println("Test passed - " + baseUrl);
    }
}