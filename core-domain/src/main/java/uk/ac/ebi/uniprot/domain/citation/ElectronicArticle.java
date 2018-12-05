package uk.ac.ebi.uniprot.domain.citation;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**The RL line for an electronic publication includes an '(er)' prefix. The format is indicated below:
 *
 * RL   (er) Free text.
 * Examples:
 *
 * RL   (er) Plant Gene Register PGR98-023.
 * RL   (er) Worm Breeder's Gazette 15(3):34(1998).
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl.class, name = "ElectronicArticleImpl")
})
public interface ElectronicArticle extends Citation {

    public Locator getLocator();

    public Journal getJournal();
}
