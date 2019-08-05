package uk.ac.ebi.uniprot.domain.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.common.Utils;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.ComponentType;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.domain.proteome.impl.ComponentImpl;

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
		Utils.nonNullAdd(dbXReference, dbXReferences);
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
