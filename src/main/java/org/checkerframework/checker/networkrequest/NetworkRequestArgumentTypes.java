package org.checkerframework.checker.networkrequest;

public enum NetworkRequestArgumentTypes {
    URL("URL"),
    URI("URI"),
    PORT("PORT"),
    SKIP("SKIP"),
    HOST("HOST"),
    FILE("FILE"),
    BASEURL("BASEURL"),
    PROTOCOL("PROTOCOL"),
    RELATIVEURL("RELATIVEURL");

    private String value;

    NetworkRequestArgumentTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
