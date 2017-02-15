package uk.ac.ebi.uniprot.domain.common;

/**
 * Encapsulates a protein sequence, in particular the sequence annotation in the
 * {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry} and
 * {@link uk.ac.ebi.kraken.interfaces.uniparc.UniParcEntry UniParcEntry}.
 * <br><br>
 * These values can be found in the SQ line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ...
 * FT   CONFLICT     40     40       T -> I (in Ref. 7; AAH68464).
 * <font color="#000000">SQ   SEQUENCE   104 AA;  11618 MW;  D47C9B513DF1C5C2 CRC64;
 * GDVEKGKKIF IMKCSQCHTV EKGGKHKTGP NLHGLFGRKT GQAPGYSYTA ANKNKGIIWG
 * EDTLMEYLEN PKKYIPGTKM IFVGIKKKEE RADLIAYLKK ATNE</font>
 * //
 * ...</font></pre>
 * In XML:
 * <pre class="example"><font color="#AAAAAA">   ...
 *   &lt;/feature&gt;
 *   <font color="#000000">&lt;sequence length="104" mass="11618" checksum="D47C9B513DF1C5C2" modified="1986-07-21"&gt;
 *     GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
 *     ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
 *     ATNE
 *   &lt;/sequence&gt;</font>
 * &lt;/entry&gt;
 * ...
 * </font></pre>
 */
public interface Sequence {

	/**
	 * Returns the length of this sequence. This should usually be the number of characters
	 * in the sequence itself.
	 * @return The length of this sequence.
	 */
	public int getLength();

	/**
	 * Returns the molecular weight of this sequence.
	 * <br><br>
	 * These values can be found in the SQ line of the flat file on the marked position
	 * in the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
	 * <pre class="example"><font color="#AAAAAA"> ...
	 * FT   CONFLICT     40     40       T -> I (in Ref. 7; AAH68464).
	 * SQ   SEQUENCE   104 AA;  <font color="#000000">11618</font> MW;  D47C9B513DF1C5C2 CRC64;
	 * GDVEKGKKIF IMKCSQCHTV EKGGKHKTGP NLHGLFGRKT GQAPGYSYTA ANKNKGIIWG
	 * EDTLMEYLEN PKKYIPGTKM IFVGIKKKEE RADLIAYLKK ATNE
	 * //
	 * ...</font></pre>
	 * In XML:
	 * <pre class="example"><font color="#AAAAAA">   ...
	 *   &lt;/feature&gt;
	 *   &lt;sequence length="104" mass="<font color="#000000">11618</font>" checksum="D47C9B513DF1C5C2" modified="1986-07-21"&gt;
	 *     GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
	 *     ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
	 *     ATNE
	 *   &lt;/sequence&gt;
	 * &lt;/entry&gt;
	 * ...
	 * </font></pre>
	 * @return The molecular weight of this sequence.
	 */
	public int getMolecularWeight();

	

	/**
	 * Returns the crc64 hashcode of this sequence.
	 * <br><br>
	 * These values can be found in the SQ line of the flat file on the marked position
	 * in the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
	 * <pre class="example"><font color="#AAAAAA"> ...
	 * FT   CONFLICT     40     40       T -> I (in Ref. 7; AAH68464).
	 * SQ   SEQUENCE   104 AA;  11618 MW;  <font color="#000000">D47C9B513DF1C5C2</font> CRC64;
	 * GDVEKGKKIF IMKCSQCHTV EKGGKHKTGP NLHGLFGRKT GQAPGYSYTA ANKNKGIIWG
	 * EDTLMEYLEN PKKYIPGTKM IFVGIKKKEE RADLIAYLKK ATNE
	 * //
	 * ...</font></pre>
	 * In XML:
	 * <pre class="example"><font color="#AAAAAA">   ...
	 *   &lt;/feature&gt;
	 *   &lt;sequence length="104" mass="11618" checksum="<font color="#000000">D47C9B513DF1C5C2</font>" modified="1986-07-21"&gt;
	 *     GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
	 *     ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
	 *     ATNE
	 *   &lt;/sequence&gt;
	 * &lt;/entry&gt;
	 * ...
	 * </font></pre>
	 * @return The crc64 hashcode of this sequence.
	 */
	public String getCRC64();

	public String getMD5();
	

	/**
	 * Returns the String representation of the amino acid composition of this sequence.
	 * <br><br>
	 * These values can be found in the SQ line of the flat file on the marked position
	 * in the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
	 * <pre class="example"><font color="#AAAAAA"> ...
	 * FT   CONFLICT     40     40       T -> I (in Ref. 7; AAH68464).
	 * SQ   SEQUENCE   104 AA;  11618 MW;  D47C9B513DF1C5C2 CRC64;
	 * <font color="#000000">GDVEKGKKIF IMKCSQCHTV EKGGKHKTGP NLHGLFGRKT GQAPGYSYTA ANKNKGIIWG
	 * EDTLMEYLEN PKKYIPGTKM IFVGIKKKEE RADLIAYLKK ATNE</font>
	 * //
	 * ...</font></pre>
	 * In XML:
	 * <pre class="example"><font color="#AAAAAA">   ...
	 *   &lt;/feature&gt;
	 *   &lt;sequence length="104" mass="11618" checksum="D47C9B513DF1C5C2" modified="1986-07-21"&gt;
	 *     <font color="#000000">GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
	 *     ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
	 *     ATNE</font>
	 *   &lt;/sequence&gt;
	 * &lt;/entry&gt;
	 * ...
	 * </font></pre>
	 * @return The String representation of the amino acid composition of this sequence.
	 */
	public String getValue();

	

    public Sequence subSequence(int start, int end);

}
