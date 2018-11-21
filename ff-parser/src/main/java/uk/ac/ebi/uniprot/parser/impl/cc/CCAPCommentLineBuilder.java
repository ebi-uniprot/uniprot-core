package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEvent;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;

/**
 * 
 * @author jieluo CC -!- ALTERNATIVE PRODUCTS: |CC Event=Alternative splicing;
 *         Named isoforms=3; |CC Comment=Additional isoforms seem to exist.
 *         Experimental |CC confirmation may be lacking for some isoforms; |CC
 *         Name=1; Synonyms=AIRE-1; |CC IsoId=O43918-1; Sequence=Displayed; |CC
 *         Name=2; Synonyms=AIRE-2; |CC IsoId=O43918-2; Sequence=VSP_004089; |CC
 *         Name=3; Synonyms=AIRE-3; |CC IsoId=O43918-3; Sequence=VSP_004089,
 *         VSP_004090;
 */

public class CCAPCommentLineBuilder extends
		CCLineBuilderAbstr<AlternativeProductsComment> {

	private static final String SEQUENCE = " Sequence=";
	private static final String ISO_ID = "IsoId=";
	private static final String SYNONYMS = " Synonyms=";
	private static final String COMMENT2 = "Comment=";
	private static final String NAMED_ISOFORMS = " Named isoforms=";
	private static final String EVENT2 = "Event=";

	@Override
	protected List<String> buildCommentLines(
			AlternativeProductsComment comment,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();

		// first line
		// if(includeFlatFileMarkings)
		lines.add(buildStart(comment, includeFlatFileMarkings));

		// event line
		StringBuilder altProd = buildEvent(comment, includeFlatFileMarkings,
				showEvidence);
		lines.add(altProd.toString());

		// comment lines
		lines.addAll(buildAltProdComment(comment, includeFlatFileMarkings,
				showEvidence));
		
		// named isoform lines
		for (APIsoform alternativeProductsIsoform : comment.getIsoforms()) {
			lines.addAll(buildIsoform(alternativeProductsIsoform,
					includeFlatFileMarkings, showEvidence));
		}
		return lines;

	}

	private StringBuilder buildEvent(AlternativeProductsComment comment,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		StringBuilder altProd = new StringBuilder();
		if (includeFlatFileMarkings) {
			altProd.append(this.linePrefix);
		}
		altProd.append(EVENT2);
		List<APEvent> events = comment.getEvents();
		for (int iii = 0; iii < events.size(); iii++) {
			APEvent event = events.get(iii);
			altProd.append(event.getName());
			if (iii < events.size() - 1) {
				altProd.append(SEPARATOR_COMA);
			} else {
				altProd.append(SEMI_COMA);
			}
		}

		if (comment.getIsoforms().size() > 0) {
			altProd.append(NAMED_ISOFORMS);
			altProd.append(comment.getIsoforms().size());
			altProd.append(SEMI_COMA);
		}
		return altProd;

	}

	private List<String> buildAltProdComment(
			AlternativeProductsComment comment,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if(comment.getNote().isPresent()) {
			Note note = comment.getNote().get();
			StringBuilder asComment = new StringBuilder();
			if (includeFlatFileMarkings) {
				asComment.append(CC_PREFIX_INDENT);
			}
			asComment.append(COMMENT2);
			String freeTextStr= buildFreeText(note, showEvidence, STOP, SEMI_COMA);
			asComment.append(freeTextStr);       
		
			if (includeFlatFileMarkings) {
				String[] seps = new String[] { SEPARATOR, DASH };
				List<String> lls = FFLineWrapper.buildLines(
						asComment.toString(), seps, CC_PREFIX_INDENT,
						LINE_LENGTH);
				lines.addAll(lls);

			} else
				lines.add(asComment.toString());

		}
		return lines;
	}

	private List<String> buildIsoformName(APIsoform alternativeProductsIsoform,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		StringBuilder altProd = new StringBuilder();
		if (includeFlatFileMarkings)
			altProd.append(linePrefix);

		altProd.append(NAME);
		altProd.append(alternativeProductsIsoform.getName().getValue());

		altProd = addEvidence(alternativeProductsIsoform.getName(), altProd,
				showEvidence, "");

		altProd.append(SEMI_COMA);
		if (!alternativeProductsIsoform.getSynonyms().isEmpty()) {
			altProd.append(SYNONYMS);
			for (int i = 0; i < alternativeProductsIsoform.getSynonyms().size(); i++) {
				altProd.append(alternativeProductsIsoform.getSynonyms().get(i)
						.getValue());
				altProd = addEvidence(alternativeProductsIsoform.getSynonyms()
						.get(i), altProd, showEvidence, "");
				addSeparator(
						altProd,
						SEMI_COMA,
						SEPARATOR_COMA,
						(i == (alternativeProductsIsoform.getSynonyms().size() - 1)));
			}
		}
		List<String> lines = new ArrayList<>();
		if (includeFlatFileMarkings) {
			List<String> lls = FFLineWrapper.buildLines(altProd, SEPARATOR,
					linePrefix);
			lines.addAll(lls);
		} else
			lines.add(altProd.toString());
		return lines;
	}

	private StringBuilder addSeparator(StringBuilder sb, String sep1,
			String sep2, boolean isSep1) {
		if (isSep1)
			sb.append(sep1);
		else
			sb.append(sep2);
		return sb;
	}

	private List<String> buildIsoformId(
			APIsoform alternativeProductsIsoform,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if (alternativeProductsIsoform.getIds().isEmpty()) {
			return lines;
		}
		StringBuilder isoform = new StringBuilder();
		if (includeFlatFileMarkings) {
			isoform.append(CC_PREFIX_INDENT);
		}
		isoform.append(ISO_ID);
		for (int i = 0; i < alternativeProductsIsoform.getIds().size(); i++) {
			isoform.append(alternativeProductsIsoform.getIds().get(i)
					.getName());
			addSeparator(isoform, SEMI_COMA, SEPARATOR_COMA,
					(i == (alternativeProductsIsoform.getIds().size() - 1)));

		}

		isoform.append(SEQUENCE);
		int space = isoform.length();
		if (!alternativeProductsIsoform.getSequenceIds().isEmpty()) {
			for (int i = 0; i < alternativeProductsIsoform.getSequenceIds()
					.size(); i++) {
				isoform.append(alternativeProductsIsoform.getSequenceIds()
						.get(i));
				addSeparator(isoform, SEMI_COMA, SEPARATOR_COMA,
						(i == (alternativeProductsIsoform.getSequenceIds()
								.size() - 1)));
			}
		} else {
			String status = alternativeProductsIsoform
					.getIsoformSequenceStatus().getValue();
			status = status.substring(0, 1).toUpperCase() + status.substring(1);
			isoform.append(status);
			isoform.append(SEMI_COMA);

		}
		if (isoform.length() > 0) {
			if (includeFlatFileMarkings) {
				String prev = "CC                          ";
				while (prev.length() < (space)) {
					prev += " ";
				}
				;
				List<String> lls = FFLineWrapper.buildLines(isoform.toString(),
						SEPS, prev, LINE_LENGTH);
				lines.addAll(lls);
			} else
				lines.add(isoform.toString());
		}
		return lines;
	}

	private List<String> buildIsoformNote(
			APIsoform alternativeProductsIsoform,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if(!alternativeProductsIsoform.getNote().isPresent()) {
			return lines;
		}
		StringBuilder asComment = new StringBuilder();
		if (includeFlatFileMarkings)
			asComment.append(CC_PREFIX_INDENT);
		asComment.append("Note=");
		String freeTextStr= buildFreeText(alternativeProductsIsoform.getNote().get(), showEvidence, STOP, SEMI_COMA);
        asComment.append(freeTextStr);    
		if (includeFlatFileMarkings) {
			List<String> lls = FFLineWrapper.buildLines(asComment.toString(),
					SEPS, CC_PREFIX_INDENT, LINE_LENGTH);
			lines.addAll(lls);
		} else
			lines.add(asComment.toString());
		return lines;
	}

	private List<String> buildIsoform(
			APIsoform alternativeProductsIsoform,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		List<String> nameLines = buildIsoformName(alternativeProductsIsoform,
				includeFlatFileMarkings, showEvidence);
		List<String> isoformLines = buildIsoformId(alternativeProductsIsoform,
				includeFlatFileMarkings, showEvidence);
		List<String> noteLines = buildIsoformNote(alternativeProductsIsoform,
				includeFlatFileMarkings, showEvidence);
		lines.addAll(nameLines);
		lines.addAll(isoformLines);
		lines.addAll(noteLines);
		return lines;
	}

}
