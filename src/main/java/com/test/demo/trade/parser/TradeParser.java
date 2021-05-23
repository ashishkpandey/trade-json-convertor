package com.test.demo.trade.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.trade.bo.error.TradeError;
import com.test.demo.trade.bo.input.Root;
import com.test.demo.trade.bo.output.TradeResult;
import com.test.demo.config.ApplicationProperties;
import com.test.demo.trade.util.PrintUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Description: This class Read the input file in JSON
 * converts into flat JSON and  write into output file
 * If Mandatory fields are missing then it will write error file in the JSON format
 */
@AllArgsConstructor
@Component
@Log4j2
public class TradeParser {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper mapper;



    public void parse() throws IOException {

        List<TradeError> errorList = new ArrayList<>();
        PrintUtils.printToFile(applicationProperties.getFile().getOutput(),mapper).andThen(PrintUtils.printToConsole(mapper)).accept(convertToResult().apply(getRoot(applicationProperties.getFile().getInput()), errorList));
        PrintUtils.printToErrorFile(applicationProperties.getFile().getError(),mapper).accept(errorList);
    }

    /*
    Converts the input json file into Root Object
     */
    private Root getRoot(String fileName) throws IOException {

        return mapper.readValue(new File(fileName), Root.class);
    }
    /*
       Converts the  Root Object into Result object
        */
    private BiFunction<Root, List<TradeError>, List<TradeResult>> convertToResult() {

        return (root, errorList) ->
                root.getTrade().getTradeSides().stream()
                        .map(
                                (tradeSide) ->
                                        TradeResult.builder()
                                                .environment(root.getEnvironment())
                                                .tradeId(root.getTrade().getTradeId())
                                                .tradeStatus(root.getTrade().getTradeStatus())
                                                .tradeDate(root.getTrade().getTradeDate())
                                                .sideId(tradeSide.getSideId().orElseGet(() -> handleError(errorList, "sideId")))
                                                .side(tradeSide.getSide().orElseGet(() -> handleError(errorList, "side")))
                                                .amount(tradeSide.getAmount().orElseGet(() -> handleError(errorList, "amount")))
                                                .price(tradeSide.getPrice().orElseGet(() -> handleError(errorList, "price")))
                                                .nominal(tradeSide.getAmount().isPresent() && tradeSide.getPrice().isPresent() ? tradeSide.getAmount().get().multiply(tradeSide.getPrice().get()) : null)
                                                .currency(tradeSide.getCurrency().orElseGet(() -> handleError(errorList, "currency")))
                                                .counterpartyId(tradeSide.getCounterpartyId().orElseGet(() -> handleError(errorList, "counterpartyId")))
                                                .build()
                        )
                        .collect(Collectors.toList());

    }
    /*
       Write the message into error list, and
        later or that can be written into the error file
        */
    private <T> T handleError(List<TradeError> errorList, String fieldName) {
        errorList.add(TradeError.builder()
                .fieldName(fieldName)
                .error(applicationProperties.getError().getMissing()).build());
        return null;
    }


}
