package org.uniprot.core.xml.uniref;

import static org.uniprot.core.uniref.UniRefUtils.getUniProtKBIdType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.impl.AbstractUniRefMemberBuilder;
import org.uniprot.core.uniref.impl.OverlapRegionBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniref.MemberType;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniref.PropertyType;

import com.google.common.base.Strings;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public abstract class AbstractMemberConverter<T extends UniRefMember>
        implements Converter<MemberType, T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractMemberConverter.class);
    public static final String PROPERTY_PROTEIN_NAME = "protein name";
    public static final String PROPERTY_NCBI_TAXONOMY = "NCBI taxonomy";
    public static final String PROPERTY_SOURCE_ORGANISM = "source organism";
    public static final String PROPERTY_SOURCE_UNIPARC = "UniParc ID";
    public static final String PROPERTY_SOURCE_UNIREF100 = "UniRef100 ID";
    public static final String PROPERTY_SOURCE_UNIREF90 = "UniRef90 ID";
    public static final String PROPERTY_SOURCE_UNIREF50 = "UniRef50 ID";
    public static final String PROPERTY_SOURCE_UNIPROT = "UniProtKB accession";
    public static final String PROPERTY_SOURCE_LENGTH = "length";
    public static final String PROPERTY_SOURCE_OVERLAP = "overlap region";
    public static final String PROPERTY_IS_SEED = "isSeed";
    protected final ObjectFactory jaxbFactory;

    public AbstractMemberConverter(ObjectFactory jaxbFactory) {
        this.jaxbFactory = jaxbFactory;
    }

    protected void updateMemberToXml(MemberType memberType, T uniObj) {
        DbReferenceType xref = jaxbFactory.createDbReferenceType();
        memberType.setDbReference(xref);
        if (uniObj.getMemberIdType().equals(UniRefMemberIdType.UNIPROTKB_TREMBL)
                || uniObj.getMemberIdType().equals(UniRefMemberIdType.UNIPROTKB_SWISSPROT)) {
            xref.setType(UniRefMemberIdType.UNIPROTKB.getXmlName());
        } else {
            xref.setType(uniObj.getMemberIdType().getXmlName());
        }
        xref.setId(uniObj.getMemberId());

        if (!Strings.isNullOrEmpty(uniObj.getOrganismName())) {
            xref.getProperty()
                    .add(createProperty(PROPERTY_SOURCE_ORGANISM, uniObj.getOrganismName()));
        }
        xref.getProperty()
                .add(
                        createProperty(
                                PROPERTY_NCBI_TAXONOMY, String.valueOf(uniObj.getOrganismTaxId())));

        if (!Strings.isNullOrEmpty(uniObj.getProteinName())) {
            xref.getProperty().add(createProperty(PROPERTY_PROTEIN_NAME, uniObj.getProteinName()));
        }
        if (uniObj.getUniParcId() != null) {
            xref.getProperty()
                    .add(createProperty(PROPERTY_SOURCE_UNIPARC, uniObj.getUniParcId().getValue()));
        }
        if (uniObj.getUniRef100Id() != null) {
            xref.getProperty()
                    .add(
                            createProperty(
                                    PROPERTY_SOURCE_UNIREF100, uniObj.getUniRef100Id().getValue()));
        }
        if (uniObj.getUniRef90Id() != null) {
            xref.getProperty()
                    .add(
                            createProperty(
                                    PROPERTY_SOURCE_UNIREF90, uniObj.getUniRef90Id().getValue()));
        }
        if (uniObj.getUniRef50Id() != null) {
            xref.getProperty()
                    .add(
                            createProperty(
                                    PROPERTY_SOURCE_UNIREF50, uniObj.getUniRef50Id().getValue()));
        }
        if ((uniObj.getUniProtAccessions() != null) && !uniObj.getUniProtAccessions().isEmpty()) {
            List<UniProtKBAccession> accessions = uniObj.getUniProtAccessions();
            accessions.forEach(
                    accession -> {
                        xref.getProperty()
                                .add(createProperty(PROPERTY_SOURCE_UNIPROT, accession.getValue()));
                    });
        }
        if (uniObj.getSequenceLength() > 0) {
            xref.getProperty()
                    .add(
                            createProperty(
                                    PROPERTY_SOURCE_LENGTH,
                                    String.valueOf(uniObj.getSequenceLength())));
        }
        if (uniObj.getOverlapRegion() != null) {
            String overlap =
                    uniObj.getOverlapRegion().getStart() + "-" + uniObj.getOverlapRegion().getEnd();
            xref.getProperty().add(createProperty(PROPERTY_SOURCE_OVERLAP, overlap));
        }
        if (uniObj.isSeed() != null) {
            xref.getProperty()
                    .add(createProperty(PROPERTY_IS_SEED, String.valueOf(uniObj.isSeed())));
        }
    }

    private PropertyType createProperty(String type, String value) {
        PropertyType property = jaxbFactory.createPropertyType();
        property.setType(type);
        property.setValue(value);
        return property;
    }

    protected void updateMemberFromXml(
            AbstractUniRefMemberBuilder<? extends AbstractUniRefMemberBuilder<?, T>, T> builder,
            MemberType xmlObj) {
        String memberId = xmlObj.getDbReference().getId();
        builder.memberId(memberId);
        String accession = null;

        List<PropertyType> properties = xmlObj.getDbReference().getProperty();
        for (PropertyType property : properties) {
            switch (property.getType()) {
                case PROPERTY_NCBI_TAXONOMY:
                    builder.organismTaxId(Long.parseLong(property.getValue()));
                    break;
                case PROPERTY_PROTEIN_NAME:
                    builder.proteinName(property.getValue());
                    break;
                case PROPERTY_SOURCE_ORGANISM:
                    builder.organismName(property.getValue());
                    break;
                case PROPERTY_SOURCE_UNIPARC:
                    builder.uniparcId(new UniParcIdBuilder(property.getValue()).build());
                    break;
                case PROPERTY_SOURCE_LENGTH:
                    builder.sequenceLength(Integer.parseInt(property.getValue()));
                    break;
                case PROPERTY_SOURCE_OVERLAP:
                    String strOverlap = property.getValue();
                    String strStart = strOverlap.substring(0, strOverlap.indexOf('-'));
                    String strEnd =
                            strOverlap.substring(strOverlap.indexOf('-') + 1, strOverlap.length());
                    builder.overlapRegion(
                            new OverlapRegionBuilder()
                                    .start(Integer.parseInt(strStart))
                                    .end(Integer.parseInt(strEnd))
                                    .build());
                    break;
                case PROPERTY_SOURCE_UNIREF100:
                    builder.uniref100Id(new UniRefEntryIdBuilder(property.getValue()).build());
                    break;
                case PROPERTY_SOURCE_UNIREF90:
                    builder.uniref90Id(new UniRefEntryIdBuilder(property.getValue()).build());
                    break;
                case PROPERTY_SOURCE_UNIREF50:
                    builder.uniref50Id(new UniRefEntryIdBuilder(property.getValue()).build());
                    break;
                case PROPERTY_SOURCE_UNIPROT:
                    if (accession == null) {
                        accession = property.getValue();
                    }
                    builder.accessionsAdd(
                            new UniProtKBAccessionBuilder(property.getValue()).build());
                    break;
                case PROPERTY_IS_SEED:
                    builder.isSeed(Boolean.parseBoolean(property.getValue()));
                    break;
                default:
                    logger.error(
                            "XML member property: " + property.getType() + " is not supported");
                    break;
            }
        }

        UniRefMemberIdType memberType =
                UniRefMemberIdType.typeOf(xmlObj.getDbReference().getType());
        if (memberType.equals(UniRefMemberIdType.UNIPROTKB)) {
            memberType = getUniProtKBIdType(memberId, accession);
        }
        builder.memberIdType(memberType);
    }
}
