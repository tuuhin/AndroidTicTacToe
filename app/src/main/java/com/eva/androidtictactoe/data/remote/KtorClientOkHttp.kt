package com.eva.androidtictactoe.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.Duration

object KtorClientOkHttp {

	fun getInstance(): HttpClient = HttpClient(OkHttp) {

		engine {
			config {
				retryOnConnectionFailure(true)
				connectTimeout(Duration.ofSeconds(10))
				pingInterval(Duration.ofSeconds(15))
			}
		}

		install(ContentNegotiation) {
			json()
		}

		install(DefaultRequest) {
			header(HttpHeaders.ContentType, ContentType.Application.Json)
		}

		install(Logging) {
			logger = Logger.ANDROID
			level = LogLevel.ALL
		}

		install(WebSockets) {
			maxFrameSize = Long.MAX_VALUE
			contentConverter = KotlinxWebsocketSerializationConverter(Json)
			pingInterval = 15_000L
		}
	}
}
