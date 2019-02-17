package com.github.leonsabr.web

import com.github.leonsabr.allure.step
import com.github.leonsabr.rules.BrowserHandlerRule
import com.github.leonsabr.rules.LoggingRule
import com.github.leonsabr.utils.Browser
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class BrowserBaseTest {

    protected val browser = Browser()

    val rules: RuleChain
        @Rule get() = RuleChain.emptyRuleChain()
                .around(BrowserHandlerRule(browser))
                .around(LoggingRule(browser))

    protected fun <T> T.onPage(title: String, block: T.() -> Unit): T {
        step(title) { block() }
        return this
    }
}
