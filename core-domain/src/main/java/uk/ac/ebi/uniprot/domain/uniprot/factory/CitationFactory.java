package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.CitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.CitationXrefsBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedObservationsBuilder;

public enum CitationFactory {
    INSTANCE;
    
   public <T extends Citation> T createCitation(CitationBuilder<T> builder){
        return builder.build();
    }
   
   public BookBuilder createBookBuilder(){
       return BookBuilder.newInstance();
   }
   
   public ElectronicArticleBuilder createElectronicArticleBuilder(){
       return ElectronicArticleBuilder.newInstance();
   }
   
   public JournalArticleBuilder createJournalArticleBuilder(){
       return JournalArticleBuilder.newInstance();
   }
   
   public PatentBuilder createPatentBuilder(){
       return PatentBuilder.newInstance();
   }
   
   public SubmissionBuilder createSubmissionBuilder(){
       return SubmissionBuilder.newInstance();
   }
   
   public ThesisBuilder createThesisBuilder(){
       return ThesisBuilder.newInstance();
   }
   
   public UnpublishedObservationsBuilder createUnpublishedObservationsBuilder(){
       return UnpublishedObservationsBuilder.newInstance();
   }
   
   
   public CitationXrefsBuilder createCitationXrefsBuilder(){
       return CitationXrefsBuilder.newInstance();
   }
}
