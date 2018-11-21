package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.impl.ThesisImpl;

public final class ThesisBuilder extends AbstractCitationBuilder<Thesis> {

    public static ThesisBuilder newInstance() {
        return new ThesisBuilder();
    }
    
    private String institute;
    private String address;
    
    public Thesis build() {
        return new ThesisImpl(authoringGroups, authors,
                xrefs, title, publicationDate, institute, address);
    }

    public ThesisBuilder institute(String institute) {
        this.institute = institute;
        return this;
    }
    
    public ThesisBuilder address(String address) {
        this.address = address;
        return this;
    }
  
}
