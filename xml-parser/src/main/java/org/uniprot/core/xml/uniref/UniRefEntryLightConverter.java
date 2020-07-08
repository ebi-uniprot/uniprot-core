package org.uniprot.core.xml.uniref;

import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.*;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author lgonzales
 * @since 06/07/2020
 */
public class UniRefEntryLightConverter  implements Converter<Entry, UniRefEntryLight> {
    public static final String PROPERTY_MEMBER_COUNT = "member count";
    public static final String PROPERTY_COMMON_TAXON = "common taxon";
    public static final String PROPERTY_COMMON_TAXON_ID = "common taxon ID";
    public static final String PROPERTY_UNIPARC_ID = "UniParc ID";
    public static final String PROPERTY_SOURCE_ORGANISM = "source organism";
    public static final String PROPERTY_ORGANISM_ID = "NCBI taxonomy";
    private static final String PROPERTY_UNIPROT_ACCESSION = "UniProtKB accession";

    @Override
    public UniRefEntryLight fromXml(Entry xmlObj) {
        UniRefEntryLightBuilder builder = new UniRefEntryLightBuilder();
        builder.id(xmlObj.getId())
                .entryType(getTypeFromId(xmlObj.getId()))
                .name(xmlObj.getName())
                .updated(XmlConverterHelper.dateFromXml(xmlObj.getUpdated()))
                .representativeSequence(xmlObj.getRepresentativeMember().getSequence().getValue());

        updateMemberValuesFromXml(builder, Collections.singletonList(xmlObj.getRepresentativeMember()));

        updateCommonPropertiesFromXml(builder, xmlObj);
        updateMemberValuesFromXml(builder, xmlObj.getMember());
        return builder.build();
    }

    private void updateMemberValuesFromXml(UniRefEntryLightBuilder builder, List<MemberType> members) {
        boolean hasUniParc = false;
        for (MemberType memberType : members) {
            DbReferenceType dbReference = memberType.getDbReference();
            if(dbReference.getType().equalsIgnoreCase(PROPERTY_UNIPARC_ID)){
                builder.membersAdd(dbReference.getId());
                hasUniParc = true;
            }
            updateMemberPropertiesFromXml(builder, dbReference.getProperty());
        }
        builder.hasMemberUniParcIds(hasUniParc);
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
                default:
                    break;
            }
        }
    }

    private void updateMemberPropertiesFromXml(UniRefEntryLightBuilder builder, List<PropertyType> properties) {
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
                    if(accession == null){ //get first accession from xml as member.
                        accession = property.getValue();
                    }
                    break;
                default:
                    break;
            }
        }
        if(accession != null){
            builder.membersAdd(accession);
        }
    }

    @Override
    public Entry toXml(UniRefEntryLight uniObj) {
        throw new UnsupportedOperationException("Unable to convert UniRefEntryLight to XML");
    }
}