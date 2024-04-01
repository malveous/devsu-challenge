package com.devsu.app.account.integration.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum CustomerStatus {
    @JsonProperty("ACTIVE")
    ACTIVE, @JsonProperty("INACTIVE")
    INACTIVE;

    public static boolean asBoolean(CustomerStatus customerStatus) {
        var isActive = false;

        if (Objects.nonNull(customerStatus) && customerStatus == ACTIVE) {
            isActive = true;
        } else {
            isActive = false;
        }

        return isActive;
    }

    @JsonCreator
    public static CustomerStatus fromBoolean(boolean boolValue) {
        return boolValue ? CustomerStatus.ACTIVE : CustomerStatus.INACTIVE;
    }
}