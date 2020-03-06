package org.uniprot.core.xml.uniprot.citation;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.jaxb.uniprot.NameListType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.PersonType;

import com.google.common.base.Strings;

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
            builder.editorsSet(
                    xmlObj.getEditorList().getConsortiumOrPerson().stream()
                            .map(val -> (PersonType) val)
                            .map(CitationConverterHelper::fromXmlAuthor)
                            .collect(Collectors.toList()));
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
        if (!Strings.isNullOrEmpty(uniObj.getAddress())) xmlCitation.setCity(uniObj.getAddress());
        setEditorsToXml(uniObj, xmlCitation);
        if (!Strings.isNullOrEmpty(uniObj.getVolume())) xmlCitation.setVolume(uniObj.getVolume());
        setFirstPageToXml(uniObj, xmlCitation); // todo replace with citationXML.setFirst
        xmlCitation.setLast(pageConverter.toXml(uniObj.getLastPage()));
        if (!Strings.isNullOrEmpty(uniObj.getPublisher()))
            xmlCitation.setPublisher(uniObj.getPublisher());
        return xmlCitation;
    }

    private void setFirstPageToXml(Book citation, CitationType citationXML) {
        String first = citation.getFirstPage();
        first = pageConverter.toXml(citation.getFirstPage());

        if (first == null) {
            if (citation.getFirstPage() == null) {
                int abstractIndex = citation.getBookName().indexOf("abstract");
                if (abstractIndex > -1) {
                    int nextSpace = citation.getBookName().indexOf(' ', abstractIndex);
                    if (nextSpace > -1) {
                        citationXML.setFirst(
                                citation.getBookName().substring(abstractIndex, nextSpace));
                    } else {
                        citationXML.setFirst(citation.getBookName().substring(abstractIndex));

                        // ..if so remove this from book title
                        String bookNameWithoutAbstract =
                                citation.getBookName().substring(0, abstractIndex - 1);
                        if (bookNameWithoutAbstract.endsWith(",")) {
                            bookNameWithoutAbstract =
                                    bookNameWithoutAbstract.substring(
                                            0, bookNameWithoutAbstract.length() - 1);
                        }
                        citationXML.setName(bookNameWithoutAbstract);
                    }
                }
            } else {
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
