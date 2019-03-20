package uk.ac.ebi.uniprot.cv.go.impl;

import uk.ac.ebi.uniprot.cv.go.GoTerm;

public class GoTermImpl implements GoTerm {
	private final String id;
	private final String name;
	public GoTermImpl(String id, String name) {
		this.id = id; 
		this.name = name;
	}
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		GoTermImpl other = (GoTermImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
