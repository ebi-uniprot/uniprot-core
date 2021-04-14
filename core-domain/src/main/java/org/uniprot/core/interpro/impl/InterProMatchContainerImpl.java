package org.uniprot.core.interpro.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.InterProMatchContainer;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 14 Apr 2021
 *
*/

public class InterProMatchContainerImpl implements InterProMatchContainer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6910225095020545153L;
	private long id;
    private UniProtKBAccession uniProtAccession;
    private List<InterProMatch> interProMatches;
    
    InterProMatchContainerImpl(){
    	interProMatches = Collections.emptyList();
	}
    InterProMatchContainerImpl(long id, UniProtKBAccession uniProtAccession,
    		List<InterProMatch> interProMatches
    		){
    	this.id = id;
    	this.uniProtAccession = uniProtAccession;
    	this.interProMatches = Utils.unmodifiableList(interProMatches);
	}
	@Override
	public long getId() {
		return id;
	}

	@Override
	public UniProtKBAccession getUniProtAccession() {
		return uniProtAccession;
	}

	@Override
	public List<InterProMatch> getInterProMatches() {
		return interProMatches;
	}
	@Override
	public int hashCode() {
		return Objects.hash(
				id,
				uniProtAccession,
				interProMatches);
	}

	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        InterProMatchContainerImpl other = (InterProMatchContainerImpl) obj;
	        return Objects.equals(id, other.id)
	                && Objects.equals(uniProtAccession, other.uniProtAccession)
	                && Objects.equals(interProMatches, other.interProMatches)
	                ;
	}
}

