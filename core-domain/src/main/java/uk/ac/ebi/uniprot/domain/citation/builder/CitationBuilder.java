package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.citation.Citation;

public interface CitationBuilder<B extends CitationBuilder<B, T>, T extends Citation> extends Builder2<B, T> {

}
