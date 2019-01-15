package uk.ac.ebi.uniprot.domain.citation2;


import java.util.List;

/**
 * The basic type containing the common values that are present in each and every
 * Citation
 * <p/>
 * In the flatfile this is the Rx lines
 * <p/>
 * To build a citation for use in you program @link uk.ac.ebi.kraken.interfaces.factories.DefaultCitationFactory
 */
public interface Citation {

    public CitationXrefs getCitationXrefs();

    public boolean hasCitationXrefs();

    public List<String> getAuthoringGroup();

    public List<Author> getAuthors();

    public CitationType getCitationType();

    public String getTitle();

    public boolean hasTitle();

    public PublicationDate getPublicationDate();

}
