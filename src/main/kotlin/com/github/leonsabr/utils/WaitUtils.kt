package com.github.leonsabr.utils

import com.github.leonsabr.pages.Page
import org.junit.Assert.fail
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import ru.yandex.qatools.htmlelements.element.HtmlElement
import java.time.Duration
import java.time.LocalDateTime

fun waitForCondition(
        message: String = "Failed to wait for condition",
        timeout: Duration = Duration.ofSeconds(30),
        pollInterval: Duration = Duration.ofMillis(500),
        condition: () -> Boolean
) {
    val timeLimit = LocalDateTime.now() + timeout

    do {
        if (condition()) return
        try {
            Thread.sleep(pollInterval.toMillis())
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(e)
        }
    } while (LocalDateTime.now() < timeLimit)

    fail(message)
}

fun <T : Page> T.waitForIt(
        message: String = "Failed to wait for $this to load!",
        timeout: Duration = Duration.ofSeconds(15)
) = apply {
    waitForCondition(message, timeout) { this.isLoaded() }
}

fun <T : HtmlElement> T.waitForIt(
        message: String? = null,
        exists: Boolean = true,
        isEnabled: Boolean? = null,
        timeout: Duration = Duration.ofSeconds(10)
) = apply {
    val msg = message ?: when {
        exists -> when (isEnabled) {
            null -> "Cannot find $this"
            true -> "Cannot wait until $this is enabled"
            false -> "Cannot wait until $this is disabled"
        }
        else -> "Failed to wait until $this disappears"
    }
    waitForCondition(msg, timeout) {
        try {
            exists == (this.exists() && this.isDisplayed) && (isEnabled == null || isEnabled == this.isEnabled)
        } catch (e: StaleElementReferenceException) {
            !exists
        } catch (e: NoSuchElementException) {
            !exists
        }
    }
}
