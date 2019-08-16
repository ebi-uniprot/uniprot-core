package org.uniprot.core.uniref.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.impl.OverlapRegionImpl;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class OverlapRegionBuilder implements Builder<OverlapRegionBuilder, OverlapRegion> {
	private int start;
	private int end;
	@Override
	public OverlapRegion build() {
		return new OverlapRegionImpl( start, end);
	}

	
	@Override
	public OverlapRegionBuilder from(OverlapRegion instance) {
		return start(instance.getStart())
		.end(instance.getEnd());
	}


	public OverlapRegionBuilder start(int start) {
		this.start = start;
		return this;
	}
	public OverlapRegionBuilder end(int end) {
		this.end = end;
		return this;
	}
}

