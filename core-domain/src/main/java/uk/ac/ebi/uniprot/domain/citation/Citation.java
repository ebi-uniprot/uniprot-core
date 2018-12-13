package uk.ac.ebi.uniprot.domain.citation;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * The basic type containing the common values that are present in each and every
 * Citation
 * <p/>
 * In the flatfile this is the Rx lines
 * <p/>
 * To build a citation for use in you program @link uk.ac.ebi.kraken.interfaces.factories.DefaultCitationFactory
 */
public interface Citation  {

    public CitationXrefs getCitationXrefs();

    public boolean hasCitationXrefs();

    public List<String> getAuthoringGroup();

    public List<Author> getAuthors();

    public CitationType getCitationType();

    public String getTitle();

    public boolean hasTitle();

    public PublicationDate getPublicationDate();

}
