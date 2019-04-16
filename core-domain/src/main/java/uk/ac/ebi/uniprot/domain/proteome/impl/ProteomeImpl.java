package uk.ac.ebi.uniprot.domain.proteome.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.proteome.CanonicalProtein;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.Proteome;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeType;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.Superkingdom;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxonomy;

public class ProteomeImpl implements Proteome {

	private static final long serialVersionUID = 1962327704149624243L;
	private ProteomeId id;
	private String description;
	private Taxonomy taxonomy;
	private LocalDate modified;
	private ProteomeType proteomeType;
	private ProteomeId redundantTo;
	private String strain;
	private String isolate;
	private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences;
	private List<Component> components;
	private List<Citation> references;
	private List<RedundantProteome> redundantProteomes;
	private ProteomeId panproteome;
	private int annotationScore;
	private Superkingdom superkingdom;
	private long proteinCount;
	private long geneCount;
	List<String> taxonLineage;
	private List<CanonicalProtein> canonicalProteins;
	
	private ProteomeImpl() {
		dbXReferences = Collections.emptyList();
		components = Collections.emptyList();
		references = Collections.emptyList();
		redundantProteomes = Collections.emptyList();
		taxonLineage = Collections.emptyList();
		canonicalProteins = Collections.emptyList();
	}
	
	public ProteomeImpl(ProteomeId id, Taxonomy taxonomy, String description,  LocalDate modified,
			ProteomeType proteomeType, ProteomeId redundantTo, String strain, String isolate,
			List<DBCrossReference<ProteomeXReferenceType>> dbXReferences, List<Component> components,
			List<Citation> references, List<RedundantProteome> redundantProteomes, ProteomeId panproteome,
			int annotationScore, Superkingdom superkingdom, long proteinCount, long geneCount,
			List<String> taxonLineage, List<CanonicalProtein> canonicalProteins) {
		super();
		this.id = id;
		this.taxonomy = taxonomy;
		this.description = description;
		
		this.modified = modified;
		this.proteomeType = proteomeType;
		this.redundantTo = redundantTo;
		this.strain = strain;
		this.isolate = isolate;
		this.dbXReferences = Utils.nonNullUnmodifiableList( dbXReferences);
		this.components =  Utils.nonNullUnmodifiableList(components);
		this.references = Utils.nonNullUnmodifiableList( references);
		this.redundantProteomes = Utils.nonNullUnmodifiableList( redundantProteomes);
		this.panproteome = panproteome;
		this.annotationScore = annotationScore;
		this.superkingdom = superkingdom;
		this.proteinCount = proteinCount;
		this.geneCount = geneCount;
		this.taxonLineage  = Utils.nonNullUnmodifiableList( taxonLineage);
		this.canonicalProteins  = Utils.nonNullUnmodifiableList( canonicalProteins);
	}


	@Override
	public ProteomeId getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

	@Override
	public LocalDate getModified() {
		return modified;
	}

	@Override
	public ProteomeType getProteomeType() {
		return proteomeType;
	}

	@Override
	public ProteomeId getRedundantTo() {
		return redundantTo;
	}

	@Override
	public String getStrain() {
		return strain;
	}

	@Override
	public String getIsolate() {
		return isolate;
	}

	@Override
	public List<DBCrossReference<ProteomeXReferenceType>> getDbXReferences() {
		return dbXReferences;
	}

	@Override
	public List<Component> getComponents() {
		return components;
	}

	@Override
	public List<Citation> getReferences() {
		return references;
	}

	@Override
	public List<RedundantProteome> getRedudantProteomes() {
		return redundantProteomes;
	}

	@Override
	public ProteomeId getPanproteome() {
		return panproteome;
	}

	@Override
	public int getAnnotationScore() {
		return annotationScore;
	}

	@Override
	public Superkingdom getSuperkingdom() {
		return superkingdom;
	}

	@Override
	public long getProteinCount() {
		return proteinCount;
	}

	@Override
	public long getGeneCount() {
		return geneCount;
	}
	@Override
	public List<String> getTaxonLineage() {
		return taxonLineage;
	}
	@Override
	public List<CanonicalProtein> getCanonicalProteins() {
		return this.canonicalProteins;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(components, dbXReferences, description, id, isolate, modified,
				panproteome, redundantProteomes, redundantTo, references,
				strain, superkingdom, taxonomy, proteomeType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProteomeImpl other = (ProteomeImpl) obj;
		return Objects.equals(components, other.components) 
				&& Objects.equals(dbXReferences, other.dbXReferences)
				&& Objects.equals(description, other.description) 
				&& Objects.equals(id, other.id)
				&& Objects.equals(isolate, other.isolate)
				&& Objects.equals(modified, other.modified)
				&& Objects.equals(panproteome, other.panproteome)
				&& Objects.equals(redundantProteomes, other.redundantProteomes)
				&& Objects.equals(redundantTo, other.redundantTo)
				&& Objects.equals(references, other.references)
				&& Objects.equals(strain, other.strain)
				&& Objects.equals(superkingdom, other.superkingdom)
				&& Objects.equals(taxonomy, other.taxonomy)
				&& Objects.equals(proteomeType, other.proteomeType)
				;
	}

	
	

}
