package com.codecool.shop.model;

import com.codecool.shop.model.MandatoryAttribute.allAttributes;

public class AttributeFactory {

    public static MandatoryAttribute newInstanceOfEngineAttribute() {
        return new MandatoryAttribute(allAttributes.HP, allAttributes.EXHAUST_TUBE_NUMBER, allAttributes.WEIGHT, allAttributes.COLOR);
    }

    public static MandatoryAttribute newInstanceOfWheelAttribute() {
        return new MandatoryAttribute(allAttributes.DIAMETER, allAttributes.TYPE, allAttributes.MATERIAL, allAttributes.COLOR, allAttributes.POSITION);
    }

    public static MandatoryAttribute newInstanceOfExhaustAttribute() {
        return new MandatoryAttribute(allAttributes.TYPE, allAttributes.COLOR, allAttributes.DIAMETER, allAttributes.LENGTH);
    }

    public static MandatoryAttribute newInstanceOfChassisAttribute() {
        return new MandatoryAttribute(allAttributes.LENGTH, allAttributes.COLOR, allAttributes.TYPE);
    }

    public static MandatoryAttribute newInstanceOfSafetyGearAttribute() {
        return new MandatoryAttribute(allAttributes.TYPE, allAttributes.COLOR, allAttributes.MATERIAL);
    }
}
