package org.uniprot.core.uniref.impl;

import java.util.Objects;

import org.uniprot.core.uniref.OverlapRegion;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class OverlapRegionImpl implements OverlapRegion {
	private static final long serialVersionUID = 6494342913253051799L;

	private int start;
	private int end;

	
	protected OverlapRegionImpl() {
		
	}

	public OverlapRegionImpl ( int start, int end) {
		this.start =start;
		this.end =end;
				
	}
	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	@Override
	public int hashCode() {
		return Objects.hash( start, end);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OverlapRegionImpl other = (OverlapRegionImpl) obj;
		return
				 (start == other.start)
				&&  (end == other.end)
				;

	}
}

