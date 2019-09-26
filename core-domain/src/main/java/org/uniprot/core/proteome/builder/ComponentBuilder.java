package org.uniprot.core.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.ProteomeXReferenceType;
import org.uniprot.core.proteome.impl.ComponentImpl;
import org.uniprot.core.util.Utils;

public class ComponentBuilder implements Builder<ComponentBuilder, Component> {
	private String name;
	private String description;
	private int proteinCount;
	private ComponentType type;
	private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences =new ArrayList<>();

	
	public static ComponentBuilder newInstance() {
		return new ComponentBuilder();
	}
	
	
	public ComponentBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ComponentBuilder description(String description) {
		this.description = description;
		return this;
	}

	public ComponentBuilder dbXReferences(List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
		this.dbXReferences = Utils.nonNullList(dbXReferences);
		return this;
	}

	public ComponentBuilder addDbXReference(DBCrossReference<ProteomeXReferenceType> dbXReference) {
		Utils.addOrIgnoreNull(dbXReference, dbXReferences);
		return this;
	}
	
	
	public ComponentBuilder proteinCount(int proteinCount) {
		this.proteinCount = proteinCount;
		return this;
	}
	public ComponentBuilder type(ComponentType type) {
		this.type = type;
		return this;
	}

	@Override
	public Component build() {
		return new ComponentImpl( name,  description,  proteinCount, type, dbXReferences) ;
	}

	@Override
	public ComponentBuilder from(Component instance) {
		this.name = instance.getName();
		this.description = instance.getDescription();
		this.proteinCount = instance.getProteinCount();
		this.type = instance.getType();
		dbXReferences.clear();
		dbXReferences.addAll(instance.getDbXReferences());
		
		return this;
	}

}
