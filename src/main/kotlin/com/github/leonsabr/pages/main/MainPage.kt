package com.github.leonsabr.pages.main

import com.github.leonsabr.allure.step
import com.github.leonsabr.pages.Page
import com.github.leonsabr.pages.mail.MailPage
import com.github.leonsabr.pages.passport.PassportLoginPage
import com.github.leonsabr.utils.waitForIt
import io.qameta.allure.Step
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.HtmlElement

class MainPage(driver: WebDriver) : Page(driver) {

    override var url = "/"

    @FindBy(css = "#text")
    private lateinit var searchInput: HtmlElement

    @FindBy(css = ".desk-notif-card_type_login .button")
    private lateinit var logInButton: HtmlElement

    override fun isLoaded() = searchInput.exists()

    @Step("Log in to Mail as [ {0} / {1} ]")
    fun logIn(login: String, password: String): MailPage {
        step("Click '$logInButton'") { logInButton.waitForIt().click() }
        return PassportLoginPage(driver).waitForIt()
                .typeLogin(login)
                .typePassword(password, MailPage(driver))
    }
}
