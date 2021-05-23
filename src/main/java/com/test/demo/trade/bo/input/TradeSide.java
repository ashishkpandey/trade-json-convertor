package com.test.demo.trade.bo.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@Data

public class TradeSide {
    @JsonProperty("sideId")
    private Optional<String> sideId;
    @JsonProperty("side")
    private Optional<Side> side;
    @JsonProperty("amount")
    private Optional<BigDecimal> amount;
    @JsonProperty("price")
    private Optional<BigDecimal> price;
    @JsonProperty("currency")
    private Optional<String> currency;
    @JsonProperty("counterpartyId")
    private Optional<String> counterpartyId;

    public Optional<String> getSideId() {
        return this.sideId != null ? this.sideId : Optional.empty();
    }

    public Optional<String> getSide() {
        return this.side != null ? Optional.of(this.side.get().getLabel()) : Optional.empty();
    }

    public Optional<BigDecimal> getAmount() {
        return this.amount != null ? this.amount : Optional.empty();
    }

    public Optional<BigDecimal> getPrice() {
        return this.price != null ? this.price : Optional.empty();
    }

    public Optional<String> getCurrency() {
        return this.currency != null ? this.currency : Optional.empty();
    }

    public Optional<String> getCounterpartyId() {
        return this.counterpartyId != null ? this.counterpartyId : Optional.empty();

    }


    public enum Side {
        Buyer("Buyer"),
        Seller("Seller");

        @Getter
        public final String label;

        private Side(String label) {
            this.label = label;
        }
    }
}
