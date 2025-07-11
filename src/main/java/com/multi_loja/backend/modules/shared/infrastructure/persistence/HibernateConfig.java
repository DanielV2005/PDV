package com.multi_loja.backend.modules.shared.infrastructure.persistence;


import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor // Lombok injeta via construtor, evitando @Autowired
public class HibernateConfig {

    private final DataSource dataSource;
    private final MultiTenantConnectionProvider connectionProvider;
    private final CurrentTenantIdentifierResolver tenantResolver;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<>();

        // Multitenancy (por schema)
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);

        // Log de SQL
        properties.put(AvailableSettings.SHOW_SQL, true);
        properties.put(AvailableSettings.FORMAT_SQL, true);

        return builder
                .dataSource(dataSource)
                .packages("com.multi_loja.backend.modules") // Pacotes onde estão as entidades
                .properties(properties)
                .build();
    }
}