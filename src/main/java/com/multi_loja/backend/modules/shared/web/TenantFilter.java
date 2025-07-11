package com.multi_loja.backend.modules.shared.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.multi_loja.backend.modules.shared.infrastructure.tenant.TenantContext;
import java.io.IOException;

@Component
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String tenantId = httpRequest.getHeader(TENANT_HEADER);

            if (tenantId != null && !tenantId.isEmpty()) {
                TenantContext.setCurrentTenant(tenantId);
            }

            chain.doFilter(request, response);

        } finally {
            TenantContext.clear(); // Garante que não vaze entre requisições
        }
    }
}