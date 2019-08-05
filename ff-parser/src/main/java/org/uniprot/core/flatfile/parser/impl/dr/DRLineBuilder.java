package org.uniprot.core.flatfile.parser.impl.dr;

import com.google.common.base.Strings;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbDisplayOrder;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;

public class DRLineBuilder extends FFLineBuilderAbstr<List<UniProtDBCrossReference>>
		implements FFLineBuilder<List<UniProtDBCrossReference>> {
	public DRLineBuilder() {
		super(LineType.DR);
	}

	@Override
	public String buildString(List<UniProtDBCrossReference> f) {
		List<String> lines = buildLine(f, false, false);
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(List<UniProtDBCrossReference> f) {
		List<String> lines = buildLine(f, false, true);
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(List<UniProtDBCrossReference> f, boolean showEvidence) {
		List<String> lines = buildLine(f, true, showEvidence);
		return FFLines.create(lines);
	}

	private List<String> buildLine(List<UniProtDBCrossReference> f, boolean includeFFMarkings, boolean showEvidence) {

		UniProtXDbDisplayOrder dorder = UniProtXDbDisplayOrder.INSTANCE;
		List<String> lines = new ArrayList<>();

		for (UniProtXDbTypeDetail databaseType : dorder.getOrderedDatabases()) {
			List<UniProtDBCrossReference> listDBXref = getCrossReferencesByType(f,databaseType.getName());
			listDBXref.stream().map(xref -> export(xref, includeFFMarkings, showEvidence))
					.forEach(val -> lines.add(val));
		}
		return lines;
	}

	private List<UniProtDBCrossReference> getCrossReferencesByType(List<UniProtDBCrossReference> list,String dbName){
		return list.stream()
				.filter(val -> dbName.equals(val.getDatabaseType().getName()))
				.collect(Collectors.toList());
	}

	public String export(UniProtDBCrossReference reference, boolean includeFFMarkings, boolean showEvidence) {
		UniProtXDbType dbType = reference.getDatabaseType();
		if ((dbType.getName().equals("EMBL")) && !includeFFMarkings)
			return exportEMBLNoFF(reference);
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkings) {
			sb.append(linePrefix);
			sb.append(reference.getDatabaseType().getDetail().getDisplayName())
			.append("; ");
		}
		sb.append(reference.getId());
		append(sb, reference.getProperties(), 0, includeFFMarkings);
		append(sb, reference.getProperties(), 1, includeFFMarkings);
		append(sb, reference.getProperties(), 2, includeFFMarkings);

		if (includeFFMarkings)
			sb.append(".");

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
