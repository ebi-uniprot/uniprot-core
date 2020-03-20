package org.uniprot.core.flatfile.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.util.Utils;

/**
 * @author jieluo 
 */
public class CCInteractionCommentLineBuilder extends CCLineBuilderAbstr<InteractionComment> {

    @Override
    protected List<String> buildCommentLines(
            InteractionComment comment,
            boolean includeFlatFileMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        List<String> lines = new ArrayList<>();
        // first line
        if (includeCommentType) lines.add(buildStart(comment, includeFlatFileMarkings));
        

        for (Interaction act : comment.getInteractions()) {
            StringBuilder sb = new StringBuilder();
            if (includeFlatFileMarkings)
                sb.append(this.linePrefix);
            sb.append(buildLine(act));         
            lines.add(sb.toString());
        }
        return lines;
    }
   
    public String buildLine(Interaction act) {
    	StringBuilder sb= new StringBuilder();
    	Interactant interactor1 = act.getInteractantOne();
    	Interactant interactor2 = act.getInteractantTwo();
    	if(Utils.nullOrEmpty(interactor1.getChainId()))
    		sb.append(interactor1.getUniProtKBAccession().getValue());
    	else
    		sb.append(interactor1.getChainId());
    	
       	sb.append("; ");
       	if(Utils.nullOrEmpty(interactor2.getChainId())) {
       		sb.append(interactor2.getUniProtKBAccession().getValue());
       	}else {
       		sb.append(interactor2.getChainId());
       		sb.append(" [").append(interactor2.getUniProtKBAccession().getValue()).append("]");
       	}
       	if(!Utils.nullOrEmpty(interactor2.getGeneName())) {
			sb.append(": ");
			sb.append(interactor2.getGeneName());
		}
        sb.append("; ");
       
        if (act.isOrganismsDiffer()) {
            sb.append("Xeno; ");
        }
        sb.append("NbExp=");
        sb.append(act.getNumberOfExperiments());
        sb.append("; IntAct");
        sb.append("=");
        sb.append(interactor1.getIntActId());
        if (!Utils.nullOrEmpty(interactor2.getIntActId()) ){
            sb.append(", ");
            sb.append(interactor2.getIntActId());
        }
        sb.append(";");
        return sb.toString();
    	
    }
}

