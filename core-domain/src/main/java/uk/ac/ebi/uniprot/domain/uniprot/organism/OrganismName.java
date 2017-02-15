package uk.ac.ebi.uniprot.domain.uniprot.organism;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the Names
 * of an {@link uk.ac.ebi.kraken.interfaces.uniprot.Organism Organism} in
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * Organisms are known under different names. These names have different types
 * (eg. {@link OrganismScientificName OrganismScientificName}, {@link OrganismCommonName OrganismCommonName}, {@link OrganismSynonym OrganismSynonym}), which all
 * implement this interface as they are special Organism Names.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * These values can be found in the OS line of the flat file
 * on the marked position.
 * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
 * OS   <font color="#000000">Solanum melongena</font> (<font color="#000000">Eggplant</font>) (<font color="#000000">Aubergine</font>).
 * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
 * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
 * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
 * ...</font></pre>
 */

public interface OrganismName extends Value {
}
