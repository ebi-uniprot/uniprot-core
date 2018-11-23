package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CommentsImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl;
import uk.ac.ebi.uniprot.domain.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniProtEntryImpl implements UniProtEntry {
	private final UniProtEntryType entryType;
	private final UniProtAccession primaryAccession;
	private final List<UniProtAccession> secondaryAccessions;
	private final UniProtId uniProtId;
	private final EntryAudit entryAudit;

	private final UniProtTaxonId taxonId;
	private final OrganismName organism;
	private final List<Organism> organismHosts;
	private final List<OrganismName> taxonomyLineage;
	private final ProteinExistence proteinExistence;

	private final ProteinDescription proteinDescription;
	private final List<Gene> genes;
	private final Comments comments;
	private final List<Feature> features;
	private final List<Organelle> organelles;

	private final List<Keyword> keywords;
	private final UniProtReferences references;
	private final UniProtDBCrossReferences databaseCrossReferences;
	private final Sequence sequence;
	private final FlagType flag;

	private final InternalSection internalSection;
	private final EntryInactiveReason inactiveReason;


	public UniProtEntryImpl( UniProtAccession primaryAccession,
			 UniProtId uniProtId,
			 EntryInactiveReason inactiveReason) {
		this(UniProtEntryType.UNKNOWN, primaryAccession, null, uniProtId,
				null, null, null, null, null,null,
				null,null, null, null, null, null,
				null, null, null, null, null, inactiveReason );
	}

	public UniProtEntryImpl(UniProtEntryType entryType,
			UniProtAccession primaryAccession,
			List<UniProtAccession> secondaryAccessions,
			UniProtId uniProtId,
			EntryAudit entryAudit,
			UniProtTaxonId taxonId,
			OrganismName organism,
			List<Organism> organismHosts,
			List<OrganismName> taxonomyLineage,
			ProteinExistence proteinExistence,
			ProteinDescription proteinDescription,
			List<Gene> genes,
			Comments comments,
			List<Feature> features,
			List<Organelle> organelles,
			List<Keyword> keywords,
			UniProtReferences references,
			UniProtDBCrossReferences databaseCrossReferences,
			Sequence sequence,
			FlagType flag,
			InternalSection internalSection) {
		this(entryType, primaryAccession, secondaryAccessions, uniProtId,
				entryAudit, taxonId, organism, organismHosts, taxonomyLineage,proteinExistence,
				proteinDescription,genes, comments, features, organelles, keywords,
				references, databaseCrossReferences, sequence, flag, internalSection, null );

	}

	@JsonCreator
	public UniProtEntryImpl(@JsonProperty("entryType") UniProtEntryType entryType,
			@JsonProperty("primaryAccession") UniProtAccession primaryAccession,
			@JsonProperty("secondaryAccessions") List<UniProtAccession> secondaryAccessions,
			@JsonProperty("uniProtId") UniProtId uniProtId, @JsonProperty("entryAudit") EntryAudit entryAudit,
			@JsonProperty("taxonId") UniProtTaxonId taxonId, @JsonProperty("organism") OrganismName organism,
			@JsonProperty("organismHosts") List<Organism> organismHosts,
			@JsonProperty("taxonomyLineage") List<OrganismName> taxonomyLineage,
			@JsonProperty("proteinExistence") ProteinExistence proteinExistence,
			@JsonProperty("proteinDescription") ProteinDescription proteinDescription,
			@JsonProperty("genes") List<Gene> genes, @JsonProperty("comments") Comments comments,
			@JsonProperty("features") List<Feature> features, @JsonProperty("organelles") List<Organelle> organelles,
			@JsonProperty("keywords") List<Keyword> keywords, @JsonProperty("references") UniProtReferences references,
			@JsonProperty("databaseCrossReferences") UniProtDBCrossReferences databaseCrossReferences,
			@JsonProperty("sequence") Sequence sequence, @JsonProperty("flag") FlagType flag,
			@JsonProperty("internalSection") InternalSection internalSection,
			@JsonProperty("inactiveReason") EntryInactiveReason inactiveReason) {
		this.entryType = entryType;
		this.primaryAccession = primaryAccession;
		this.secondaryAccessions = Utils.unmodifierList(secondaryAccessions);
		this.uniProtId = uniProtId;
		this.entryAudit = entryAudit;
		this.taxonId = taxonId;
		this.organism = organism;
		this.organismHosts = Utils.unmodifierList(organismHosts);
		this.taxonomyLineage = Utils.unmodifierList(taxonomyLineage);
		this.proteinExistence = proteinExistence;
		this.proteinDescription = proteinDescription;
		this.genes = Utils.unmodifierList(genes);
		if (comments != null)
			this.comments = comments;
		else
			this.comments = new CommentsImpl(null);
		this.features = Utils.unmodifierList(features);
		this.organelles = Utils.unmodifierList(organelles);
		this.keywords = Utils.unmodifierList(keywords);
		if (references != null)
			this.references = references;
		else
			this.references = new UniProtReferencesImpl(null);

		if (databaseCrossReferences != null)
			this.databaseCrossReferences = databaseCrossReferences;
		else
			this.databaseCrossReferences = new UniProtDBCrossReferencesImpl(null);

		this.sequence = sequence;

		this.flag = flag;
		this.internalSection = internalSection;
		this.inactiveReason = inactiveReason;
	}

	@Override
	public UniProtEntryType getEntryType() {
		return entryType;
	}

	@Override
	public UniProtAccession getPrimaryAccession() {
		return primaryAccession;
	}

	@Override
	public List<UniProtAccession> getSecondaryAccessions() {
		return secondaryAccessions;
	}

	@Override
	public UniProtId getUniProtId() {
		return uniProtId;
	}

	@Override
	public EntryAudit getEntryAudit() {
		return entryAudit;
	}

	@Override
	public UniProtTaxonId getTaxonId() {
		return taxonId;
	}

	@Override
	public OrganismName getOrganism() {
		return organism;
	}

	@Override
	public List<Organism> getOrganismHosts() {
		return organismHosts;
	}

	@Override
	public List<OrganismName> getTaxonomyLineage() {
		return taxonomyLineage;
	}

	@Override
	public ProteinExistence getProteinExistence() {
		return proteinExistence;
	}

	@Override
	public ProteinDescription getProteinDescription() {
		return proteinDescription;
	}

	@Override
	public List<Gene> getGenes() {
		return genes;
	}

	@Override
	public Comments getComments() {
		return comments;
	}

	@Override
	public List<Feature> getFeatures() {
		return features;
	}

	@JsonIgnore
	public List<Feature> getFeaturesByType(FeatureType type) {
		return features.stream().filter(feature -> feature.getType() == type).collect(Collectors.toList());
	}

	@Override
	public List<Organelle> getOrganelles() {
		return organelles;
	}

	@Override
	public List<Keyword> getKeywords() {
		return keywords;
	}

	@Override
	public UniProtReferences getReferences() {
		return references;
	}

	@Override
	public UniProtDBCrossReferences getDatabaseCrossReferences() {
		return databaseCrossReferences;
	}

	@Override
	public Sequence getSequence() {
		return sequence;
	}

	@Override
	public FlagType getFlag() {
		return flag;
	}

	@Override
	public InternalSection getInternalSection() {
		return internalSection;
	}

	@JsonIgnore
	@Override
	public Boolean isFragment() {
		return !getFeaturesByType(FeatureType.NON_TER).isEmpty();
	}

	@Override
	public EntryInactiveReason getInactiveReason() {
		return inactiveReason;
	}
	@JsonIgnore
	@Override
	public boolean isActive() {
		return inactiveReason ==null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((databaseCrossReferences == null) ? 0 : databaseCrossReferences.hashCode());
		result = prime * result + ((entryAudit == null) ? 0 : entryAudit.hashCode());
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((features == null) ? 0 : features.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		result = prime * result + ((inactiveReason == null) ? 0 : inactiveReason.hashCode());
		result = prime * result + ((internalSection == null) ? 0 : internalSection.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((organelles == null) ? 0 : organelles.hashCode());
		result = prime * result + ((organism == null) ? 0 : organism.hashCode());
		result = prime * result + ((organismHosts == null) ? 0 : organismHosts.hashCode());
		result = prime * result + ((primaryAccession == null) ? 0 : primaryAccession.hashCode());
		result = prime * result + ((proteinDescription == null) ? 0 : proteinDescription.hashCode());
		result = prime * result + ((proteinExistence == null) ? 0 : proteinExistence.hashCode());
		result = prime * result + ((references == null) ? 0 : references.hashCode());
		result = prime * result + ((secondaryAccessions == null) ? 0 : secondaryAccessions.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((taxonId == null) ? 0 : taxonId.hashCode());
		result = prime * result + ((taxonomyLineage == null) ? 0 : taxonomyLineage.hashCode());
		result = prime * result + ((uniProtId == null) ? 0 : uniProtId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniProtEntryImpl other = (UniProtEntryImpl) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (databaseCrossReferences == null) {
			if (other.databaseCrossReferences != null)
				return false;
		} else if (!databaseCrossReferences.equals(other.databaseCrossReferences))
			return false;
		if (entryAudit == null) {
			if (other.entryAudit != null)
				return false;
		} else if (!entryAudit.equals(other.entryAudit))
			return false;
		if (entryType != other.entryType)
			return false;
		if (features == null) {
			if (other.features != null)
				return false;
		} else if (!features.equals(other.features))
			return false;
		if (flag != other.flag)
			return false;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		if (inactiveReason == null) {
			if (other.inactiveReason != null)
				return false;
		} else if (!inactiveReason.equals(other.inactiveReason))
			return false;
		if (internalSection == null) {
			if (other.internalSection != null)
				return false;
		} else if (!internalSection.equals(other.internalSection))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (organelles == null) {
			if (other.organelles != null)
				return false;
		} else if (!organelles.equals(other.organelles))
			return false;
		if (organism == null) {
			if (other.organism != null)
				return false;
		} else if (!organism.equals(other.organism))
			return false;
		if (organismHosts == null) {
			if (other.organismHosts != null)
				return false;
		} else if (!organismHosts.equals(other.organismHosts))
			return false;
		if (primaryAccession == null) {
			if (other.primaryAccession != null)
				return false;
		} else if (!primaryAccession.equals(other.primaryAccession))
			return false;
		if (proteinDescription == null) {
			if (other.proteinDescription != null)
				return false;
		} else if (!proteinDescription.equals(other.proteinDescription))
			return false;
		if (proteinExistence != other.proteinExistence)
			return false;
		if (references == null) {
			if (other.references != null)
				return false;
		} else if (!references.equals(other.references))
			return false;
		if (secondaryAccessions == null) {
			if (other.secondaryAccessions != null)
				return false;
		} else if (!secondaryAccessions.equals(other.secondaryAccessions))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (taxonId == null) {
			if (other.taxonId != null)
				return false;
		} else if (!taxonId.equals(other.taxonId))
			return false;
		if (taxonomyLineage == null) {
			if (other.taxonomyLineage != null)
				return false;
		} else if (!taxonomyLineage.equals(other.taxonomyLineage))
			return false;
		if (uniProtId == null) {
			if (other.uniProtId != null)
				return false;
		} else if (!uniProtId.equals(other.uniProtId))
			return false;
		return true;
	}

}
