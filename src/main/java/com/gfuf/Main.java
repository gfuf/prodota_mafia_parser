package com.gfuf;

import com.gfuf.telegram.config.HandlersConfig;
import com.gfuf.web.config.WebConfig;
import com.gfuf.prodota.config.ProdotaConfig;
import com.gfuf.telegram.config.TelegramConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@Import({ ProdotaConfig.class, WebConfig.class, TelegramConfig.class, HandlersConfig.class })
public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        SpringApplication.run(Main.class, args);
    }
}
