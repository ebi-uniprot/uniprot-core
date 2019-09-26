package org.uniprot.core.proteome.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.ProteomeXReferenceType;
import org.uniprot.core.util.Utils;

public class ComponentImpl implements Component {
	private static final long serialVersionUID = -5592878122341180241L;
	private String name;
	private String description;
	private int proteinCount;
	private ComponentType type;
	
	private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences;

	protected ComponentImpl() {
		dbXReferences = Collections.emptyList();
	}
	public ComponentImpl(String name, String description, int proteinCount, ComponentType type,
			List<DBCrossReference<ProteomeXReferenceType>> dbXReferences ) {
		this.name = name;
		this.description = description;
		this.proteinCount = proteinCount;
		this.type = type;
		this.dbXReferences =Utils.unmodifiableList(dbXReferences);
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
	public int getProteinCount() {
		return proteinCount;
	}
	@Override
	public ComponentType getType() {
		return type;
	}
	@Override
	public int hashCode() {	
		return Objects.hash(name, description, proteinCount, dbXReferences, type);

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
				&& Objects.equals(type, other.type) 
				&& Objects.equals(dbXReferences, other.dbXReferences);
	}
	
}
