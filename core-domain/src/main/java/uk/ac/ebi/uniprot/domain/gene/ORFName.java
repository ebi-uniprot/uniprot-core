package uk.ac.ebi.uniprot.domain.gene;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the ORF Names of a {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} object of
 * a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * ORF names (a.k.a. sequencing names or contig names or temporary ORFNames).
 * A name temporarily attributed by a sequencing project to an open reading frame.
 * This name is generally based on a cosmid numbering system.
 * Examples: MtCY277.28c, SYGP-ORF50, SpBC2F12.04, C06E1.1, CG10954.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * These values can be found in the GN line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> DE   1-aminocyclopropane-1-carboxylate synthase 5 (EC 4.4.1.14) (ACC
 * DE   synthase 5) (S-adenosyl-L-methionine methylthioadenosine-lyase 5)
 * DE   (Ethylene-overproduction protein 2).
 * GN   Name=ACS5; Synonyms=ACC5, ETO2; OrderedLocusNames=At5g65800;
 * GN   ORFNames=<font color="#000000">MPA24.15</font>, <font color="#000000">F6H11.90</font>;
 * OS   Arabidopsis thaliana (Mouse-ear cress).
 * ...</font></pre>
 * </p>
 * <h3>How to work with this Interface</h3>
 * <p>
 * The standard way of retrieving this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} entry = getEntryFromParserOrAPI();
 * List<{@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene}> genes = entry.getGenes();
 * for ({@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene : genes) {
 *     for (ORFName orfName : gene.getORFNames()) {
 *         System.out.println(orfName);
 *     }
 * }</div>
 * </p>
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} uniProtEntry = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildEntry();
 * {@link uk.ac.ebi.uniprot.domain.gene.kraken.interfaces.uniprot.Gene Gene} gene                 = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildGene();
 * {@link ORFName ORFName} orf               = {@link uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory DefaultUniProtFactory}.getInstance().buildORFName("OrfName");
 * gene.getORFNames().add(orf);
 * uniProtEntry.getGenes().add(gene);</div>
 * </p>
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.ORFNameImpl.class, name = "orfName")
})
public interface ORFName extends EvidencedValue {

}
