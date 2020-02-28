package org.uniprot.core.citation;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.DBCrossReference;

/**
 * The basic type containing the common values that are present in each and every Citation
 *
 * <p>In the flatfile this is the Rx lines
 *
 * <p>To build a citation for use in you program @link
 * uk.ac.ebi.kraken.interfaces.factories.DefaultCitationFactory
 */
public interface Citation extends Serializable {

    List<DBCrossReference<CitationDatabase>> getCitationXrefs();

    Optional<DBCrossReference<CitationDatabase>> getCitationXrefsByType(CitationDatabase type);

    boolean hasCitationXrefs();

    List<String> getAuthoringGroups();

    List<Author> getAuthors();

    CitationType getCitationType();

    String getTitle();

    boolean hasTitle();

    PublicationDate getPublicationDate();

    boolean hasAuthoringGroup();

    boolean hasAuthors();

    boolean hasPublicationDate();
}
