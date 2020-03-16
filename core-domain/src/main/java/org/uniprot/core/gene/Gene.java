package org.uniprot.core.gene;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * <h3>About This Data Structure</h3>
 *
 * <p>Encapsulates the Genes of a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry
 * UniProtkbEntry}.
 *
 * <h3>The Semantics of this Data Structure</h3>
 *
 * <p>The GN (Gene Name) line indicates the name(s) of the gene(s) that code for the stored protein
 * sequence.
 *
 * <h3>An Example in the UniProtKB Flat File</h3>
 *
 * <p>These values can be found in the GN line of the flat file on the marked position.
 *
 * <pre class="example">
 * <font color="#AAAAAA"> DE   Serine proteases 1/2 precursor (EC 3.4.21.-) (Protein Jonah
 * DE   99Cii/99Ciii).
 * GN   <font color="#000000">Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;</font>
 * GN   <font color="#000000">and</font>
 * GN   <font color="#000000">Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;</font>
 * OS   Drosophila melanogaster (Fruit fly).
 * ...</font></pre>
 *
 * <h3>How to work with this Interface</h3>
 *
 * <p>The standard way of retrieving this data type <div class="codeexample"> {@link
 * uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtkbEntry} entry =
 * getEntryFromParserOrAPI(); List<{@link
 * uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene}> genes = entry.getGenes(); for
 * ({@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene : genes) { for
 * (ORFName orfName : gene.getORFNames()) { System.out.println(orfName); } }</div>
 *
 * <p>The standard way of setting this data type <div class="codeexample"> {@link
 * uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtkbEntry} uniProtEntry = {@link
 * uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory
 * DefaultUniProtFactory}.getInstance().buildEntry(); {@link
 * uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene = {@link
 * uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory
 * DefaultUniProtFactory}.getInstance().buildGene(); uniProtEntry.getGenes().add(gene);</div>
 */
public interface Gene extends Serializable {

    /**
     * Indicates whether this Gene has a Gene name. Use this method before operating {@link
     * #getGeneName() getGeneName()}. This value can be found in the GN line of the flat file on the
     * marked position.
     *
     * <pre class="example">
     * <font color="#AAAAAA"> DE   Serine proteases 1/2 precursor (EC 3.4.21.-) (Protein Jonah
     * DE   99Cii/99Ciii).
     * GN   Name=<font color="#000000">Jon99Cii</font>; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
     * GN   and
     * GN   Name=<font color="#000000">Jon99Ciii</font>; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;
     * OS   Drosophila melanogaster (Fruit fly).
     * ...</font></pre>
     *
     * @return true, if this Gene has a Gene Name, false, if it has not.
     */
    boolean hasGeneName();

    /**
     * Returns the Gene Name of this Gene. Use {@link #hasGeneName() hasGeneName()} before operating
     * this method. This value can be found in the GN line of the flat file on the marked position.
     *
     * <pre class="example">
     * <font color="#AAAAAA"> DE   Serine proteases 1/2 precursor (EC 3.4.21.-) (Protein Jonah
     * DE   99Cii/99Ciii).
     * GN   Name=<font color="#000000">Jon99Cii</font>; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
     * GN   and
     * GN   Name=<font color="#000000">Jon99Ciii</font>; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;
     * OS   Drosophila melanogaster (Fruit fly).
     * ...</font></pre>
     *
     * @return The Gene Name of this Gene.
     */
    GeneName getGeneName();

    /**
     * Returns the List of Gene Name Synonyms of this Gene. These values can be found in the GN line
     * of the flat file on the marked position.
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
     * @return The Gene Name Synonyms of this Gene.
     */
    List<GeneNameSynonym> getSynonyms();

    /**
     * Returns the List of Ordered Locus Names of this Gene. These values can be found in the GN
     * line of the flat file on the marked position.
     *
     * <pre class="example">
     * <font color="#AAAAAA"> DE   Putative 3-methyladenine DNA glycosylase (EC 3.2.2.-).
     * GN   OrderedLocusNames=<font color="#000000">CPn_0505</font>, <font color="#000000">CP_0248</font>, <font color="#000000">CPj0505</font>, <font color="#000000">CpB0526</font>;
     * OS   Chlamydia pneumoniae (Chlamydophila pneumoniae).
     * ...</font></pre>
     *
     * @return The Ordered Locus Names of this Gene.
     */
    List<OrderedLocusName> getOrderedLocusNames();

    /**
     * Returns the List of ORF Names of this Gene. These values can be found in the GN line of the
     * flat file on the marked position.
     *
     * <pre class="example">
     * <font color="#AAAAAA"> DE   1-aminocyclopropane-1-carboxylate synthase 5 (EC 4.4.1.14) (ACC
     * DE   synthase 5) (S-adenosyl-L-methionine methylthioadenosine-lyase 5)
     * DE   (Ethylene-overproduction protein 2).
     * GN   Name=ACS5; Synonyms=ACC5, ETO2; OrderedLocusNames=At5g65800;
     * GN   ORFNames=<font color="#000000">MPA24.15</font>, <font color="#000000">F6H11.90</font>;
     * OS   Arabidopsis thaliana (Mouse-ear cress).
     * ...</font></pre>
     *
     * @return The ORF Names of this Gene.
     */
    List<ORFName> getOrfNames();
}
