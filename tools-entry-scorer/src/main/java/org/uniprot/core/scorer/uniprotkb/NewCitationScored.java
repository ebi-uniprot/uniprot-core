package org.uniprot.core.scorer.uniprotkb;

import java.util.List;
import java.util.regex.Pattern;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 02-Mar-2010 Time: 16:14:37 To change this template
 * use File | Settings | File Templates.
 */
public class NewCitationScored implements HasScore {
    @SuppressWarnings("unused")
    private final Citation citation;

    @SuppressWarnings("unused")
    private final List<EvidenceDatabase> evidenceDatabases;

    /** GENOME REANNOTATION ERRATUM RETRACTION SEQUENCE REVISION GENE NAME NOMENCLATURE */
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

    public NewCitationScored(Citation citation, List<EvidenceDatabase> evidenceDatabases) {
        this.citation = citation;
        this.evidenceDatabases = evidenceDatabases;
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
