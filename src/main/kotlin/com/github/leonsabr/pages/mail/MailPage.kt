package com.github.leonsabr.pages.mail

import com.github.leonsabr.pages.Page
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.element.HtmlElement

class MailPage(driver: WebDriver) : Page(driver) {

    override var url = "/mail"

    @FindBy(css = ".mail-App-Footer")
    private lateinit var footer: HtmlElement

    override fun isLoaded() = footer.exists()
}
