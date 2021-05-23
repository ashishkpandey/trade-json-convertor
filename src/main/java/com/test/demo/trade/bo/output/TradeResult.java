package com.test.demo.trade.bo.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description: This is Result Business object,
 * using this output json will generated
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class TradeResult {
    @JsonProperty("tradeId")
    private String tradeId;
    @JsonProperty("tradeStatus")
    private String tradeStatus;
    @JsonProperty("tradeDate")
    private String tradeDate;
    @JsonProperty("sideId")
    private String sideId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("nominal")
    private BigDecimal nominal;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("counterpartyId")
    private String counterpartyId;
    @JsonProperty("environment")
    private String environment;
    @JsonProperty("side")
    private String side;
    //used to support the test cases
    TradeResult() {

    }
}

