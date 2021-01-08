package com.gfuf.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gfuf.web.rest.RestWrapper;
import com.gfuf.web.rest.impl.RestSimpleWrapper;

@Configuration
public class WebConfig
{
    @Bean
    public RestWrapper restWrapper()
    {
        return new RestSimpleWrapper();
    }
}
