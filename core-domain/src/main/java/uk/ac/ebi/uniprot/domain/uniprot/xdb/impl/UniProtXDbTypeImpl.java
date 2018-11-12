package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseCategory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniProtXDbTypeImpl implements UniProtXDbType {
	private final String name;
	private final String displayName;
	private final DatabaseCategory category;
	private final String uriLink;
	private  final List<DBXRefTypeAttribute> attributes;
	private static final DBXRefTypeAttribute DEFAULT_ATTRIBUTE = new DBXRefTypeAttributeImpl("Description",
			"description", null);

	@JsonCreator
	public UniProtXDbTypeImpl(
			@JsonProperty("name")String name, 
			@JsonProperty("displayName")String displayName, 
			@JsonProperty("category")DatabaseCategory category, 
			@JsonProperty("uriLink")String uriLink,
			@JsonProperty("attributes")
			List<DBXRefTypeAttributeImpl> attributes) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.category = category;
		this.uriLink = uriLink;
	
		this.attributes= new ArrayList<>();
		if((attributes !=null) && !attributes.isEmpty())
			this.attributes.addAll(attributes);
		else
			this.attributes.add(DEFAULT_ATTRIBUTE);
		
	}



	public void setAttributes(List<DBXRefTypeAttributeImpl> attributes) {
		if ((attributes != null) && (!attributes.isEmpty())) {
			this.attributes.clear();
			this.attributes.addAll(attributes);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public DatabaseCategory getCategory() {
		return category;
	}

	@Override
	public String getUriLink() {
		return uriLink;
	}

	@Override
	public List<DBXRefTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uriLink == null) ? 0 : uriLink.hashCode());
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
		UniProtXDbTypeImpl other = (UniProtXDbTypeImpl) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (category != other.category)
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uriLink == null) {
			if (other.uriLink != null)
				return false;
		} else if (!uriLink.equals(other.uriLink))
			return false;
		return true;
	}

}
