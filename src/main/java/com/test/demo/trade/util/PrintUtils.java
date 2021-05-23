package com.test.demo.trade.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.trade.bo.error.TradeError;
import com.test.demo.trade.bo.output.TradeResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
/**
 * Description: This Util class print the JSON message to Console, Error and Output file
 */
public final class PrintUtils {
   public PrintUtils(){}

    public static Consumer<List<TradeResult>> printToConsole(ObjectMapper mapper) {
        return (results) -> {

            try {
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
    }

    public static Consumer<List<TradeResult>> printToFile(String fileName,ObjectMapper mapper) {
        return (results) -> {

            try {
                FileUtils.writeStringToFile(new File(fileName), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

        };


    }

    public static Consumer<List<TradeError>> printToErrorFile(String fileName,ObjectMapper mapper) {
        return (errorList) -> {

            try {
                FileUtils.writeStringToFile(new File(fileName), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorList), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

        };


    }

}
