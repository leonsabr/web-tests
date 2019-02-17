package com.github.leonsabr.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory.initElements
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

open class Page(val driver: WebDriver) {

    init {
        @Suppress("LeakingThis")
        (initElements(HtmlElementDecorator(HtmlElementLocatorFactory(driver)), this))
    }

    open lateinit var url: String

    override fun toString(): String = this.javaClass.simpleName

    open fun isLoaded() = true
}
