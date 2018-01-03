package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Citation;

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
