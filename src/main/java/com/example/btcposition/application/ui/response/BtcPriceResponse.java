package com.example.btcposition.application.ui.response;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class BtcPriceResponse {

    private Map<String, Object> bitcoin;

    public BtcPriceResponse() {
        bitcoin = new HashMap<>();
    }




}
