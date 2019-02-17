package com.github.leonsabr.utils

import io.qameta.allure.Step
import com.github.leonsabr.allure.attachScreenshot
import com.github.leonsabr.allure.step
import com.github.leonsabr.config.Config
import com.github.leonsabr.pages.Page
import org.openqa.selenium.Dimension
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import java.time.Duration

class Browser {

    lateinit var driver: WebDriver

    private var baseURL: String = Config.baseUrl

    private var name: String = "default"

    private var pageLoadTimeout = Duration.ofSeconds(15)

    fun withBaseURL(baseURL: String) = apply { this.baseURL = baseURL }

    fun withName(name: String) = apply { this.name = name }

    fun withPageLoadTimeout(pageLoadTimeout: Duration) = apply { this.pageLoadTimeout = pageLoadTimeout }

    fun withWindowSize(width: Int, height: Int) = apply { driver.manage().window().size = Dimension(width, height) }

    fun <T : Page> navigateTo(page: T, vararg params: String): T {
        @Step("Open {0}")
        fun loadPage(url: String) {
            doSeveralAttempts(3) {
                driver.get(url)
                page.waitForIt("$page is not loaded!", timeout = pageLoadTimeout).attachScreenshot()
            }
        }

        loadPage(baseURL + String.format(page.url, *params))
        return page
    }

    fun <T : Page> navigateTo(factory: (driver: WebDriver) -> T, vararg params: String) = navigateTo(factory(driver), *params)

    fun navigateTo(url: String) {
        val absoluteUrl = if (url.startsWith("http")) url else baseURL + url
        step("Open $absoluteUrl") { driver.get(absoluteUrl) }
    }

    fun start(seleniumHub: String, browserName: String, browserVersion: String) = apply {
        step("Start [$name] $browserName browser") {
            driver = RemoteWebDriver(URL(seleniumHub), DesiredCapabilities(browserName, browserVersion, Platform.ANY))
        }
    }

    fun quit() = step("Quit [$name] browser") {
        try {
            driver.quit()
        } catch (ignored: WebDriverException) {
        }
    }
}
