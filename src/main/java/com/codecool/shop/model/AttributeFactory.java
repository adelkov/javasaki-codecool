package com.codecool.shop.model;

import com.codecool.shop.model.MandatoryAttribute.allAttributes;

public class AttributeFactory {

    public static MandatoryAttribute newInstanceOfEngineAttribute() {
        return new MandatoryAttribute(allAttributes.HP, allAttributes.EXHAUST_TUBE_NUMBER, allAttributes.WEIGHT, allAttributes.COLOR);
    }

    public static MandatoryAttribute newInstanceOfWheelAttribute() {
        return new MandatoryAttribute(allAttributes.DIAMETER, allAttributes.TYPE, allAttributes.MATERIAL, allAttributes.COLOR, allAttributes.POSITION);
    }

    // TODO: Finish all categories
}
