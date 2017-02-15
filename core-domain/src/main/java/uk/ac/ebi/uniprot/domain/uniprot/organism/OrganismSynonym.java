package uk.ac.ebi.uniprot.domain.uniprot.organism;


/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the Synonym Name
 * of an {@link uk.ac.ebi.kraken.interfaces.uniprot.Organism Organism} in
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
  * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * The synonym under which an Organism is known.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * This value can be found in the OS line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
 * OS   Solanum melongena (Eggplant) (<font color="#000000">Aubergine</font>).
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
 *     System.out.println(organism.getSynonym());
 * }</div>
 * </p>
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} uniProtEntry  = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildEntry();
 * {@link uk.ac.ebi.kraken.interfaces.uniprot.Organism Organism} organism          = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildOrganism();
 * OrganismSynonym oss        = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildOrganismSynonym("Synonym");
 * organism.setSynonym(oss);
 * entry.getOrganisms().add(organism);</div>
 */

public interface OrganismSynonym extends OrganismName{
}
