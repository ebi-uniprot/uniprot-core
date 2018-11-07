package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DBXRefTypeAttributeImpl implements DBXRefTypeAttribute {
	private  String name;
	private  String xmlTag;
	private  String uriLink;
	public DBXRefTypeAttributeImpl() {
		
	}
	public DBXRefTypeAttributeImpl(String name, String xmlTag, String uriLink) {
		this.name = name;
		this.xmlTag = xmlTag;
		this.uriLink= uriLink;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setXmlTag(String xmlTag) {
		this.xmlTag = xmlTag;
	}
	public void setUriLink(String uriLink) {
		this.uriLink = uriLink;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getXmlTag() {
		return xmlTag;
	}

	@Override
	public String getUriLink() {
		return uriLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uriLink == null) ? 0 : uriLink.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((xmlTag == null) ? 0 : xmlTag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBXRefTypeAttributeImpl other = (DBXRefTypeAttributeImpl) obj;
		if (uriLink == null) {
			if (other.uriLink != null)
				return false;
		} else if (!uriLink.equals(other.uriLink))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (xmlTag == null) {
			if (other.xmlTag != null)
				return false;
		} else if (!xmlTag.equals(other.xmlTag))
			return false;
		return true;
	}

	
}
