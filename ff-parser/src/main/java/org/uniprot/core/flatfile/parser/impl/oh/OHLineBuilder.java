package org.uniprot.core.flatfile.parser.impl.oh;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;

public class OHLineBuilder extends FFLineBuilderAbstr<List<OrganismHost>>
        implements FFLineBuilder<List<OrganismHost>> {
    private static final String NCBI_TAX_ID = "NCBI_TaxID=";

    public OHLineBuilder() {
        super(LineType.OH);
    }

    @Override
    protected FFLine buildLine(List<OrganismHost> f, boolean showEvidence) {
        List<String> lls = new ArrayList<>();
        for (OrganismHost oh : f) {
            lls.add(build(oh, showEvidence, true).toString());
        }
        return FFLines.create(lls);
    }

    @Override
    public String buildString(List<OrganismHost> f) {
        StringBuilder sb = new StringBuilder();
        for (OrganismHost oh : f) {
            sb.append(build(oh, false, false));
        }
        return sb.toString();
    }

    @Override
    public String buildStringWithEvidence(List<OrganismHost> f) {
        StringBuilder sb = new StringBuilder();
        for (OrganismHost oh : f) {
            sb.append(build(oh, true, false));
        }
        return sb.toString();
    }

    private StringBuilder build(
            OrganismHost organismHost, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) sb.append(linePrefix);

        sb.append(NCBI_TAX_ID);
        sb.append(organismHost.getTaxonId());
        sb.append("; ");
        if (organismHost != null) {
            if (organismHost.getScientificName() != null) {
                sb.append(organismHost.getScientificName());
            }
            if (organismHost.getCommonName() != null && !organismHost.getCommonName().equals("")) {
                sb.append(" (");
                sb.append(organismHost.getCommonName());
                sb.append(")");
            }
            if (!organismHost.getSynonyms().isEmpty()) {
                String val = organismHost.getSynonyms().stream().collect(Collectors.joining(", "));
                sb.append(" (");
                sb.append(val);
                sb.append(")");
            }
        }
        sb.append(STOP);
        return sb;
    }
}
