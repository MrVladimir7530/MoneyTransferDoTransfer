package org.example.service;

import org.example.dao.ClientDao;
import org.example.model.PaymentsResult;
import org.example.model.PersonInfo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class ClientService  {
    private ClientDao dao;

    public ClientService() throws SQLException {
        this.dao = new ClientDao();
    }

    public PaymentsResult getResultTransfer(long fromPersonId, long toPersonId, BigDecimal money) throws SQLException {
        return dao.getResultTransfer(fromPersonId, toPersonId, money);
    }
}
