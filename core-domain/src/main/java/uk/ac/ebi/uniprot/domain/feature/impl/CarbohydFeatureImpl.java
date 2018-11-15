package uk.ac.ebi.uniprot.domain.feature.impl;

import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.LinkedSugar;

public class CarbohydFeatureImpl  extends FeatureWithFeatureIdImpl implements CarbohydFeature {
    private final CarbohydLinkType carbohydLinkType;
    private final LinkedSugar linkedSugar;
    private static final Pattern FEATURE_ID_PATTERN = Pattern.compile("PRO_(\\d+)");

    public CarbohydFeatureImpl(FeatureLocation location, String description,
        String featureId, CarbohydLinkType carbohydLinkType,
        String linkedSugar) {
        super(FeatureType.CARBOHYD, location, description, featureId, FEATURE_ID_PATTERN);
        this.carbohydLinkType = carbohydLinkType;
        if (linkedSugar == null) {
            this.linkedSugar = new LinkedSugarImpl("");
        } else {
            this.linkedSugar = new LinkedSugarImpl(linkedSugar);
        }

    }


    @Override
    public CarbohydLinkType getCarbohydLinkType() {
        return carbohydLinkType;
    }

    @Override
    public LinkedSugar getLinkedSugar() {
        return linkedSugar;
    }

    class LinkedSugarImpl implements LinkedSugar {
        private String value;

        public LinkedSugarImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

   
}
