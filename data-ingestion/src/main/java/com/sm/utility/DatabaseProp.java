package com.sm.utility;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum DatabaseProp {
    Host("mysql.host", ""),
    Port("mysql.port", ""),
    DatabaseName("mysql.db", "sm"),
    MemberTable("mysql.members.table", "members");

    private static PropertiesConfiguration properties;
    private String key;
    private String value;
    private static Map<String, DatabaseProp> key2Property;

    DatabaseProp(String key, String value) {
        this.key = key;
        this.value = value;
        getKey2Property().put(key, this);
    }

    private static Map<String, DatabaseProp> getKey2Property() {
        if (key2Property == null) {
            key2Property = new HashMap<>();
        }
        return key2Property;
    }



    static {
        try {
            properties = new PropertiesConfiguration("database.properties");
            for(DatabaseProp pr : DatabaseProp.values()) {
                if (properties.containsKey(pr.key)) {
                    pr.value = String.join(",", properties.getStringArray(pr.key));
                }
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String toString() {
        return key + "=" + value;
    }

}
