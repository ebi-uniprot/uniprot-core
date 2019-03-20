package uk.ac.ebi.uniprot.cv.pathway;

import java.util.ArrayList;
import java.util.List;

public class UniPathway implements Comparable<UniPathway>{
	private final String accession;
	private final String name;
	private UniPathway parent ;
	private List<UniPathway> children =new ArrayList<>();
	
	public UniPathway(String accession, String name) {
		this.accession = accession;
		this.name = name;
	}

	public String getAccession() {
		return accession;
	}

	public String getName() {
		return name;
	}
	public void setParent(UniPathway parent) {
		this.parent = parent;
		if(this.parent !=null) {
			if (!this.parent.getChildren().contains(this)) {
				this.parent.getChildren().add(this);
			}
		}
		
	}
	public UniPathway getParent(){
		return parent;
	}
	public List<UniPathway> getChildren(){
		return children;
	}

	@Override
	public int compareTo(UniPathway o) {
		return name.compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accession == null) ? 0 : accession.hashCode());
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
		UniPathway other = (UniPathway) obj;
		if (accession == null) {
			if (other.accession != null)
				return false;
		} else if (!accession.equals(other.accession))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
