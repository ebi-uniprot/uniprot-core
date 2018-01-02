package uk.ac.ebi.uniprot.domain.uniprot.description;



import java.util.List;

/**
 * Contains a list of all names, and respective fields, that compose a protein within the DE line. This
 * class is used to characterize the main protein described in the DE line, as well as the
 * proteins found within the includes and contains sections of the DE line.
 */
public interface Section{

    /**
     * Returns a list of valid name types as well as the associated names
     *
     * @return list of names
     */
    public List<Name> getNames();

    /**
     * Returns a list of names that map to a specific type of name type
     * (see {@link uk.ac.ebi.uniprot.domain.uniprot.description.NameType NameType})
     *
     * @param type - The type of name one wants to retreive
     * @return list of names belonging to the sought after name type
     */
    public List<Name> getNamesByType(NameType type);
    boolean isValidSection();
}
