package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;


/**
 * The description annotation of the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 *
 * <br><br>
 *
 * Example of a DE line
 *
 * <br>
 *
 * <pre class="example"><font color="#AAAAAA">   ...
 *
 * DE   <font color="#000000"> RecName: Full=Arginine biosynthesis bifunctional protein argJ;</font>
 * DE   <font color="#000000">   Includes:                                                   </font>
 * DE   <font color="#000000">     RecName: Full=Glutamate N-acetyltransferase;              </font>
 * DE   <font color="#000000">              EC=2.3.1.35;                                     </font>
 * DE   <font color="#000000">     AltName: Full=Ornithine acetyltransferase;                </font>
 * DE   <font color="#000000">              Short=OATase;                                    </font>
 * DE   <font color="#000000">     AltName: Full=Ornithine transacetylase;                   </font>
 * DE   <font color="#000000">   Includes:                                                   </font>
 * DE   <font color="#000000">     RecName: Full=Amino-acid acetyltransferase;               </font>
 * DE   <font color="#000000">              EC=2.3.1.1;                                      </font>
 * DE   <font color="#000000">     AltName: Full=N-acetylglutamate synthase;                 </font>
 * DE   <font color="#000000">              Short=AGS;                                       </font>
 * DE   <font color="#000000">   Contains:                                                   </font>
 * DE   <font color="#000000">     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;</font>
 * DE   <font color="#000000">   Contains:                                                                   </font>
 * DE   <font color="#000000">     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain; </font>
 *      ...</font></pre>
 *
 * The DE line is composed by a protein nomenculature hierarchy:
 *      There exist Names (see {@link uk.ac.ebi.kraken.interfaces.uniprot.newDescription.NameType NameType}),
 *      and within these fields (see {@link uk.ac.ebi.kraken.interfaces.uniprot.newDescription.FieldType FieldType}).
 *
 * <br>
 * Within the DE line there can also exist a list of proteins (subdivided into includes and contains lists).
 *
 * <br>
 *
 * When necessary the DE may also contain various flags, see
 * {@link uk.ac.ebi.kraken.interfaces.uniprot.description.FlagType FlagType}.
 * Flags are used to indicate whether the protein sequence is a precursor or a fragment. 
 *
 */
public interface ProteinDescription  {


    /**
     * Retrieves the recommended name of the protein from the DE line
     *
     * @return - returns the existing recommended name or a Name with no content
     */
     Name getRecommendedName();

    /**
     * Verifies whether there exists a recommended name for the protein being described
     *
     * @return - True if a recommended name exists, false otherwise
     */
     boolean hasRecommendedName();


    /**
     * Retrieves a list of alternate names for the main protein
     *
     * @return - list containing the alternate names of the main protein
     */
    public List<Name> getAlternativeNames();

    /**
     * Verifies whether there exist alternative names for the protein being described
     *
     * @return - True if there exist alternative names, false otherwise
     */
    public boolean hasAlternativeNames();

    /**
     * Retrieves a list of sub names for the main protein
     *
     * @return - list containing the sub names of the main protein
     */
    public List<Name> getSubNames();

    /**
     * Verifies whether there exist sub names for the protein being described
     *
     * @return - True if there exist sub names, false otherwise
     */
    public boolean hasSubNames();


    /**
     * Returns a list of proteins described in the includes section of the DE line
     *
     * @return list of proteins described in the includes section
     */
    public List<Section> getIncludes();

    /**
     * Returns a list of proteins described in the contains section of the DE line
     *
     * @return list of proteins described in the contains section
     */
    public List<Section> getContains();
    /**
     * Retrives the list of flags from the DE line
     *
     * @return list of flags found within the DE line
     */
    public List<Flag> getFlags();

    /**
     * Alternativley to defining the main protein through the getter methods of the
     * recommended, alternative and sub names methods, one can get the main protein as an
     * entire section.
     *
     * @return Section - The main protein containg all of its nomenculatures
     */
    public Section getSection();

    /**
     * Retrieves a list of all the EC numbers present in the DE line
     * 
     * @return list of all EC numbers found wihtin a the De line
     */
    public List<String> getEcNumbers();
}
