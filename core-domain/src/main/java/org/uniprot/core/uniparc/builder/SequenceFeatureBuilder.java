package org.uniprot.core.uniparc.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.Location;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.impl.SequenceFeatureImpl;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

public class SequenceFeatureBuilder implements Builder<SequenceFeatureBuilder, SequenceFeature> {
	private InterproGroup interproGroup;
	private SignatureDbType dbType;
	private String dbId;
	private List<Location> locations =new ArrayList<>();
	@Override
	public SequenceFeature build() {
		return new SequenceFeatureImpl( interproGroup,  dbType,  dbId, locations) ;
	}
	public SequenceFeatureBuilder interproGroup(InterproGroup interproGroup) {
		this.interproGroup = interproGroup;
		return this;
	}
	
	public SequenceFeatureBuilder signatureDbType(SignatureDbType dbType) {
		this.dbType = dbType;
		return this;
	}
	public SequenceFeatureBuilder signatureDbId(String dbId) {
		this.dbId = dbId;
		return this;
	}
	public SequenceFeatureBuilder locations(List<Location> locations) {
		this.locations = Utils.nonNullList(locations);
		return this;
	}
	public SequenceFeatureBuilder addLocation(Location location) {
		Utils.addOrIgnoreNull(location, locations);
		return this;
	}
	

	@Override
	public SequenceFeatureBuilder from(SequenceFeature instance) {
		this.interproGroup = instance.getInterProDomain();
		this.dbType = instance.getSignatureDbType();
		this.dbId = instance.getSignatureDbId();
		this.locations.clear();
		this.locations.addAll(instance.getLocations());
		return this;
	}

}

