package org.uniprot.core.xml.uniprot.description;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.impl.ProteinSubNameBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.SubmittedName;

public class SubNameConverter
        implements Converter<SubmittedName, ProteinSubName>, ToXmlDbReferences<ProteinSubName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public SubNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public SubNameConverter(
            NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinSubName fromXml(SubmittedName xmlObj) {
        return createProteinName(
                nameConverter.fromXml(xmlObj.getFullName()),
                xmlObj.getEcNumber().stream()
                        .map(ecConverter::fromXml)
                        .collect(Collectors.toList()));
    }

    @Override
    public SubmittedName toXml(ProteinSubName uniObj) {
        SubmittedName subName = xmlUniprotFactory.createProteinTypeSubmittedName();
        subName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        uniObj.getEcNumbers().forEach(val -> subName.getEcNumber().add(ecConverter.toXml(val)));
        return subName;
    }

    private ProteinSubName createProteinName(Name fullName, List<EC> ecNumbers) {
        return new ProteinSubNameBuilder().ecNumbersSet(ecNumbers).fullName(fullName).build();
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinSubName uniObj) {
        return uniObj.getEcNumbers().stream()
                .map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
