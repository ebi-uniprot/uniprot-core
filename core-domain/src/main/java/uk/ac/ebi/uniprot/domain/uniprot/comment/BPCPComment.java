package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.Optional;

public interface BPCPComment extends Comment {

	 Optional<Absorption> getAbsorption();
	
	 Optional<KineticParameters> getKineticParameters();

	 Optional<PHDependence> getPHDependence();

	 Optional<RedoxPotential> getRedoxPotential();

	 Optional<TemperatureDependence> getTemperatureDependence();
	 
	 default boolean isValid() {
		 return getAbsorption().isPresent()
				 || getKineticParameters().isPresent()
				 || getPHDependence().isPresent()
				 || getRedoxPotential().isPresent()
				 || getTemperatureDependence().isPresent();
	 }



}
