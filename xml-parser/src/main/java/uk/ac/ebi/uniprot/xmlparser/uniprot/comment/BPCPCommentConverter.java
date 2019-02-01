package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class BPCPCommentConverter implements CommentConverter< BPCPComment> {

	private final ObjectFactory xmlUniprotFactory;
	private final BPCPAbsorptionConverter absorptionConverter;
	private final BPCPKineticParametersConverter kpConverter;
	private final BPCPPhDependenceConverter phDependenceConverter;
	private final BPCPRedoxPotentialConverter redoxConverter;
	private final BPCPTempDependenceConverter tempConverter;

	public BPCPCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public BPCPCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.absorptionConverter = new BPCPAbsorptionConverter(evRefMapper, xmlUniprotFactory);
		this.kpConverter = new BPCPKineticParametersConverter(evRefMapper, xmlUniprotFactory);
		this.phDependenceConverter = new BPCPPhDependenceConverter(evRefMapper, xmlUniprotFactory);
		this.redoxConverter = new BPCPRedoxPotentialConverter(evRefMapper, xmlUniprotFactory);
		this.tempConverter = new BPCPTempDependenceConverter(evRefMapper, xmlUniprotFactory);
	}

	@Override
	public BPCPComment fromXml(CommentType xmlObj) {
		if(xmlObj == null)
			return null;
		BPCPCommentBuilder builder =new BPCPCommentBuilder();
		
		 //Absorption
        if(xmlObj.getAbsorption() != null){
        	builder.absorption(absorptionConverter.fromXml(xmlObj.getAbsorption()));
        }

        if(xmlObj.getKinetics() != null){
        	builder.kineticParameters(kpConverter.fromXml(xmlObj.getKinetics()));
        }
        
        //PHdependencies
        if(xmlObj.getPhDependence() != null){
        	builder.phDependence(phDependenceConverter.fromXml(xmlObj.getPhDependence()));
        }

        //RedoxPotential
        if(xmlObj.getRedoxPotential() != null){
        	builder.redoxPotential(redoxConverter.fromXml(xmlObj.getRedoxPotential()));
        }

        //TemperatureDependencies
        if(xmlObj.getTemperatureDependence() != null){
        	builder.temperatureDependence(tempConverter.fromXml(xmlObj.getTemperatureDependence()));
        }

       
        return builder.build();
	}

	@Override
	public CommentType toXml(BPCPComment comment) {
		if (comment == null)
			return null;
		CommentType commentXML = xmlUniprotFactory.createCommentType();
		commentXML.setType(comment.getCommentType().toXmlDisplayName());
		// Absorption
		if ((comment.getAbsorption() != null) && (comment.getAbsorption().getMax() != 0)) {
			commentXML.setAbsorption(absorptionConverter.toXml(comment.getAbsorption()));
		}
		if (comment.getKineticParameters() != null) {
			if (comment.getKineticParameters().getMichaelisConstants().isEmpty()
					&& comment.getKineticParameters().getMaximumVelocities().isEmpty()
					&& comment.getKineticParameters().getNote().getTexts().isEmpty()) {
				// ignore
			} else {
				commentXML.setKinetics(kpConverter.toXml(comment.getKineticParameters()));
			}
		}
		if (comment.getPhDependence() != null) {
			commentXML.setPhDependence(phDependenceConverter.toXml(comment.getPhDependence()));
		}
		if (comment.getRedoxPotential() != null) {
			commentXML.setRedoxPotential(redoxConverter.toXml(comment.getRedoxPotential()));
		}
		if (comment.getTemperatureDependence() != null) {
			commentXML.setTemperatureDependence(tempConverter.toXml(comment.getTemperatureDependence()));
		}

		return commentXML;
	}

}
