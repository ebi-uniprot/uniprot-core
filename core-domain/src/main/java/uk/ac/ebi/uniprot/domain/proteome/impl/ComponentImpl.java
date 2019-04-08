package uk.ac.ebi.uniprot.domain.proteome.impl;

import java.util.List;
import java.util.Objects;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;

public class ComponentImpl implements Component {
	private static final long serialVersionUID = -5592878122341180241L;
	private String name;
	private String description;
	private long proteinCount;
	private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences;
	private ComponentImpl() {
		
	}
	public ComponentImpl(String name, String description, long proteinCount,
			List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
		this.name = name;
		this.description = description;
		this.proteinCount = proteinCount;
		this.dbXReferences =Utils.nonNullList(dbXReferences);
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<DBCrossReference<ProteomeXReferenceType>> getDbXReferences() {
		return dbXReferences;
	}

	@Override
	public long getProteinCount() {
		return proteinCount;
	}
	@Override
	public int hashCode() {	
		return Objects.hash(name, description, proteinCount, dbXReferences);

	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComponentImpl other = (ComponentImpl) obj;
		return Objects.equals(name, other.name) && Objects.equals(description, other.description)
				&& Objects.equals(proteinCount, other.proteinCount) 
				&& Objects.equals(dbXReferences, other.dbXReferences);
	}

}
