package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.impl.PatentImpl;

public final class PatentBuilder extends AbstractCitationBuilder<PatentBuilder, Patent> {
    private String patentNumber;

    public static PatentBuilder newInstance() {
        return new PatentBuilder();
    }

    public Patent build() {
        return new PatentImpl(authoringGroups, authors,
                              xrefs, title, publicationDate, patentNumber);
    }

    public PatentBuilder patentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
        return this;
    }

	@Override
	protected PatentBuilder getThis() {
		return this;
	}

}
