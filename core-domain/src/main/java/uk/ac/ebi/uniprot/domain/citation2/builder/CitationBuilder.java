package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.citation2.Citation;

interface CitationBuilder<B extends CitationBuilder<B, T>, T extends Citation> extends Builder2<B, T> {

}
