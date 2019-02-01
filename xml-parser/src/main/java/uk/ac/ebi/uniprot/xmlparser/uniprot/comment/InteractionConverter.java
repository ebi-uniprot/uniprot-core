package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.InteractantType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.List;

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
        if (xmlObject == null)
            return null;
        InteractionBuilder builder = new InteractionBuilder();

        List<InteractantType> actTypes = xmlObject.getInteractant();
        assert (actTypes.size() == 2);
        InteractantType firstAct = actTypes.get(0);
        InteractantType secondAct = actTypes.get(1);

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
        if (uniObj == null)
            return null;
        CommentType commentType = xmlUniprotFactory.createCommentType();
        commentType.setType(INTERACTION);


        String firstInteract = uniObj.getFirstInteractor().getValue();
        InteractantType firstactantType = xmlUniprotFactory.createInteractantType();
        firstactantType.setIntactId(firstInteract);
        commentType.getInteractant().add(firstactantType);

        InteractantType secondactantType = xmlUniprotFactory.createInteractantType();
        String secondInteract = uniObj.getSecondInteractor().getValue();
        secondactantType.setIntactId(secondInteract);
        commentType.getInteractant().add(secondactantType);


        InteractionType type = uniObj.getType();
        if (type != InteractionType.SELF) {
            secondactantType.setId(uniObj.getUniProtAccession().getValue());
            String interactionGeneName = uniObj.getGeneName();
            if (!Strings.isNullOrEmpty(interactionGeneName) && !"-".equals(interactionGeneName)) {
                secondactantType.setLabel(interactionGeneName);
            }
        }

        if (type == InteractionType.XENO) {
            commentType.setOrganismsDiffer(true);
        } else {
            commentType.setOrganismsDiffer(false);
        }
        commentType.setExperiments(uniObj.getNumberOfExperiments());

        return commentType;
    }

}
