package uk.ac.ebi.uniprot.xml.uniprot.citation;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.NameListType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PersonType;

import java.util.List;
import java.util.stream.Collectors;

public class BookConverter implements Converter<CitationType, Book> {

	private final ObjectFactory xmlUniprotFactory;

	PageConverter pageConverter = new PageConverter();

	public BookConverter() {
		this(new ObjectFactory());
	}

	public BookConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Book fromXml(CitationType xmlObj) {
		BookBuilder builder = new BookBuilder();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		builder.address(xmlObj.getCity());
		builder.bookName(xmlObj.getName());

		if (xmlObj.getEditorList() != null) {
			builder.editors(xmlObj.getEditorList().getConsortiumOrPerson().stream().map(val -> (PersonType) val)
					.map(CitationConverterHelper::fromXmlAuthor).collect(Collectors.toList()));

		}

		String first = pageConverter.fromXml(xmlObj.getFirst());
		if (first != null) {
			builder.firstPage(first);

		} else if ((xmlObj.getFirst() != null) && (!xmlObj.getFirst().isEmpty())) {
			String bookNameS = xmlObj.getName() + ", " + xmlObj.getFirst();
			builder.bookName(bookNameS);
		}
		builder.lastPage(pageConverter.fromXml(xmlObj.getLast()));

		builder.publisher(xmlObj.getPublisher());
		builder.volume(xmlObj.getVolume());

		return builder.build();
	}

	@Override
	public CitationType toXml(Book uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
		xmlCitation.setType(uniObj.getCitationType().getValue());
		xmlCitation.setName(uniObj.getBookName());
		if(!Strings.isNullOrEmpty(uniObj.getAddress()))
			xmlCitation.setCity(uniObj.getAddress());
		setEditorsToXml(uniObj, xmlCitation);
		if(!Strings.isNullOrEmpty(uniObj.getVolume()))
			xmlCitation.setVolume(uniObj.getVolume());
		setFirstPageToXml(uniObj, xmlCitation); // todo replace with citationXML.setFirst
		xmlCitation.setLast(pageConverter.toXml(uniObj.getLastPage()));
		if(!Strings.isNullOrEmpty(uniObj.getPublisher()))
			xmlCitation.setPublisher(uniObj.getPublisher());
		return xmlCitation;
	}

	private void setFirstPageToXml(Book citation, CitationType citationXML) {
		String first = citation.getFirstPage();
		 first = pageConverter.toXml(citation.getFirstPage());
		
		if (first == null)  {
			if(citation.getFirstPage() ==null) {
			int abstractIndex = citation.getBookName().indexOf("abstract");
			if (abstractIndex > -1) {
				int nextSpace = citation.getBookName().indexOf(' ', abstractIndex);
				if (nextSpace > -1) {
					citationXML.setFirst(citation.getBookName().substring(abstractIndex, nextSpace));
				} else {
					citationXML.setFirst(citation.getBookName().substring(abstractIndex));

					// ..if so remove this from book title
					String bookNameWithoutAbstract = citation.getBookName().substring(0, abstractIndex - 1);
					if (bookNameWithoutAbstract.endsWith(",")) {
						bookNameWithoutAbstract = bookNameWithoutAbstract.substring(0,
								bookNameWithoutAbstract.length() - 1);
					}
					citationXML.setName(bookNameWithoutAbstract);
				}
			}
			}else {
				citationXML.setFirst(citation.getFirstPage());
			}

		} else {
			citationXML.setFirst(first);
		}
	}

	private void setEditorsToXml(Book citation, CitationType citationXML) {
		if (citation.getEditors() != null && !citation.getEditors().isEmpty()) {
			List<Author> listEditors = citation.getEditors();
			NameListType editorList = xmlUniprotFactory.createNameListType();

			for (Author editor : listEditors) {
				PersonType person = xmlUniprotFactory.createPersonType();
				person.setName(editor.getValue());
				editorList.getConsortiumOrPerson().add(person);
			}
			citationXML.setEditorList(editorList);
		}
	}

}
