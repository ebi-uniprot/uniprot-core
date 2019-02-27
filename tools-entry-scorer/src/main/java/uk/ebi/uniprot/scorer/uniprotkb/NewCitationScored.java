package uk.ebi.uniprot.scorer.uniprotkb;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 02-Mar-2010 Time: 16:14:37 To change this template use File | Settings
 * | File Templates.
 */
public class NewCitationScored implements HasScore {
    @SuppressWarnings("unused")
    private final Citation citation;
    @SuppressWarnings("unused")
    private final List<EvidenceType> evidenceTypes;

    /**
     * GENOME REANNOTATION ERRATUM RETRACTION SEQUENCE REVISION GENE NAME NOMENCLATURE
     */
    Pattern largeScaleRegEx = Pattern.compile("\\[LARGE SCALE\\b");
    Pattern largeScaleAnalysisRegEx = Pattern.compile("\\[LARGE SCALE ANALYSIS\\]");
    Pattern sequenceOnlyRegEx = Pattern.compile("\\bSEQUENCE\\b");
    Pattern genomeRegEx = Pattern.compile("\\bGENOME REANNOTATION\\b");
    Pattern erratumRegEx = Pattern.compile("\\bERRATUM\\b");
    Pattern retractionRegEx = Pattern.compile("\\bRETRACTION\\b");
    Pattern sequenceRevisionRegEx = Pattern.compile("\\bSEQUENCE REVISION\\b");
    Pattern geneRegEx = Pattern.compile("\\bGENE NAME\\b");
    Pattern nomenclatureRegEx = Pattern.compile("\\bNOMENCLATURE\\b");
    Pattern submissionRegEx = Pattern.compile("^EMBL[/]GenBank[/]DDBJ databases$");

    public NewCitationScored(Citation citation, List<EvidenceType> evidenceTypes) {
        this.citation = citation;
        this.evidenceTypes = evidenceTypes;
    }

    public NewCitationScored(Citation citation) {
        this(citation, null);
    }

    @Override
    public double score() {
        return 0.0d;
    }

    @Override
    public Consensus consensus() {
        return Consensus.COMPLEX;
    }
}
