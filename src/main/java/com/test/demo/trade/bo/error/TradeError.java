package com.test.demo.trade.bo.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Description: This is Error Business object,
 * using this error json will generated
 */
@Builder
@Data
@AllArgsConstructor
public class TradeError {
    //used to support the test cases
    TradeError(){}
    @JsonProperty("fieldName")
    private  String fieldName;
    @JsonProperty("error")
    private String error;
}

