package org.uniprot.core.xml.proteome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.impl.AbstractCitationBuilder;
import org.uniprot.core.citation.impl.AuthorBuilder;
import org.uniprot.core.xml.jaxb.proteome.*;
import org.uniprot.core.xml.uniprot.citation.PublicationDateConverter;

import com.google.common.base.Strings;

public class ReferenceConverterHelper {

    private ReferenceConverterHelper() {}

    private static final PublicationDateConverter dateConverter = new PublicationDateConverter();

    public static void updateFromXmlCitaiton(
            CitationType xmlCitation,
            AbstractCitationBuilder<? extends AbstractCitationBuilder<?, ?>, ? extends Citation>
                    builder) {

        if (!Strings.isNullOrEmpty(xmlCitation.getDate())) {
            builder.publicationDate(dateConverter.fromXml(xmlCitation.getDate()));
        }
        builder.authorsSet(authorsFromXml(xmlCitation.getAuthorList()));
        builder.authoringGroupsSet(authoringGroupfromXml(xmlCitation.getAuthorList()));
    }

    private static List<String> authoringGroupfromXml(NameListType authorListXML) {
        if (authorListXML == null) return Collections.emptyList();

        List<Object> listPersonOrConsortium = authorListXML.getConsortiumOrPerson();

        List<String> authoringGroupList = new ArrayList<>();
        for (Object o : listPersonOrConsortium) {
            if (o instanceof ConsortiumType) {
                ConsortiumType consortium = (ConsortiumType) o;
                authoringGroupList.add(fromXmlAuthoringGroup(consortium));
            }
        }
        return authoringGroupList;
    }

    private static List<Author> authorsFromXml(NameListType authorListXML) {
        if (authorListXML == null) return Collections.emptyList();

        List<Object> listPersonOrConsortium = authorListXML.getConsortiumOrPerson();

        List<Author> authorList = new ArrayList<>();
        for (Object o : listPersonOrConsortium) {
            if (o instanceof PersonType) {
                PersonType person = (PersonType) o;
                authorList.add(fromXmlAuthor(person));
            }
        }
        return authorList;
    }

    public static void updateToXmlCitatation(
            ObjectFactory xmlUniprotFactory, CitationType xmlCitation, Citation citation) {

        if ((citation.getPublicationDate() != null)
                && !Strings.isNullOrEmpty(citation.getPublicationDate().getValue()))
            xmlCitation.setDate(dateConverter.toXml(citation.getPublicationDate()));
        // Authors and Consortium
        NameListType authorsAndConsortiumList = xmlUniprotFactory.createNameListType();
        if ((citation.getAuthoringGroups() != null) && !citation.getAuthoringGroups().isEmpty()) {
            citation.getAuthoringGroups().stream()
                    .map(val -> toXmlAuthoringGroup(xmlUniprotFactory, val))
                    .forEach(val -> authorsAndConsortiumList.getConsortiumOrPerson().add(val));
        }
        if ((citation.getAuthors() != null) && !citation.getAuthors().isEmpty()) {
            citation.getAuthors().stream()
                    .map(val -> toXmlAuthor(xmlUniprotFactory, val))
                    .forEach(val -> authorsAndConsortiumList.getConsortiumOrPerson().add(val));
        }

        if (!authorsAndConsortiumList.getConsortiumOrPerson().isEmpty()) {
            xmlCitation.setAuthorList(authorsAndConsortiumList);
        }
    }

    public static PersonType toXmlAuthor(ObjectFactory xmlUniprotFactory, Author uniObj) {
        PersonType personXML = xmlUniprotFactory.createPersonType();
        personXML.setName(uniObj.getValue());
        return personXML;
    }

    public static Author fromXmlAuthor(PersonType personType) {
        return new AuthorBuilder(personType.getName()).build();
    }

    public static ConsortiumType toXmlAuthoringGroup(
            ObjectFactory xmlUniprotFactory, String uniObj) {
        ConsortiumType personXML = xmlUniprotFactory.createConsortiumType();
        personXML.setName(uniObj);
        return personXML;
    }

    public static String fromXmlAuthoringGroup(ConsortiumType consortiumType) {
        return consortiumType.getName();
    }
}
