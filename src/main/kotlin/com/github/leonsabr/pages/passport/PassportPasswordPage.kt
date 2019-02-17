package com.github.leonsabr.pages.passport

import com.github.leonsabr.allure.attachScreenshot
import com.github.leonsabr.pages.Page
import com.github.leonsabr.utils.waitForIt
import io.qameta.allure.Step
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.HtmlElement

class PassportPasswordPage(driver: WebDriver) : PassportFormPage(driver) {

    override var url = "/auth/welcome?origin=%s&retpath=%s&backpath=%s"

    @FindBy(css = "input[name='passwd']")
    private lateinit var passwordInput: HtmlElement

    override fun isLoaded() = passwordInput.exists()

    @Step("Type password [{0}] and proceed to {1}")
    fun <T : Page> typePassword(password: String, page: T): T {
        passwordInput.sendKeys(password)
        authForm.attachScreenshot("$authForm with password typed")
        submitButton.click()
        return page.waitForIt().attachScreenshot()
    }
}