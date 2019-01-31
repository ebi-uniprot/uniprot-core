package uk.ac.ebi.uniprot.domain.gene;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the Gene Name of a
 * {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} object of
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * This data item gives the standard name of the gene, where the protein is encoded.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * These values can be found in the GN line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> DE   Serine proteases 1/2 precursor (EC 3.4.21.-) (Protein Jonah
 * DE   99Cii/99Ciii).
 * GN   Name=<font color="#000000">Jon99Cii</font>; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
 * GN   and
 * GN   Name=<font color="#000000">Jon99Ciii</font>; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;
 * OS   Drosophila melanogaster (Fruit fly).
 * ...</font></pre>
 * <h3>How to work with this Interface</h3>
 * <p>
 * The standard way of retrieving this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} entry = getEntryFromParserOrAPI();
 * List<{@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene}> genes = entry.getGenes();
 * for ({@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene : genes) {
 * if (gene.hasGeneName())
 * System.out.println(gene.getGeneName());
 * }
 * }</div>
 * </p>
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} uniProtEntry = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildEntry();
 * {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene                 = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildGene();
 * {@link GeneName GeneName} gen              = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildGeneName("GeneName");
 * gene.setGeneName().add(gen);
 * uniProtEntry.getGenes().add(gene);</div>
 * </p>
 */
public interface GeneName extends EvidencedValue {

}

