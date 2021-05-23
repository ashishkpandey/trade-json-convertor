package com.test.demo.trade.bo.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
/**
 * Description: This is Root Business object,
 * using this input json will be converted into Root BO
 */
public class Root {
    @JsonProperty("schemaVersion")
    private String schemaVersion;
    @JsonProperty("documentVersion")
    private int documentVersion;
    @JsonProperty("environment")
    private String environment;
    @JsonProperty("trade")
    private Trade trade;
}
