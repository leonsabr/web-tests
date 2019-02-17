package com.github.leonsabr.rules

import com.github.leonsabr.allure.attachJson
import com.github.leonsabr.allure.attachScreenshot
import com.github.leonsabr.allure.attachText
import com.github.leonsabr.utils.Browser
import org.junit.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.openqa.selenium.JavascriptExecutor

class LoggingRule(private val browser: Browser) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                var needLogs = false
                try {
                    base.evaluate()
                } catch (e: Throwable) {
                    needLogs = e !is AssumptionViolatedException
                    throw e
                } finally {
                    if (needLogs) {
                        attachScreenshot("Screenshot of the failure", browser.driver)
                        attachText("URL", browser.driver.currentUrl)
                        attachJson("localStorage", "${(browser.driver as JavascriptExecutor).executeScript("return JSON.stringify(localStorage);")}")
                    }
                }
            }
        }
    }
}
