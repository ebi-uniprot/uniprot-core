package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;

import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ConsortiumType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.NameListType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PersonType;

public class CitationConverterHelper {
	private static final PublicationDateConverter dateConverter = new PublicationDateConverter();
	public static <T extends Citation> void updateFromXmlCitaiton(CitationType xmlCitation,
			AbstractCitationBuilder<? extends AbstractCitationBuilder<?,?>,? extends Citation> builder) {
		if (!xmlCitation.getDbReference().isEmpty()) {
			builder.citationXrefs(
					xmlCitation.getDbReference().stream().map(val -> fromXml(val)).collect(Collectors.toList()));
		}
		if (!Strings.isNullOrEmpty(xmlCitation.getTitle())) {
			builder.title(xmlCitation.getTitle());

		}

		if (!Strings.isNullOrEmpty(xmlCitation.getDate())) {
			builder.publicationDate(dateConverter.fromXml(xmlCitation.getDate()));
		}
		builder.authors(authorsFromXml(xmlCitation.getAuthorList()));
		builder.authoringGroups(authoringGroupfromXml(xmlCitation.getAuthorList()));
	
	}
	
	private static List<String> authoringGroupfromXml(NameListType authorListXML) {
		if (authorListXML == null)
			return Collections.emptyList();

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
		if (authorListXML == null)
			return Collections.emptyList();

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

	public static void updateToXmlCitatation(ObjectFactory xmlUniprotFactory, CitationType xmlCitation,
			Citation citation) {
		if (citation.hasCitationXrefs()) {
			List<DBCrossReference<CitationXrefType>> xrefs = citation.getCitationXrefs();
			for (DBCrossReference<CitationXrefType> xref : xrefs) {
				xmlCitation.getDbReference().add(toXml(xmlUniprotFactory, xref));
			}

		}
		if(!Strings.isNullOrEmpty(citation.getTitle()))
			xmlCitation.setTitle(citation.getTitle());
		if((citation.getPublicationDate() !=null ) && !Strings.isNullOrEmpty(citation.getPublicationDate().getValue()))
			xmlCitation.setDate(
					dateConverter.toXml(citation.getPublicationDate()));
		// Authors and Consortium
		NameListType authorsAndConsortiumList = xmlUniprotFactory.createNameListType();
		if ((citation.getAuthoringGroup() != null) && !citation.getAuthoringGroup().isEmpty()) {
			citation.getAuthoringGroup().stream().map(val -> toXmlAuthoringGroup(xmlUniprotFactory, val))
					.forEach(val -> authorsAndConsortiumList.getConsortiumOrPerson().add(val));
		}
		if ((citation.getAuthors() != null) && !citation.getAuthors().isEmpty()) {
			citation.getAuthors().stream().map(val -> toXmlAuthor(xmlUniprotFactory, val))
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
		return AbstractCitationBuilder.createAuthor(personType.getName());
	}

	public static ConsortiumType toXmlAuthoringGroup(ObjectFactory xmlUniprotFactory, String uniObj) {
		ConsortiumType personXML = xmlUniprotFactory.createConsortiumType();
		personXML.setName(uniObj);
		return personXML;
	}

	public static String fromXmlAuthoringGroup(ConsortiumType consortiumType) {
		return consortiumType.getName();
	}

	private static DbReferenceType toXml(ObjectFactory xmlUniprotFactory, DBCrossReference<CitationXrefType> xref) {
		DbReferenceType dbReferenceType = xmlUniprotFactory.createDbReferenceType();
		dbReferenceType.setId(xref.getId());
		dbReferenceType.setType(xref.getDatabaseType().getName());
		return dbReferenceType;
	}

	private static DBCrossReference<CitationXrefType> fromXml(DbReferenceType xmlRef) {
		CitationXrefType type = CitationXrefType.typeOf(xmlRef.getType());
		return UniProtFactory.INSTANCE.createDBCrossReference(type, xmlRef.getId());

	}
}
