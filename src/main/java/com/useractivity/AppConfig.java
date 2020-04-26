package com.useractivity;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
    private static final Logger LOG = Logger.getLogger(AppConfig.class);


    @Autowired
    Environment environment;


    @Bean
    public PropertyConfigurator log4jconfiguration(){
        PropertyConfigurator pc = new PropertyConfigurator();

        String log4jPropertiesPath = environment.getRequiredProperty("log4j.properties.filepath");
        PropertyConfigurator.configure(log4jPropertiesPath);
        LOG.info("Log4j using " + log4jPropertiesPath);

        return pc;
    }
}
