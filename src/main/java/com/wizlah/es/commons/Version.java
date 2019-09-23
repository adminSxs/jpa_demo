package com.wizlah.es.commons;

public enum Version {
    CURRENT_VERSION("0.0.1");

    private String version;

    Version(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
