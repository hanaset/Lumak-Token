package com.lumak.ponny.common.config.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.*
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jmx.export.MBeanExporter
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource

@Configuration
@ComponentScan(basePackages = [
    "com.lumak.ponny.common.repository"
])
@EnableJpaRepositories(
        basePackages = ["com.lumak.ponny.common.repository"],
        entityManagerFactoryRef = "ponnyEntityManagerFactory",
        transactionManagerRef = "ponnyTransactionManager"
)
@PropertySource("classpath:properties/database/ponny-database-\${spring.profiles.active}.properties")
class PonnyJpaDatabaseConfig(private val mbeanExporter: MBeanExporter) {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "ponny.jpa")
    fun ponnyJpaProperties(): JpaProperties {
        return JpaProperties()
    }

    @Bean
    @Primary
    fun ponnyHibernateSettings(): HibernateSettings {
        return HibernateSettings()
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "ponny")
    fun ponnyHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    @Primary
    fun ponnyDataSource(): DataSource {
        val dataSource = HikariDataSource(ponnyHikariConfig())
        mbeanExporter.addExcludedBean("ponnyDataSource")
        return dataSource
    }

    @Bean
    @Primary
    fun ponnyEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
                .dataSource(ponnyDataSource())
                .packages("com.ponny.common.entity")
                .persistenceUnit("ponnyPersistenceUnit")
                .properties(getVendorProperties(ponnyDataSource()))
                .build()
    }

    private fun getVendorProperties(dataSource: DataSource): Map<String, String> {
        var properties = ponnyJpaProperties().properties
//        properties.put("hibernate.dialec", "org.hibernate.dialect.MySQL5InnoDBDialect")
        return properties
    }

    @Bean(name = ["ponnyJdbcTemplate"])
    fun ponnyJdbcTemplate(@Qualifier("ponnyDataSource") dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    @Primary
    fun ponnyTransactionManager(builder: EntityManagerFactoryBuilder): PlatformTransactionManager {
        return JpaTransactionManager(ponnyEntityManagerFactory(builder).getObject()!!)
    }

    @Bean
    @Primary
    fun ponnyTransactionTemplate(builder: EntityManagerFactoryBuilder): TransactionTemplate {
        return TransactionTemplate(ponnyTransactionManager(builder))
    }
}