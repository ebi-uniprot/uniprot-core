package uk.ac.ebi.uniprot.domain.gene;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the Ordered Locus Names of a {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} object of
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * Ordered locus names (a.k.a. OLN, ORF numbers, CDS numbers or Gene numbers).
 * A name used to represent an ORF in a completely sequenced genome or chromosome.
 * It is generally based on a prefix representing the organism and a number which
 * usually represents the sequential ordering of genes on the chromosome.
 * Depending on the genome sequencing center, numbers are only attributed to
 * protein-coding genes, or also to pseudogenes, or also to tRNAs and other
 * features. If two predicted genes have been merged to form a new gene, both
 * gene identifiers are indicated, separated by a slash (see last example).
 * Examples: HI0934, Rv3245c, At5g34500, YER456W, YAR042W/YAR044W.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * These values can be found in the GN line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> DE   Putative 3-methyladenine DNA glycosylase (EC 3.2.2.-).
 * GN   OrderedLocusNames=<font color="#000000">CPn_0505</font>, <font color="#000000">CP_0248</font>, <font color="#000000">CPj0505</font>, <font color="#000000">CpB0526</font>;
 * OS   Chlamydia pneumoniae (Chlamydophila pneumoniae).
 * ...</font></pre>
 * </p>
 * <h3>How to work with this Interface</h3>
 * <p>
 * The standard way of retrieving this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} entry = getEntryFromParserOrAPI();
 * List<{@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene}> genes = entry.getGenes();
 * for ({@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene : genes) {
 *     for (OrderedLocusName olName : gene.getOrderedLocusNames()) {
 *         System.out.println(olName);
 *     }
 * }</div>
 * </p>
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} uniProtEntry = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildEntry();
 * {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene                 = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildGene();
 * {@link OrderedLocusName OrderedLocusName} oln      = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildOrderedLocusName("OlName");
 * gene.getOrderedLocusNames().add(oln);
 * uniProtEntry.getGenes().add(gene);</div>
 * </p>
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.OrderedLocusNameImpl.class, name = "orderedLocusName")
})
public interface OrderedLocusName extends EvidencedValue {

}

