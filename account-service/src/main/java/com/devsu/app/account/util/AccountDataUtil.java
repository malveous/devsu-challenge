package com.devsu.app.account.util;

import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountDataUtil {

    public String generateAccountNumber() {
        var smallest = 1000_0000_0000_0000L;
        var largest = 9999_9999_9999_9999L;

        var randomizedAccountNumber = ThreadLocalRandom.current().nextLong(smallest, largest + 1);

        return String.valueOf(randomizedAccountNumber);
    }

}
