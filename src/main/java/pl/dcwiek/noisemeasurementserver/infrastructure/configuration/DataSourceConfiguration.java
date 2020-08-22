package pl.dcwiek.noisemeasurementserver.infrastructure.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"pl.dcwiek.noisemeasurementserver.infrastructure"})
@Slf4j
public class DataSourceConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Primary
    public DataSource dataSource() {
        log.trace("Postgres database url: {}", dbUrl);

        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .url(dbUrl + "&useUnicode=true&characterEncoding=utf-8")
                .build();
    }
}
