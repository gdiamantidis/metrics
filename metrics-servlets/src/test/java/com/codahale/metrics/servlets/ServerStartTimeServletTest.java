package com.codahale.metrics.servlets;

import com.codahale.metrics.common.RealClock;
import com.codahale.metrics.common.SystemClock;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerStartTimeServletTest {
    private SystemClock clock = mock(SystemClock.class);

    @Test
    public void returnStartTime() throws Exception {
        int time = 10000;
        Date timeOnServletInit = new Date();
        Date timeOnRequest = new Date(timeOnServletInit.getTime() + time);
        String expectedString = timeOnServletInit.toString() + "\nUptime: " + time + "\n";

        when(clock.now()).thenReturn(timeOnServletInit).thenReturn(timeOnRequest);
        ServerStartTimeServlet servlet = new ServerStartTimeServlet();
        ServletConfig config = mock(ServletConfig.class);
        addClockConfig(config);
        servlet.init(config);

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        servlet.service(null, response);
        assertThat(writer.toString()).isEqualTo(expectedString);
    }

    private void addClockConfig(ServletConfig config) {
        ServletContext servletContext = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(RealClock.CLOCK)).thenReturn(clock);
    }

    private DefaultServlet configWithClock() {
        DefaultServlet config = new DefaultServlet();
        config.getServletContext().setAttribute(RealClock.CLOCK, clock);
        return config;
    }
}
