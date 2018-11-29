package uk.ac.ebi.uniprot.parser.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

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
		InteractionCommentBuilder builder = InteractionCommentBuilder.newInstance();
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
			if (interaction != null)
				interactions.add(interaction);
		}

		return interactions;
	}

	public Interaction convertInteraction(String value) {
		if (value == null) {
			throw new IllegalArgumentException();

		}
		InteractionBuilder builder = InteractionBuilder.newInstance();

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
			if (genename.endsWith("(xeno)")) {
				builder.interactionType(InteractionType.XENO);
				genename = genename.substring(0, genename.length() - 7);
			} else {
				builder.interactionType(InteractionType.BINARY);
			}
			builder.uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession(acc));
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
		builder.firstInteractor(InteractionBuilder.createInteractor(acc1))
				.secondInteractor(InteractionBuilder.createInteractor(acc2));

		return builder.build();
	}

}
