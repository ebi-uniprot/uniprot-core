package uk.ac.ebi.uniprot.parser.impl.gn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceConverterHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.GnName;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.GnObject;

public class GnLineConverter extends EvidenceCollector implements
		Converter<GnLineObject, List<Gene>> {
	GeneFactory factory =GeneFactory.INSTANCE;
	@Override
	public List<Gene> convert(GnLineObject f) {
		List<Gene> genes = new ArrayList<>();
		for (GnObject gno : f.gnObjects) {
			GeneName geneName = null;
			List<GeneNameSynonym> synonyms = new ArrayList<>();
			List<ORFName> orfNames = new ArrayList<>();
			List<OrderedLocusName> olnNames = new ArrayList<>();
			for (GnName gn : gno.names) {
				Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper
						.convert(gn.getEvidenceInfo());
				this.addAll(evidenceMap.values());
				switch (gn.type) {
				case GENAME:
					if (!gn.names.isEmpty()) {
						 geneName =factory.createGeneName(gn.names.get(0), evidenceMap.get(gn.names.get(0)));						
					}
					break;
				case SYNNAME:

					for (String name : gn.names) {
						synonyms.add( factory.createGeneNameSynonym(name, evidenceMap.get(name)));
					}
					break;
				case ORFNAME:
					for (String name : gn.names) {
						orfNames.add(factory.createORFName(name, evidenceMap.get(name)));
					}
					break;
				case OLNAME:
					for (String name : gn.names) {
						olnNames.add(factory.createOrderedLocusName(name, evidenceMap.get(name)));
					}
					break;
				}
				
			}
			genes.add(factory.createGene(geneName, synonyms, olnNames, orfNames));
		}
		return genes;
	}
}
