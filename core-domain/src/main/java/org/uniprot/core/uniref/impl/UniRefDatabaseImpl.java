package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.Objects;

import org.uniprot.core.uniref.UniRefDatabase;
import org.uniprot.core.uniref.UniRefDatabaseType;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class UniRefDatabaseImpl implements UniRefDatabase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 45045013241666780L;
	private UniRefDatabaseType type;
	private String version;
	private LocalDate releaseDate;
	
	protected UniRefDatabaseImpl() {
		
	}
	public UniRefDatabaseImpl(UniRefDatabaseType type, String version, LocalDate date) {
		this.type =type;
		this.version = version;
		this.releaseDate =date;
	}
	@Override
	public UniRefDatabaseType getType() {
		return type;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	@Override
	public int hashCode() {
		return Objects.hash(type, version, releaseDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniRefDatabaseImpl other = (UniRefDatabaseImpl) obj;
		return Objects.equals(type, other.type)
				&& Objects.equals(version, other.version)
				&& Objects.equals(releaseDate, other.releaseDate)
				;

	}
}

