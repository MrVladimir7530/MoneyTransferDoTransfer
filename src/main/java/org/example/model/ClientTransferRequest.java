package org.example.model;

import java.math.BigDecimal;

public class ClientTransferRequest {
    private long fromPersonId;
    private long toPersonId;
    private BigDecimal money;


    public long getToPersonId() {
        return toPersonId;
    }

    public long getFromPersonId() {
        return fromPersonId;
    }

    public BigDecimal getMoney() {
        return money;
    }
}
