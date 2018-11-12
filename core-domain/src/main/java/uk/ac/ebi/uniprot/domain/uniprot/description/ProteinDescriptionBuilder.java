package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;

public class ProteinDescriptionBuilder {
	private  ProteinName recommendedName;
	private  List<ProteinName> alternativeNames;
	private  List<ProteinName> submissionNames;
	
	private  Name allergenName;
	private  Name biotechName;
	private  List<Name> cdAntigenNames;
	private  List<Name> innNames;	
	private  List<ProteinSection> includes;  //dmain
	private  List<ProteinSection> contains;  //component
	public static ProteinDescriptionBuilder newInstance() {
		return new ProteinDescriptionBuilder();
	}
	public ProteinDescription  build() {
		return new ProteinDescriptionImpl( recommendedName,
				 alternativeNames, submissionNames,
				  allergenName,
				 biotechName, cdAntigenNames,  innNames,
				includes,  contains);
		
	}
	public ProteinDescriptionBuilder recommendedName(ProteinName recommendedName) {
		this.recommendedName = recommendedName;
		return this;
	}
	public ProteinDescriptionBuilder submissionNames(List<ProteinName> submissionNames) {
		this.submissionNames = submissionNames;
		return this;
	}
	public ProteinDescriptionBuilder alternativeNames(List<ProteinName> alternativeNames) {
		this.alternativeNames = alternativeNames;
		return this;
	}
	
	public ProteinDescriptionBuilder allergenName(Name allergenName) {
		this.allergenName = allergenName;
		return this;
	}
	public ProteinDescriptionBuilder biotechName(Name biotechName) {
		this.biotechName = biotechName;
		return this;
	}
	public ProteinDescriptionBuilder cdAntigenNames(List<Name> cdAntigenNames) {
		this.cdAntigenNames = cdAntigenNames;
		return this;
	}
	public ProteinDescriptionBuilder innNames(List<Name> innNames) {
		this.innNames = innNames;
		return this;
	}
	public ProteinDescriptionBuilder includes(List<ProteinSection> includes) {
		this.includes = includes;
		return this;
	}
	public ProteinDescriptionBuilder contains(List<ProteinSection> contains) {
		this.contains = contains;
		return this;
	}
}
