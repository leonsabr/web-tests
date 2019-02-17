package com.github.leonsabr.rules

import com.github.leonsabr.config.Config
import com.github.leonsabr.utils.Browser
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class BrowserHandlerRule(private val browser: Browser) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                browser.start(Config.seleniumHub, Config.browserName, Config.browserVersion).withWindowSize(1280, 800)
                try {
                    base.evaluate()
                } finally {
                    browser.quit()
                }
            }
        }
    }
}
