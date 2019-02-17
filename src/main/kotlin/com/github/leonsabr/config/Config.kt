package com.github.leonsabr.config

object Config {
    val baseUrl = System.getProperty("baseUrl") ?: "https://yandex.ru"

    val login = System.getProperty("login") ?: "arrrrival"
    val password = System.getProperty("password") ?: "NasrUgwB43Ny3DFq"

    val seleniumHub = System.getProperty("seleniumHub") ?: "http://localhost:4444/wd/hub"
    val browserName = System.getProperty("browser") ?: "chrome"
    const val browserVersion = ""

    init {
        System.setProperty("webdriver.timeouts.implicitlywait", "0")
    }
}
