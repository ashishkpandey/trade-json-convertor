package com.test.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.test.demo.trade.bo.error.TradeError;
import com.test.demo.trade.bo.output.TradeResult;

import java.io.*;

import java.util.List;
/**
 * Description: Util class convert the json file and string into trade bo
 */
public class TestUtil {

     public static List<TradeResult> getResult(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
         TypeFactory typeFactory = mapper.getTypeFactory();
         return mapper.readValue(new File(fileName), typeFactory.constructCollectionType(List.class, TradeResult.class));

     }

    public static List<TradeError> getError(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(new File(fileName), typeFactory.constructCollectionType(List.class, TradeError.class));
    }
    public static List<TradeResult> getResultUsingString(String string) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(string, typeFactory.constructCollectionType(List.class, TradeResult.class));

    }
}
