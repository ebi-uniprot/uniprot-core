package uk.ac.ebi.uniprot.domain.proteome.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Builder;
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
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteomeImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxonomy;

public class ProteomeBuilder implements Builder<ProteomeBuilder, Proteome> {
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
	private long proteinCount;
	private long geneCount;
	private List<String> taxonLineage =new ArrayList<>();
	private List<CanonicalProtein> canonicalProteins  =new ArrayList<>();
	public static ProteomeBuilder newInstance() {
		return new ProteomeBuilder();
	}
	
	@Override
	public Proteome build() {
		return new ProteomeImpl( id,  taxonomy,  description,   modified,
				 proteomeType,  redundantTo,  strain,  isolate,
				dbXReferences,  components,
				references,  redundantProteomes,  panproteome,
				 annotationScore,  superkingdom,  proteinCount,  geneCount, taxonLineage, canonicalProteins) ;

	}
	
	@Override
	public ProteomeBuilder from(Proteome instance) {
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
		this.proteinCount = instance.getProteinCount();
		this.geneCount =instance.getGeneCount();
		this.taxonLineage =instance.getTaxonLineage();
		canonicalProteins.clear();
		canonicalProteins.addAll(instance.getCanonicalProteins());
		return this;
	}

	
	public ProteomeBuilder proteomeId(ProteomeId id) {
		this.id = id;
		return this;
	}

	
	public ProteomeBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public ProteomeBuilder taxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
		return this;
	}
	
	public ProteomeBuilder modified(LocalDate modified) {
		this.modified = modified;
		return this;
	}

	public ProteomeBuilder proteomeType(ProteomeType proteomeType) {
		this.proteomeType = proteomeType;
		return this;
	}
	
	public ProteomeBuilder redundantTo(ProteomeId redundantTo) {
		this.redundantTo = redundantTo;
		return this;
	}
	
	public ProteomeBuilder strain(String strain) {
		this.strain = strain;
		return this;
	}
	
	public ProteomeBuilder isolate(String isolate) {
		this.isolate = isolate;
		return this;
	}
	
	public ProteomeBuilder dbXReferences(List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
		this.dbXReferences =  Utils.nonNullList(dbXReferences);
		return this;
	}
	
	public ProteomeBuilder addDbXReferences(DBCrossReference<ProteomeXReferenceType> dbXReference) {
		Utils.nonNullAdd(dbXReference, dbXReferences);
		return this;
	}
	
	public ProteomeBuilder components(List<Component> components) {
		this.components =  Utils.nonNullList(components);
		return this;
	}
	
	public ProteomeBuilder addComponent(Component component) {
		Utils.nonNullAdd(component, components);
		return this;
	}
	
	public ProteomeBuilder references(List<Citation> references) {
		this.references =  Utils.nonNullList(references);
		return this;
	}
	
	public ProteomeBuilder addReference(Citation reference) {
		Utils.nonNullAdd(reference, references);
		return this;
	}
	
	public ProteomeBuilder redundantProteomes(List<RedundantProteome> redundantProteomes) {
		this.redundantProteomes =  Utils.nonNullList(redundantProteomes);
		return this;
	}
	
	public ProteomeBuilder addRedundantProteome(RedundantProteome redundantProteome) {
		Utils.nonNullAdd(redundantProteome, redundantProteomes);
		return this;
	}
	
	public ProteomeBuilder panproteome(ProteomeId panproteome) {
		this.panproteome = panproteome;
		return this;
	}
	public ProteomeBuilder annotationScore(int annotationScore) {
		this.annotationScore = annotationScore;
		return this;
	}
	
	public ProteomeBuilder superkingdom(Superkingdom superkingdom) {
		this.superkingdom = superkingdom;
		return this;
	}
	
	public ProteomeBuilder proteinCount(long proteinCount) {
		this.proteinCount = proteinCount;
		return this;
	}
	public ProteomeBuilder geneCount(long geneCount) {
		this.geneCount = geneCount;
		return this;
	}
	public ProteomeBuilder taxonLineage(List<String> taxonLineage) {
		this.taxonLineage =  Utils.nonNullList(taxonLineage);
		return this;
	}
	public ProteomeBuilder canonicalProteins(List<CanonicalProtein> canonicalProteins) {
		this.canonicalProteins = Utils.nonNullList(canonicalProteins);
		return this;
	}

	public ProteomeBuilder addCanonicalProtein(CanonicalProtein canonicalProtein) {
		Utils.nonNullAdd(canonicalProtein, canonicalProteins);
		return this;
	}

}
