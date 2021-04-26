package com.lumak.ponny.api

import com.lumak.ponny.common.config.database.PonnyJpaDatabaseConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.info.BuildProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication(scanBasePackages = [
	"com.lumak.ponny.common.*",
	"com.lumak.ponny.api.*"
])
@Import(PonnyJpaDatabaseConfig::class)
class PonnyApiApplication(
	private val buildProperties: BuildProperties,
	private val environment: Environment
) : ApplicationListener<ApplicationReadyEvent> {

	private val logger = LoggerFactory.getLogger(PonnyApiApplication::class.java)

	@PostConstruct
	fun init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	}

	override fun onApplicationEvent(event: ApplicationReadyEvent) {
		logger.info("{} applicationReady, profiles = {}", buildProperties.name, environment.activeProfiles.contentToString())
	}
}

fun main(args: Array<String>) {
	SpringApplicationBuilder(PonnyApiApplication::class.java)
		.listeners(ApplicationPidFileWriter())
		.run(*args)
}