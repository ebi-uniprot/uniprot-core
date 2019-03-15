package uk.ac.ebi.uniprot.cv.disease;

import java.util.Collections;
import java.util.List;

public class CrossReference {
	private final String databaseType;
    private final String id;
    private final List<String> properties;
    
    public CrossReference(String databaseType, String id) {
    	this(databaseType, id, Collections.emptyList());
		
	}
    
	public CrossReference(String databaseType, String id, List<String> properties) {
		this.databaseType = databaseType;
		this.id = id;
		this.properties = properties;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public String getId() {
		return id;
	}

	public List<String> getProperties() {
		return properties;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(databaseType).append("; ").append(id);
		for(String property:properties) {
			sb.append("; ").append(property);
		}
		sb.append(".");
		
		return sb.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseType == null) ? 0 : databaseType.hashCode());
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
		CrossReference other = (CrossReference) obj;
		if (databaseType == null) {
			if (other.databaseType != null)
				return false;
		} else if (!databaseType.equals(other.databaseType))
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