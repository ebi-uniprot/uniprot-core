package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.Locator;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ElectronicArticleImpl extends AbstractCitationImpl implements ElectronicArticle {
	private final Journal journal;
	private final Locator locator;

	public ElectronicArticleImpl( List<String> authoringGroup, List<Author> authors,  CitationXrefs citationXrefs,
			String title,  PublicationDate publicationDate,
			 String journalName,  Locator locator) {
		this(authoringGroup, authors, citationXrefs, title, publicationDate,
				new JournalImpl(journalName), locator);
	

	}
	
	@JsonCreator
	public ElectronicArticleImpl(
			@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			@JsonProperty("journal") Journal journal, @JsonProperty("locator")  Locator locator) {
		super(CitationType.ELECTRONIC_ARTICLE, authoringGroup, authors, citationXrefs, title, publicationDate);
		this.journal = journal;
		this.locator = locator;
	}

	@Override
	public Locator getLocator() {
		return locator;
	}

	@Override
	public Journal getJournal() {
		return journal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((journal == null) ? 0 : journal.hashCode());
		result = prime * result + ((locator == null) ? 0 : locator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElectronicArticleImpl other = (ElectronicArticleImpl) obj;
		if (journal == null) {
			if (other.journal != null)
				return false;
		} else if (!journal.equals(other.journal))
			return false;
		if (locator == null) {
			if (other.locator != null)
				return false;
		} else if (!locator.equals(other.locator))
			return false;
		return true;
	}
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class LocatorImpl implements Locator {
		private final String value;
		@JsonCreator
		public LocatorImpl(@JsonProperty("value")  String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LocatorImpl other = (LocatorImpl) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}

}
