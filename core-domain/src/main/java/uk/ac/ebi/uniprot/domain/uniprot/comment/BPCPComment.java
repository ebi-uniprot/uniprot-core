package uk.ac.ebi.uniprot.domain.uniprot.comment;

public interface BPCPComment extends Comment {

    Absorption getAbsorption();

    KineticParameters getKineticParameters();

    PhDependence getPhDependence();

    RedoxPotential getRedoxPotential();

    TemperatureDependence getTemperatureDependence();

}
