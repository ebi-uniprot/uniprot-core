package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Location;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public class SequenceFeatureImpl implements SequenceFeature {

    /** */
    private static final long serialVersionUID = -8511912268843073779L;

    private InterProGroup interproGroup;
    private SignatureDbType database;
    private String databaseId;
    private List<Location> locations;

    SequenceFeatureImpl() {
        this.locations = Collections.emptyList();
    }

    SequenceFeatureImpl(
            InterProGroup domain, SignatureDbType dbType, String dbId, List<Location> locations) {
        super();
        this.interproGroup = domain;
        this.database = dbType;
        this.databaseId = dbId;
        this.locations = Utils.unmodifiableList(locations);
    }

    @Override
    public InterProGroup getInterProDomain() {
        return interproGroup;
    }

    @Override
    public SignatureDbType getSignatureDbType() {
        return database;
    }

    @Override
    public String getSignatureDbId() {
        return databaseId;
    }

    @Override
    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interproGroup, database, databaseId, locations);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SequenceFeatureImpl other = (SequenceFeatureImpl) obj;
        return Objects.equals(interproGroup, other.interproGroup)
                && Objects.equals(database, other.database)
                && Objects.equals(databaseId, other.databaseId)
                && Objects.equals(locations, other.locations);
    }
}
