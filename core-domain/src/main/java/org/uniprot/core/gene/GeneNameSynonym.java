package org.uniprot.core.gene;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

/**
 *
 *
 * <h3>About This Data Structure</h3>
 *
 * <p>Encapsulates the Gene Name Synonyms of a {@link GeneName GeneName} in a {@link
 * uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} object of a {@link
 * uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtKBEntry}.
 *
 * <h3>The Semantics of this Data Structure</h3>
 *
 * <p>This data item is used in cases where there are well established synonyms to a gene name.
 *
 * <h3>An Example in the UniProtKB Flat File</h3>
 *
 * <p>These values can be found in the GN line of the flat file on the marked position.
 *
 * <pre class="example">
 * <font color="#AAAAAA"> DE   Serine proteases 1/2 precursor (EC 3.4.21.-) (Protein Jonah
 * DE   99Cii/99Ciii).
 * GN   Name=Jon99Cii; Synonyms=<font color="#000000">SER1</font>, <font color="#000000">SER5</font>, <font color="#000000">Ser99Da</font>; ORFNames=CG7877;
 * GN   and
 * GN   Name=Jon99Ciii; Synonyms=<font color="#000000">SER2</font>, <font color="#000000">SER5</font>, <font color="#000000">Ser99Db</font>; ORFNames=CG15519;
 * OS   Drosophila melanogaster (Fruit fly).
 * ...</font></pre>
 *
 * <h3>How to work with this Interface</h3>
 *
 * <p>The standard way of retrieving this data type <div class="codeexample"> {@link
 * uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtKBEntry} entry =
 * getEntryFromParserOrAPI(); List<{@link
 * uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene}> genes = entry.getGenes(); for
 * ({@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene : genes) { for
 * (GeneNameSynonym synonym : gene.getGeneNameSynonyms()) { System.out.println(synonym); } }</div>
 *
 * <p>The standard way of setting this data type <div class="codeexample"> {@link
 * uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtKBEntry} uniProtEntry = {@link
 * uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory
 * DefaultUniProtFactory}.getInstance().buildEntry(); {@link
 * uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene = {@link
 * uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory
 * DefaultUniProtFactory}.getInstance().buildGene(); {@link GeneNameSynonym GeneNameSynonym} syn =
 * {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory
 * DefaultUniProtFactory}.getInstance().buildGeneNameSynonym("Synonym");
 * gene.getGeneNameSynonyms().add(syn); uniProtEntry.getGenes().add(gene);</div>
 */
public interface GeneNameSynonym extends EvidencedValue {}
