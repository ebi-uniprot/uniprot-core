package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.uniprot.comment.builder.InteractionCommentBuilder;

public class InteractionCommentTransformer implements CommentTransformer<InteractionComment> {
    private static final CommentType COMMENT_TYPE = CommentType.INTERACTION;

    @Override
    public InteractionComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public InteractionComment transform(CommentType type, String annotation) {
        List<Interaction> interactions = convertInteractions(annotation);
        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        return builder.interactions(interactions).build();
    }

    private List<Interaction> convertInteractions(String annotation) {
        List<Interaction> interactions = new ArrayList<>();
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");
        if (annotation == null) {
            throw new IllegalArgumentException();
        }
        String[] tokens = annotation.split("\n");
        for (String token : tokens) {
            Interaction interaction = convertInteraction(token);
            if (interaction != null) interactions.add(interaction);
        }

        return interactions;
    }

    public Interaction convertInteraction(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        InteractionBuilder builder = new InteractionBuilder();

        String[] tokens = value.split("; ");
        String first = tokens[0];
        String nbexp = tokens[1];
        String intact = tokens[2];

        int index = first.indexOf(':');
        String acc = null;
        String genename = null;
        if (index != -1) {
            acc = first.substring(0, index);
            genename = first.substring(index + 1);
        } else {
            acc = first;
        }

        if (acc.equalsIgnoreCase("self")) {
            builder.interactionType(InteractionType.SELF);
        } else {
            if (genename != null && genename.endsWith("(xeno)")) {
                builder.interactionType(InteractionType.XENO);
                genename = genename.substring(0, genename.length() - 7);
            } else {
                builder.interactionType(InteractionType.BINARY);
            }
            builder.uniProtAccession(acc);
            builder.geneName(genename);
        }
        builder.numberOfExperiments(Integer.parseInt(nbexp.substring(6)));

        // intact is something like
        // IntAct=EBI-206607, EBI-108331
        if (intact.endsWith(";")) {
            intact = intact.substring(0, intact.length() - 1);
        }
        StringTokenizer st = new StringTokenizer(intact, "=, ");
        st.nextToken(); // IntAct
        String acc1 = st.nextToken(); // EBI-206607
        String acc2 = st.nextToken(); // EBI-108331
        builder.firstInteractor(acc1).secondInteractor(acc2);

        return builder.build();
    }
}
