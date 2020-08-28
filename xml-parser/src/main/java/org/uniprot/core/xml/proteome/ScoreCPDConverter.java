package org.uniprot.core.xml.proteome;

import java.util.List;

import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;
import org.uniprot.core.proteome.impl.CPDReportBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.PropertyType;
import org.uniprot.core.xml.jaxb.proteome.ScoreType;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
public class ScoreCPDConverter implements Converter<ScoreType, CPDReport> {

    static final String NAME = "cpd";
    static final String PROPERTY_AVERAGE_CDS = "averageCds";
    static final String PROPERTY_STATUS = "status";
    static final String PROPERTY_CONFIDENCE = "confidence";
    static final String PROPERTY_PROTEOME_COUNT = "proteomeCount";
    static final String PROPERTY_STD_CDSS = "stdCdss";
    private final PropertyConverter propertyConverter;
    private final ObjectFactory xmlFactory;

    public ScoreCPDConverter() {
        this(new ObjectFactory());
    }

    public ScoreCPDConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.propertyConverter = new PropertyConverter(xmlFactory);
    }

    @Override
    public CPDReport fromXml(ScoreType xmlObj) {
        CPDReport result = null;
        if (Utils.notNull(xmlObj)) {
            CPDReportBuilder builder = new CPDReportBuilder();
            List<PropertyType> properties = xmlObj.getProperty();
            for (PropertyType property : properties) {
                switch (property.getName()) {
                    case PROPERTY_AVERAGE_CDS:
                        builder.averageCdss(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_CONFIDENCE:
                        builder.confidence(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_PROTEOME_COUNT:
                        builder.proteomeCount(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_STD_CDSS:
                        builder.stdCdss(Double.parseDouble(property.getValue()));
                        break;
                    case PROPERTY_STATUS:
                        builder.status(CPDStatus.fromValue(property.getValue()));
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown CPDReport property ScorePropertyType.getName: "
                                        + property.getName());
                }
            }
            result = builder.build();
        }
        return result;
    }

    @Override
    public ScoreType toXml(CPDReport uniObj) {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(NAME);
        scoreType
                .getProperty()
                .add(
                        propertyConverter.createProperty(
                                PROPERTY_AVERAGE_CDS, uniObj.getAverageCdss()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_CONFIDENCE, uniObj.getConfidence()));
        scoreType
                .getProperty()
                .add(
                        propertyConverter.createProperty(
                                PROPERTY_PROTEOME_COUNT, uniObj.getProteomeCount()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_STD_CDSS, uniObj.getStdCdss()));
        if (Utils.notNull(uniObj.getStatus())) {
            scoreType
                    .getProperty()
                    .add(
                            propertyConverter.createProperty(
                                    PROPERTY_STATUS, uniObj.getStatus().getDisplayName()));
        }
        return scoreType;
    }
}
