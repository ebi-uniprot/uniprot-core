package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.interfaces.HasEvidences;

/**
 * The DBCrossReference annotation of the {@link UniProtEntry UniProtEntry}.
 * This interface defines the coarse grain access to the Databasecrossreference
 * line int the UniProt entry (DR line). For finer access see the classes in
 * the subinterfaces extending this interface. This interface allows using unspecified
 * {@link Value Values} to set the individual data items in each DR line. Use
 * {@link uk.ac.ebi.kraken.interfaces.factories.UniProtFactory#buildValue(String) UniProtFactory.buildValue(String)}
 * to intstantiate the values.
 * <br><br>
 * These values can be found in the DR lines of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ...
 * CC   -!- SIMILARITY: Belongs to the cytochrome c family.
 * <font color="#000000">DR   EMBL; M22877; AAA35732.1; -.</font>
 * <font color="#000000">DR   PROSITE; PS51007; CYTC; 1.</font>
 * KW   Acetylation; Apoptosis; Direct protein sequencing; Electron transport;
 * ...</font></pre>
 * In XML:
 * <pre><font color="#AAAAAA"> ...
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
 * </font></pre>
 * @author jieluo
 * @author Wudong Liu
 * @see Comment
 * @version 1.0
 */

public interface DatabaseCrossReference extends HasEvidences {
	/**
	 * Returns the {@link DatabaseType DATABASE} of the DBCrossReference.
	 * <br><br>
	 * This value can be found in the DR lines of the flat file on the marked position.
	 * <pre class="example"><font color="#AAAAAA"> ...
	 * CC   -!- SIMILARITY: Belongs to the cytochrome c family.
	 * DR   <font color="#000000">EMBL</font>; M22877; AAA35732.1; -.
	 * DR   <font color="#000000">PROSITE</font>; PS51007; CYTC; 1.
	 * KW   Acetylation; Apoptosis; Direct protein sequencing; Electron transport;
	 * ...</font></pre>
	 * In XML:
	 * <pre class="example"><font color="#AAAAAA"> ...
	 * &lt;comment type="similarity"&gt;
	 * &lt;text&gt;Belongs to the cytochrome c family&lt;/text&gt;
	 * &lt;/comment&gt;
	 * &lt;dbReference type="<font color="#000000">EMBL</font>" id="M22877" key="24"&gt;
	 *   &lt;property type="protein sequence ID" value="AAA35732.1"/&gt;
	 * &lt;/dbReference&gt;
	 * &lt;dbReference type="<font color="#000000">PROSITE</font>" id="PS51007" key="69"&gt;
	 *   &lt;property type="entry name" value="CYTC"/&gt;
	 *   &lt;property type="match status" value="1"/&gt;
	 * &lt;/dbReference&gt;
	 * &lt;keyword id="KW-0007"&gt;Acetylation&lt;/keyword&gt;
	 * &lt;keyword id="KW-0053"&gt;Apoptosis&lt;/keyword&gt;
	 * ...
	 * </font></pre>
	 * @return The {@link DatabaseType DATABASE} of the DatabaseCrossReference.
	 */
	DatabaseType getDatabase();
	
	/**
	 * 
	 * @return free database cross reference primary accession
	 */
	DatabaseAttribute getPrimaryId();
	
	/**
	 * 
	 * @return description
	 */
	DatabaseAttribute getDescription();
	
	
	/**
	 * 
	 * @return third parameter value
	 */
	DatabaseAttribute getThird();
	
	/**
	 * 
	 * @return fourth parameter value
	 */
	DatabaseAttribute getFourth();
	

/**
 * 
 * @return true if database has 3 or 4 db fields otherwise return false
 */
	boolean hasThird();
	/**
	 * 
	 * @return true if database has 4 db fields otherwise return false;
	 */
	boolean hasFourth();
	/**
	 *  
	 * @return UniProtIsoformId the cross reference belong to. 
	 * If return the valid isoform, it means the cross reference links only to the isoform.
	 */
	public UniProtIsoformId getIsoformId();
	boolean hasUniProtIsoformId();
	
}
