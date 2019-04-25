package ua.procamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class is provides root Java config for Spring application.
 * <p>
 * todo: 0. PLEASE NOTE, THAT SOME REQUIRED STEPS ARE OMITTED IN THE TODO LIST AND YOU HAVE TO DO IT ON YOUR OWN
 * <p>
 * todo: 1. Configure {@link PlatformTransactionManager} bean with name "transactionManager"
 * todo: 2. Enable transaction management
 */
@Configuration
@ComponentScan("ua.procamp")
@EnableTransactionManagement
public class RootConfig {

    @Bean(value = "transactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        return new JpaTransactionManager();
    }

}

