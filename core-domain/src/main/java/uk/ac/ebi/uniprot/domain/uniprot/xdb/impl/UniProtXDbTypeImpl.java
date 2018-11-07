package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseCategory;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniProtXDbTypeImpl implements UniProtXDbType {
	private String name;
	private String displayName;
	private DatabaseCategory category;
	private String uriLink;
	private List<DBXRefTypeAttribute> attributes = new ArrayList<>();
	private static final DBXRefTypeAttribute DEFAULT_ATTRIBUTE = new DBXRefTypeAttributeImpl("Description",
			"description", null);

	public UniProtXDbTypeImpl() {
		attributes.add(DEFAULT_ATTRIBUTE);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setCategory(String category) {
		this.category = DatabaseCategory.typeOf(category);
	}

	public void setUriLink(String uriLink) {
		this.uriLink = uriLink;
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
