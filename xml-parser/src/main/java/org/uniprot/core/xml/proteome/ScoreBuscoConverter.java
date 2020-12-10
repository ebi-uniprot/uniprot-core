package org.uniprot.core.xml.proteome;

import java.util.List;

import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.impl.BuscoReportBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ScorePropertyType;
import org.uniprot.core.xml.jaxb.proteome.ScoreType;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
public class ScoreBuscoConverter implements Converter<ScoreType, BuscoReport> {

    public static final String NAME = "busco";
    public static final String PROPERTY_TOTAL = "total";
    public static final String PROPERTY_COMPLETED = "completed";
    public static final String PROPERTY_COMPLETED_SINGLE = "completedSingle";
    public static final String PROPERTY_COMPLETED_DUPLICATED = "completedDuplicated";
    public static final String PROPERTY_FRAGMENTED = "fragmented";
    public static final String PROPERTY_MISSING = "missing";
    public static final String PROPERTY_SCORE = "score";
    public static final String PROPERTY_LINEAGE = "lineage";

    final ScorePropertyConverter propertyConverter;

    private final ObjectFactory xmlFactory;

    public ScoreBuscoConverter() {
        this(new ObjectFactory());
    }

    public ScoreBuscoConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.propertyConverter = new ScorePropertyConverter(xmlFactory);
    }

    @Override
    public BuscoReport fromXml(ScoreType xmlObj) {
        BuscoReport result = null;
        if (Utils.notNull(xmlObj)) {
            List<ScorePropertyType> properties = xmlObj.getProperty();
            BuscoReportBuilder builder = new BuscoReportBuilder();
            for (ScorePropertyType property : properties) {
                switch (property.getName()) {
                    case PROPERTY_COMPLETED:
                        builder.complete(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_COMPLETED_SINGLE:
                        builder.completeSingle(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_COMPLETED_DUPLICATED:
                        builder.completeDuplicated(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_TOTAL:
                        builder.total(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_MISSING:
                        builder.missing(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_FRAGMENTED:
                        builder.fragmented(Integer.parseInt(property.getValue()));
                        break;
                    case PROPERTY_LINEAGE:
                        builder.lineageDb(property.getValue());
                        break;
                    case PROPERTY_SCORE:
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown BUSCO property ScorePropertyType.getName: "
                                        + property.getName());
                }
            }
            result = builder.build();
        }
        return result;
    }

    @Override
    public ScoreType toXml(BuscoReport uniObj) {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(NAME);
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_COMPLETED, uniObj.getComplete()));
        scoreType
                .getProperty()
                .add(
                        propertyConverter.createProperty(
                                PROPERTY_COMPLETED_SINGLE, uniObj.getCompleteSingle()));
        scoreType
                .getProperty()
                .add(
                        propertyConverter.createProperty(
                                PROPERTY_COMPLETED_DUPLICATED, uniObj.getCompleteDuplicated()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_TOTAL, uniObj.getTotal()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_FRAGMENTED, uniObj.getFragmented()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_MISSING, uniObj.getMissing()));
        scoreType
                .getProperty()
                .add(propertyConverter.createProperty(PROPERTY_SCORE, uniObj.getScore()));
        if (Utils.notNullNotEmpty(uniObj.getLineageDb())) {
            scoreType
                    .getProperty()
                    .add(propertyConverter.createProperty(PROPERTY_LINEAGE, uniObj.getLineageDb()));
        }
        return scoreType;
    }
}
