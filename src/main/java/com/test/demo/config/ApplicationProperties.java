package com.test.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Description: ApplicationProperties reads the application.yml file
 * and make sure the data safety
 * Also throws the error if any required parameter is missing
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "trade")
public final class ApplicationProperties {

    @NotNull File file;
    @NotNull Error error;

    @Data
    public static class File {
        @NotNull String input;
        @NotNull String output;
        @NotNull String error;
    }

    @Data
    public static class Error {
        @NotNull String missing;
    }
}
