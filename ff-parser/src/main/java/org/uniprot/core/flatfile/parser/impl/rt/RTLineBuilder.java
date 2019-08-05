package org.uniprot.core.flatfile.parser.impl.rt;

import com.google.common.base.Strings;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.RLine;

public class RTLineBuilder implements RLine<String> {
	private final LineType lineType = LineType.RT;
	private final String linePrefix = lineType + DEFAUT_LINESPACE;
	@Override
	public List<String> buildLine(String f, boolean includeFFMarkup,
			boolean showEvidence) {
		if(Strings.isNullOrEmpty(f))
			return new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkup){
			sb.append(linePrefix);	
		}
		sb.append("\"");
		sb.append(f);
		if(needAddStop(f))
			sb.append(STOP);
		sb.append("\";");
		if(includeFFMarkup){
			return FFLineWrapper.buildLines(sb.toString(),
					SEPS, linePrefix, LINE_LENGTH);
		}else{
			List<String> lines = new ArrayList<>();
			lines.add(sb.toString());
			return lines;
		}
	}
	private boolean needAddStop(String title){
		if ((title ==null) ||title.isEmpty())
			return false;
		if(!title.endsWith(".")
				&& !title.endsWith("?")
				&& !title.endsWith("!"))
			return true;
		return false;
	}
}
