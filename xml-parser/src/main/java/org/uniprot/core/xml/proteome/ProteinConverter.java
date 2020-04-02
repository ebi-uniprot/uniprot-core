package org.uniprot.core.xml.proteome;

import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.impl.ProteinBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.EntryType;
import org.uniprot.core.xml.jaxb.proteome.GeneType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

public class ProteinConverter implements Converter<GeneType, Protein> {
    private final ObjectFactory xmlFactory;

    public ProteinConverter() {
        this(new ObjectFactory());
    }

    public ProteinConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Protein fromXml(GeneType xmlObj) {
        ProteinBuilder proteinBuilder = new ProteinBuilder();
        proteinBuilder
                .accession(xmlObj.getAccession())
                .geneName(xmlObj.getGeneName())
                .entryType(fromXml(xmlObj.getEntryType()))
                .geneNameType(GeneNameType.typeOf(xmlObj.getGeneNameType().value()))
                .sequenceLength(xmlObj.getLength());

        return proteinBuilder.build();
    }

    @Override
    public GeneType toXml(Protein uniObj) {
        GeneType xmlObj = xmlFactory.createGeneType();
        xmlObj.setAccession(uniObj.getAccession().getValue());
        xmlObj.setEntryType(toXml(uniObj.getEntryType()));
        xmlObj.setGeneName(uniObj.getGeneName());
        xmlObj.setLength(uniObj.getSequenceLength());
        xmlObj.setGeneNameType(
                org.uniprot.core.xml.jaxb.proteome.GeneNameType.fromValue(
                        uniObj.getGeneNameType().getName()));
        return xmlObj;
    }

    private UniProtKBEntryType fromXml(EntryType xmlObj) {
        if (xmlObj == EntryType.SWISS_PROT) return UniProtKBEntryType.SWISSPROT;
        else return UniProtKBEntryType.TREMBL;
    }

    private EntryType toXml(UniProtKBEntryType uniObj) {
        if (uniObj == UniProtKBEntryType.SWISSPROT) return EntryType.SWISS_PROT;
        else return EntryType.TR_EMBL;
    }
}
