package org.example.dao;

import org.example.bl.Util;
import org.example.exceptions.NotEnoughMoney;
import org.example.model.PaymentsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import java.sql.*;

public class ClientDao extends Util {

    Connection connection;
    Logger logger;

    public ClientDao() throws SQLException {
        this.connection = getConnection();
        this.logger  = LoggerFactory.getLogger(ClientDao.class);
    }

    public PaymentsResult getResultTransfer(long fromPersonId, long toPersonId, BigDecimal money) throws SQLException {
        try {
            logger.info("transaction begin");
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            if (getMoney(fromPersonId).compareTo(money) >= 0) {
                boolean resultUpdate = getResultUpdate(fromPersonId, toPersonId, money);
                int status = 400;
                String accountFrom = getAccount(fromPersonId);
                String accountTo = getAccount(toPersonId);
                if (resultUpdate) {
                    status = 200;
                }
                PaymentsResult paymentsResult = new PaymentsResult(money, status, accountFrom, accountTo);
                addPaymentHistory(paymentsResult);
                connection.commit();
                return new PaymentsResult(paymentsResult.getAmount(), paymentsResult.getStatus(), paymentsResult.getAccount_from(), paymentsResult.getAccount_to());


            } else {
                connection.rollback();
                String accountFrom = getAccount(fromPersonId);
                String accountTo = getAccount(toPersonId);
                int status = 406;
                String description = "Недостаточно денег";
                PaymentsResult paymentsResult = new PaymentsResult(money, status, accountFrom, accountTo, description);
                connection.setAutoCommit(true);
                addPaymentHistoryWithDescription(paymentsResult);
                return new PaymentsResult(paymentsResult.getAmount(), paymentsResult.getStatus(), paymentsResult.getAccount_from(), paymentsResult.getAccount_to(), paymentsResult.getDescription());
            }

        } catch (Exception e) {
            connection.rollback();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            throw new SQLException();
        }
    }

    private BigDecimal getMoney(long fromPersonId) {
        PreparedStatement preparedStatement = null;

        String SQL = "SELECT * FROM public.\"account\"" +
                "Where person_id = ?";

        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, fromPersonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return (BigDecimal) resultSet.getBigDecimal("amount");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }

    private boolean getResultUpdate(long fromPersonId, long toPersonId, BigDecimal money) {
        logger.info("update amount from account");
        PreparedStatement preparedStatementFrom = null;
        PreparedStatement preparedStatementTo = null;

        String SQLFrom = "       UPDATE public.\"account\"" +
                        "       SET amount = amount - ?" +
                        "       WHERE person_id = ?;";

        String SQLTo = "       UPDATE public.\"account\"" +
                        "       SET amount = amount + ?" +
                        "       WHERE person_id = ?;";

        try {
            preparedStatementFrom = connection.prepareStatement(SQLFrom);
            preparedStatementFrom.setBigDecimal(1, money);
            preparedStatementFrom.setLong(2, fromPersonId);
            preparedStatementFrom.executeUpdate();

            preparedStatementTo = connection.prepareStatement(SQLTo);
            preparedStatementTo.setBigDecimal(1, money);
            preparedStatementTo.setLong(2, toPersonId);
            preparedStatementTo.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private String getAccount(long PersonId) {
        logger.info("get person by id");
        PreparedStatement preparedStatement = null;

        String SQL = "SELECT * FROM public.\"account\"" +
                "Where person_id = ?";

        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, PersonId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("account");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }

    private void addPaymentHistory(PaymentsResult paymentsResult) {
        logger.info("entry to the history");
        PreparedStatement preparedStatement = null;

        String SQL = "INSERT INTO public.\"payment\" (amount, status, account_from, account_to)" +
                "VALUES (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setBigDecimal(1, paymentsResult.getAmount());
            preparedStatement.setInt(2, paymentsResult.getStatus());
            preparedStatement.setString(3, paymentsResult.getAccount_from());
            preparedStatement.setString(4, paymentsResult.getAccount_to());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPaymentHistoryWithDescription(PaymentsResult paymentsResult) {
        logger.info("entry to the history with unlucky");
        PreparedStatement preparedStatement = null;

        String SQL = "INSERT INTO public.\"payment\" (amount, status, account_from, account_to, description)" +
                "VALUES (?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setBigDecimal(1, paymentsResult.getAmount());
            preparedStatement.setInt(2, paymentsResult.getStatus());
            preparedStatement.setString(3, paymentsResult.getAccount_from());
            preparedStatement.setString(4, paymentsResult.getAccount_to());
            preparedStatement.setString(5, paymentsResult.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
