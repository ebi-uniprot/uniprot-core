package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.APCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import static java.util.Arrays.asList;


public class APCommentTransformer implements CommentTransformer<AlternativeProductsComment> {
    /**
     * "Event=Alternative splicing; Named isoforms=6;\n" + "Comment=Additional isoforms seem to exist.
     * {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" + "Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A
     * {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" + "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
     * "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No
     * experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" + "Name=2;\n" + "IsoId=Q9V8R9-2;
     * Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" + "Name=Bim-alpha3
     * {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10
     * {ECO:0000313|EMBL:BAG16761.1}, Bim-AD {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n" +
     * "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" + "Name=4; Synonyms=B;\n" + "IsoId=Q9V8R9-4;
     * Sequence=VSP_000476, VSP_000477, VSP_000479;\n" + "Name=5;\n" + "IsoId=Q9V8R9-5; Sequence=VSP_000474,
     * VSP_000478;\n" + "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554,
     * ECO:0000313|EMBL:BAG16761.1};\n" + "Name=6; Synonyms=D;\n" + "IsoId=Q9V8R9-6; Sequence=Described;\n" + "Note=No
     * experimental confirmation.;";
     */
    private static final String EVENT = "Event=";
    private static final String NAME_ISOFORM = "Named isoforms=";
    private static final String COMMENT = "Comment=";
    private static final String NAME = "Name=";
    private static final String SYNONYMS = "Synonyms=";
    private static final String NOTE = "Note=";
    private static final String ISOID = "IsoId=";
    private static final String SEQUENCE = "Sequence=";
    private static final CommentType COMMENT_TYPE = CommentType.ALTERNATIVE_PRODUCTS;

    @Override
    public AlternativeProductsComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public AlternativeProductsComment transform(CommentType commentType, String annotation) {
        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");
        if (annotation == null || annotation.length() <= 0) {
            throw new IllegalArgumentException();
        }
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        String[] tokens = annotation.split(";");
        APIsoformBuilder isoformBuilder = null;
        APCommentBuilder apBuilder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        List<APIsoform> apIsoforms = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if (token.startsWith(EVENT)) {
                addEventInfoToAPBuilder(apBuilder, events, token);
                continue;
            }
            if (token.startsWith(NAME_ISOFORM)) {
                continue;
            }
            if (token.startsWith(COMMENT)) {
                String val = token.substring(COMMENT.length());

                List<EvidencedValue> evValues = new ArrayList<>();
                evValues.add(CommentTransformerHelper.parseEvidencedValue(val, false));
                int j = i + 1;
                do {
                    val = tokens[j].trim();
                    if (isPartOfComment(val)) {
                        evValues.add(CommentTransformerHelper.parseEvidencedValue(val, false));
                    } else {
                        i = j - 1;
                        break;
                    }
                    j++;
                } while (j < tokens.length);
                apBuilder.note(new NoteBuilder(evValues).build());
                continue;
            }

            if (token.startsWith(NAME)) {
                if (isoformBuilder != null) {
                    apIsoforms.add(isoformBuilder.build());
                }
                isoformBuilder = new APIsoformBuilder();
                String label = token.substring(NAME.length());
                List<Evidence> evidences = new ArrayList<>();
                String value = CommentTransformerHelper.stripEvidences(label, evidences);
                isoformBuilder.name(new IsoformNameBuilder(value, evidences).build());
            } else if (token.startsWith(SYNONYMS) && isoformBuilder != null) {
                String label = token.substring(SYNONYMS.length());
                isoformBuilder.synonyms(buildSynonyms(label));
            } else if (token.startsWith(ISOID) && isoformBuilder != null) {
                String label = token.substring(ISOID.length());
                isoformBuilder.ids(asList(label.split(",")));
            } else if (token.startsWith(SEQUENCE) && isoformBuilder != null) {
                addSequenceInfoToIsoform(isoformBuilder, token);
            } else if (token.startsWith(NOTE) && isoformBuilder != null) {
                String val = token.substring(NOTE.length());
                List<EvidencedValue> evValues = new ArrayList<>();
                evValues.add(CommentTransformerHelper.parseEvidencedValue(val, false));
                int j = i + 1;
                while (j < tokens.length) {
                    val = tokens[j].trim();
                    if (isPartOfComment(val)) {
                        evValues.add(CommentTransformerHelper.parseEvidencedValue(val, false));
                    } else {
                        i = j - 1;
                        break;
                    }
                    j++;
                }
                isoformBuilder.note(new NoteBuilder(evValues).build());
            }
        }
        if (isoformBuilder != null) {
            apIsoforms.add(isoformBuilder.build());

        }
        apBuilder.isoforms(apIsoforms);
        return apBuilder.build();
    }

    private void addEventInfoToAPBuilder(APCommentBuilder apBuilder, List<APEventType> events, String token) {
        String val = token.substring(EVENT.length());
        String[] eventTokens = val.split(",");
        for (String eventToken : eventTokens) {
            events.add(APEventType.typeOf(eventToken));
        }
        apBuilder.events(events);
    }

    private void addSequenceInfoToIsoform(APIsoformBuilder isoformBuilder, String token) {
        String label = token.substring(SEQUENCE.length()).trim();
        if (label.equalsIgnoreCase(IsoformSequenceStatus.DISPLAYED
                                           .getValue())) {
            isoformBuilder.sequenceStatus(IsoformSequenceStatus.DISPLAYED);

        } else if (label.equalsIgnoreCase(IsoformSequenceStatus.NOT_DESCRIBED.getValue())) {
            isoformBuilder.sequenceStatus(IsoformSequenceStatus.NOT_DESCRIBED);
        } else if (label.equalsIgnoreCase(IsoformSequenceStatus.EXTERNAL.getValue())) {
            isoformBuilder.sequenceStatus(IsoformSequenceStatus.EXTERNAL);

        } else {
            isoformBuilder.sequenceIds(
                    Arrays.stream(label.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList()));
        }
    }

    private List<IsoformName> buildSynonyms(String value) {
        List<IsoformName> synonyms = new ArrayList<>();
        // Synonyms=BCL2-like 11 transcript"
        // + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD {ECO:0000313|PDB:3OW2, ECO:0000256|HAMAP-Rule:MF_00205},
        // BimAD;\n" +
        // Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;
        int index = value.indexOf(',');
        int evInd1 = value.indexOf('{');
        int evInd2 = value.indexOf('}');
        if ((evInd1 == -1) || (evInd2 == -1)) {
            String[] synsTokens = value.split(",");
            for (String syn : synsTokens) {
                syn = syn.trim();
                List<Evidence> evidences = new ArrayList<>();
                String synName = CommentTransformerHelper.stripEvidences(syn, evidences);
                synonyms.add(new IsoformNameBuilder(synName, evidences).build());
            }
            return synonyms;
        }
        String temp = value;
        boolean isTrue = true;

        if ((evInd1 != -1) && (evInd2 != -1)) {
            if ((index > evInd1) && (index < evInd2))
                index = temp.indexOf(',', evInd2);
        }
        if (index == -1)
            isTrue = false;
        while (isTrue) {
            String syn = temp.substring(0, index);
            temp = temp.substring(index + 1).trim();
            List<Evidence> evidences = new ArrayList<>();
            String synName = CommentTransformerHelper.stripEvidences(syn, evidences);
            synonyms.add(new IsoformNameBuilder(synName, evidences).build());


            evInd1 = temp.indexOf('{');
            evInd2 = temp.indexOf('}');

            index = temp.indexOf(',');
            if ((evInd1 != -1) && (evInd2 != -1)) {
                if (index == -1)
                    break;
                if ((index > evInd1) && (index < evInd2))
                    index = temp.indexOf(',', evInd2);
            }

            if (index == -1)
                break;
        }
        if (!temp.isEmpty()) {
            List<Evidence> evidences = new ArrayList<>();
            String synName = CommentTransformerHelper.stripEvidences(temp, evidences);
            synonyms.add(new IsoformNameBuilder(synName, evidences).build());
        }
        return synonyms;
    }

    private boolean isPartOfComment(String token) {
        return !token.startsWith(EVENT)
                && !token.startsWith(NAME_ISOFORM)
                && !token.startsWith(COMMENT)
                && !token.startsWith(NAME)
                && !token.startsWith(SYNONYMS)
                && !token.startsWith(NOTE)
                && !token.startsWith(ISOID)
                && !token.startsWith(SEQUENCE);
    }
}
