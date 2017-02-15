package uk.ac.ebi.uniprot.domain.uniprot;



/**
 * Interface to an Organism Host used in an UniProtKB entry.
 * <br><br>
 * These values can be found in the OH line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> OC   Viruses; ssRNA positive-strand viruses, no DNA stage; Picornaviridae;
 * OC   Hepatovirus.
 * OX   NCBI_TaxID=31707;
 * OH   <font color="#000000">NCBI_TaxID=9481; Callithrix.</font>
 * OH   <font color="#000000">NCBI_TaxID=9536; Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey).</font>
 * OH   <font color="#000000">NCBI_TaxID=9539; Macaca (macaques).</font>
 * OH   <font color="#000000">NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).</font>
 * RN   [1]
 * ...</font></pre>
 */
public interface OrganismHost {

    /**
     * Returns the Organism of this Organism Host.
     * <br><br>
     * This value can be found in the OH line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> OC   Viruses; ssRNA positive-strand viruses, no DNA stage; Picornaviridae;
     * OC   Hepatovirus.
     * OX   NCBI_TaxID=31707;
     * OH   NCBI_TaxID=9481; <font color="#000000">Callithrix</font>.
     * OH   NCBI_TaxID=9536; <font color="#000000">Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey)</font>.
     * OH   NCBI_TaxID=9539; <font color="#000000">Macaca (macaques)</font>.
     * OH   NCBI_TaxID=9598; <font color="#000000">Pan troglodytes (Chimpanzee)</font>.
     * RN   [1]
     * ...</font></pre>
     * @return The Organism of this Organism Host.
     */
    public Organism getOrganism();

    /**
     * Returns the NCBI Taxonomy ID of the Organism of this Organism Host.
     * <br><br>
     * This value can be found in the OH line of the flat file on the marked position.
     * <pre class="example"><font color="#AAAAAA"> OC   Viruses; ssRNA positive-strand viruses, no DNA stage; Picornaviridae;
     * OC   Hepatovirus.
     * OX   NCBI_TaxID=31707;
     * OH   NCBI_TaxID=<font color="#000000">9481</font>; Callithrix.
     * OH   NCBI_TaxID=<font color="#000000">9536</font>; Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey).
     * OH   NCBI_TaxID=<font color="#000000">9539</font>; Macaca (macaques).
     * OH   NCBI_TaxID=<font color="#000000">9598</font>; Pan troglodytes (Chimpanzee).
     * RN   [1]
     * ...</font></pre>
     * @return The NCBI Taxonomy ID of the Organism of this Organism Host.
     */
    public NcbiTaxonomyId getNcbiTaxonomyId();
}
