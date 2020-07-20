package org.uniprot.core.xml.uniref;

import static org.uniprot.core.uniref.UniRefUtils.getUniProtKBIdType;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniref.Entry;
import org.uniprot.core.xml.jaxb.uniref.MemberType;
import org.uniprot.core.xml.jaxb.uniref.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

/**
 * @author lgonzales
 * @since 06/07/2020
 */
public class UniRefEntryLightConverter implements Converter<Entry, UniRefEntryLight> {
    public static final String PROPERTY_MEMBER_COUNT = "member count";
    public static final String PROPERTY_COMMON_TAXON = "common taxon";
    public static final String PROPERTY_COMMON_TAXON_ID = "common taxon ID";
    public static final String PROPERTY_UNIPARC_ID = "UniParc ID";
    public static final String PROPERTY_SOURCE_ORGANISM = "source organism";
    public static final String PROPERTY_ORGANISM_ID = "NCBI taxonomy";
    private static final String PROPERTY_UNIPROT_ACCESSION = "UniProtKB accession";
    private static final String PROPERTY_GO_FUNCTION = "GO Molecular Function";
    private static final String PROPERTY_GO_COMPONENT = "GO Cellular Component";
    private static final String PROPERTY_GO_PROCESS = "GO Biological Process";
    private static final String PROPERTY_IS_SEED = "isSeed";

    @Override
    public UniRefEntryLight fromXml(Entry xmlObj) {
        UniRefEntryLightBuilder builder = new UniRefEntryLightBuilder();
        builder.id(xmlObj.getId())
                .entryType(getTypeFromId(xmlObj.getId()))
                .name(xmlObj.getName())
                .updated(XmlConverterHelper.dateFromXml(xmlObj.getUpdated()))
                .sequence(xmlObj.getRepresentativeMember().getSequence().getValue())
                .representativeId(xmlObj.getRepresentativeMember().getDbReference().getId());

        updateMemberValuesFromXml(
                builder, Collections.singletonList(xmlObj.getRepresentativeMember()));
        updateCommonPropertiesFromXml(builder, xmlObj);
        updateMemberValuesFromXml(builder, xmlObj.getMember());
        return builder.build();
    }

    private void updateMemberValuesFromXml(
            UniRefEntryLightBuilder builder, List<MemberType> members) {
        for (MemberType memberType : members) {
            DbReferenceType dbReference = memberType.getDbReference();
            if (dbReference.getType().equalsIgnoreCase(PROPERTY_UNIPARC_ID)) {
                builder.membersAdd(dbReference.getId());
                builder.memberIdTypesAdd(UniRefMemberIdType.UNIPARC);
            }
            updateMemberPropertiesFromXml(builder, dbReference.getProperty(), dbReference.getId());
        }
    }

    private UniRefType getTypeFromId(String id) {
        if (!id.contains("_")) {
            throw new IllegalArgumentException("UniRef id:" + id + " is wrong format");
        }
        String val = id.substring(0, id.indexOf('_'));
        return UniRefType.valueOf(val);
    }

    private void updateCommonPropertiesFromXml(UniRefEntryLightBuilder builder, Entry jaxbEntry) {
        for (PropertyType property : jaxbEntry.getProperty()) {
            switch (property.getType()) {
                case PROPERTY_COMMON_TAXON:
                    builder.commonTaxon(property.getValue());
                    break;
                case PROPERTY_COMMON_TAXON_ID:
                    builder.commonTaxonId(Long.parseLong(property.getValue()));
                    break;
                case PROPERTY_MEMBER_COUNT:
                    builder.memberCount(Integer.parseInt(property.getValue()));
                    break;
                case PROPERTY_GO_FUNCTION:
                    builder.goTermsAdd(createGoTerm(GoAspect.FUNCTION, property.getValue()));
                    break;
                case PROPERTY_GO_COMPONENT:
                    builder.goTermsAdd(createGoTerm(GoAspect.COMPONENT, property.getValue()));
                    break;
                case PROPERTY_GO_PROCESS:
                    builder.goTermsAdd(createGoTerm(GoAspect.PROCESS, property.getValue()));
                    break;
                default:
                    break;
            }
        }
    }

    private GeneOntologyEntry createGoTerm(GoAspect aspect, String id) {
        return new GeneOntologyEntryBuilder().aspect(aspect).id(id).build();
    }

    private void updateMemberPropertiesFromXml(
            UniRefEntryLightBuilder builder, List<PropertyType> properties, String id) {
        String accession = null;
        for (PropertyType property : properties) {
            switch (property.getType()) {
                case PROPERTY_SOURCE_ORGANISM:
                    builder.organismsAdd(property.getValue());
                    break;
                case PROPERTY_ORGANISM_ID:
                    builder.organismIdsAdd(Long.parseLong(property.getValue()));
                    break;
                case PROPERTY_UNIPROT_ACCESSION:
                    if (accession == null) { // get first accession from xml as member.
                        accession = property.getValue();
                    }
                    break;
                case PROPERTY_IS_SEED:
                    if(Boolean.parseBoolean(property.getValue())){
                        builder.seedId(id);
                    }
                    break;
                default:
                    break;
            }
        }
        if (accession != null) {
            builder.membersAdd(accession);
            builder.memberIdTypesAdd(getUniProtKBIdType(id, accession));
        }
    }

    @Override
    public Entry toXml(UniRefEntryLight uniObj) {
        throw new UnsupportedOperationException("Unable to convert UniRefEntryLight to XML");
    }
}
