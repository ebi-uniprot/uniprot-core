package uk.ac.ebi.uniprot.parser.impl.rl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.CitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedObservationsBuilder;
import uk.ac.ebi.uniprot.parser.Converter;

public class RlLineConverter implements Converter<RlLineObject, AbstractCitationBuilder< Citation>> {
	@Override
	public AbstractCitationBuilder< Citation> convert(RlLineObject f) {
		if(f.reference instanceof RlLineObject.JournalArticle){
			return (AbstractCitationBuilder) convert((RlLineObject.JournalArticle) f.reference);
		}else if(f.reference instanceof RlLineObject.Book){
			return (AbstractCitationBuilder)convert((RlLineObject.Book) f.reference);
		}else if(f.reference instanceof RlLineObject.EPub){
			return (AbstractCitationBuilder)convert((RlLineObject.EPub) f.reference);
		}else if(f.reference instanceof RlLineObject.Patent){
			return (AbstractCitationBuilder)convert((RlLineObject.Patent) f.reference);
		}else if(f.reference instanceof RlLineObject.Submission){
			return (AbstractCitationBuilder)convert((RlLineObject.Submission) f.reference);
		}else if(f.reference instanceof RlLineObject.Thesis){
			return (AbstractCitationBuilder)convert((RlLineObject.Thesis) f.reference);
		}else if(f.reference instanceof RlLineObject.Unpublished){
			return (AbstractCitationBuilder)convert((RlLineObject.Unpublished) f.reference);
		}
		
		else
			throw new RuntimeException("Unable to parse RL line");


	}
	private JournalArticleBuilder convert(RlLineObject.JournalArticle ja){
		JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
		builder.journalName(ja.journal)
		.firstPage(""+ja.first_page)
		.lastPage(""+ja.last_page)
		.volume("" + ja.volume)
		.publicationDate(AbstractCitationBuilder.createPublicationDate("" +ja.year));
		return builder;
		
		
	}
	private BookBuilder convert (RlLineObject.Book b){
		BookBuilder builder = BookBuilder.newInstance();
		 if(b.page_start !=null)
		 builder.firstPage(""+b.page_start);
		 if(b.page_end !=null)
		 builder.lastPage(""+b.page_end);
		 builder.publicationDate(AbstractCitationBuilder.createPublicationDate("" +b.year));

		 if(b.volume !=null)
			 builder.volume("" + b.volume);
		 if((b.page_start ==null) &&(b.page_end ==null) &&
				 b.pageString !=null){
			 builder.bookName(b.title +", " + b.pageString);
		 }else
			 builder.bookName(b.title);


		 
		 List<Author> editors = new ArrayList<>();
		 for(String val:b.editors){
			 editors.add(AbstractCitationBuilder.createAuthor(val));
		 }
		 builder.editors(editors);
		 if(b.press !=null &&( !b.press.isEmpty())){
			 builder.publisher(b.press);
		 }
		 if((b.place !=null) &&(!b.place.isEmpty())){
			 builder.address(b.place);
		 }
		return builder;
	}
	private ElectronicArticleBuilder convert(RlLineObject.EPub ep){
		String line = ep.title;
		ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
		String pubDate=null;
		if(line.endsWith(")")){
			int index =line.lastIndexOf('(');
			if(index !=-1){
				pubDate = line.substring(index+1, line.length()-1);
				 line = line.substring(0, index).trim();
				 builder.publicationDate(AbstractCitationBuilder.createPublicationDate(pubDate));

			}
		}
		if (line.startsWith("Plant Gene Register ")) {
			builder.journalName("Plant Gene Register");
			builder.locator(line.substring(line.lastIndexOf(" ") + 1, line.length() ));

		} else if (line.startsWith("Worm Breeder's Gazette")) {
			builder.journalName("Worm Breeder's Gazette");
			builder.locator(line.substring(line.lastIndexOf(" ") + 1, line.length() ));
		} else {
			builder.journalName(line);
		}
		return builder;
	}
	private PatentBuilder convert(RlLineObject.Patent patent){
		PatentBuilder builder = PatentBuilder.newInstance();
		builder.patentNumber(patent.patentNumber);
		String day = ""+patent.day;
		if(patent.day<10){
			day ="0"+patent.day;
		}
		String date = day +"-" + patent.month +"-" +patent.year;
		 builder.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		return builder;
	}
	
	private SubmissionBuilder convert(RlLineObject.Submission submission){
		SubmissionBuilder builder =SubmissionBuilder.newInstance();
		SubmissionDatabase sdb = SubmissionDatabase.UNKNOWN;
		switch (submission.db){
		case EMBL:
			 sdb =SubmissionDatabase.EMBL_GENBANK_DDBJ;
			break;
		case UNIPROTKB:
			 sdb =SubmissionDatabase.UNIPROTKB;
			break;
		case PDB:
			 sdb =SubmissionDatabase.PDB;
			break;
		case PIR:
			 sdb =SubmissionDatabase.PIR;
			break;
		default:
			throw new RuntimeException("submission db is not supported");
		}
		builder.submittedToDatabase(sdb);
		String date = submission.month +"-"+submission.year;
		 builder.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		return builder;
	}
	private ThesisBuilder convert(RlLineObject.Thesis thesis){
		ThesisBuilder builder = ThesisBuilder.newInstance();
		builder.publicationDate(AbstractCitationBuilder.createPublicationDate(""+thesis.year));
	    builder.institute(thesis.institute);
		 if(thesis.country !=null)
			 builder.address(thesis.country);
		return builder;
	}
	private UnpublishedObservationsBuilder convert(RlLineObject.Unpublished unpub){
		UnpublishedObservationsBuilder builder = UnpublishedObservationsBuilder.newInstance();
		builder.publicationDate(AbstractCitationBuilder.createPublicationDate(unpub.month +"-"+unpub.year));
		return builder;
	}
}
