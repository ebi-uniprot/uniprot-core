package uk.ac.ebi.uniprot.domain.uniparc.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcId;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.TaxonomyBuilder;

/**
 *
 * @author jluo
 * @date: 22 May 2019
 *
 */

public class UniParcEntryImpl implements UniParcEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1558006779501834241L;
	private UniParcId uniParcId;
	private List<UniParcDBCrossReference> databaseCrossReferences;
	private Sequence sequence;
	private String uniprotExclusionReason;
	private List<SequenceFeature> sequenceFeatures;
	private List<Taxonomy> taxonomies;

	protected UniParcEntryImpl() {
		this.databaseCrossReferences = Collections.emptyList();
		this.sequenceFeatures = Collections.emptyList();
		this.taxonomies = Collections.emptyList();
	}

	public UniParcEntryImpl(UniParcId uniParcId, List<UniParcDBCrossReference> databaseCrossReferences,
			Sequence sequence, List<SequenceFeature> sequenceFeatures, List<Taxonomy> taxonomies) {
		this(uniParcId, databaseCrossReferences, sequence, sequenceFeatures, taxonomies, null);

	}

	public UniParcEntryImpl(UniParcId uniParcId, List<UniParcDBCrossReference> databaseCrossReferences,
			Sequence sequence, List<SequenceFeature> sequenceFeatures, List<Taxonomy> taxonomies,
			String uniprotExclusionReason) {
		super();
		this.uniParcId = uniParcId;
		this.databaseCrossReferences = Utils.nonNullList(databaseCrossReferences);
		this.sequence = sequence;

		this.sequenceFeatures = Utils.nonNullList(sequenceFeatures);
		this.taxonomies = Utils.nonNullList(taxonomies);
		this.uniprotExclusionReason = uniprotExclusionReason;
	}

	@Override
	public UniParcId getUniParcId() {
		return uniParcId;
	}

	@Override
	public List<UniParcDBCrossReference> getDbXReferences() {
		return databaseCrossReferences;
	}

	@Override
	public Sequence getSequence() {
		return sequence;
	}

	@Override
	public String getUniProtExclusionReason() {
		return uniprotExclusionReason;
	}

	@Override
	public List<SequenceFeature> getSequenceFeatures() {
		return sequenceFeatures;
	}

	@Override
	public List<Taxonomy> getTaxonomies() {
		if((taxonomies ==null) || taxonomies.isEmpty()) {
			 taxonomies = databaseCrossReferences.stream().flatMap(val -> val.getProperties().stream())
						.filter(val -> val.getKey().equals(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID))
						.map(val -> val.getValue()).distinct().map(this::convertTaxonomy).collect(Collectors.toList());
		}
		return taxonomies;
	}
	private Taxonomy convertTaxonomy(String taxId) {
		return new TaxonomyBuilder().taxonId(Long.parseLong(taxId)).build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;		
		UniParcEntryImpl that = (UniParcEntryImpl) o;
		return Objects.equals(uniParcId, that.uniParcId)
				&& Objects.equals(databaseCrossReferences, that.databaseCrossReferences)
				&& Objects.equals(sequence, that.sequence)
				&& Objects.equals(uniprotExclusionReason, that.uniprotExclusionReason)
				&& Objects.equals(sequenceFeatures, that.sequenceFeatures);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniParcId, databaseCrossReferences, sequence, uniprotExclusionReason, sequenceFeatures);
	}
}
