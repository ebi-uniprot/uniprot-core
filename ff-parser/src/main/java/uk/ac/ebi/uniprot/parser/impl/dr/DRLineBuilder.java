package uk.ac.ebi.uniprot.parser.impl.dr;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEMICOLON;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbDisplayOrder;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypeDetail;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

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

		UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
		List<String> lines = new ArrayList<>();

		for (UniProtXDbTypeDetail databaseType : dorder.getOrderedDatabases()) {
			List<UniProtDBCrossReference> listDBXref = f.getCrossReferencesByType(databaseType.getName());
			listDBXref.stream().map(xref -> export(xref, includeFFMarkings, showEvidence))
					.forEach(val -> lines.add(val));
		}
		return lines;
	}

	public String export(UniProtDBCrossReference reference, boolean includeFFMarkings, boolean showEvidence) {
		UniProtXDbType dbType = reference.getDatabaseType();
		if ((dbType.equals("EMBL")) && !includeFFMarkings)
			return exportEMBLNoFF(reference);
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkings) {
			sb.append(linePrefix);
			sb.append(reference.getDatabaseType().getDetail().getDisplayName())
			.append("; ");
		}
		sb.append(reference.getId());
		sb = append(sb, reference.getProperties(), 0, includeFFMarkings);
		sb = append(sb, reference.getProperties(), 1, includeFFMarkings);
		sb = append(sb, reference.getProperties(), 2, includeFFMarkings);

		if (includeFFMarkings)
			sb.append(".");

		addEvidences(sb, reference, showEvidence);

		if (!Strings.isNullOrEmpty(reference.getIsoformId())) {
			sb.append(" [").append(reference.getIsoformId()).append("]");
		}
		return sb.toString();
	}

	private StringBuilder append(StringBuilder sb, String value, boolean includeFFMarkings) {

		if (includeFFMarkings)
			sb.append(SEMICOLON);
		sb.append(SPACE);
		sb.append(value);
		return sb;
	}

	private StringBuilder append(StringBuilder sb, List<Property> properties, int pos , boolean includeFFMarkings) {
		if (properties.size()>pos) {
			return append(sb, properties.get(pos).getValue(), includeFFMarkings);
		} else if(pos ==0) {
			return append(sb, "-", includeFFMarkings);
		}else
			return sb;

	}


	private static String exportEMBLNoFF(UniProtDBCrossReference embl) {
		StringBuilder sb = new StringBuilder();
		sb.append(embl.getId());
		sb.append(" ");
		String description = embl.getProperties().get(0).getValue();
		sb.append(description).append(" ");
		for (String part : description.split("\\.")) {
			sb.append(part).append(" ");
		}
		sb.append(" ");
		sb.append(embl.getProperties().get(1).getValue());
		sb.append(" ");
		sb.append(embl.getProperties().get(2).getValue());
		return sb.toString();
	}

}
