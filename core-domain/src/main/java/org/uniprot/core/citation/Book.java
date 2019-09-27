package org.uniprot.core.citation;

import java.util.List;

/**
 * A variation of the RL line format is used for papers found in books or other types of
 * publication, which are then cited using the following format:
 *
 * <p>RL (In) Editor_1 I.[, Editor_2 I., Editor_X I.] (eds.); RL Book_name,
 * pp.[Volume:]First_page-Last_page, Publisher, City (YYYY). Examples:
 *
 * <p>RL (In) Boyer P.D. (eds.); RL The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York
 * (1975). RL (In) Rich D.H., Gross E. (eds.); RL Proceedings of the 7th American peptide symposium,
 * pp.69-72, Pierce RL Chemical Co., Rockford Il. (1981). RL (In) Magnusson S., Ottesen M., Foltmann
 * B., Dano K., Neurath H. RL (eds.); RL Regulatory proteolytic enzymes and their inhibitors,
 * pp.163-172, RL Pergamon Press, New York (1978).
 */
public interface Book extends Citation {

    String getBookName();

    List<Author> getEditors();

    String getFirstPage();

    String getLastPage();

    String getVolume();

    String getPublisher();

    String getAddress();

    boolean hasBookName();

    boolean hasEditors();

    boolean hasFirstPage();

    boolean hasLastPage();

    boolean hasVolume();

    boolean hasPublisher();

    boolean hasAddress();
}
