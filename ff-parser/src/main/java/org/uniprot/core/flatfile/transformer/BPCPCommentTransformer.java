package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class BPCPCommentTransformer implements CommentTransformer<BPCPComment> {
    private static final String VMAX = "Vmax=";
    private static final String NOTE2 = "Note=";
    private static final String ABS_MAX = "Abs(max)=";
    private static final String ABSORPTION = "Absorption:";
    private static final String REDOX_POTENTIAL = "Redox potential:";
    private static final String P_H_DEPENDENCE = "pH dependence:";
    private static final String TEMPERATURE_DEPENDENCE = "Temperature dependence:";
    private static final String KINETIC_PARAMETERS = "Kinetic parameters:";
    private static final String KM2 = "KM=";
    private static final CommentType COMMENT_TYPE = CommentType.BIOPHYSICOCHEMICAL_PROPERTIES;

    @Override
    public BPCPComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public BPCPComment transform(CommentType commentType, String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");

        StringTokenizer st = new StringTokenizer(annotation, "\n");
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        annotation = updateMolecule(annotation, builder);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            switchTopic(token, st, builder);
        }
        return builder.build();
    }

    private String updateMolecule(String annotation, BPCPCommentBuilder builder) {
        if (annotation.startsWith("[") && annotation.contains("]")) {
            int index = annotation.indexOf("]");
            String molecule = annotation.substring(1, index);
            molecule = molecule.replaceAll("\n", " ");
            builder.molecule(molecule);
            annotation = annotation.substring(index + 2);
            if (annotation.startsWith("\n")) annotation = annotation.substring(1);
            return annotation;
        }
        return annotation;
    }

    private void switchTopic(String line, StringTokenizer lines, BPCPCommentBuilder builder) {

        if (line.equals(KINETIC_PARAMETERS)) {
            List<String> tokens = getTypeLines(lines, builder);
            builder.kineticParameters(buildKineticParameters(tokens));

        } else if (line.equals(TEMPERATURE_DEPENDENCE)) {
            List<String> tokens = getTypeLines(lines, builder);
            builder.temperatureDependence(buildTemperatureDependence(tokens));

        } else if (line.equals(P_H_DEPENDENCE)) {
            List<String> tokens = getTypeLines(lines, builder);
            builder.phDependence(buildPHDependence(tokens));

        } else if (line.equals(REDOX_POTENTIAL)) {
            List<String> tokens = getTypeLines(lines, builder);
            builder.redoxPotential(buildRedoxPotential(tokens));
        } else if (line.equals(ABSORPTION)) {
            List<String> tokens = getTypeLines(lines, builder);
            builder.absorption(buildAbsorption(tokens));
        } else {
            throw new RuntimeException(line + " is unknown");
        }
    }

    private List<String> getTypeLines(StringTokenizer lines, BPCPCommentBuilder builder) {
        List<String> tokenlines = new ArrayList<>();
        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            if (!isLineTitle(line)) {
                tokenlines.add(line.trim());
            } else {
                switchTopic(line, lines, builder);
            }
        }
        return tokenlines;
    }

    private boolean isLineTitle(String line) {
        return line.equals(KINETIC_PARAMETERS)
                || line.equals(TEMPERATURE_DEPENDENCE)
                || line.equals(P_H_DEPENDENCE)
                || line.equals(REDOX_POTENTIAL)
                || line.equals(ABSORPTION);
    }

    public Absorption buildAbsorption(List<String> lines) {
        List<Evidence> evidences = new ArrayList<>();
        int max = 0;
        boolean approximate = false;
        Note note = null;
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith(ABS_MAX)) {
                line = CommentTransformerHelper.stripEvidences(line, evidences);

                String unitSection = line.substring(9, line.length());

                String val = unitSection.substring(0, unitSection.indexOf(' '));
                approximate = val.startsWith("~");
                if (approximate) {
                    max = Integer.parseInt(val.substring(1));
                } else {
                    max = Integer.parseInt(val);
                }
            } else if (line.startsWith(NOTE2)) {
                String comment = line.substring(5, line.length());

                List<EvidencedValue> evValues =
                        CommentTransformerHelper.parseEvidencedValues(comment, false);
                note = new NoteBuilder(evValues).build();
            }
        }
        return new AbsorptionBuilder()
                .max(max)
                .approximate(approximate)
                .note(note)
                .evidencesSet(evidences)
                .build();
    }

    public PhDependence buildPHDependence(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(" ");
        }
        return new PhDependenceBuilder(
                        CommentTransformerHelper.parseEvidencedValues(sb.toString().trim(), false))
                .build();
    }

    public RedoxPotential buildRedoxPotential(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(" ");
        }
        return new RedoxPotentialBuilder(
                        CommentTransformerHelper.parseEvidencedValues(sb.toString().trim(), false))
                .build();
    }

    public TemperatureDependence buildTemperatureDependence(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(" ");
        }

        return new TemperatureDependenceBuilder(
                        CommentTransformerHelper.parseEvidencedValues(sb.toString().trim(), false))
                .build();
    }

    public MichaelisConstant buildMichaelisConstant(String line) {
        List<Evidence> evidences = new ArrayList<>();
        line = CommentTransformerHelper.stripEvidences(line, evidences);
        if (line.endsWith(";")) line = line.substring(0, line.length() - 1);
        StringTokenizer st = new StringTokenizer(line.substring(3), " ");
        String val = st.nextToken();
        val = val.replace(",", "");
        double value = Double.parseDouble(val);
        String unit = st.nextToken();

        if (!st.nextToken().equals("for")) {
            throw new RuntimeException();
        }

        StringBuilder commentRestline = new StringBuilder();
        while (st.hasMoreTokens()) {
            commentRestline.append(st.nextToken());
            commentRestline.append(" ");
        }
        String commentStr = commentRestline.toString().trim();
        return new MichaelisConstantBuilder()
                .constant(value)
                .unit(MichaelisConstantUnit.convert(unit))
                .substrate(commentStr)
                .evidencesSet(evidences)
                .build();
    }

    public MaximumVelocity buildMaximumVelocity(String line) {
        List<Evidence> evidences = new ArrayList<>();
        line = CommentTransformerHelper.stripEvidences(line, evidences);
        if (line.endsWith(";")) line = line.substring(0, line.length() - 1);

        StringTokenizer st = new StringTokenizer(line.substring(5), " ");
        double value = Double.parseDouble(st.nextToken());
        String unit = st.nextToken();

        StringBuilder commentRestline = new StringBuilder();
        while (st.hasMoreTokens()) {
            commentRestline.append(st.nextToken());
            commentRestline.append(" ");
        }
        String commentStr = commentRestline.toString().trim();
        return new MaximumVelocityBuilder()
                .velocity(value)
                .unit(unit)
                .enzyme(commentStr)
                .evidencesSet(evidences)
                .build();
    }

    public KineticParameters buildKineticParameters(List<String> lines) {
        List<MaximumVelocity> velocities = new ArrayList<>();
        List<MichaelisConstant> mConstants = new ArrayList<>();
        Note note = null;
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith(KM2)) {
                mConstants.add(buildMichaelisConstant(line));

            } else if (line.startsWith(NOTE2)) {
                String commentStr = line.substring(5);
                note =
                        new NoteBuilder(
                                        CommentTransformerHelper.parseEvidencedValues(
                                                commentStr, false))
                                .build();
            } else if (line.startsWith(VMAX)) {
                velocities.add(buildMaximumVelocity(line));

            } else {
                throw new RuntimeException();
            }
        }

        return new KineticParametersBuilder()
                .maximumVelocitiesSet(velocities)
                .michaelisConstantsSet(mConstants)
                .note(note)
                .build();
    }
}
