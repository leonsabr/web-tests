package com.github.leonsabr.pages.passport

import com.github.leonsabr.pages.Page
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.HtmlElement

abstract class PassportFormPage(driver: WebDriver) : Page(driver) {

    @FindBy(css = "button[type='submit']")
    protected lateinit var submitButton: HtmlElement

    @FindBy(css = "[class='passp-auth']")
    protected lateinit var authForm: HtmlElement
}
