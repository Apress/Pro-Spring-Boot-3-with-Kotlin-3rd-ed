package com.apress.users.config

import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.service.UserService
import com.zaxxer.hikari.HikariDataSource
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.vault.core.lease.SecretLeaseContainer // comment out if Vault isn't used
import org.springframework.vault.core.lease.domain.RequestedSecret // comment out if Vault isn't used
import org.springframework.vault.core.lease.event.SecretLeaseCreatedEvent // comment out if Vault isn't used
import org.springframework.vault.core.lease.event.SecretLeaseExpiredEvent // comment out if Vault isn't used

// With Consul (Part 1)
@Configuration
@EnableConfigurationProperties(UserProperties::class)
class UserConfiguration {
    @Bean
    fun init(userService: UserService): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            userService.saveUpdateUser(
                User(
                    "ximena@email.com",
                    "Ximena",
                    "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    "aw2s0meR!",
                    listOf(
                        UserRole.USER
                    ),
                    true
                )
            )
            userService.saveUpdateUser(
                User(
                    "norma@email.com",
                    "Norma",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    "aw2s0meR!",
                    listOf(
                        UserRole.USER, UserRole.ADMIN
                    ),
                    false
                )
            )
        }
    }

    @Value("\${spring.cloud.vault.database.role}")
    lateinit var databaseRoleName:String // comment out if Vault isn't used

    @Autowired
    lateinit var secretLeaseContainer:SecretLeaseContainer // comment out if Vault isn't used

    @Autowired
    lateinit var  hikariDataSource: HikariDataSource // comment out if Vault isn't used

    @PostConstruct
    fun postConstruct() { // comment out if Vault isn't used
        val vaultCredentialsPath = String.format("database/creds/%s", databaseRoleName)
        secretLeaseContainer.addLeaseListener{ event ->
            LOG.info("[SecretLeaseContainer]> Received event: {}", event);
            if (vaultCredentialsPath == event.source.path) {
                if (event is SecretLeaseExpiredEvent &&
                        event.getSource().mode == RequestedSecret.Mode.RENEW) {
                    LOG.info("[SecretLeaseContainer]> Let's replace the RENEWED lease by a ROTATE one.");
                    secretLeaseContainer.requestRotatingSecret(vaultCredentialsPath);
                } else if (event is SecretLeaseCreatedEvent &&
                        event.getSource().mode == RequestedSecret.Mode.ROTATE) {
                    val username = event.secrets["username"].toString()
                    val password = event.secrets["password"].toString()
                    updateHikariDataSource(username, password)
                }
            }
        }
    }
    private fun updateHikariDataSource(username: String, password: String) { // comment out if Vault isn't used
        LOG.info("[SecretLeaseContainer]> Soft evict the current database connections");
        hikariDataSource.hikariPoolMXBean?.softEvictConnections()
        LOG.info("[SecretLeaseContainer]> Update database credentials with the new ones.");
        val hikariConfigMXBean = hikariDataSource.hikariConfigMXBean
        hikariConfigMXBean.setUsername(username)
        hikariConfigMXBean.setPassword(password)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserConfiguration::class.java)
    }
}
