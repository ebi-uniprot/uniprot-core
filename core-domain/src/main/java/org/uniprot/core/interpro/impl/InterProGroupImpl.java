package org.uniprot.core.interpro.impl;

import java.util.Objects;

import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProGroup;
import org.uniprot.core.interpro.InterProType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 *
 * @author jluo
 * @date: 12 Apr 2021
 *
 */

public class InterProGroupImpl implements InterProGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 323942306073883213L;
	private Abstract entryAbstract;
	private String name;
	private String shortName;
	private InterProAc interProAc;
	private UniProtKBAccession uniprotAccession;
	private InterProType type;

	InterProGroupImpl() {

	}

	InterProGroupImpl(Abstract entryAbstract, String name, String shortName, InterProAc interProAc,
			UniProtKBAccession uniprotAccession, InterProType type) {
		this.entryAbstract = entryAbstract;
		this.name = name;
		this.shortName = shortName;
		this.interProAc = interProAc;
		this.uniprotAccession = uniprotAccession;
		this.type = type;

	}

	@Override
	public Abstract getEntryAbstract() {
		return this.entryAbstract;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public InterProAc getInterProAc() {
		return interProAc;
	}

	@Override
	public String getShortName() {
		return shortName;
	}

	@Override
	public UniProtKBAccession getUniProtAccession() {
		return uniprotAccession;
	}

	@Override
	public InterProType getType() {
		return type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(
				entryAbstract,
				name,
				interProAc,
				shortName,
				uniprotAccession,
				type);
	}

	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        InterProGroupImpl other = (InterProGroupImpl) obj;
	        return Objects.equals(entryAbstract, other.entryAbstract)
	                && Objects.equals(name, other.name)
	                && Objects.equals(interProAc, other.interProAc)
	                && Objects.equals(shortName, other.shortName)
	                && Objects.equals(uniprotAccession, other.uniprotAccession)
	                && Objects.equals(type, other.type)
	                ;
	}
}
