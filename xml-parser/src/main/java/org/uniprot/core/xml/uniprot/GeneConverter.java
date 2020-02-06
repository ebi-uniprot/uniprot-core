package org.uniprot.core.xml.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.builder.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.HasEvidences;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.GeneNameType;
import org.uniprot.core.xml.jaxb.uniprot.GeneType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class GeneConverter implements Converter<GeneType, Gene> {
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;
    public static final String GENENAME_XMLTAG = "primary";
    public static final String SYNONYM_XMLTAG = "synonym";
    public static final String ORF_XMLTAG = "ORF";
    public static final String OLN_XMLTAG = "ordered locus";

    public GeneConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public GeneConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Gene fromXml(GeneType xmlGene) {
        GeneName geneName = null;
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        List<ORFName> orfNames = new ArrayList<>();
        String geneNameXMLTag = GENENAME_XMLTAG;
        String geneNameSynonymXMLTag = SYNONYM_XMLTAG;
        String orderedLocusNameXMLTag = OLN_XMLTAG;
        String orfNameXMLTag = ORF_XMLTAG;
        List<GeneNameType> listNames = xmlGene.getName();

        for (GeneNameType geneNameType : listNames) {
            String name = geneNameType.getValue();
            List<Evidence> evidences = evRefMapper.parseEvidenceIds(geneNameType.getEvidence());
            if (geneNameType.getType().equalsIgnoreCase(geneNameXMLTag)) {
                geneName = new GeneNameBuilder(name, evidences).build();

            } else if (geneNameType.getType().equalsIgnoreCase(geneNameSynonymXMLTag)) {
                synonyms.add(new GeneNameSynonymBuilder(name, evidences).build());

            } else if (geneNameType.getType().equalsIgnoreCase(orderedLocusNameXMLTag)) {
                olnNames.add(new OrderedLocusNameBuilder(name, evidences).build());

            } else if (geneNameType.getType().equalsIgnoreCase(orfNameXMLTag)) {
                orfNames.add(new ORFNameBuilder(name, evidences).build());
            }
        }

        return new GeneBuilder()
                .orfNamesSet(orfNames)
                .geneName(geneName)
                .synonymsSet(synonyms)
                .orderedLocusNamesSet(olnNames)
                .build();
    }

    @Override
    public GeneType toXml(Gene gene) {
        String geneNameXMLTag = GENENAME_XMLTAG;
        String geneNameSynonymXMLTag = SYNONYM_XMLTAG;
        String orderedLocusNameXMLTag = OLN_XMLTAG;
        String orfNameXMLTag = ORF_XMLTAG;
        GeneType xmlGene = xmlUniprotFactory.createGeneType();
        List<GeneNameType> listNames = xmlGene.getName();
        if (gene.hasGeneName()) {
            GeneNameType geneNameXml = xmlUniprotFactory.createGeneNameType();
            geneNameXml.setType(geneNameXMLTag);
            geneNameXml.setValue(gene.getGeneName().getValue());
            addEvidencetoXML(gene.getGeneName(), geneNameXml);
            listNames.add(geneNameXml);
        }
        for (GeneNameSynonym geneNameSynonym : gene.getSynonyms()) {
            GeneNameType geneSynXml = xmlUniprotFactory.createGeneNameType();
            geneSynXml.setType(geneNameSynonymXMLTag);
            geneSynXml.setValue(geneNameSynonym.getValue());
            addEvidencetoXML(geneNameSynonym, geneSynXml);
            listNames.add(geneSynXml);
        }
        for (OrderedLocusName orderedLocusName : gene.getOrderedLocusNames()) {
            GeneNameType geneOrderedLocusNameXml = xmlUniprotFactory.createGeneNameType();
            geneOrderedLocusNameXml.setType(orderedLocusNameXMLTag);
            geneOrderedLocusNameXml.setValue(orderedLocusName.getValue());
            addEvidencetoXML(orderedLocusName, geneOrderedLocusNameXml);
            listNames.add(geneOrderedLocusNameXml);
        }
        for (ORFName orfName : gene.getOrfNames()) {
            GeneNameType geneORFNameXml = xmlUniprotFactory.createGeneNameType();
            geneORFNameXml.setType(orfNameXMLTag);
            geneORFNameXml.setValue(orfName.getValue());
            addEvidencetoXML(orfName, geneORFNameXml);
            listNames.add(geneORFNameXml);
        }
        return xmlGene;
    }

    private void addEvidencetoXML(HasEvidences evidences, GeneNameType geneNameXml) {
        if (!evidences.getEvidences().isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidences.getEvidences());
            if (!evs.isEmpty()) geneNameXml.getEvidence().addAll(evs);
        }
    }
}
