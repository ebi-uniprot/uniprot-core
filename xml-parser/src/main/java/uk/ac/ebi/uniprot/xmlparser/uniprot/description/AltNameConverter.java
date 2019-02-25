package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinAltNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinRecNameBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.AlternativeName;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class AltNameConverter implements Converter<AlternativeName, ProteinAltName>, ToXmlDbReferences<ProteinAltName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public AltNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public AltNameConverter(NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinAltName fromXml(AlternativeName xmlObj) {
        return new ProteinAltNameBuilder()
                .fullName(nameConverter.fromXml(xmlObj.getFullName()))
                .shortNames(xmlObj.getShortName().stream().map(nameConverter::fromXml)
                                    .collect(Collectors.toList()))
                .ecNumbers(xmlObj.getEcNumber().stream().map(ecConverter::fromXml)
                                   .collect(Collectors.toList()))
                .build();
    }

    @Override
    public AlternativeName toXml(ProteinAltName uniObj) {
        AlternativeName altName = xmlUniprotFactory.createProteinTypeAlternativeName();
        altName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        uniObj.getShortNames().forEach(val -> altName.getShortName().add(nameConverter.toXml(val)));
        uniObj.getEcNumbers().forEach(val -> altName.getEcNumber().add(ecConverter.toXml(val)));
        return altName;
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinAltName uniObj) {
        return uniObj.getEcNumbers().stream().map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
