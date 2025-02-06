package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CardTransferPage {
    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    private SelenideElement fromInput = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public void makeTransfer(String amount, String fromCard) {
        amountInput.setValue(amount);
        fromInput.setValue(fromCard);
        transferButton.click();
    }
}