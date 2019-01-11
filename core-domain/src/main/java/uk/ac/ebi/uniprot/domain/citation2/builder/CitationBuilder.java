package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.citation2.Citation;

interface CitationBuilder<B extends CitationBuilder<B, T>, T extends Citation> extends Builder<B, T> {

}
