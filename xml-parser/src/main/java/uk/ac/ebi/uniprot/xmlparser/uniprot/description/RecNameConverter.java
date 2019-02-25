package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinRecNameBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.RecommendedName;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class RecNameConverter implements Converter<RecommendedName, ProteinRecName>, ToXmlDbReferences<ProteinRecName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public RecNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public RecNameConverter(NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinRecName fromXml(RecommendedName xmlObj) {
        return new ProteinRecNameBuilder()
                .fullName(nameConverter.fromXml(xmlObj.getFullName()))
                .shortNames(xmlObj.getShortName().stream().map(nameConverter::fromXml)
                                    .collect(Collectors.toList()))
                .ecNumbers(xmlObj.getEcNumber().stream().map(ecConverter::fromXml)
                                   .collect(Collectors.toList()))
                .build();
    }

    @Override
    public RecommendedName toXml(ProteinRecName uniObj) {
        RecommendedName recName = xmlUniprotFactory.createProteinTypeRecommendedName();
        recName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        uniObj.getShortNames().forEach(val -> recName.getShortName().add(nameConverter.toXml(val)));
        uniObj.getEcNumbers().forEach(val -> recName.getEcNumber().add(ecConverter.toXml(val)));
        return recName;
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinRecName uniObj) {
        return uniObj.getEcNumbers().stream().map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
