package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DbXRefAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtXDbDisplayOrder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;

public class DRLineBuilder extends FFLineBuilderAbstr<UniProtDBCrossReferences>
		implements FFLineBuilder<UniProtDBCrossReferences> {
	public DRLineBuilder() {
		super(LineType.DR);
	}

	@Override
	public String buildString(UniProtDBCrossReferences f) {
		List<String> lines = buildLine(f, false, false);
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(UniProtDBCrossReferences f) {
		List<String> lines = buildLine(f, false, true);
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(UniProtDBCrossReferences f, boolean showEvidence) {
		List<String> lines = buildLine(f, true, showEvidence);
		return FFLines.create(lines);
	}

	private List<String> buildLine(UniProtDBCrossReferences f, boolean includeFFMarkings, boolean showEvidence) {

		UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.getInstance();
		List<String> lines = new ArrayList<>();

		for (DatabaseType databaseType : dorder.getOrderedDatabases()) {
			List<UniProtDBCrossReference> listDBXref = f.getCrossReferencesByType(databaseType);
			listDBXref.stream().map(xref -> export(xref, includeFFMarkings, showEvidence))
					.forEach(val -> lines.add(val));
		}
		return lines;
	}

	public String export(UniProtDBCrossReference reference, boolean includeFFMarkings, boolean showEvidence) {
		DatabaseType dbType = reference.getDatabase();
		if ((dbType == DatabaseType.EMBL) && !includeFFMarkings)
			return exportEMBLNoFF(reference);
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkings) {
			sb.append(linePrefix);
			sb.append(reference.getDatabase().getDisplayName());
		}
		sb.append(reference.getId());
		sb = append(sb, reference.getDescription(), includeFFMarkings);
		sb = append(sb, reference.getThirdAttribute(), includeFFMarkings);
		sb = append(sb, reference.getFourthAttribute(), includeFFMarkings);

		if (includeFFMarkings)
			sb.append(".");

		addEvidences(sb, reference, showEvidence);

		if (reference.getIsoformId().isPresent()) {
			sb.append(" [").append(reference.getIsoformId().get().getValue()).append("]");
		}
		return sb.toString();
	}

	private StringBuilder append(StringBuilder sb, String value, boolean includeFFMarkings) {

		if (includeFFMarkings)
			sb.append(SEMI_COMA);
		sb.append(SPACE);
		sb.append(value);
		return sb;
	}

	private StringBuilder append(StringBuilder sb, Optional<DbXRefAttribute> opAttr, boolean includeFFMarkings) {
		if (opAttr.isPresent()) {
			return append(sb, opAttr.get().getValue(), includeFFMarkings);
		} else
			return sb;

	}

	private static String exportEMBLNoFF(UniProtDBCrossReference embl) {
		StringBuilder sb = new StringBuilder();
		sb.append(embl.getId());
		sb.append(" ");
		sb.append(embl.getDescription()).append(" ");
		for (String part : embl.getDescription().split("\\.")) {
			sb.append(part).append(" ");
		}
		sb.append(" ");
		sb.append(embl.getThirdAttribute().get().getValue());
		sb.append(" ");
		sb.append(embl.getFourthAttribute().get().getValue());
		return sb.toString();
	}

}
