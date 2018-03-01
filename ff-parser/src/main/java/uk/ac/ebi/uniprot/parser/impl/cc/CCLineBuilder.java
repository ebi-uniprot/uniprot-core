package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;


public   class CCLineBuilder extends FFLineBuilderAbstr< List< Comment > > implements
		FFLineBuilder<List<Comment> > {
	public CCLineBuilder(){
		super(LineType.CC);
	}

	@Override
	public String buildString(List<Comment> f) {
		List<String> lines = buildLines(f, false, false);
		return  FFLines.create(lines).toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<Comment> f) {
		List<String> lines = buildLines(f, false, true);
		return  FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(List<Comment> f, boolean showEvidence) {
		List<String> lines = buildLines(f, true, showEvidence);
		return  FFLines.create(lines);
	}
	private List<String> buildLines(List<Comment> f, boolean includeFFMarkings, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		int nSeqCaution=0;
		for(Comment comment:f){
			FFLineBuilder< Comment> fbuilder =CCLineBuilderFactory.create(comment);
			if(comment.getCommentType()==CommentType.SEQUENCE_CAUTION){
				nSeqCaution +=1;
				FFLineBuilder< SequenceCautionComment> seqCautionBuilder =CCLineBuilderFactory.create((SequenceCautionComment)comment);
				((CCSequenceCautionCommentLineBuilder)seqCautionBuilder).setIsFirstSequenceCaution(nSeqCaution ==1);
		
			}
			if(includeFFMarkings) {
				if(showEvidence)
					lines.addAll(fbuilder.buildWithEvidence(comment).lines());
				else
					lines.addAll(fbuilder.build(comment).lines());
			}else{
				if(showEvidence)
					lines.add(fbuilder.buildStringWithEvidence(comment));
				else
					lines.add(fbuilder.buildString(comment));
			}
		}
		return lines;
	}
	

}
