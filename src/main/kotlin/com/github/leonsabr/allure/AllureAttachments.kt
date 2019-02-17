package com.github.leonsabr.allure

import io.qameta.allure.Attachment
import io.qameta.allure.Step
import com.github.leonsabr.pages.Page
import com.github.leonsabr.utils.getDriver
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import ru.yandex.qatools.htmlelements.element.HtmlElement
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Step("{0}")
fun <T> step(@Suppress("UNUSED_PARAMETER") title: String, body: () -> T): T = body()

private const val IMAGE_PNG: String = "image/png"
private const val TEXT_PLAIN: String = "text/plain"
private const val APPLICATION_JSON: String = "application/json"

@Suppress("unused_parameter")
@Attachment(value = "{0}", type = IMAGE_PNG)
fun attachScreenshot(name: String = "Screenshot", driver: WebDriver): ByteArray? {
    try {
        if (driver is TakesScreenshot) {
            return driver.getScreenshotAs(OutputType.BYTES)
        }
    } catch (ignored: Exception) {
    }
    return null
}

fun <T : Page> T.attachScreenshot(name: String = "$this screenshot") = apply {
    attachScreenshot(name, this.driver)
}

@Suppress("unused_parameter")
@Attachment(value = "{0}", type = IMAGE_PNG)
private fun attachScreenshot(name: String = "screenshot", driver: WebDriver, element: WebElement) = try {
    val ratio = (driver as JavascriptExecutor).executeScript("return window.devicePixelRatio;").toString().toFloat()
    val image = AShot()
            .coordsProvider(WebDriverCoordsProvider())
            .imageCropper(IndentCropper().addIndentFilter(BlurFilter()))
            .shootingStrategy(ShootingStrategies.scaling(ratio))
            .takeScreenshot(driver, element).image
    val out = ByteArrayOutputStream()
    ImageIO.write(image, "png", out)
    out.toByteArray()
} catch (ignored: Exception) {
    null
}

fun <T : HtmlElement> T.attachScreenshot(name: String = "$this") = apply {
    attachScreenshot(name, this.getDriver(), this)
}

@Suppress("unused_parameter")
@Attachment(value = "{0}", type = TEXT_PLAIN)
fun attachText(name: String = "text", text: String?) = text

@Suppress("unused_parameter")
@Attachment(value = "{0}", type = APPLICATION_JSON)
fun attachJson(name: String = "json", json: String): String {
    val jsonObject = JsonParser().parse(json).asJsonObject
    return GsonBuilder().setPrettyPrinting().create().toJson(jsonObject)
}
