package com.github.leonsabr.web

import com.github.leonsabr.config.Config
import com.github.leonsabr.pages.main.MainPage
import io.qameta.allure.junit4.DisplayName
import org.junit.Test

@DisplayName("Login test")
class LoginTest : BrowserBaseTest() {

    @Test
    fun `user can log in to yandex`() {
        browser.navigateTo(::MainPage)
                .logIn(Config.login, Config.password)
    }
}
