package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EntryAuditImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceLineImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.KeywordImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.OrganelleImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.SequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtTaxonIdImpl;

import java.time.LocalDate;
import java.util.List;

public enum UniProtFactory {
    INSTANCE;

    public GeneFactory getGeneFactory() {
        return GeneFactory.INSTANCE;
    }

    public OrganismFactory getOrganismFactory() {
        return OrganismFactory.INSTANCE;
    }

    public ProteinDescriptionFactory getProteinDescriptionFactory() {
        return ProteinDescriptionFactory.INSTANCE;
    }

    public TaxonomyFactory getTaxonomyFactory() {
        return TaxonomyFactory.INSTANCE;
    }

    public UniProtDBCrossReferenceFactory getUniProtDBCrossReferenceFactory() {
        return UniProtDBCrossReferenceFactory.INSTANCE;
    }

    public CommentFactory getCommentFactory() {
        return CommentFactory.INSTANCE;
    }

    public FeatureFactory getFeatureFactory() {
        return FeatureFactory.INSTANCE;
    }

    public UniProtReferenceFactory getUniProtReferenceFactory() {
        return UniProtReferenceFactory.INSTANCE;
    }

    public EvidencedValue createEvidencedValue(String value, List<Evidence> evidences) {
        return new EvidencedValueImpl(value, evidences);
    }

    public UniProtAccession createUniProtAccession(String value) {
        return new UniProtAccessionImpl(value);
    }

    public UniProtId createUniProtId(String value) {
        return new UniProtIdImpl(value);
    }

    public Organelle createOrganelle(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences) {
        return new OrganelleImpl(geneEncodingType, value, evidences);
    }

    public InternalSection createInternalSection(List<InternalLine> internalLines, 
    		List<EvidenceLine> evidenceLines,
    		List<SourceLine> sourceLines) {
        return new InternalSectionImpl(internalLines, evidenceLines, sourceLines);
    }

    public InternalLine createInternalLine(InternalLineType type, String value) {
        return InternalSectionImpl.createInternalLine(type, value);
    }

    public EvidenceLine createEvidenceLine(Evidence evidence, LocalDate createDate, String curator) {
        return new EvidenceLineImpl( evidence, createDate,  curator ) ;
    }

    
    public SourceLine createSourceLine(String value) {
        return InternalSectionImpl.createSourceLine(value);
    }

    public Keyword createKeyword(String value, List<Evidence> evidences) {
        return new KeywordImpl(value, evidences);
    }

    public EntryAudit createEntryAudit(LocalDate firstPublicDate, LocalDate lastAnnotationUpdateDate,
            LocalDate lastSequenceUpdateDate, int entryVersion, int sequenceVersion) {
        return new EntryAuditImpl(firstPublicDate, lastAnnotationUpdateDate,
                lastSequenceUpdateDate, entryVersion, sequenceVersion);
    }

    public Sequence createSequence(String seq) {
        return new SequenceImpl(seq);
    }

    public UniProtTaxonId createUniProtTaxonId(long taxId, List<Evidence> evidences) {
        return new UniProtTaxonIdImpl(taxId, evidences);
    }
    
    public Evidence createEvidence(String val) {
    	return EvidenceImpl.parseEvidenceLine(val);
    }

	public Flag createFlag(FlagType type) {
		return new FlagImpl(type);
	}

}
