package org.cloudfoundry.identity.uaa.db;

import org.apache.commons.lang.StringUtils;

public class UaaDatabaseName {
    private static final String UAA_DB_NAME = "uaa";
    private static final String DATABASE_NAME = System.getProperty("database.name");

    private final String gradleWorkerId;

    public UaaDatabaseName(String gradleWorkerId) {
        this.gradleWorkerId = gradleWorkerId;
    }

    public String getName() {
        if(!StringUtils.isBlank(DATABASE_NAME)) {
            return DATABASE_NAME;
        }
        if (gradleWorkerId == null) {
            return UAA_DB_NAME;
        }

        return UAA_DB_NAME + "_" + gradleWorkerId;
    }
}
