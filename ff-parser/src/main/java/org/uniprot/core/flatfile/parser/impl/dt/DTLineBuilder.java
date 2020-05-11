package org.uniprot.core.flatfile.parser.impl.dt;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.EntryAudit;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTLineBuilder extends FFLineBuilderAbstr<Map.Entry<EntryAudit, UniProtKBEntryType>>
        implements FFLineBuilder<Map.Entry<EntryAudit, UniProtKBEntryType>> {
    private static final String ENTRY_VERSION = "entry version ";
    private static final String SEQUENCE_VERSION = "sequence version ";

    public DTLineBuilder() {
        super(LineType.DT);
    }

    @Override
    public String buildString(Map.Entry<EntryAudit, UniProtKBEntryType> f) {
        List<String> lines = build(f.getKey(), f.getValue(), false);
        return FFLines.create(lines).toString();
    }

    @Override
    public String buildStringWithEvidence(Map.Entry<EntryAudit, UniProtKBEntryType> f) {
        List<String> lines = build(f.getKey(), f.getValue(), false);
        return FFLines.create(lines).toString();
    }

    @Override
    protected FFLine buildLine(Map.Entry<EntryAudit, UniProtKBEntryType> f, boolean showEvidence) {
        List<String> lines = build(f.getKey(), f.getValue(), true);
        return FFLines.create(lines);
    }

    private List<String> build(
            EntryAudit audit, UniProtKBEntryType type, boolean includeFlatFileMarkings) {
        List<String> lines = new ArrayList<>();
        lines.add(
                buildLine(
                        audit.getFirstPublicDate(),
                        getIntegrateString(type),
                        includeFlatFileMarkings));
        lines.add(
                buildLine(
                        audit.getLastSequenceUpdateDate(),
                        SEQUENCE_VERSION + audit.getSequenceVersion(),
                        includeFlatFileMarkings));
        lines.add(
                buildLine(
                        audit.getLastAnnotationUpdateDate(),
                        ENTRY_VERSION + audit.getEntryVersion(),
                        includeFlatFileMarkings));
        return lines;
    }

    private String buildLine(LocalDate date, String value, boolean includeFlatFileMarkings) {
        StringBuilder sb = new StringBuilder();
        if (includeFlatFileMarkings) {
            sb.append(linePrefix);
        }
        String sdate = dateFormatter.format(date).toUpperCase();
        sb.append(sdate);
        sb.append(SEPARATOR_COMA);
        sb.append(value);
        sb.append(STOP);
        return sb.toString();
    }

    private String getIntegrateString(UniProtKBEntryType type) {
        StringBuilder sb = new StringBuilder("integrated into ");
        switch (type) {
            case SWISSPROT:
                sb.append(UNIPROT_SWISSPROT);
                break;
            case TREMBL:
                sb.append(UNIPROT_TREMBL);
                break;
            case UNKNOWN:
                sb.append(UNIPROT);
                break;
        }
        return sb.toString();
    }
}
