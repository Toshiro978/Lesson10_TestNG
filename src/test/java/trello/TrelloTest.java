package trello;

import com.codeborne.selenide.Condition;
import config.Browser;
import org.testng.annotations.*;

import java.util.Random;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;




public class TrelloTest {
    Random random = new Random();
    int randomNumber = random.nextInt(101);;
    String randomName = "Test card " + randomNumber;
    @BeforeTest
    public void setUpBrowser() {
        Browser.setBrowser();
    }

    @Test
    public void loginTrello() {
        open("https://trello.com/");
        $("[data-uuid='MJFtCCgVhXrVl7v9HA7EH_login']").should(Condition.visible);
        $("[data-uuid='MJFtCCgVhXrVl7v9HA7EH_login']").click();
        $("#username").setValue("josip33639@gosarlar.com");
        $("#login-submit").click();
        $("#password").setValue("Qwe1rty2");
        $("#login-submit").click();

    }

    @Test(dependsOnMethods = "loginTrello")
    public void checkBoard() {
        $(".boards-page-board-section-list li a").should(Condition.visible);
        $(".boards-page-board-section-list li a").click();
    }
    @Test(dependsOnMethods = "checkBoard")
    public void addCard(){
        $("[data-testid='list-add-card-button']").click();
        $("[data-testid='list-card-composer-textarea']").setValue(randomName);
        $("[data-testid='list-card-composer-add-card-button']").click();
    }

    @Test(dependsOnMethods = "addCard")
    public void addDescriptionOnCard() {
        refresh();
        $(byText(randomName)).should(visible,enabled,exist);
        $(byText(randomName)).click();
        $("#ak-editor-textarea").setValue("Test Description");
        $(".confirm").click();


    }

    @Test(dependsOnMethods = "addDescriptionOnCard")
    public void addCommentToCard(){
        $("[data-testid='card-back-new-comment-input-skeleton']").click();
        actions().sendKeys("Test Comment").perform();
        $("[data-testid='card-back-comment-save-button']").click();
    }

    @AfterTest
    public void deleteCard(){
      //  $(byText(randomName)).click();
        $("a.button-link.js-archive-card").click();
        $("a.button-link.js-delete-card").click();
        $("input.js-confirm").click();
    }
}




