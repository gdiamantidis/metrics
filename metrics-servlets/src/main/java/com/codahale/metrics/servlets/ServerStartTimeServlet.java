package com.codahale.metrics.servlets;

import com.codahale.metrics.common.RealClock;
import com.codahale.metrics.common.SystemClock;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ServerStartTimeServlet extends AppDiagnosticBaseServlet {

    private static final long serialVersionUID = 6219385150345628817L;

    private transient Date serverStartDate;
    private transient SystemClock clock;
    private transient Logger LOGGER = LoggerFactory.getLogger(VersionServlet.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.clock = (SystemClock) config.getServletContext().getAttribute(RealClock.CLOCK);
        this.serverStartDate = this.clock.now();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.println(serverStartDate);
            writer.println("Uptime: " + (clock.now().getTime() - serverStartDate.getTime()));
            writer.flush();
        } catch (Exception e) {
            LOGGER.warn("Problem encountered while start date time requested", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    public String uri() {
        return "/uptime";
    }

    @Override
    public String displayName() {
        return "Uptime";
    }

    @Override
    public boolean supportsPrettyPrint() {
        return false;
    }
}
