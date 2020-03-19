package org.uniprot.core.xml.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.InteractantType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import com.google.common.base.Strings;

public class InteractionConverter implements Converter<CommentType, Interaction> {
    private static final String INTERACTION = "interaction";
    private final ObjectFactory xmlUniprotFactory;

    public InteractionConverter() {
        this(new ObjectFactory());
    }

    public InteractionConverter(ObjectFactory xmlUniprotFactory) {

        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Interaction fromXml(CommentType xmlObject) {
        if (xmlObject == null) return null;
        InteractionBuilder builder = new InteractionBuilder();

        List<InteractantType> actTypes = xmlObject.getInteractant();
        assert (actTypes.size() == 2);
        InteractantType firstAct = actTypes.get(0);
        InteractantType secondAct = actTypes.get(1);
        InteractantBuilder builder1 =new InteractantBuilder();
        InteractantBuilder builder2 =new InteractantBuilder();
        
        
        
        if ((firstAct.getId() != null) && (!firstAct.getId().isEmpty())) {
            InteractantType temp = firstAct;
            firstAct = secondAct;
            secondAct = temp;
        }
        InteractionType type;
        if (xmlObject.isOrganismsDiffer()) {
            type = InteractionType.XENO;
        } else {
            if (secondAct.getId() != null) {
                type = InteractionType.BINARY;
            } else {
                type = InteractionType.SELF;
            }
        }
        builder.interactionType(type);

        builder.firstInteractor(firstAct.getIntactId());
        builder.secondInteractor(secondAct.getIntactId());
        if (secondAct.getLabel() != null) {
            builder.geneName(secondAct.getLabel());
        } else if (type != InteractionType.SELF) {
            builder.geneName("-");
        }
        if (type != InteractionType.SELF) {
            builder.uniProtAccession(secondAct.getId());
        }
        builder.numberOfExperiments(xmlObject.getExperiments());
        return builder.build();
    }
    
    

    @Override
    public CommentType toXml(Interaction uniObj) {
        if (uniObj == null) return null;
        CommentType commentType = xmlUniprotFactory.createCommentType();
        commentType.setType(INTERACTION);
        InteractantType firstactantType = xmlUniprotFactory.createInteractantType();
        firstactantType.setIntactId(uniObj.getFirstInteractant().getIntActId());
        if(!Utils.nullOrEmpty(uniObj.getFirstInteractant().getChainId())) {
        	firstactantType.setId(uniObj.getFirstInteractant().getChainId());
        }else {
        	firstactantType.setId(uniObj.getFirstInteractant().getUniProtkbAccession().getValue());
        }
        commentType.getInteractant().add(firstactantType);
    
        InteractantType secondactantType = xmlUniprotFactory.createInteractantType();
        secondactantType.setIntactId(uniObj.getSecondInteractant().getIntActId());
        if(!Utils.nullOrEmpty(uniObj.getSecondInteractant().getGeneName())) {
        	secondactantType.setLabel(uniObj.getSecondInteractant().getGeneName());
        }
        if(!Utils.nullOrEmpty(uniObj.getSecondInteractant().getChainId())) {
        	secondactantType.setId(uniObj.getSecondInteractant().getChainId());
        	 DbReferenceType xmlReference = xmlUniprotFactory.createDbReferenceType();
			 xmlReference.setType("UniProtKB");
		     xmlReference.setId(uniObj.getSecondInteractant().getUniProtkbAccession().getValue());
		     secondactantType.setDbReference(xmlReference);
        	
        }else {
        	secondactantType.setId(uniObj.getSecondInteractant().getUniProtkbAccession().getValue());
        }
        commentType.getInteractant().add(secondactantType);
        commentType.setOrganismsDiffer(uniObj.isOrganismsDiffer());

        commentType.setExperiments(uniObj.getNumberOfExperiments());

        return commentType;
    }
}
