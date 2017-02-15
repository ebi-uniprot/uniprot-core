package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.organism.OrganismScientificName;
import uk.ac.ebi.uniprot.domain.uniprot.organism.OrganismSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.organism.OrganismCommonName;
import uk.ac.ebi.uniprot.domain.uniprot.organism.OrganismName;

import java.util.List;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the Organism in
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * The OS (Organism Species) line specifies the organism which was the source of the stored sequence.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * This value can be found in the OS line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
 * OS   <font color="#000000">Solanum melongena (Eggplant) (Aubergine).</font>
 * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
 * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
 * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
 * ...</font></pre>
 * <h3>How to work with this Interface</h3>
 * <p>
 * The standard way of retrieving this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} entry = getEntryFromParserOrAPI();
 * List<Organism> organisms = entry.getOrganisms();
 * for (Organism organism : organisms) {
 *     System.out.println(organism.getScientificName());
 * }</div>
 * </p>
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} uniProtEntry = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildEntry();
 * Organism organism = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildOrganism();
 * entry.getOrganisms().add(organism);</div>
 * </p>
 */

public interface Organism   {


    /**
     * Returns the scientific name of this Organism.
     * <br><br>
     * This value can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   <font color="#000000">Solanum melongena</font> (Eggplant) (Aubergine).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return The scientific name of this Organism Host.
     */
    public OrganismScientificName getScientificName();


    /**
     * Returns the common name of this Organism. Use {@link #hasCommonName() hasCommonName()}
     * before using this method.
     * <br><br>
     * This value can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   Solanum melongena (<font color="#000000">Eggplant</font>) (Aubergine).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return The common name of this Organism Host.
     */
    public OrganismCommonName getCommonName();

    /**
     * Indicates whether this Organism has a common name. This method
     * should be used before operating {@link #getCommonName() getCommonName()}.
     * <br><br>
     * This common name can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   Solanum melongena (<font color="#000000">Eggplant</font>) (Aubergine).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return The common name of this Organism Host.
     */
    public boolean hasCommonName();


    /**
     * Returns the synonym name of this Organism. Use {@link #hasSynonym() hasSynonym()}
     * before using this method.
     * <br><br>
     * This value can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   Solanum melongena (Eggplant) (<font color="#000000">Aubergine</font>).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return The synonym name of this Organism Host.
     */
    public OrganismSynonym getSynonym();

    /**
     * Indicates whether this Organism has a synonym name. This method
     * should be used before operating {@link #getSynonym()  getSynonym()}.
     * <br><br>
     * This common name can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   Solanum melongena (Eggplant) (<font color="#000000">Aubergine</font>).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return The synonym name of this Organism Host.
     */
    public boolean hasSynonym();

    /**
     * Returns the list of all OrganismNames of this Organism.
     * <br><br>
     * These values can be found in the OS line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   <font color="#000000">Solanum melongena</font> (<font color="#000000">Eggplant</font>) (<font color="#000000">Aubergine</font>).
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font></pre>
     * @return scientificName The list of OrganismNames.
     */
    public List<OrganismName> getOrganismNames();

}
