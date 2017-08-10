package com.test.transfer.configuration;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig{
    public JerseyConfiguration() {
        packages("com.test.transfer.service");
        register(org.glassfish.jersey.jackson.JacksonFeature.class);
    }
}
