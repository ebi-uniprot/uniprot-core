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
	private String name;
	private int start;
	private int end;
	@Override
	public OverlapRegion build() {
		return new OverlapRegionImpl(name, start, end);
	}

	
	@Override
	public OverlapRegionBuilder from(OverlapRegion instance) {
		return this.name(instance.getName())
		.start(instance.getStart())
		.end(instance.getEnd());
	}

	public OverlapRegionBuilder name(String name) {
		this.name = name;
		return this;
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

