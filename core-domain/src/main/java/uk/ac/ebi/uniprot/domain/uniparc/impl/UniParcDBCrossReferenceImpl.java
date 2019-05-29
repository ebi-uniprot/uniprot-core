package uk.ac.ebi.uniprot.domain.uniparc.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;

/**
 *
 * @author jluo
 * @date: 22 May 2019
 *
*/

public class UniParcDBCrossReferenceImpl extends DBCrossReferenceImpl<UniParcDatabaseType> implements UniParcDBCrossReference {
	private static final long serialVersionUID = 1387909162449408089L;
	private int versionI;
	private Integer version;
	private boolean active;
	private LocalDate created;
	private LocalDate lastUpdated;

	
    protected UniParcDBCrossReferenceImpl() {
        super(null, "", Collections.emptyList());
    }
	
	public UniParcDBCrossReferenceImpl(UniParcDatabaseType databaseType, String id, List<Property> properties,
			int versionI, Integer version, boolean active, LocalDate created, LocalDate lastUpdated) {
		super(databaseType, id, properties);
		this.versionI= versionI;
		this.version = version;
		this.active = active;
		this.created = created;
		this.lastUpdated = lastUpdated;
	}


	@Override
	public int getVersionI() {
		return versionI;
	}


	@Override
	public Integer getVersion() {
		return version;
	}


	@Override
	public boolean isActive() {
		return active;
	}


	@Override
	public LocalDate getCreated() {
		return created;
	}


	@Override
	public LocalDate getLastUpdated() {
		return lastUpdated;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniParcDBCrossReferenceImpl that = (UniParcDBCrossReferenceImpl) o;
        return  (this.versionI == that.versionI)
        		&& Objects.equals(version, that.version) 
        		&& (this.active == that.active)
        		 && Objects.equals(created, that.created) 
        		 && Objects.equals(lastUpdated, that.lastUpdated) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), versionI, version, active, created, lastUpdated);
    }
}

