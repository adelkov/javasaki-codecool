package com.codecool.shop.model;

public class FailedMandatoryKeys extends Exception {

    public FailedMandatoryKeys() { super(); }
    public FailedMandatoryKeys(String message) { super(message); }
    public FailedMandatoryKeys(String message, Throwable cause) { super(message, cause); }
    public FailedMandatoryKeys(Throwable cause) { super(cause); }
}
