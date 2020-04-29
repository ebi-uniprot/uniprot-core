package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.impl.SamTriggerBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

public class SamTriggerConverter implements Converter<SamTriggerType, SamTrigger> {

    private final ObjectFactory objectFactory;
    private final RangeConverter rangeConverter;

    public SamTriggerConverter() {
        this(new ObjectFactory());
    }

    public SamTriggerConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.rangeConverter = new RangeConverter(objectFactory);
    }

    @Override
    public SamTrigger fromXml(SamTriggerType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        SamTriggerBuilder builder = new SamTriggerBuilder();
        if (Objects.nonNull(xmlObj.getTransmembrane())) {
            builder.expectedHits(
                    this.rangeConverter.fromXml(xmlObj.getTransmembrane().getExpectedHits()));
            builder.samTriggerType(org.uniprot.core.unirule.SamTriggerType.TRANSMEMBRANE);
        } else if (Objects.nonNull(xmlObj.getSignal())) {
            builder.expectedHits(this.rangeConverter.fromXml(xmlObj.getSignal().getExpectedHits()));
            builder.samTriggerType(org.uniprot.core.unirule.SamTriggerType.SIGNAL);
        } else if (Objects.nonNull(xmlObj.getCoiledCoil())) {
            builder.expectedHits(
                    this.rangeConverter.fromXml(xmlObj.getCoiledCoil().getExpectedHits()));
            builder.samTriggerType(org.uniprot.core.unirule.SamTriggerType.COILED_COIL);
        }
        return builder.build();
    }

    @Override
    public SamTriggerType toXml(SamTrigger uniObj) {
        if (Objects.isNull(uniObj)) return null;

        SamTriggerType samTriggerType = this.objectFactory.createSamTriggerType();
        RangeType expectedHits = this.rangeConverter.toXml(uniObj.getExpectedHits());

        if (org.uniprot.core.unirule.SamTriggerType.TRANSMEMBRANE.equals(
                uniObj.getSamTriggerType())) {
            SamTransmembraneConditionType transmembrane =
                    this.objectFactory.createSamTransmembraneConditionType();
            transmembrane.setExpectedHits(expectedHits);
            samTriggerType.setTransmembrane(transmembrane);
        } else if (org.uniprot.core.unirule.SamTriggerType.SIGNAL.equals(
                uniObj.getSamTriggerType())) {
            SamSignalConditionType signal = this.objectFactory.createSamSignalConditionType();
            signal.setExpectedHits(expectedHits);
            samTriggerType.setSignal(signal);
        } else if (org.uniprot.core.unirule.SamTriggerType.COILED_COIL.equals(
                uniObj.getSamTriggerType())) {
            SamCoiledCoilConditionType coiledCoil =
                    this.objectFactory.createSamCoiledCoilConditionType();
            coiledCoil.setExpectedHits(expectedHits);
            samTriggerType.setCoiledCoil(coiledCoil);
        }
        return samTriggerType;
    }
}
