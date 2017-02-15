package uk.ac.ebi.uniprot.domain.uniprot.comments;


public interface BioPhysicoChemicalPropertiesComment extends Comment {

	public Absorption getAbsorption();
	public boolean hasAbsorptionProperty();

	public PHDependence getPHDependence();
	public boolean hasPHDependenceProperty();

	public RedoxPotential getRedoxPotential();
	public boolean hasRedoxPotentialProperty();

	public TemperatureDependence getTemperatureDependence();
	public boolean hasTemperatureDependenceProperty();

	public KineticParameters getKineticParameters();
	public boolean hasKineticParametersProperty();

}
