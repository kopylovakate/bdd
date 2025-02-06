package ru.netology.bdd.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPageValid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.data.DataHelper.*;

public class CardTransferTest {

    @Test
    void shouldTransferFromSecondToFirst() {
        var info = getAuthInfo();
        var loginPage = Selenide.open("http://localhost:9999", LoginPageValid.class);
        var verificationPage = loginPage.validLogin(info);
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);


        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int amount = 999;

        int balanceFirstCardBefore = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardBefore = dashboardPage.getCardBalance(secondCard.getId());

        var transferPage = dashboardPage.clickDepositCard(secondCard.getId());
        transferPage.makeTransfer(String.valueOf(amount), firstCard.getNumber());

        int balanceFirstCardAfter = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardAfter = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(balanceFirstCardBefore - amount, balanceFirstCardAfter);
        assertEquals(balanceSecondCardBefore + amount, balanceSecondCardAfter);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var info = getAuthInfo();
        var loginPage = Selenide.open("http://localhost:9999", LoginPageValid.class);
        var verificationPage = loginPage.validLogin(info);
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);


        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int amount = 999;

        int balanceFirstCardBefore = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardBefore = dashboardPage.getCardBalance(secondCard.getId());

        var transferPage = dashboardPage.clickDepositCard(firstCard.getId());
        transferPage.makeTransfer(String.valueOf(amount), secondCard.getNumber());

        int balanceFirstCardAfter = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardAfter = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(balanceSecondCardBefore - amount, balanceSecondCardAfter);
        assertEquals(balanceFirstCardBefore + amount, balanceFirstCardAfter);
    }

    @Test
    void shouldKeepDepositSizeIfTransferIsZero() {
        var info = getAuthInfo();
        var loginPage = Selenide.open("http://localhost:9999", LoginPageValid.class);
        var verificationPage = loginPage.validLogin(info);
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);


        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int amount = 0;

        int balanceFirstCardBefore = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardBefore = dashboardPage.getCardBalance(secondCard.getId());

        var transferPage = dashboardPage.clickDepositCard(firstCard.getId());
        transferPage.makeTransfer(String.valueOf(amount), secondCard.getNumber());

        int balanceFirstCardAfter = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardAfter = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(balanceFirstCardBefore, balanceFirstCardAfter);
        assertEquals(balanceSecondCardBefore, balanceSecondCardAfter);

    }

    @Test
    void shouldNotTransferIfGreaterThanDeposit() {
        var info = getAuthInfo();
        var loginPage = Selenide.open("http://localhost:9999", LoginPageValid.class);
        var verificationPage = loginPage.validLogin(info);
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int amount = 100000;

        int balanceFirstCardBefore = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardBefore = dashboardPage.getCardBalance(secondCard.getId());

        var transferPage = dashboardPage.clickDepositCard(firstCard.getId());
        transferPage.makeTransfer(String.valueOf(amount), secondCard.getNumber());

        int balanceFirstCardAfter = dashboardPage.getCardBalance(firstCard.getId());
        int balanceSecondCardAfter = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(balanceFirstCardBefore, balanceFirstCardAfter, "Баланс первой карты изменился, хотя не должен был");
        assertEquals(balanceSecondCardBefore, balanceSecondCardAfter, "Баланс второй карты изменился, хотя не должен был");

    }
}

