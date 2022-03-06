package org.uniprot.core.xml.uniprot.description;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.description.impl.ProteinNameBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName;

public class AltNameConverter
        implements Converter<AlternativeName, ProteinName>, ToXmlDbReferences<ProteinName> {
    private final NameConverter nameConverter;
    private final ECConverter ecConverter;
    private final ObjectFactory xmlUniprotFactory;

    public AltNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
        this(nameConverter, ecConverter, new ObjectFactory());
    }

    public AltNameConverter(
            NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
        this.nameConverter = nameConverter;
        this.ecConverter = ecConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinName fromXml(AlternativeName xmlObj) {
        ProteinNameBuilder buider = new ProteinNameBuilder();
        if(Utils.notNull(xmlObj.getFullName())) {
            buider.fullName(nameConverter.fromXml(xmlObj.getFullName()));
        }
        if(Utils.notNullNotEmpty(xmlObj.getShortName())){
            buider.shortNamesSet(
                    xmlObj.getShortName().stream()
                            .map(nameConverter::fromXml)
                            .collect(Collectors.toList()));
        }
        if(Utils.notNullNotEmpty(xmlObj.getEcNumber())){
            buider.ecNumbersSet(
                    xmlObj.getEcNumber().stream()
                            .map(ecConverter::fromXml)
                            .collect(Collectors.toList()));
        }
        return buider.build();
    }

    @Override
    public AlternativeName toXml(ProteinName uniObj) {
        AlternativeName altName = xmlUniprotFactory.createProteinTypeAlternativeName();
        if(Utils.notNull(uniObj.getFullName())) {
            altName.setFullName(nameConverter.toXml(uniObj.getFullName()));
        }
        if(Utils.notNullNotEmpty(uniObj.getShortNames())) {
            uniObj.getShortNames()
                    .forEach(val -> altName.getShortName().add(nameConverter.toXml(val)));
        }
        if(Utils.notNullNotEmpty(uniObj.getEcNumbers())) {
            uniObj.getEcNumbers()
                    .forEach(val -> altName.getEcNumber().add(ecConverter.toXml(val)));
        }
        return altName;
    }

    public List<DbReferenceType> toXmlDbReferences(ProteinName uniObj) {
        return uniObj.getEcNumbers().stream()
                .map(ecConverter::toXmlDbReference)
                .collect(Collectors.toList());
    }
}
