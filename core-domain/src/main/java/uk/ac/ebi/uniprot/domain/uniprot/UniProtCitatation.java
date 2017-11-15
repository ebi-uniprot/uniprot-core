package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;

import java.util.List;

public interface UniProtCitatation extends Citation, HasEvidences {

    public List<SampleSource> getSampleSources();

    public List<String> getCitationSummary();
}
