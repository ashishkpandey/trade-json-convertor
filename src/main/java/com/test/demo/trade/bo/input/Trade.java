package com.test.demo.trade.bo.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class Trade {
    @JsonProperty("tradeId")
    private String tradeId;
    @JsonProperty("tradeStatus")
    private String tradeStatus;
    @JsonProperty("tradeDate")
    private String tradeDate;
    @JsonProperty("tradeSides")
    private List<TradeSide> tradeSides;
}
