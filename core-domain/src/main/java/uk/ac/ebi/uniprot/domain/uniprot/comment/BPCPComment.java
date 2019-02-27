package uk.ac.ebi.uniprot.domain.uniprot.comment;

public interface BPCPComment extends Comment {

    Absorption getAbsorption();

    KineticParameters getKineticParameters();

    PhDependence getPhDependence();

    RedoxPotential getRedoxPotential();

    TemperatureDependence getTemperatureDependence();

    boolean hasAbsorption();

    boolean hasKineticParameters();

    boolean hasPhDependence();

    boolean hasRedoxPotential();

    boolean hasTemperatureDependence();

}
