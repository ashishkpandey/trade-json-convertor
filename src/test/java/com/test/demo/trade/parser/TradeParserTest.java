package com.test.demo.trade.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.test.demo.util.TestUtil;
import com.test.demo.trade.bo.error.TradeError;
import com.test.demo.trade.bo.output.TradeResult;
import com.test.demo.config.ApplicationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description: Unit test for TradeParser class
 */
class TradeParserTest {
    ApplicationProperties applicationProperties;
    ObjectMapper mapper;
    TradeParser tradeParser;
    ApplicationProperties.File file;
    ApplicationProperties.Error error;

    @BeforeEach
    void setUp() {
        applicationProperties = new ApplicationProperties();
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        file = new ApplicationProperties.File();
        applicationProperties.setFile(file);
        error = new ApplicationProperties.Error();
        applicationProperties.setError(error);
        tradeParser = new TradeParser(applicationProperties,mapper);
    }

    @AfterEach
    void tearDown() throws IOException {
        //FileUtils.cleanDirectory(new File("test/output"));
        //FileUtils.cleanDirectory(new File("test/error"));
    }

    @Test
    @DisplayName("Success Scenario")
    void parseSuccess() throws IOException, URISyntaxException {


        Given:
        {
            file.setInput("test/input/input.json");
            file.setOutput("test/output/output.json");
            file.setError("test/error/error.json");

        }
        When:
        {
            tradeParser.parse();
        }
        Then:
        {

            List<TradeResult> tradeResults = TestUtil.getResult(file.getOutput());
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
            assertEquals(3, tradeResults.size());

            List<TradeError> tradeErrors = TestUtil.getError(file.getError());
            assertEquals(0, tradeErrors.size(), 0);
        }


    }

    @Test
    @DisplayName("Failure Scenario - Side Id is missing")
    void parseFailSideIdMissing() throws IOException, URISyntaxException {


        Given:
        {
            //side id 1 and side id 3 is missing
            file.setInput("test/input/input-sideid-missing.json");
            file.setOutput("test/output/output-sideid-missing.json");
            file.setError("test/error/error-sideid-missing.json");
            error.setMissing("Property Value is not present");

        }
        When:
        {
            tradeParser.parse();
        }
        Then:
        {

            List<TradeResult> tradeResults = TestUtil.getResult(file.getOutput());
            assertEquals("51849291", tradeResults.get(0).getTradeId());
            assertEquals("NEW", tradeResults.get(0).getTradeStatus());
            assertEquals("2021-05-17T21:11:17.265000+0100", tradeResults.get(0).getTradeDate());
            assertEquals("Seller", tradeResults.get(0).getSide());
            assertEquals(250.00, tradeResults.get(0).getAmount().doubleValue());
            assertEquals("USD", tradeResults.get(0).getCurrency(), "USD");
            assertEquals("123XYZ", tradeResults.get(0).getCounterpartyId());
            assertEquals("BETA", tradeResults.get(0).getEnvironment());
            assertEquals(3, tradeResults.size());

            List<TradeError> tradeErrors = TestUtil.getError(file.getError());
            assertEquals("sideId", tradeErrors.get(0).getFieldName());
            assertEquals(error.getMissing(), tradeErrors.get(0).getError());
            assertEquals(0, tradeErrors.size(), 2);
        }
    }

    @Test
    @DisplayName("Failure Scenario - Wrong Enum")
    void parseFailWrongEnum() throws IOException, URISyntaxException {


        Given:
        {
            //side id 1 and side id 3 is missing
            file.setInput("test/input/input-wrong-enum.json");
            file.setOutput("test/output/output-wrong-enum.json");
            file.setError("test/error/error-wrong-enum.json");
            error.setMissing("Property Value is not present");

        }
        When:
        {
            try {
                tradeParser.parse();
            } catch (com.fasterxml.jackson.databind.exc.InvalidFormatException exception) {
                assertEquals ("Cannot deserialize value of type `com.test.demo.trade.bo.input.TradeSide$Side` from String \"SellerWrongValue\": not one of the values accepted for Enum class: [Buyer, Seller]"
                ,exception.getMessage().substring(0, 173)
                    );
            }
            Then:
            {
                try {
                    List<TradeResult> tradeResults = TestUtil.getResult(file.getOutput());
                }catch (FileNotFoundException exception)
                {
                    assertEquals("test/output/output-wrong-enum.json (No such file or directory)",exception.getMessage());
                }
                try {
                    List<TradeError> tradeErrors = TestUtil.getError(file.getError());
                }catch (FileNotFoundException exception)
                {
                    assertEquals("test/error/error-wrong-enum.json (No such file or directory)",exception.getMessage());
                }

            }

        }
    }

    @Test
    @DisplayName("Failure Scenario - Price is missing")
    void parseFailPriceMissing() throws IOException, URISyntaxException {


        Given:
        {
            //side id 1 and side id 3 is missing
            file.setInput("test/input/input-price-missing.json");
            file.setOutput("test/output/output-price-missing.json");
            file.setError("test/error/error-price-missing.json");
            error.setMissing("Property Value is not present");

        }
        When:
        {
            tradeParser.parse();
        }
        Then:
        {

            List<TradeResult> tradeResults = TestUtil.getResult(file.getOutput());
            assertEquals("51849291", tradeResults.get(0).getTradeId());
            assertEquals("NEW", tradeResults.get(0).getTradeStatus());
            assertEquals("2021-05-17T21:11:17.265000+0100", tradeResults.get(0).getTradeDate());
            assertEquals("Seller", tradeResults.get(0).getSide());
            assertEquals(250.00, tradeResults.get(0).getAmount().doubleValue());
            assertEquals("USD", tradeResults.get(0).getCurrency(), "USD");
            assertEquals("123XYZ", tradeResults.get(0).getCounterpartyId());
            assertEquals("BETA", tradeResults.get(0).getEnvironment());
            assertEquals(3, tradeResults.size());

            List<TradeError> tradeErrors = TestUtil.getError(file.getError());
            assertEquals("price", tradeErrors.get(0).getFieldName());
            assertEquals(error.getMissing(), tradeErrors.get(0).getError());
            assertEquals(0, tradeErrors.size(), 2);
        }
    }



    @Test
    @DisplayName("Failure Scenario - InputFileMissing")
    void parseFailInputFileMissing() throws IOException, URISyntaxException {


        Given:
        {

            file.setInput("test/input/input-FileMissing.json");
            file.setOutput("test/output/output-FileMissing.json");
            file.setError("test/error/error-FileMissing.json");
            error.setMissing("Property Value is not present");

        }
        When:
        {
            try {
                tradeParser.parse();
            } catch (java.io.FileNotFoundException exception) {
                assertEquals ("test/input/input-FileMissing.json (No such file or directory)",exception.getMessage()
                );
            }
            Then:
            {
                try {
                    List<TradeResult> tradeResults = TestUtil.getResult(file.getOutput());
                }catch (FileNotFoundException exception)
                {
                    assertEquals("test/output/output-FileMissing.json (No such file or directory)",exception.getMessage());
                }
                try {
                    List<TradeError> tradeErrors = TestUtil.getError(file.getError());
                }catch (FileNotFoundException exception)
                {
                    assertEquals("test/error/error-FileMissing.json (No such file or directory)",exception.getMessage());
                }

            }

        }
    }
}
