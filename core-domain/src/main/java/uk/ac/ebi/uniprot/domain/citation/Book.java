package uk.ac.ebi.uniprot.domain.citation;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A variation of the RL line format is used for papers found in books or other types of publication, which are then cited using the following format:
 *
 * RL   (In) Editor_1 I.[, Editor_2 I., Editor_X I.] (eds.);
 * RL   Book_name, pp.[Volume:]First_page-Last_page, Publisher, City (YYYY).
 * Examples:
 *
 * RL   (In) Boyer P.D. (eds.);
 * RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York (1975).
 * RL   (In) Rich D.H., Gross E. (eds.);
 * RL   Proceedings of the 7th American peptide symposium, pp.69-72, Pierce
 * RL   Chemical Co., Rockford Il. (1981).
 * RL   (In) Magnusson S., Ottesen M., Foltmann B., Dano K., Neurath H.
 * RL   (eds.);
 * RL   Regulatory proteolytic enzymes and their inhibitors, pp.163-172,
 * RL   Pergamon Press, New York (1978).
 *
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.BookImpl.class, name = "BookImpl")
})
public interface Book extends Citation {
    
    public String getBookName();

    public List<Author> getEditors();

    public String getFirstPage();

    public String getLastPage();

    public String getVolume();

    public String getPublisher();
    public String getAddress();

}
