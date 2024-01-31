package api;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ImdbApi {

    public final ImdbRestClient client = new ImdbRestClient();

    public ResponseBody getTopChartResponse() throws IOException {
        return client.books.getTopChart().execute().body();
    }

    public LinkedHashMap<String, String> getTopChart() throws IOException {
        var htmlString = getTopChartResponse().string();
        var document = Jsoup.parse(htmlString);
        var ipcTitleElements = document.select(".ipc-title");
        var yearElements = document.select(".sc-1e00898e-7");
        var allFilms = new LinkedHashMap<String, String>();
       // var allYears = new LinkedHashMap<Integer, String>();
        for (Element ipcTitleElement : ipcTitleElements) {
            var aTag = ipcTitleElement.select("a").first();
            if (aTag != null) {
                var href = aTag.attr("href");
                var h3Text = aTag.select("h3").text();
                var link = ImdbRestClient.BASE_URL + href;
                allFilms.put(h3Text, link);

            }
        }

        return allFilms;
    }

    public LinkedHashMap<String, String> getTopYears() throws IOException {
        var htmlString = getTopChartResponse().string();
        var document = Jsoup.parse(htmlString);
        var yearElements = document.select(".sc-1e00898e-7");
        var allYears = new LinkedHashMap<String, String>();
        int index = 0;
        for (Element yearElement : yearElements) {
            var aTag = yearElement.select("span").first();
            index++;
            if (aTag != null) {
                var year = aTag.text();
                allYears.put(String.valueOf(index),year);
            }
        }

        return allYears;
    }

    public LinkedHashMap<String, String> getTopMarks() throws IOException {
        var htmlString = getTopChartResponse().string();
        var document = Jsoup.parse(htmlString);
        var marksElements = document.select("[data-testid='ratingGroup--container']");
        var allMarks = new LinkedHashMap<String, String>();
        int index = 0;
        for (Element markElement : marksElements) {
            var aTag = markElement;
            index++;
            if (aTag != null) {
                var mark = aTag.text().substring(0,3);
                allMarks.put(String.valueOf(index), mark);
            }
        }
        return allMarks;
    }

    public List<Map.Entry<String, String>> getTop100Films() throws IOException {
        return getTopChart().entrySet().stream().limit(100).toList();
    }
    public List<Map.Entry<String, String>> getTop100Years() throws IOException {
        return getTopYears().entrySet().stream().limit(100).toList();
    }
    public List<Map.Entry<String, String>> getTop100Marks() throws IOException {
        return getTopMarks().entrySet().stream().limit(100).toList();
    }

}