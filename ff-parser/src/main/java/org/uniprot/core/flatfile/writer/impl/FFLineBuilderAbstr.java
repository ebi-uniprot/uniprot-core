package org.uniprot.core.flatfile.writer.impl;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.DEFAUT_LINESPACE;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public abstract class FFLineBuilderAbstr<T> implements FFLineBuilder<T> {
    protected abstract FFLine buildLine(T f, boolean showEvidence);

    protected final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    protected final LineType lineType;
    protected final String linePrefix;

    public FFLineBuilderAbstr(LineType lineType) {
        this(lineType, DEFAUT_LINESPACE);
    }

    public FFLineBuilderAbstr(LineType lineType, String linespace) {
        this.lineType = lineType;
        this.linePrefix = lineType + linespace;
    }

    @Override
    public FFLine build(T f) {
        return buildLine(f, false);
    }

    @Override
    public FFLine buildWithEvidence(T f) {
        return buildLine(f, true);
    }

    protected StringBuilder addEvidences(StringBuilder sb, HasEvidences he, boolean showEvidence) {
        if (!showEvidence) return sb;
        sb.append(LineBuilderHelper.export(he.getEvidences()));
        return sb;
    }

    protected void appendIfNot(StringBuilder sb, char c) {

        if (sb.charAt(sb.length() - 1) != c) {
            sb.append(c);
        }
    }

    protected void appendIfNot(StringBuilder sb, String c) {
        if (!sb.toString().endsWith(c)) {
            sb.append(c);
        }
    }
}
