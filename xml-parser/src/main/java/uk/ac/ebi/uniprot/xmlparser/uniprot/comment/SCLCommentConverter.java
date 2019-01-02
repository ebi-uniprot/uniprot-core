package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;


import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.MoleculeType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class SCLCommentConverter implements Converter<CommentType, SubcellularLocationComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final RnaEdPositionConverter positionConverter;
	private final EvidencedValueConverter evValueConverter;

	public SCLCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public SCLCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.positionConverter = new RnaEdPositionConverter(evRefMapper, xmlUniprotFactory);
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}
	@Override
	public SubcellularLocationComment fromXml(CommentType xmlObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentType toXml(SubcellularLocationComment uniObj) {
		if(uniObj ==null)
			return null;
		  CommentType commentXML = xmlUniprotFactory.createCommentType();


	            //type
	            commentXML.setType(uniObj.getCommentType().toDisplayName().toLowerCase());

	            //Molecule
	            if(!Strings.isNullOrEmpty(uniObj.getMolecule())) {
	            	MoleculeType mol = xmlUniprotFactory.createMoleculeType();
	            	mol.setValue(uniObj.getMolecule());
	                commentXML.setMolecule(mol);
	            }
	            uniObj.getSubcellularLocations()
	            //LocationInfo
	            for(SubcellularLocation subcellularLocation : uniObj.getSubcellularLocations()){
	                commentXML.getSubcellularLocation().add(subcellularLocationTypeHandler.toXmlBinding(subcellularLocation));
	            }
	            //note
	            SubcellularLocationNote note =uniObj.getSubcellularLocationNote();
	            if((note!=null) && (!note.getTexts().isEmpty())){
	            	for(EvidencedValue evValue: note.getTexts()){
	            	    EvidencedStringType xmlText=  evidencedValueHandler.toXmlBinding(evValue);
	            	    commentXML.getText().add(xmlText);
	            	}
	            }
	           
	            //Evidences
	            if(!uniObj.getEvidenceIds().isEmpty()){
	                commentXML.getEvidence().addAll(evidenceReferenceHandler.writeEvidenceIDs(comment.getEvidenceIds()));
	            }
	            return commentXML;

	     
	}

 
}
