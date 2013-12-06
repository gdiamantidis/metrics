package com.codahale.metrics.servlets;

import com.codahale.metrics.common.RealClock;
import com.codahale.metrics.common.SystemClock;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class DefaultAdminServletContextListener extends AdminServletContextListener {

    @Override
    protected List<? extends AppDiagnosticBaseServlet> diagnostics() {
        return asList(
                new MetricsServlet(),
                new PingServlet(),
                new ThreadDumpServlet(),
                new HealthCheckServlet(),
                new VersionServlet(),
                new ServerStartTimeServlet());
    }
}
