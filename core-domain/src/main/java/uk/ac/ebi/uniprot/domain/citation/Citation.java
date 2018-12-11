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
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.BookImpl.class, name = "BookImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl.class, name = "ElectronicArticleImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.JournalArticleImpl.class, name = "JournalArticleImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.PatentImpl.class, name = "PatentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.SubmissionImpl.class, name = "SubmissionImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.ThesisImpl.class, name = "ThesisImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl.class, name = "UnpublishedImpl")
})
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
