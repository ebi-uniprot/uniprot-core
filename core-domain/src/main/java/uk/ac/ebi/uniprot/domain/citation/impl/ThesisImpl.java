package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.List;

import static java.util.Collections.emptyList;

public class ThesisImpl extends AbstractCitationImpl implements Thesis {
    private String institute;
    private String address;

    private ThesisImpl() {
        this(emptyList(), emptyList(), emptyList(), null, null, null, null);
    }

    public ThesisImpl(List<String> authoringGroup, List<Author> authors, List<DBCrossReference<CitationXrefType>> citationXrefs,
                      String title, PublicationDate publicationDate, String institute, String address) {
        super(CitationType.THESIS, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.institute = Utils.nullToEmpty(institute);
        this.address = Utils.nullToEmpty(address);
    }

    @Override
    public String getInstitute() {
        return institute;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((institute == null) ? 0 : institute.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ThesisImpl other = (ThesisImpl) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (institute == null) {
            if (other.institute != null)
                return false;
        } else if (!institute.equals(other.institute))
            return false;
        return true;
    }


}
