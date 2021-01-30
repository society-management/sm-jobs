package com.sm.utility;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Util {

    private static PropertiesConfiguration properties;

    static {
        try {
            properties = new PropertiesConfiguration("clickhouse.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
