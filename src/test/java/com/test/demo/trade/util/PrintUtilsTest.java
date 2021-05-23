package com.test.demo.trade.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.test.demo.trade.bo.error.TradeError;
import com.test.demo.trade.bo.output.TradeResult;
import com.test.demo.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Description: Unit test for PrintUtils class
 */
class PrintUtilsTest {
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
    }

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.cleanDirectory(new File("test/output"));
        FileUtils.cleanDirectory(new File("test/error"));
    }

    @Test
    void printToConsole() throws IOException {
        {
            List<TradeResult> tradeResultList = null;
            PrintStream originalOut = System.out;
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();


            Given:
            {
                tradeResultList = new ArrayList<>();
                TradeResult tradeResult = TradeResult.builder()
                        .environment("BETA")
                        .tradeId("51849291")
                        .tradeStatus("NEW")
                        .tradeDate("2021-05-17T21:11:17.265000+0100")
                        .sideId("1")
                        .side("Seller")
                        .amount(BigDecimal.valueOf(250.00))
                        .price(BigDecimal.valueOf(10.2600))
                        .nominal(BigDecimal.valueOf(2565.000000))
                        .currency("USD")
                        .counterpartyId("123XYZ")
                        .build();
                tradeResultList.add(tradeResult);
                System.setOut(new PrintStream(outContent));


            }
            When:
            {
                PrintUtils.printToConsole(mapper).accept(tradeResultList);
            }

            Then:
            {
                List<TradeResult> tradeResults = TestUtil.getResultUsingString(outContent.toString());
                assertEquals("51849291", tradeResults.get(0).getTradeId());
                assertEquals("NEW", tradeResults.get(0).getTradeStatus());
                assertEquals("2021-05-17T21:11:17.265000+0100", tradeResults.get(0).getTradeDate());
                assertEquals("1", tradeResults.get(0).getSideId());
                assertEquals("Seller", tradeResults.get(0).getSide());
                assertEquals(250.00, tradeResults.get(0).getAmount().doubleValue());
                assertEquals(10.2600, tradeResults.get(0).getPrice().doubleValue());
                assertEquals(2565.000000, tradeResults.get(0).getNominal().doubleValue());
                assertEquals("USD", tradeResults.get(0).getCurrency(), "USD");
                assertEquals("123XYZ", tradeResults.get(0).getCounterpartyId());
                assertEquals("BETA", tradeResults.get(0).getEnvironment());
                assertEquals(1, tradeResults.size());


            }

        }
    }

    @Test
    void printToFile() throws IOException {
        String fileName = null;
        List<TradeResult> tradeResultList = null;

        Given:
        {
            fileName = "test/output/output.json";
            tradeResultList = new ArrayList<>();
            TradeResult tradeResult = TradeResult.builder()
                    .environment("BETA")
                    .tradeId("51849291")
                    .tradeStatus("NEW")
                    .tradeDate("2021-05-17T21:11:17.265000+0100")
                    .sideId("1")
                    .side("Seller")
                    .amount(BigDecimal.valueOf(250.00))
                    .price(BigDecimal.valueOf(10.2600))
                    .nominal(BigDecimal.valueOf(2565.000000))
                    .currency("USD")
                    .counterpartyId("123XYZ")
                    .build();
            tradeResultList.add(tradeResult);

        }
        When:
        {
            PrintUtils.printToFile(fileName, mapper).accept(tradeResultList);
        }

        Then:
        {
            List<TradeResult> tradeResults = TestUtil.getResult(fileName);
            assertEquals("51849291", tradeResults.get(0).getTradeId());
            assertEquals("NEW", tradeResults.get(0).getTradeStatus());
            assertEquals("2021-05-17T21:11:17.265000+0100", tradeResults.get(0).getTradeDate());
            assertEquals("1", tradeResults.get(0).getSideId());
            assertEquals("Seller", tradeResults.get(0).getSide());
            assertEquals(250.00, tradeResults.get(0).getAmount().doubleValue());
            assertEquals(10.2600, tradeResults.get(0).getPrice().doubleValue());
            assertEquals(2565.000000, tradeResults.get(0).getNominal().doubleValue());
            assertEquals("USD", tradeResults.get(0).getCurrency(), "USD");
            assertEquals("123XYZ", tradeResults.get(0).getCounterpartyId());
            assertEquals("BETA", tradeResults.get(0).getEnvironment());
            assertEquals(1, tradeResults.size());


        }

    }

    @Test
    void printToErrorFile() throws IOException {
        String fileName = null;
        List<TradeError> errorList = null;

        Given:
        {
            fileName = "test/error/error.json";
            errorList = new ArrayList<>();
            TradeError error = TradeError.builder()
                    .error("Property Value is not present")
                    .fieldName("amount")
                    .build();
            errorList.add(error);
        }
        When:
        {
            PrintUtils.printToErrorFile(fileName, mapper).accept(errorList);
        }

        Then:
        {

            List<TradeError> tradeErrors = TestUtil.getError(fileName);
            assertEquals("amount", tradeErrors.get(0).getFieldName());
            assertEquals("Property Value is not present", tradeErrors.get(0).getError());
            assertEquals(1, errorList.size());


        }
    }
}