package org.uniprot.core.xml.uniprot.description;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.description.impl.ProteinNameBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName;

public class RecNameConverter
        implements Converter<RecommendedName, ProteinName>, ToXmlDbReferences<ProteinName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public RecNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public RecNameConverter(
            NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinName fromXml(RecommendedName xmlObj) {
        return new ProteinNameBuilder()
                .fullName(nameConverter.fromXml(xmlObj.getFullName()))
                .shortNamesSet(
                        xmlObj.getShortName().stream()
                                .map(nameConverter::fromXml)
                                .collect(Collectors.toList()))
                .ecNumbersSet(
                        xmlObj.getEcNumber().stream()
                                .map(ecConverter::fromXml)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public RecommendedName toXml(ProteinName uniObj) {
        RecommendedName recName = xmlUniprotFactory.createProteinTypeRecommendedName();
        recName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        uniObj.getShortNames().forEach(val -> recName.getShortName().add(nameConverter.toXml(val)));
        uniObj.getEcNumbers().forEach(val -> recName.getEcNumber().add(ecConverter.toXml(val)));
        return recName;
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinName uniObj) {
        return uniObj.getEcNumbers().stream()
                .map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
