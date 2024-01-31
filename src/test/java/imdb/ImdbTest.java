package imdb;

import api.ImdbApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import config.Browser;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ImdbTest {

    @DataProvider(name = "topList")
    public Object[][] topList() {
        String[][] dataArray = new String[dataList.size()][4];
        for (int i = 0; i < dataList.size(); i++) {
            Entry<String, String> entry = dataList.get(i);
            Entry<String, String> year = yearList.get(i);
            Entry<String, String> mark = markList.get(i);
            dataArray[i][0] = entry.getKey();
            dataArray[i][1] = entry.getValue();
            dataArray[i][2] = year.getValue();
            dataArray[i][3] = mark.getValue();
        }
        return dataArray;
    }

    private final ImdbApi imdbApi = new ImdbApi();
    private List<Entry<String, String>> dataList = new ArrayList<>();
    private List<Entry<String, String>> yearList = new ArrayList<>();
    private List<Entry<String, String>> markList = new ArrayList<>();

    @BeforeTest
    public void setUpBrowser() {
        Browser.setBrowser();
    }

    @Test
    public void imdbTop100ChartTest() throws IOException {
        dataList = imdbApi.getTop100Films();
        yearList = imdbApi.getTop100Years();
        markList = imdbApi.getTop100Marks();
    }

    @Test(dataProvider = "topList", dependsOnMethods = "imdbTop100ChartTest")
    public void imdbPageTest(String title, String requestURL, String year, String mark) {
        open(requestURL);

        String tittleOnPage = $("[data-testid='hero__primary-text']").getAttribute("textContent");
        String yearOnPage = $(".sc-69e49b85-0 .ipc-inline-list__item a").getText();
        String markOnPage = $("[data-testid='hero-rating-bar__aggregate-rating__score']").getText().substring(0, 3);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(tittleOnPage, title);
        softAssert.assertEquals(yearOnPage, year);
        softAssert.assertEquals(markOnPage, mark);
        softAssert.assertAll();
    }
}
