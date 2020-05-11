package org.uniprot.core.citation;

import org.uniprot.core.CrossReference;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * The basic type containing the common values that are present in each and every Citation
 *
 * <p>In the flatfile this is the Rx lines
 *
 * <p>To build a citation for use in you program @link
 * uk.ac.ebi.kraken.interfaces.factories.DefaultCitationFactory
 */
public interface Citation extends Serializable {

    List<CrossReference<CitationDatabase>> getCitationCrossReferences();

    Optional<CrossReference<CitationDatabase>> getCitationCrossReferenceByType(
            CitationDatabase type);

    boolean hasCitationCrossReferences();

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
