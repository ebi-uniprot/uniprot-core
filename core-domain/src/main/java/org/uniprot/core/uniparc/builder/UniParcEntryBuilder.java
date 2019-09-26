package org.uniprot.core.uniparc.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.UniParcEntryImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

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
		this.databaseCrossReferences = Utils.modifiableList(databaseCrossReferences);
		return this;
	}
	
	public UniParcEntryBuilder addDatabaseCrossReference(UniParcDBCrossReference databaseCrossReference) {
		Utils.addOrIgnoreNull(databaseCrossReference, databaseCrossReferences);
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
		this.sequenceFeatures = Utils.modifiableList(sequenceFeatures);
		return this;
	}
	
	public UniParcEntryBuilder addSequenceFeature(SequenceFeature sequenceFeature) {
		Utils.addOrIgnoreNull(sequenceFeature, sequenceFeatures);
		return this;
	}
	
	public UniParcEntryBuilder taxonomies(List<Taxonomy> taxonomies) {
		this.taxonomies = Utils.modifiableList(taxonomies);
		return this;
	}
	
	public UniParcEntryBuilder addTaxonomy(Taxonomy taxonomy) {
		Utils.addOrIgnoreNull(taxonomy, taxonomies);
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

