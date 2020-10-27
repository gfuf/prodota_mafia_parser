package gfuf.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gfuf.web.rest.RestWrapper;
import gfuf.web.rest.impl.RestSimpleWrapper;

@Configuration
public class WebConfig
{
    @Bean
    public RestWrapper restWrapper()
    {
        return new RestSimpleWrapper();
    }
}
