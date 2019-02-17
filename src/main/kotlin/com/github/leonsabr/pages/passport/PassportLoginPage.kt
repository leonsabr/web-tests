package com.github.leonsabr.pages.passport

import com.github.leonsabr.allure.attachScreenshot
import com.github.leonsabr.utils.waitForIt
import io.qameta.allure.Step
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.HtmlElement

class PassportLoginPage(driver: WebDriver) : PassportFormPage(driver) {

    override var url = "/auth?origin=%s&retpath=%s&backpath=%s"

    @FindBy(css = "input[name='login']")
    private lateinit var loginInput: HtmlElement

    override fun isLoaded() = loginInput.exists()

    @Step("Type login [{0}] and proceed to PassportPasswordPage")
    fun typeLogin(login: String): PassportPasswordPage {
        loginInput.sendKeys(login)
        authForm.attachScreenshot("$authForm with login typed")
        submitButton.click()
        return PassportPasswordPage(driver).waitForIt()
    }
}
