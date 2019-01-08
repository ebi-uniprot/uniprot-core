package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

public class WRCommentConverter implements CommentConverter<WebResourceComment> {
	private final ObjectFactory xmlUniprotFactory;

	public WRCommentConverter() {
		this(new ObjectFactory());
	}

	public WRCommentConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;

	}

	@Override
	public WebResourceComment fromXml(CommentType xmlObject) {
		if(xmlObject ==null)
			return null;
		WebResourceCommentBuilder builder = WebResourceCommentBuilder.newInstance();
		
		if (xmlObject.getName() != null) {
			builder.resourceName(xmlObject.getName());
		}

		List<CommentType.Link> listLinks = xmlObject.getLink();

		for (CommentType.Link linkXML : listLinks) {
			String link =linkXML.getUri();
			builder.resourceUrl(link);
		}
	     //Value
        if(!xmlObject.getText().isEmpty()){
            String text = xmlObject.getText().get(0).getValue();
            builder.note(text);
    
        }

        return builder.build();
	}

	@Override
	public CommentType toXml(WebResourceComment uniObj) {
		if(uniObj ==null)
			return null;	
		CommentType commentType = xmlUniprotFactory.createCommentType();
		commentType.setType(WebResourceComment.ONLINE_INFORMATION_XMLTAG);
		if (!Strings.isNullOrEmpty(uniObj.getResourceName())) {
			commentType.setName(uniObj.getResourceName());
		}
		if (!Strings.isNullOrEmpty(uniObj.getResourceUrl())) {
			CommentType.Link xmlLink = xmlUniprotFactory.createCommentTypeLink();
			xmlLink.setUri(uniObj.getResourceUrl());
			commentType.getLink().add(xmlLink);
		}
		if(!Strings.isNullOrEmpty(uniObj.getNote())) {
			EvidencedStringType evidencedStringType = xmlUniprotFactory.createEvidencedStringType();
			evidencedStringType.setValue(uniObj.getNote());
			commentType.getText().add(evidencedStringType);
		}
		return commentType;
	}

	

}
