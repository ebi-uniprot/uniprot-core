package org.uniprot.core.uniprotkb.comment;

public interface BPCPComment extends Comment, HasMolecule {

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
