package com.multi_loja.backend.modules.shared.infrastructure.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantConnectionProvider implements MultiTenantConnectionProvider<Object> {

    private final DataSource dataSource;
    private static final String DEFAULT_TENANT = "public";

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        final Connection connection = dataSource.getConnection();
        try {
            connection.setSchema(tenantIdentifier.toString()); // usa toString() para converter
        } catch (SQLException e) {
            log.error("Erro ao definir schema: {}", tenantIdentifier, e);
            throw e;
        }
        return connection;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        Connection conn = connection;
        // liberação do recurso, se necessário
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (unwrapType.isAssignableFrom(this.getClass())) {
            return (T) this;
        }
        throw new IllegalArgumentException("Cannot unwrap to " + unwrapType.getName());
    }
}