package org.uniprot.core.xml.uniref;

import com.google.common.base.Strings;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.Entry;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniref.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public class UniRefEntryConverter implements Converter<Entry, UniRefEntry> {
    public static final String PROPERTY_MEMBER_COUNT = "member count";
    public static final String PROPERTY_COMMON_TAXON = "common taxon";
    public static final String PROPERTY_COMMON_TAXON_ID = "common taxon ID";
    public static final String PROPERTY_GO_FUNCTION = "GO Molecular Function";
    public static final String PROPERTY_GO_COMPONENT = "GO Cellular Component";
    public static final String PROPERTY_GO_PROCESS = "GO Biological Process";

    private final MemberConverter memberConverter;
    private final RepresentativeMemberConverter representativeMemverConverter;
    private final ObjectFactory jaxbFactory;

    public UniRefEntryConverter() {
        this(new ObjectFactory());
    }

    public UniRefEntryConverter(ObjectFactory jaxbFactory) {
        this.jaxbFactory = jaxbFactory;
        memberConverter = new MemberConverter(jaxbFactory);
        representativeMemverConverter = new RepresentativeMemberConverter(jaxbFactory);
    }

    @Override
    public UniRefEntry fromXml(Entry xmlObj) {

        UniRefEntryBuilder builder = new UniRefEntryBuilder();
        builder.id(xmlObj.getId())
                .entryType(getTypeFromId(xmlObj.getId()))
                .name(xmlObj.getName())
                .updated(XmlConverterHelper.dateFromXml(xmlObj.getUpdated()))
                .representativeMember(
                        representativeMemverConverter.fromXml(xmlObj.getRepresentativeMember()))
                .membersSet(
                        xmlObj.getMember().stream()
                                .map(memberConverter::fromXml)
                                .collect(Collectors.toList()));

        updatePropertFromXml(builder, xmlObj);
        return builder.build();
    }

    private UniRefType getTypeFromId(String id) {
        if (!id.contains("_")) {
            throw new IllegalArgumentException("UniRef id:" + id + " is wrong format");
        }
        String val = id.substring(0, id.indexOf("_"));
        return UniRefType.valueOf(val);
    }

    private void updatePropertFromXml(UniRefEntryBuilder builder, Entry jaxbEntry) {
        for (PropertyType property : jaxbEntry.getProperty()) {
            if (property.getType().equals(PROPERTY_COMMON_TAXON)) {
                builder.commonTaxon(property.getValue());
            } else if (property.getType().equals(PROPERTY_COMMON_TAXON_ID)) {
                builder.commonTaxonId(Long.parseLong(property.getValue()));
            } else if (property.getType().equals(PROPERTY_GO_FUNCTION)) {
                builder.goTermsAdd(createGoTerm(GoAspect.FUNCTION, property.getValue()));
            } else if (property.getType().equals(PROPERTY_GO_COMPONENT)) {
                builder.goTermsAdd(createGoTerm(GoAspect.COMPONENT, property.getValue()));
            } else if (property.getType().equals(PROPERTY_GO_PROCESS)) {
                builder.goTermsAdd(createGoTerm(GoAspect.PROCESS, property.getValue()));
            } else if (property.getType().equals(PROPERTY_MEMBER_COUNT)) {
                builder.memberCount(Integer.parseInt(property.getValue()));
            } else {
                System.out.println("property.typeOf() = " + property.getType() + " not supported");
            }
        }
    }

    private GeneOntologyEntry createGoTerm(GoAspect aspect, String id) {
        return new GeneOntologyEntryBuilder().aspect(aspect).id(id).build();
    }

    @Override
    public Entry toXml(UniRefEntry uniObj) {
        Entry jaxbEntry = jaxbFactory.createEntry();
        jaxbEntry.setId(uniObj.getId().getValue());
        jaxbEntry.setName(uniObj.getName());

        jaxbEntry.setRepresentativeMember(
                representativeMemverConverter.toXml(uniObj.getRepresentativeMember()));
        jaxbEntry.setUpdated(XmlConverterHelper.dateToXml(uniObj.getUpdated()));
        uniObj.getMembers().stream()
                .map(memberConverter::toXml)
                .forEach(jaxbEntry.getMember()::add);
        updatePropertyToXml(jaxbEntry, uniObj);
        return jaxbEntry;
    }

    private void updatePropertyToXml(Entry jaxbEntry, UniRefEntry uniObj) {
        int count = uniObj.getMemberCount();
        if (count == 0) {
            count = uniObj.getMembers().size() + 1;
        }
        jaxbEntry.getProperty().add(createProperty(PROPERTY_MEMBER_COUNT, String.valueOf(count)));
        if (!Strings.isNullOrEmpty(uniObj.getCommonTaxon())) {
            jaxbEntry
                    .getProperty()
                    .add(createProperty(PROPERTY_COMMON_TAXON, uniObj.getCommonTaxon()));
        }
        jaxbEntry
                .getProperty()
                .add(
                        createProperty(
                                PROPERTY_COMMON_TAXON_ID,
                                String.valueOf(uniObj.getCommonTaxonId())));
        uniObj.getGoTerms().stream().map(this::convert).forEach(jaxbEntry.getProperty()::add);
    }

    private PropertyType convert(GeneOntologyEntry goTerm) {
        switch (goTerm.getAspect()) {
            case FUNCTION:
                return createProperty(PROPERTY_GO_FUNCTION, goTerm.getId());
            case COMPONENT:
                return createProperty(PROPERTY_GO_COMPONENT, goTerm.getId());
            case PROCESS:
                return createProperty(PROPERTY_GO_PROCESS, goTerm.getId());
        }
        return createProperty(goTerm.getAspect().getDisplayName(), goTerm.getId());
    }

    private PropertyType createProperty(String type, String value) {
        PropertyType property = jaxbFactory.createPropertyType();
        property.setType(type);
        property.setValue(value);
        return property;
    }
}
