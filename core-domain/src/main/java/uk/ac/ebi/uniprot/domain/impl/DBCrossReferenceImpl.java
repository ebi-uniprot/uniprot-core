package uk.ac.ebi.uniprot.domain.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Property;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DBCrossReferenceImpl implements DBCrossReference {

	 private final  String databaseName;
	 private final String id;
	 private List<Property> properties;
//	public DBCrossReferenceImpl() {
//		properties = Collections.emptyList();
//	}
//	
	public DBCrossReferenceImpl(String databaseName, String id) {
		this(databaseName, id, Collections.emptyList());
	}
	
	@JsonCreator
	public DBCrossReferenceImpl(@JsonProperty("databaseName")String databaseName,
			@JsonProperty("id")String id,
			@JsonProperty("properties")List<Property> properties) {
		this.databaseName = databaseName;
		this.id =id;
		setProperties(properties);
	}
	
	@Override
	public String getDatabaseName() {
		return databaseName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		if((properties ==null) || properties.isEmpty())
			this.properties =Collections.emptyList();
		else {
			this.properties = new ArrayList<>();
			this.properties.addAll(properties);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		DBCrossReferenceImpl other = (DBCrossReferenceImpl) obj;
		if (databaseName == null) {
			if (other.databaseName != null)
				return false;
		} else if (!databaseName.equals(other.databaseName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

}
