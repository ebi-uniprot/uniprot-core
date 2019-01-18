package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalArticleImpl;

public final class JournalArticleBuilder extends AbstractCitationBuilder<JournalArticleBuilder, JournalArticle> {

    private String journalName;
    private String firstPage = "";
    private String lastPage = "";
    private String volume = "";

    public static JournalArticleBuilder newInstance() {
        return new JournalArticleBuilder();
    }

    public JournalArticle build() {
        return new JournalArticleImpl(authoringGroups, authors,
                                      xrefs, title, publicationDate,
                                      journalName, firstPage, lastPage, volume);
    }

    public JournalArticleBuilder journalName(String journalName) {
        this.journalName = journalName;
        return this;
    }


    public JournalArticleBuilder firstPage(String firstPage) {
        this.firstPage = firstPage;
        return this;
    }

    public JournalArticleBuilder lastPage(String lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    public JournalArticleBuilder volume(String volume) {
        this.volume = volume;
        return this;
    }

	@Override
	protected JournalArticleBuilder getThis() {
		return this;
	}


}
