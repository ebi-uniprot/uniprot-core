package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.SequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.InactiveReasonType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EntryAuditImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EntryInactiveReasonImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceLineImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalLineImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.KeywordImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.OrganelleImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.SourceLineImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtTaxonIdImpl;

public enum UniProtFactory {
    INSTANCE;

    public GeneFactory getGeneFactory() {
        return GeneFactory.INSTANCE;
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
        return new InternalLineImpl(type, value);
    }

    public EvidenceLine createEvidenceLine(String evidence, LocalDate createDate, String curator) {
        return new EvidenceLineImpl( evidence, createDate,  curator ) ;
    }

    
    public SourceLine createSourceLine(String value) {
        return new SourceLineImpl(value);
    }

    public Keyword createKeyword(String id, String value, List<Evidence> evidences) {
        return new KeywordImpl(id, value, evidences);
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
    public Evidence createEvidence(EvidenceCode evidenceCode,
			 String databaseName, String dbId) {
    	return new  EvidenceImpl(evidenceCode, databaseName, dbId);
    }
    
    public Evidence createEvidence(EvidenceCode evidenceCode,
    		DBCrossReference<EvidenceType> source) {
   	return new  EvidenceImpl(evidenceCode, source);
   }
    
    public EntryInactiveReason createInactiveReason(InactiveReasonType inactiveReasonType, List<String> mergeDemergeTo) {
    	return new EntryInactiveReasonImpl(inactiveReasonType, mergeDemergeTo);
    }
    public UniProtEntry createInactiveEntry(UniProtAccession primaryAccession,
			 UniProtId uniProtId,
			 EntryInactiveReason inactiveReason) {
    	return new UniProtEntryImpl(primaryAccession, uniProtId, inactiveReason);
    }
    
    public <T extends DatabaseType> DBCrossReference<T> createDBCrossReference(T databaseType, String id){
    	return createDBCrossReference(databaseType, id, Collections.emptyList());
    }
    
    public <T extends DatabaseType> DBCrossReference<T> createDBCrossReference(T databaseType, String id, List<Property> properties){
    	return new DBCrossReferenceImpl<>(databaseType, id, properties);
    }
}
