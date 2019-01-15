package uk.ac.ebi.uniprot.domain.uniprot.comment2;

public interface BPCPComment extends Comment {

    Absorption getAbsorption();

    KineticParameters getKineticParameters();

    PhDependence getPhDependence();

    RedoxPotential getRedoxPotential();

    TemperatureDependence getTemperatureDependence();

}
