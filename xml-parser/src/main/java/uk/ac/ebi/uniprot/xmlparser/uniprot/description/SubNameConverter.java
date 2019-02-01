package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinNameBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.SubmittedName;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SubNameConverter implements Converter<SubmittedName, ProteinName>, ToXmlDbReferences<ProteinName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public SubNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public SubNameConverter(NameConverter nameConverter, ECConverter ecConverter,
                            ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinName fromXml(SubmittedName xmlObj) {
        return createProteinName(
                nameConverter.fromXml(xmlObj.getFullName()),
                Collections.emptyList(),
                xmlObj.getEcNumber().stream().map(ecConverter::fromXml)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public SubmittedName toXml(ProteinName uniObj) {
        SubmittedName subName = xmlUniprotFactory.createProteinTypeSubmittedName();
        subName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        uniObj.getEcNumbers().forEach(val -> subName.getEcNumber().add(ecConverter.toXml(val)));
        return subName;
    }

    private ProteinName createProteinName(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinNameBuilder()
                .ecNumbers(ecNumbers)
                .shortNames(shortNames)
                .fullName(fullName)
                .build();
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinName uniObj) {
        return uniObj.getEcNumbers().stream().map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
