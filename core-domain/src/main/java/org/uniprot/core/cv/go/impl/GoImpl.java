package org.uniprot.core.cv.go.impl;

import java.util.Objects;

import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;

/**
 *
 * @author jluo
 * @date: 8 Apr 2021
 *
*/

public class GoImpl extends GoTermImpl implements Go {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211498340646395976L;
	private final GoAspect aspect;
	private final GoEvidenceType goEvidenceType;
	private final String goEvidenceSource;
	
    GoImpl() {
        this(null, null, null, null, null);
    }
	
	 GoImpl(String goId, String name, GoAspect aspect, GoEvidenceType goEvidenceType, String goEvidenceSource) {
		super(goId, name);
		this.aspect = aspect;
		this.goEvidenceType =goEvidenceType;
		this.goEvidenceSource = goEvidenceSource;
		
	}

	@Override
	public GoAspect getAspect() {
		return aspect;
	}

	@Override
	public GoEvidenceType getGoEvidenceType() {
		return goEvidenceType;
	}

	@Override
	public String getGoEvidenceSource() {
		return goEvidenceSource;
	}

	@Override
	public int hashCode() {
		 return Objects.hash(
	                super.hashCode(), aspect, goEvidenceType, goEvidenceSource);
	}

	@Override
	public boolean equals(Object o) {	
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GoImpl that = (GoImpl) o;

        return Objects.equals(getAspect(), that.getAspect())
                && Objects.equals(getGoEvidenceType(), that.getGoEvidenceType())
                && Objects.equals(getGoEvidenceSource(), that.getGoEvidenceSource());       
	}

}

