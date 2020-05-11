package org.uniprot.core.flatfile.transformer;

import static java.util.Arrays.stream;

import com.google.common.base.Strings;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.CatalyticActivityCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.ReactionBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.cv.evidence.EvidenceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CatalyticActivityCommentTransformer
        implements CommentTransformer<CatalyticActivityComment> {
    private static final CommentType COMMENT_TYPE = CommentType.CATALYTIC_ACTIVITY;
    private static final String CATALYTIC_ACTIVITY_REGEX =
            "^([\\w/-]+(\\s[\\w/-]+)*:)?(\\s*(Reaction=(.+?));( (Xref=([^;]+));)?("
                    + " EC=(([0-9]|\\.|n)+);)?( Evidence=\\{([^;]+)\\};)?)(([ \\t\\r"
                    + "\\n"
                    + "\\f]+)(PhysiologicalDirection=([^;]+)); (Xref=([^;]+));("
                    + " Evidence=\\{([^;]+)\\};)?)?(([ \\t\\r"
                    + "\\n"
                    + "\\f]+)(PhysiologicalDirection=([^;]+)); (Xref=([^;]+));("
                    + " Evidence=\\{([^;]+)\\};)?)?";

    public static final Pattern ATALYTIC_ACTIVITY_PATTERN =
            Pattern.compile(CATALYTIC_ACTIVITY_REGEX);

    @Override
    public CatalyticActivityComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public CatalyticActivityComment transform(CommentType commentType, String annotation) {
        CatalyticActivityCommentBuilder builder = new CatalyticActivityCommentBuilder();
        annotation = updateMolecule(annotation, builder);
        Matcher matcher = ATALYTIC_ACTIVITY_PATTERN.matcher(annotation);

        if (matcher.matches()) {

            String reactionName = matcher.group(5);
            String reactionXref = matcher.group(8);
            String reactionEc = matcher.group(10);
            String reactionEvidence = matcher.group(13);
            String pdName1 = matcher.group(17);
            String pdXref1 = matcher.group(19);
            String pdEvidence1 = matcher.group(21);
            String pdName2 = matcher.group(25);
            String pdXref2 = matcher.group(27);
            String pdEvidence2 = matcher.group(29);

            Reaction reaction =
                    createReaction(reactionName, reactionXref, reactionEc, reactionEvidence);
            builder.reaction(reaction);
            List<PhysiologicalReaction> pds = new ArrayList<>();
            if (!Strings.isNullOrEmpty(pdName1)) {
                pds.add(createPhysiologicalDirection(pdName1, pdXref1, pdEvidence1));
            }
            if (!Strings.isNullOrEmpty(pdName2)) {
                pds.add(createPhysiologicalDirection(pdName2, pdXref2, pdEvidence2));
            }
            builder.physiologicalReactionsSet(pds);

            return builder.build();
        } else {
            throw new IllegalArgumentException(
                    "Unable to convert annotation to CATALYTIC_ACTIVITY comment: " + annotation);
        }
    }

    private String updateMolecule(String annotation, CatalyticActivityCommentBuilder builder) {
        if (annotation.startsWith("[") && annotation.contains("]")) {
            int index = annotation.indexOf("]");
            String molecule = annotation.substring(1, index);
            molecule = molecule.replaceAll("\n", " ");
            builder.molecule(molecule);
            annotation = annotation.substring(index + 2).trim();
            if (annotation.startsWith("\n")) annotation = annotation.substring(1);
            return annotation;
        }
        return annotation;
    }

    private PhysiologicalReaction createPhysiologicalDirection(
            String name, String xref, String evidence) {
        CrossReference<ReactionDatabase> reference = null;
        if (!Strings.isNullOrEmpty(xref)) {
            reference = convertReactionReference(xref);
        }
        List<Evidence> evidences = new ArrayList<>();
        if (!Strings.isNullOrEmpty(evidence)) {
            evidences =
                    EvidenceHelper.parseEvidenceLines(
                            stream(evidence.split(", ")).collect(Collectors.toList()));
        }
        return new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.typeOf(name))
                .reactionCrossReference(reference)
                .evidencesSet(evidences)
                .build();
    }

    private Reaction createReaction(String name, String xref, String ec, String evidence) {
        ECNumber ecNumber = null;

        if (!Strings.isNullOrEmpty(ec)) {
            ecNumber = new ECNumberBuilder(ec).build();
        }
        List<CrossReference<ReactionDatabase>> references = new ArrayList<>();
        if (!Strings.isNullOrEmpty(xref)) {
            references =
                    stream(xref.split(", "))
                            .map(this::convertReactionReference)
                            .collect(Collectors.toList());
        }
        List<Evidence> evidences = new ArrayList<>();
        if (!Strings.isNullOrEmpty(evidence)) {

            evidences =
                    EvidenceHelper.parseEvidenceLines(
                            stream(evidence.split(", ")).collect(Collectors.toList()));
        }
        return new ReactionBuilder()
                .name(name)
                .reactionCrossReferencesSet(references)
                .ecNumber(ecNumber)
                .evidencesSet(evidences)
                .build();
    }

    private CrossReference<ReactionDatabase> convertReactionReference(String val) {

        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        return new CrossReferenceBuilder<ReactionDatabase>()
                .database(ReactionDatabase.typeOf(type))
                .id(id)
                .build();
    }
}
