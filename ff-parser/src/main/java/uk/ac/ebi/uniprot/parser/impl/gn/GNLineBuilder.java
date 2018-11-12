package uk.ac.ebi.uniprot.parser.impl.gn;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilder;

public class GNLineBuilder extends FFLineBuilderAbstr<List<Gene>> implements FFLineBuilder<List<Gene>> {
	private static final String ORF_NAMES = "ORFNames=";
	private static final String ORDERED_LOCUS_NAMES = "OrderedLocusNames=";
	private static final String SYNONYMS = "Synonyms=";
	private static final String NAME = "Name=";

	public GNLineBuilder() {
		super(LineType.GN);
	}

	@Override
	public String buildString(List<Gene> f) {
		List<String> lines = new ArrayList<>();
		boolean isFirst = true;

		for (Gene gene : f) {
			if (!isFirst) {
				lines.add(" and ");
			}
			lines.addAll(buildLines(gene, false, false));
			isFirst = false;
		}
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(List<Gene> f) {
		List<String> lines = new ArrayList<>();
		boolean isFirst = true;

		for (Gene gene : f) {
			if (!isFirst) {
				lines.add(" and ");
			}
			lines.addAll(buildLines(gene, false, true));
			isFirst = false;
		}
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(List<Gene> f, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		boolean isFirst = true;

		for (Gene gene : f) {
			if (!isFirst) {
				lines.add(linePrefix + "and");
			}
			lines.addAll(buildLines(gene, true, showEvidence));
			isFirst = false;
		}
		return FFLines.create(lines);
	}

	private List<String> buildLines(Gene gene, boolean includeFFMarkup, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if (!hasGene(gene))
			return lines;
		LineBuilder lineBuilder = new LineBuilder(linePrefix, lineType);
		List<StringBuilder> typeBuilders = new ArrayList<StringBuilder>();
		// Add Gene name
		if (gene.hasGeneName()) {
			StringBuilder gnBuilder = new StringBuilder();
			gnBuilder.append(NAME);
			gnBuilder = addValue(gnBuilder, gene.getGeneName(), showEvidence, 1, 1);
			typeBuilders.add(gnBuilder);
		}

		// Add Synonyms
		StringBuilder gSBuilder = addGeneItems(SYNONYMS, gene.getSynonyms(), showEvidence);
		if (gSBuilder != null) {
			typeBuilders.add(gSBuilder);
		}

		// Add Ordered locus Name
		StringBuilder gOLNBuilder = addGeneItems(ORDERED_LOCUS_NAMES, gene.getOrderedLocusNames(), showEvidence);
		if (gOLNBuilder != null) {
			typeBuilders.add(gOLNBuilder);
		}

		// add orfnames
		StringBuilder gORFBuilder = addGeneItems(ORF_NAMES, gene.getORFNames(), showEvidence);
		if (gORFBuilder != null) {
			typeBuilders.add(gORFBuilder);
		}
		for (StringBuilder typeBuilder : typeBuilders) {
			lineBuilder.addItem(typeBuilder.toString(), SEPARATOR);
		}
		lineBuilder.finish("");
		return lineBuilder.getLines();

	}

	private <T extends EvidencedValue> StringBuilder addGeneItems(String prefix, List<T> genes, boolean showEvidence) {
		int size = genes.size();
		if (size == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		int count = 0;
		for (EvidencedValue orfName : genes) {
			count++;
			sb = addValue(sb, orfName, showEvidence, count, size);
		}
		return sb;

	}

	private StringBuilder addValue(StringBuilder sb, EvidencedValue value, boolean showEvidence, int count,
			int totalNumber) {
		sb.append(value.getValue());
		addEvidences(sb, value, showEvidence);
		if (count < totalNumber) {
			sb.append(SEPARATOR_COMA);
		} else {
			sb.append(SEMI_COMA);
		}
		return sb;
	}

	private boolean hasGene(Gene gene) {
		return (gene.hasGeneName() || gene.getSynonyms().size() > 0 || gene.getOrderedLocusNames().size() > 0
				|| gene.getORFNames().size() > 0);
	}

}
