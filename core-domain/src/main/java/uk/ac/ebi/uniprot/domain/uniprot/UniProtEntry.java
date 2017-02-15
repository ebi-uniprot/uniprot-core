package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.citation.CitationType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.internalsection.InternalSection;

import java.util.List;
import uk.ac.ebi.uniprot.domain.common.Sequence;

/**
 * 
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 *
 */
public interface UniProtEntry {
    /**
     *
     * Returns the Primary UniProtAccessions of the UniProtEntry. <br>
     * <br>
     * This value can be found in first elements in the AC line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ID   CYC_HUMAN      STANDARD;      PRT;   104 AA.
     * AC   <font color="#000000">P99999</font>; <font color="#000000">P00001</font>; <font color=
    "#000000">Q6NUR2</font>; <font color="#000000">Q6NX69</font>; <font color="#000000">Q96BV4</font>;
     * DT   21-JUL-1986 (Rel. 01, Created)
     * DT   21-JUL-1986 (Rel. 01, Last sequence update)
     * DT   01-FEB-2005 (Rel. 46, Last annotation update)
     * DE   Cytochrome c.
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> &lt;entry dataset="Swiss-Prot" created="1986-07-21" modified="2005-02-01"&gt;
     * &lt;accession&gt;<font color="#000000">P99999</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">P00001</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q6NUR2</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q6NX69</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q96BV4</font>&lt;/accession&gt;
     * &lt;name&gt;CYC_HUMAN&lt;/name&gt;
     * &lt;protein&gt;
     * &lt;name&gt;Cytochrome c&lt;/name&gt;
     * &lt;/protein&gt;
     * ...
     * &lt;/entry&gt;</font>
     * </pre>
     *
     * @return All UniProtAccessions of the UniProtEntry.
     */
    UniProtAccession getPrimaryUniProtAccession();

    /**
     * Returns the list of UniProtAccessions of the UniProtEntry. <br>
     * <br>
     * These values can be found in the AC line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ID   CYC_HUMAN      STANDARD;      PRT;   104 AA.
     * AC   <font color="#000000">P99999</font>; <font color="#000000">P00001</font>; <font color=
    "#000000">Q6NUR2</font>; <font color="#000000">Q6NX69</font>; <font color="#000000">Q96BV4</font>;
     * DT   21-JUL-1986 (Rel. 01, Created)
     * DT   21-JUL-1986 (Rel. 01, Last sequence update)
     * DT   01-FEB-2005 (Rel. 46, Last annotation update)
     * DE   Cytochrome c.
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> &lt;entry dataset="Swiss-Prot" created="1986-07-21" modified="2005-02-01"&gt;
     * &lt;accession&gt;<font color="#000000">P99999</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">P00001</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q6NUR2</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q6NX69</font>&lt;/accession&gt;
     * &lt;accession&gt;<font color="#000000">Q96BV4</font>&lt;/accession&gt;
     * &lt;name&gt;CYC_HUMAN&lt;/name&gt;
     * &lt;protein&gt;
     * &lt;name&gt;Cytochrome c&lt;/name&gt;
     * &lt;/protein&gt;
     * ...
     * &lt;/entry&gt;</font>
     * </pre>
     *
     * @return All UniProtAccessions of the UniProtEntry.
     */
    List<UniProtAccession> getSecondaryUniProtAccessions();

    /**
     * Returns the UniProtId of the UniProtEntry. <br>
     * <br>
     * This value can be found in the ID line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ID   <font color="#000000">CYC_HUMAN</font>      STANDARD;      PRT;   104 AA.
     * AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;
     * DT   21-JUL-1986 (Rel. 01, Created)
     * DT   21-JUL-1986 (Rel. 01, Last sequence update)
     * DT   01-FEB-2005 (Rel. 46, Last annotation update)
     * DE   Cytochrome c.
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> &lt;entry dataset="Swiss-Prot" created="1986-07-21" modified="2005-02-01"&gt;
     * &lt;accession&gt;P99999&lt;/accession&gt;
     * &lt;accession&gt;P00001&lt;/accession&gt;
     * &lt;accession&gt;Q6NUR2&lt;/accession&gt;
     * &lt;accession&gt;Q6NX69&lt;/accession&gt;
     * &lt;accession&gt;Q96BV4&lt;/accession&gt;
     * &lt;name&gt;<font color="#000000">CYC_HUMAN</font>&lt;/name&gt;
     * &lt;protein&gt;
     * &lt;name&gt;Cytochrome c&lt;/name&gt;
     * &lt;/protein&gt;
     * ...
     * &lt;/entry&gt;</font>
     * </pre>
     *
     * @return The UniProtId of the UniProtEntry.
     */
    UniProtId getUniProtId();

    /**
     * Returns the taxonomy annotated in the UniProtEntry. <br>
     * <br>
     * These values can be found in the OC line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * OS   Serratia marcescens.
     * OC   <font color="#000000">Bacteria</font>; <font color="#000000">Proteobacteria</font>; <font color=
    "#000000">Gammaproteobacteria</font>; <font color="#000000">Enterobacteriales</font>;
     * OC   <font color="#000000">Enterobacteriaceae</font>; <font color="#000000">Serratia</font>.
     * OX   NCBI_TaxID=615;
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * &lt;organism key="2"&gt;
     * &lt;name type="scientific"&gt;Serratia marcescens&lt;/name&gt;
     * &lt;dbReference type="NCBI Taxonomy" id="615" key="3"/&gt;
     * &lt;lineage&gt;
     * &lt;taxon&gt;<font color="#000000">Bacteria</font>&lt;/taxon&gt;
     * &lt;taxon&gt;<font color="#000000">Proteobacteria</font>&lt;/taxon&gt;
     * &lt;taxon&gt;<font color="#000000">Gammaproteobacteria</font>&lt;/taxon&gt;
     * &lt;taxon&gt;<font color="#000000">Enterobacteriales</font>&lt;/taxon&gt;
     * &lt;taxon&gt;<font color="#000000">Enterobacteriaceae</font>&lt;/taxon&gt;
     * &lt;taxon&gt;<font color="#000000">Serratia</font>&lt;/taxon&gt;
     * &lt;/lineage&gt;
     * &lt;/organism&gt;
     * ...
     * </font>
     * </pre>
     *
     * @return The Taxonomy of the UniProtEntry.
     */
    List<NcbiTaxon> getTaxonomy();

    ProteinExistence getProteinExistence();

    UniProtEntryType getType();

    /**
     * Returns the Entry Audit Information. <br>
     * <br>
     * These values can be found in the DT line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ID   CYC_HUMAN      STANDARD;      PRT;   104 AA.
     * AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;
     * DT   <font color="#000000">21-JUL-1986 (Rel. 01, Created)</font>
     * DT   <font color="#000000">21-JUL-1986 (Rel. 01, Last sequence update)</font>
     * DT   <font color="#000000">01-FEB-2005 (Rel. 46, Last annotation update)</font>
     * DE   Cytochrome c.
     * ...</font>
     * </pre>
     *
     * @return The EntryAudit of the UniProtEntry.
     */
    EntryAudit getEntryAudit();

    List<Organelle> getOrganelles();

    List<Keyword> getKeywords();

    ProteinDescription getProteinDescription();

    public List<Comment> getComments();

    <T extends Comment> List<T> getComments(CommentType type);

    public List<Citation> getCitations();

    public <T extends Citation> List<T> getCitationsNew(CitationType citationType);

    public List<Gene> getGenes();

    /**
     * Returns the Organisms in which this protein is found. In recent UniProt Versions only one Organism is used. For
     * backward compatibility a List of Organisms is still supported through this interface. <br/>
     * <br/>
     * This value can be found in the OS line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> GN   Name=CYP71A2; Synonyms=CYPEG4;
     * OS   <font color="#000000">Solanum melongena (Eggplant) (Aubergine).</font>
     * OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;
     * OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons;
     * OC   asterids; lamiids; Solanales; Solanaceae; Solanum.
     * ...</font>
     * </pre>
     *
     * @return the List of Organisms in which this protein is found.
     */
    public Organism getOrganism();

    /**
     * Returns the list of organism hosts. This data item is optional and appears only in viral entries. It indicates
     * the host organism(s) that are susceptible to be infected by a virus. <br>
     * <br>
     * This value can be found in the OH line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> OC   Viruses; ssRNA positive-strand viruses, no DNA stage; Picornaviridae;
     * OC   Hepatovirus.
     * OX   NCBI_TaxID=31707;
     * OH   <font color="#000000">NCBI_TaxID=9481; Callithrix</font>.
     * OH   <font color="#000000">NCBI_TaxID=9536; Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey)</font>.
     * OH   <font color="#000000">NCBI_TaxID=9539; Macaca (macaques)</font>.
     * OH   <font color="#000000">NCBI_TaxID=9598; Pan troglodytes (Chimpanzee)</font>.
     * RN   [1]
     * ...</font>
     * </pre>
     *
     * @return The list of organism hosts.
     */
    public List<OrganismHost> getOrganismHosts();

    /**
     * Returns the list of DatabaseCrossReferences annotated in the UniProtEntry. <br>
     * <br>
     * These values can be found in the DR lines of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * CC   -!- SIMILARITY: Belongs to the cytochrome c family.
     * <font color="#000000">DR   EMBL; M22877; AAA35732.1; -.</font>
     * <font color="#000000">DR   PROSITE; PS51007; CYTC; 1.</font>
     * KW   Acetylation; Apoptosis; Direct protein sequencing; Electron transport;
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * &lt;comment type="similarity"&gt;
     * &lt;text&gt;Belongs to the cytochrome c family&lt;/text&gt;
     * &lt;/comment&gt;
     * <font color="#000000">&lt;dbReference type="EMBL" id="M22877" key="24"&gt;</font>
     * <font color="#000000">  &lt;property type="protein sequence ID" value="AAA35732.1"/&gt;</font>
     * <font color="#000000">&lt;/dbReference&gt;</font>
     * <font color="#000000">&lt;dbReference type="PROSITE" id="PS51007" key="69"&gt;</font>
     * <font color="#000000">  &lt;property type="entry name" value="CYTC"/&gt;</font>
     * <font color="#000000">  &lt;property type="match status" value="1"/&gt;</font>
     * <font color="#000000">&lt;/dbReference&gt;</font>
     * &lt;keyword id="KW-0007"&gt;Acetylation&lt;/keyword&gt;
     * &lt;keyword id="KW-0053"&gt;Apoptosis&lt;/keyword&gt;
     * ...
     * </font>
     * </pre>
     *
     * @return The DatabaseCrossReferences annotation of the UniProtEntry.
     */
    public List<DatabaseCrossReference> getDatabaseCrossReferences();

    public List<DatabaseCrossReference> getDatabaseCrossReferences(DatabaseType type);

    /**
     * Returns the Sequence annotation in the UniProtEntry. <br>
     * <br>
     * These values can be found in the SQ line of the flat file on the marked position.
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * FT   CONFLICT     40     40       T -> I (in Ref. 7; AAH68464).
     * <font color="#000000">SQ   SEQUENCE   104 AA;  11618 MW;  D47C9B513DF1C5C2 CRC64;
     * GDVEKGKKIF IMKCSQCHTV EKGGKHKTGP NLHGLFGRKT GQAPGYSYTA ANKNKGIIWG
     * EDTLMEYLEN PKKYIPGTKM IFVGIKKKEE RADLIAYLKK ATNE</font>
     * //
     * ...</font>
     * </pre>
     * 
     * In XML:
     * 
     * <pre class="example">
     * <font color="#AAAAAA"> ...
     * &lt;/feature&gt;
     * <font color=
    "#000000">&lt;sequence length="104" mass="11618" checksum="D47C9B513DF1C5C2" modified="1986-07-21"&gt;
     * GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
     * ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
     * ATNE
     * &lt;/sequence&gt;</font>
     * &lt;/entry&gt;
     * ...
     * </font>
     * </pre>
     *
     * @return The Sequence of the UniProtEntry.
     */
    public Sequence getSequence();

    public List<NcbiTaxonomyId> getNcbiTaxonomyIds();

    public Boolean isFragment();

    InternalSection getInternalSection();
}
