object DependencyVersions {
	const val SWAGGER_VERSION = "2.9.2"
}

dependencies {

	implementation(project(":ponny-common"))

	//swagger
	implementation("io.springfox:springfox-swagger2:${DependencyVersions.SWAGGER_VERSION}")
	implementation("io.springfox:springfox-swagger-ui:${DependencyVersions.SWAGGER_VERSION}")

	implementation("org.springframework.boot:spring-boot-starter-aop")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	implementation("xyz.groundx.caver:caver-java-ext-kas:1.1.1-rc.2")
}

springBoot.buildInfo { properties { } }

configurations {
	val archivesBaseName = "ponny-api-staging"
}