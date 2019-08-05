package uk.ac.ebi.uniprot.domain.uniparc.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.common.Utils;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcId;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

public class UniParcEntryBuilder implements Builder<UniParcEntryBuilder, UniParcEntry> {
	private UniParcId uniParcId;
	private List<UniParcDBCrossReference> databaseCrossReferences =new ArrayList<>();
	private Sequence sequence;
	private String uniprotExclusionReason;
	private List<SequenceFeature> sequenceFeatures=new ArrayList<>();
	private List<Taxonomy> taxonomies=new ArrayList<>();
	@Override
	public UniParcEntry build() {
		return new UniParcEntryImpl( uniParcId,  databaseCrossReferences,
				 sequence, sequenceFeatures, taxonomies,
				 uniprotExclusionReason) ;
	}
	public UniParcEntryBuilder uniParcId(String uniParcId) {
		return uniParcId(new UniParcIdBuilder(uniParcId).build());
	}
	
	public UniParcEntryBuilder uniParcId(UniParcId uniParcId) {
		this.uniParcId = uniParcId;
		return this;
	}
	
	public UniParcEntryBuilder databaseCrossReferences(List<UniParcDBCrossReference> databaseCrossReferences) {
		this.databaseCrossReferences = Utils.nonNullList(databaseCrossReferences);
		return this;
	}
	
	public UniParcEntryBuilder addDatabaseCrossReference(UniParcDBCrossReference databaseCrossReference) {
		Utils.nonNullAdd(databaseCrossReference, databaseCrossReferences);
		return this;
	}
	public UniParcEntryBuilder sequence(Sequence sequence) {
		this.sequence = sequence;
		return this;
	}
	
	public UniParcEntryBuilder uniprotExclusionReason(String uniprotExclusionReason) {
		this.uniprotExclusionReason = uniprotExclusionReason;
		return this;
	}
	
	public UniParcEntryBuilder sequenceFeatures(List<SequenceFeature> sequenceFeatures) {
		this.sequenceFeatures = Utils.nonNullList(sequenceFeatures);
		return this;
	}
	
	public UniParcEntryBuilder addSequenceFeature(SequenceFeature sequenceFeature) {
		Utils.nonNullAdd(sequenceFeature, sequenceFeatures);
		return this;
	}
	
	public UniParcEntryBuilder taxonomies(List<Taxonomy> taxonomies) {
		this.taxonomies = Utils.nonNullList(taxonomies);
		return this;
	}
	
	public UniParcEntryBuilder addTaxonomy(Taxonomy taxonomy) {
		Utils.nonNullAdd(taxonomy, taxonomies);
		return this;
	}
	
	@Override
	public UniParcEntryBuilder from(UniParcEntry instance) {
		return uniParcId(instance.getUniParcId())
				.databaseCrossReferences(instance.getDbXReferences())
				.sequence(instance.getSequence())
				.uniprotExclusionReason(instance.getUniProtExclusionReason())
				.sequenceFeatures(instance.getSequenceFeatures())
				.taxonomies(instance.getTaxonomies());
	}

}

