package com.codecool.shop.model;

public class MandatoryAttribute {
    public enum allAttributes{
        WEIGHT,
        LENGTH,
        COLOR,
        DIAMETER,
        MATERIAL,
        TYPE,
        HP,
        EXHAUST_TUBE_NUMBER,
        POSITION
    }

    private allAttributes[] mandatory;

    public MandatoryAttribute(allAttributes... params){
        mandatory = params;
    }

    public allAttributes[] getAttributes(){
        return this.mandatory;
    }
}
