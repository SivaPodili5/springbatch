package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {

    @Bean(name="datasource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password) throws SQLException {
        OracleDataSource oracleDataSource=new OracleDataSource();
        //oracleDataSource.setURL("jdbc:mysql://localhost:3306/spring_batch");
        oracleDataSource.setURL(url);
        oracleDataSource.setUser(username);
        oracleDataSource.setPassword(password);

        HikariConfig hikariConfig=new HikariConfig();
        hikariConfig.setDataSource(oracleDataSource);
        hikariConfig.setConnectionTestQuery("select 1 from dual");

        return new HikariDataSource(hikariConfig);

    }

   /* @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties oracleDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource oracleDatasource() {
        return oracleDatasourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }*/
   /* @Bean
    public PlatformTransactionManager platformTransactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerBean){
        JpaTransactionManager jpaTransactionManager=new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerBean.getObject());
        return jpaTransactionManager;

    }*/

}
