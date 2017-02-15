package uk.ac.ebi.uniprot.domain.uniprot.description;


import java.util.List;

/**
 * Maps the Names of a protein in a DE line : RecName, AltName, SubName
 * <br><br>
 * As an example:
 *<pre class="example"><font color="#AAAAAA">   ...
 * DE   <font color="#000000">RecName: Full=Interleukin-2; </font>
 * DE   <font color="#000000">         Short=IL-2;         </font>
 * DE   <font color="#000000">AltName: Full=T-cell growth factor; </font>
 * DE   <font color="#000000">         Short=TCGF;                </font>
 * DE   <font color="#000000">AltName: INN=Aldesleukin;           </font>
 *
 *      ...</font></pre>
 *
 * See {@link NameType NameType} for a
 * a list of the allowable names.
 *
 * See {@link FieldType FieldType}
 * for a list of the allowable fields.
 */
public interface Name {

    /**
     * Returns the type of the name being analyzed
     *
     * @return type of name (see {@link NameType NameType})
     */
    public NameType getNameType();
    /**
     * Returns the fields beloning to this name
     *
     * @return list of stored fields
     */
    public List<Field> getFields();

    /**
     * Returns a list of fields of a specific type
     *
     * @param fieldType - a valid field type (see {@link FieldType FieldType})
     * @return list of fields of a specific type fot the current name
     */
    public List<Field> getFieldsByType(FieldType fieldType);
}
