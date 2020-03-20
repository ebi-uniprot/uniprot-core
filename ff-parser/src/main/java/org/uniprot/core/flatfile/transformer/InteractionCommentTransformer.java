package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

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
        return builder.interactionsSet(interactions).build();
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
        InteractantBuilder builder1 = new InteractantBuilder();
        InteractantBuilder builder2 = new InteractantBuilder();

        // todo: handle parse errors
        String[] tokens = value.split("; ");
        String first = tokens[0];
        String second = tokens[1];
        boolean xeno = false;
        int length = tokens.length;
        if (length == 5) {
            xeno = true;
        }
        String nbexp = tokens[length - 2];
        String intact = tokens[length - 1];
        UniProtKBAccession interactant1 = new UniProtKBAccessionBuilder(first).build();
        if (interactant1.isValidAccession()) builder1.uniProtKBAccession(interactant1);
        else builder1.chainId(first);

        int index = second.indexOf(':');
        String acc = null;
        String genename = null;
        String parent = null;
        if (index != -1) {
            acc = second.substring(0, index);
            genename = second.substring(index + 1).trim();
        } else {
            acc = second;
        }
        index = acc.indexOf('[');
        if (index != -1) {
            parent = acc.substring(index + 1, acc.length() - 1);
            acc = acc.substring(0, index).trim();
        }
        //	builder.isXeno(xeno);
        if (parent != null) {
            builder2.chainId(acc).uniProtKBAccession(parent);
        } else {
            builder2.uniProtKBAccession(acc);
        }
        if (genename != null) {
            builder2.geneName(genename);
        }

        //	builder.numberOfExperiments(Integer.parseInt(nbexp.substring(6)));

        // intact is something like
        // IntAct=EBI-206607, EBI-108331
        if (intact.endsWith(";")) {
            intact = intact.substring(0, intact.length() - 1);
        }
        StringTokenizer st = new StringTokenizer(intact, "=, ");
        st.nextToken(); // IntAct
        String acc1 = st.nextToken(); // EBI-206607
        String acc2 = st.nextToken(); // EBI-108331
        builder1.intActId(acc1);
        builder2.intActId(acc2);
        builder.interactantOne(builder1.build())
                .interactantTwo(builder2.build())
                .isOrganismDiffer(xeno)
                .numberOfExperiments(Integer.parseInt(nbexp.substring(6)));
        return builder.build();
    }
}
