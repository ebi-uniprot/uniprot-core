package org.uniprot.core.interpro.impl;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProEntry;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 12 Apr 2021
 *
*/

public class InterProEntryImpl implements InterProEntry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616209578735589523L;
	private InterProAc interProAc;
	private boolean checked;
	private String name;
	private String shortName;
	private Set<UniProtKBAccession> swissProtAccessions;
	private Set<UniProtKBAccession> tremblAccessions;
	private Abstract entryAbstract;
	
	InterProEntryImpl(){
		swissProtAccessions = Collections.emptySet();
		tremblAccessions = Collections.emptySet();
	}

	InterProEntryImpl( InterProAc interProAc,
	 boolean checked,
	 String name,
	 String shortName,
	 Set<UniProtKBAccession> swissProtAccessions,
	 Set<UniProtKBAccession> tremblAccessions,
	 Abstract entryAbstract){
		this.interProAc = interProAc;
		this.checked = checked;
		this.name = name;
		this.shortName =shortName;
		this.swissProtAccessions = Utils.modifiableSet(swissProtAccessions);
		this.tremblAccessions =  Utils.modifiableSet(tremblAccessions);
		this.entryAbstract = entryAbstract;
	}
	
	@Override
	public InterProAc getInterProAc() {
		return this.interProAc;
	}

	@Override
	public boolean isChecked() {
		return this.checked;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getShortName() {
		return this.shortName;
	}

	@Override
	public Set<UniProtKBAccession> getSwissProtAccessions() {
		return this.swissProtAccessions;
	}

	@Override
	public Set<UniProtKBAccession> getTremblAccessions() {
		return this.tremblAccessions;
	}

	@Override
	public Abstract getEntryAbstract() {
		return this.entryAbstract;
	}
	@Override
	public int hashCode() {
		return Objects.hash(
				interProAc,
				checked,
				name,
				shortName,
				swissProtAccessions,
				tremblAccessions,
				entryAbstract);
	}

	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        InterProEntryImpl other = (InterProEntryImpl) obj;
	        return Objects.equals(interProAc, other.interProAc)
	                && Objects.equals(checked, other.checked)
	                && Objects.equals(name, other.name)
	                && Objects.equals(shortName, other.shortName)
	                && Objects.equals(swissProtAccessions, other.swissProtAccessions)
	                && Objects.equals(tremblAccessions, other.tremblAccessions)
	                && Objects.equals(entryAbstract, other.entryAbstract)
	                ;
	}
}

