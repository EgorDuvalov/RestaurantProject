package com.innowise.duvalov.pool;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final int DEFAULT_POOL_SIZE = 10;

    private final BlockingQueue<ConnectionProxy> availableConnection;
    private final List<ConnectionProxy> takenConnections;

    ConnectionPool() {
        availableConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        takenConnections = new LinkedList<>();
    }

    public void openPool() {
        if (!isCreated.get()) {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                availableConnection.add(ConnectionManager.getConnectionProxy());
            }
            isCreated.set(true);
        }
    }

    public ConnectionProxy getConnection() {
        ConnectionProxy connection = null;
        try {
            connection = availableConnection.take();
            takenConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        if (connection != null) {
            takenConnections.remove(connection);
            boolean isReturned = availableConnection.add(connection);
            if (isReturned) {
                LOGGER.warn("Connection can't be added to available ones");
            }
        }
    }

    public void closePool() {
        if (!takenConnections.isEmpty()) {
            LOGGER.warn("Some connections weren't released before cleaning");
            for (ConnectionProxy connection : takenConnections) {
                releaseConnection(connection);
            }
        }
        try {
            for (ConnectionProxy connection : availableConnection) {
                connection.vanish();
            }
            availableConnection.clear();
        } catch (SQLException throwables) {
            LOGGER.error(throwables);
        }
        isCreated.set(false);
    }
}

