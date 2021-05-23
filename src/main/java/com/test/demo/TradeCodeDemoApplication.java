package com.test.demo;

import com.test.demo.trade.parser.TradeParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class TradeCodeDemoApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ConfigurableApplicationContext context = SpringApplication.run(TradeCodeDemoApplication.class, args);
        TradeParser tradeParser = context.getBean(TradeParser.class);
        tradeParser.parse();
    }

}
