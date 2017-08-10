package com.test.transfer;

import com.test.transfer.configuration.JerseyConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.Objects;

public class Main {
    private static Tomcat tomcat;

    public static void main(String[] args) throws Exception {
        tomcat = new Main().start();
        tomcat.getServer().await();
    }

    public Tomcat start() throws Exception {

        String contextPath = "";
        String appBase = ".";
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080";
        }

        tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port));
        tomcat.getHost().setAppBase(appBase);
        Context context = tomcat.addWebapp(contextPath, appBase);

        Tomcat.addServlet(context, "jersey-container-servlet",
                new ServletContainer(resourceConfig()));
        context.addServletMapping("/api/*", "jersey-container-servlet");

        tomcat.start();
        return tomcat;

    }

    public synchronized void stop() throws LifecycleException {
        if(Objects.nonNull(tomcat))
            tomcat.stop();
    }

    private ResourceConfig resourceConfig() {
        return new JerseyConfiguration();
    }
}
