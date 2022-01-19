package org.uniprot.core.flatfile.parser.impl.og;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineConstant;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.GeneEncodingType;
import org.uniprot.core.uniprotkb.GeneLocation;

public class OGLineBuilder extends FFLineBuilderAbstr<List<GeneLocation>>
        implements FFLineBuilder<List<GeneLocation>> {

    private static final String SEPARATOR_AND1 = "and ";

    public OGLineBuilder() {
        super(LineType.OG);
    }

    @Override
    protected FFLine buildLine(List<GeneLocation> f, boolean showEvidence) {
        List<String> lls = new ArrayList<>();
        lls.addAll(buildLine2(f, showEvidence, false));
        lls.addAll(buildLine2(f, showEvidence, true));

        return FFLines.create(lls);
    }

    private List<String> buildLine2(List<GeneLocation> f, boolean showEvidence, boolean isPlasmid) {
        List<String> result = buildOrganellesWithLineMark2(f, showEvidence, isPlasmid);
        List<String> lls = new ArrayList<>();
        String[] seps = new String[] {FFLineConstant.SEPARATOR_COMA, FFLineConstant.SEPARATOR_AND};
        for (String line : result) {
            List<String> lls1 =
                    FFLineWrapper.buildLines(line, seps, linePrefix, FFLineConstant.LINE_LENGTH);
            lls.addAll(lls1);
        }
        return lls;
    }

    @Override
    public String buildString(List<GeneLocation> f) {
        StringBuilder sb = buildOrganellesNoFFMarkup(f, false, false);
        StringBuilder plasmid = buildOrganellesNoFFMarkup(f, false, true);
        if (plasmid.length() > 0) {
            sb.append(" ").append(plasmid);
        }
        return sb.toString();
    }

    @Override
    public String buildStringWithEvidence(List<GeneLocation> f) {
        StringBuilder sb = buildOrganellesNoFFMarkup(f, true, false);
        StringBuilder plasmid = buildOrganellesNoFFMarkup(f, true, true);
        if (plasmid.length() > 0) {
            sb.append(" ").append(plasmid);
        }
        return sb.toString();
    }

    private StringBuilder buildOrganellesNoFFMarkup(
            List<GeneLocation> f, boolean showEvidence, boolean isPlasmid) {
        List<GeneLocation> organelles = getOrganelles(f, isPlasmid);
        StringBuilder og = new StringBuilder();
        if (organelles.isEmpty()) {
            return og;
        }
        int size = organelles.size();
        int inc = 0;
        for (GeneLocation organelle : organelles) {
            if ((inc > 0) && (inc == (size - 1))) og.append(SEPARATOR_AND1);
            og.append(toSwissprotString(organelle, showEvidence));

            if ((size > 1) && (inc != (size - 1)))
                og.append(FFLineConstant.SEPARATOR_COMA); // see Q9Z4N4 for an example
            inc++;
        }
        og.append(FFLineConstant.STOP);
        return og;
    }

    private List<GeneLocation> getOrganelles(List<GeneLocation> organelles, boolean isPlasmid) {
        return organelles.stream()
                .filter(
                        val -> {
                            if (isPlasmid) {
                                return val.getGeneEncodingType() == GeneEncodingType.PLASMID;
                            } else {
                                return val.getGeneEncodingType() != GeneEncodingType.PLASMID;
                            }
                        })
                .collect(Collectors.toList());
    }

    private List<String> buildOrganellesWithLineMark2(
            List<GeneLocation> f, boolean showEvidence, boolean isPlasmid) {
        List<GeneLocation> organelles = getOrganelles(f, isPlasmid);
        if (organelles.isEmpty()) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        StringBuilder og = new StringBuilder();
        int size = organelles.size();
        og.append(linePrefix);
        int inc = 0;
        String[] seps = new String[] {FFLineConstant.SPACE};
        for (GeneLocation organelle : organelles) {
            if ((inc > 0) && (inc == (size - 1))) og.append(SEPARATOR_AND1);
            String line = toSwissprotString(organelle, showEvidence);
            if (line.length() > (FFLineConstant.LINE_LENGTH) - linePrefix.length()) {
                if (!og.toString().equals(linePrefix)) {
                    result.add(og.toString().trim());
                }
                line = linePrefix + line;
                List<String> lines =
                        FFLineWrapper.buildLines(
                                line, seps, linePrefix, FFLineConstant.LINE_LENGTH);
                line = lines.get(lines.size() - 1);
                og = new StringBuilder(line);
                lines.remove(lines.size() - 1);
                result.addAll(lines);
            } else og.append(toSwissprotString(organelle, showEvidence));

            if ((size > 1) && (inc != (size - 1)))
                og.append(FFLineConstant.SEPARATOR_COMA); // see Q9Z4N4 for an example
            inc++;
        }
        og.append(FFLineConstant.STOP);
        result.add(og.toString());
        return result;
    }

    private String toSwissprotString(GeneLocation organelle, boolean showEvidence) {

        StringBuilder sb = new StringBuilder();
        switch (organelle.getGeneEncodingType()) {
            case HYDROGENOSOME:
            case MITOCHONDRION:
            case NUCLEOMORPH:
            case PLASTID:
                sb.append(organelle.getGeneEncodingType().getName());
                break;

            case PLASMID:
                sb.append(organelle.getGeneEncodingType().getName());
                if (!(organelle.getValue() == null || organelle.getValue().equals(""))) {
                    sb.append(" ");
                    sb.append(organelle.getValue());
                }
                break;
            case APICOPLAST:
            case CHLOROPLAST:
            case CYANELLE:
            case NON_PHOTOSYNTHETIC_PLASTID:
            case ORGANELLAR_CHROMATOPHORE:
                sb.append("Plastid; ");
                sb.append(organelle.getGeneEncodingType().getName());
                break;
            case UNKNOWN:
                break;
        }

        addEvidences(sb, organelle, showEvidence);

        return sb.toString();
    }
}
