package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneNameType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

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
				geneName = GeneFactory.INSTANCE.createGeneName(name, evidences);

			} else if (geneNameType.getType().equalsIgnoreCase(geneNameSynonymXMLTag)) {
				synonyms.add(GeneFactory.INSTANCE.createGeneNameSynonym(name, evidences));

			} else if (geneNameType.getType().equalsIgnoreCase(orderedLocusNameXMLTag)) {
				olnNames.add(GeneFactory.INSTANCE.createOrderedLocusName(name, evidences));

			} else if (geneNameType.getType().equalsIgnoreCase(orfNameXMLTag)) {
				orfNames.add(GeneFactory.INSTANCE.createORFName(name, evidences));
			}
		}

		return GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);

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
			if (!evs.isEmpty())
				geneNameXml.getEvidence().addAll(evs);
		}
	}
}
