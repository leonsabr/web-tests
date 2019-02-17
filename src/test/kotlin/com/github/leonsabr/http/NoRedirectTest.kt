package com.github.leonsabr.http

import com.github.leonsabr.allure.step
import com.github.leonsabr.config.Config
import io.qameta.allure.junit4.DisplayName
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

@DisplayName("No redirects on yandex.ru")
class NoRedirectsTest {

    @Test
    fun `no redirects on yandex site`() {
        val request = HttpGet(Config.baseUrl)
        val expectedResponse = "200 Ok"

        HttpClientBuilder.create()
                .disableRedirectHandling()
                .build()
                .use {
                    val response = step("With disabled redirects GET [${request.uri}]") { it.execute(request) }
                    step("Verify response is [$expectedResponse]") {
                        val status = "${response.statusLine.statusCode} ${response.statusLine.reasonPhrase}"
                        assertThat("Incorrect response status!", status, equalTo(expectedResponse))
                    }
                }
    }
}
