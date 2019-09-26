package org.uniprot.core.proteome.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.ProteomeEntryImpl;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class ProteomeEntryBuilder implements Builder<ProteomeEntryBuilder, ProteomeEntry> {
	private ProteomeId id;
	private String description;
	private Taxonomy taxonomy;
	private LocalDate modified;
	private ProteomeType proteomeType= ProteomeType.NORMAL;
	private ProteomeId redundantTo;
	private String strain;
	private String isolate;
	private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences =new ArrayList<>();
	private List<Component> components =new ArrayList<>();
	private List<Citation> references =new ArrayList<>();
	private List<RedundantProteome> redundantProteomes =new ArrayList<>();
	private ProteomeId panproteome;
	private int annotationScore;
	private Superkingdom superkingdom;
	private int geneCount;
	private List<TaxonomyLineage> taxonLineage =new ArrayList<>();
	private List<CanonicalProtein> canonicalProteins  =new ArrayList<>();
	private String sourceDb;
	public static ProteomeEntryBuilder newInstance() {
		return new ProteomeEntryBuilder();
	}
	
	@Override
	public ProteomeEntry build() {
		return new ProteomeEntryImpl( id,  taxonomy,  description,   modified,
				 proteomeType,  redundantTo,  strain,  isolate,
				dbXReferences,  components,
				references,  redundantProteomes,  panproteome,
				 annotationScore,  superkingdom,   geneCount, taxonLineage, canonicalProteins, sourceDb) ;

	}
	
	@Override
	public ProteomeEntryBuilder from(ProteomeEntry instance) {
		this.id = instance.getId();
		this.taxonomy =instance.getTaxonomy();
		this.description =instance.getDescription();
	
		this.modified = instance.getModified();
		this.proteomeType = instance.getProteomeType();
		this.redundantTo = instance.getRedundantTo();
		this.strain = instance.getStrain();
		this.isolate =instance.getIsolate();
		this.dbXReferences.clear();
		this.dbXReferences.addAll(instance.getDbXReferences());

		this.components.clear();
		this.components.addAll(instance.getComponents());
		
		this.references.clear();
		this.references.addAll(instance.getReferences());
		
		this.redundantProteomes.clear();
		this.redundantProteomes.addAll(instance.getRedudantProteomes());

		this.panproteome =instance.getPanproteome();
		this.annotationScore = instance.getAnnotationScore();
		this.superkingdom = instance.getSuperkingdom();
		this.geneCount =instance.getGeneCount();
		this.taxonLineage =instance.getTaxonLineage();
		canonicalProteins.clear();
		canonicalProteins.addAll(instance.getCanonicalProteins());
		this.sourceDb =instance.getSourceDb();
	
		return this;
	}

	
	public ProteomeEntryBuilder proteomeId(ProteomeId id) {
		this.id = id;
		return this;
	}
	public ProteomeEntryBuilder proteomeId(String id) {
		this.id = new ProteomeIdBuilder(id).build();
		return this;
	}
	
	public ProteomeEntryBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public ProteomeEntryBuilder taxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
		return this;
	}
	
	public ProteomeEntryBuilder modified(LocalDate modified) {
		this.modified = modified;
		return this;
	}

	public ProteomeEntryBuilder proteomeType(ProteomeType proteomeType) {
		this.proteomeType = proteomeType;
		return this;
	}
	
	public ProteomeEntryBuilder redundantTo(ProteomeId redundantTo) {
		this.redundantTo = redundantTo;
		return this;
	}
	
	public ProteomeEntryBuilder strain(String strain) {
		this.strain = strain;
		return this;
	}
	
	public ProteomeEntryBuilder isolate(String isolate) {
		this.isolate = isolate;
		return this;
	}
	public ProteomeEntryBuilder sourceDb(String sourceDb) {
		this.sourceDb = sourceDb;
		return this;
	}
	
	public ProteomeEntryBuilder dbXReferences(List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
		this.dbXReferences =  Utils.modifiableList(dbXReferences);
		return this;
	}
	
	public ProteomeEntryBuilder addDbXReferences(DBCrossReference<ProteomeXReferenceType> dbXReference) {
		Utils.addOrIgnoreNull(dbXReference, dbXReferences);
		return this;
	}
	
	public ProteomeEntryBuilder components(List<Component> components) {
		this.components =  Utils.modifiableList(components);
		return this;
	}
	
	public ProteomeEntryBuilder addComponent(Component component) {
		Utils.addOrIgnoreNull(component, components);
		return this;
	}
	
	public ProteomeEntryBuilder references(List<Citation> references) {
		this.references =  Utils.modifiableList(references);
		return this;
	}
	
	public ProteomeEntryBuilder addReference(Citation reference) {
		Utils.addOrIgnoreNull(reference, references);
		return this;
	}
	
	public ProteomeEntryBuilder redundantProteomes(List<RedundantProteome> redundantProteomes) {
		this.redundantProteomes =  Utils.modifiableList(redundantProteomes);
		return this;
	}
	
	public ProteomeEntryBuilder addRedundantProteome(RedundantProteome redundantProteome) {
		Utils.addOrIgnoreNull(redundantProteome, redundantProteomes);
		return this;
	}
	
	public ProteomeEntryBuilder panproteome(ProteomeId panproteome) {
		this.panproteome = panproteome;
		return this;
	}
	public ProteomeEntryBuilder annotationScore(int annotationScore) {
		this.annotationScore = annotationScore;
		return this;
	}
	
	public ProteomeEntryBuilder superkingdom(Superkingdom superkingdom) {
		this.superkingdom = superkingdom;
		return this;
	}
	public ProteomeEntryBuilder geneCount(int geneCount) {
		this.geneCount = geneCount;
		return this;
	}
	public ProteomeEntryBuilder taxonLineage(List<TaxonomyLineage> taxonLineage) {
		this.taxonLineage =  Utils.modifiableList(taxonLineage);
		return this;
	}
	public ProteomeEntryBuilder addTaxonLineage(TaxonomyLineage taxon) {
		Utils.addOrIgnoreNull(taxon, taxonLineage);
		return this;
	}
	public ProteomeEntryBuilder canonicalProteins(List<CanonicalProtein> canonicalProteins) {
		this.canonicalProteins = Utils.modifiableList(canonicalProteins);
		return this;
	}

	public ProteomeEntryBuilder addCanonicalProtein(CanonicalProtein canonicalProtein) {
		Utils.addOrIgnoreNull(canonicalProtein, canonicalProteins);
		return this;
	}

}
