package ru.netology.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String id) {
        SelenideElement cardElement = findCardById(id);
        String text = cardElement.text();
        return extractBalance(text);
    }

    private SelenideElement findCardById(String id) {
        return cards.findBy(Condition.attribute("data-test-id", id));
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value.trim());
    }

    public CardTransferPage clickDepositCard(String id) {
        findCardById(id).$("[data-test-id='action-deposit']").click();
        return new CardTransferPage();
    }
}