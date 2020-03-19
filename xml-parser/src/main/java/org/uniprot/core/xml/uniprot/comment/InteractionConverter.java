package org.uniprot.core.xml.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.InteractantType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class InteractionConverter implements Converter<CommentType, Interaction> {
  private static final String INTERACTION = "interaction";
  private final ObjectFactory xmlUniprotFactory;
  private final InteractantConverter interactantConverter;
  public InteractionConverter() {
    this(new ObjectFactory());
  }

  public InteractionConverter(ObjectFactory xmlUniprotFactory) {

    this.xmlUniprotFactory = xmlUniprotFactory;
    this.interactantConverter = new InteractantConverter(xmlUniprotFactory);
  }

  @Override
  public Interaction fromXml(CommentType xmlObject) {
    if (xmlObject == null) return null;
    InteractionBuilder builder = new InteractionBuilder();

    List<InteractantType> actTypes = xmlObject.getInteractant();
    assert (actTypes.size() == 2);
    InteractantType firstAct = actTypes.get(0);
    InteractantType secondAct = actTypes.get(1);
    Interactant interactant1 = interactantConverter.fromXml(firstAct);
    Interactant interactant2= interactantConverter.fromXml(secondAct);
    
    builder.firstInteractant(interactant1)
    .secondInteractant(interactant2)
    .numberOfExperiments(xmlObject.getExperiments())
    .isOrganismDiffer(xmlObject.isOrganismsDiffer());
    
   
    return builder.build();
  }

  @Override
  public CommentType toXml(Interaction uniObj) {
    if (uniObj == null) return null;
    CommentType commentType = xmlUniprotFactory.createCommentType();
    commentType.setType(INTERACTION);

    commentType.getInteractant().add(interactantConverter.toXml(uniObj.getFirstInteractant()));	
    commentType.getInteractant().add(interactantConverter.toXml(uniObj.getSecondInteractant()));		
    commentType.setOrganismsDiffer(uniObj.isOrganismsDiffer());

    commentType.setExperiments(uniObj.getNumberOfExperiments());

    return commentType;
  }

  
}
