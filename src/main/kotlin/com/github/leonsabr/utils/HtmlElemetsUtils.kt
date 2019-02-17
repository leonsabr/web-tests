package com.github.leonsabr.utils

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebElement
import ru.yandex.qatools.htmlelements.element.HtmlElement

fun <T : Any?> doSeveralAttempts(attempts: Int = 3, code: () -> T): T {
    for (i in 1..attempts) {
        try {
            return code()
        } catch (t: Throwable) {
            if (i == attempts) throw t
        }
    }
    throw Exception("not gonna happen")
}

fun HtmlElement.getDriver(): WebDriver {
    val rwe = this.findElement(By.xpath("."))
    when (rwe) {
        is RemoteWebElement -> return rwe.wrappedDriver
        else -> throw UnsupportedOperationException("Can't extract driver for non-RemoteWebElement!")
    }
}
