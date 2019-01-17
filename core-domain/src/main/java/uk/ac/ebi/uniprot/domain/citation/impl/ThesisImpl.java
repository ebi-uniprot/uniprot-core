package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;

public class ThesisImpl extends AbstractCitationImpl implements Thesis {
    private String institute;
    private String address;

    private ThesisImpl() {
        super(CitationType.THESIS, Collections.emptyList(), Collections.emptyList(),
              null, null, null);
        this.institute = "";
        this.address = "";
    }

    public ThesisImpl(ThesisBuilder builder) {
        super(CitationType.THESIS, builder.getAuthoringGroups(), builder.getAuthors(), builder.getXrefs(),
              builder.getTitle(), builder.getPublicationDate());
        this.institute = Utils.resetNull(builder.getInstitute());
        this.address = Utils.resetNull(builder.getAddress());
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
