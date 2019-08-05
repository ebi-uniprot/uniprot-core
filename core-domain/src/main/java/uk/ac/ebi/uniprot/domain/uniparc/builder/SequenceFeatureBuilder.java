package uk.ac.ebi.uniprot.domain.uniparc.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.common.Utils;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.Location;
import uk.ac.ebi.uniprot.domain.uniparc.InterproGroup;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.SignatureDbType;
import uk.ac.ebi.uniprot.domain.uniparc.impl.SequenceFeatureImpl;

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
		Utils.nonNullAdd(location, locations);
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

