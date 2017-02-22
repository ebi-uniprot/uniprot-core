package uk.ac.ebi.uniprot.domain.uniprot.citation;




/**
 * The RL line for a journal citation includes the journal abbreviation, the volume number, the page range and the year. The format for such an RL line is:
 *
 * RL   Journal_abbrev Volume:First_page-Last_page(YYYY).
 * Journal names are abbreviated according to the conventions used by the National Library of Medicine (NLM) and are based on the existing ISO and ANSI standards. A list of the abbreviations currently in use is given in the document file jourlist.txt
 *
 * An example of an RL line is:
 * 
 * RL   J. Mol. Biol. 168:321-331(1983).
 * When a reference is made to a paper which is 'in press' at the time the database is released, the page range, and possibly the volume number, are indicated as '0' (zero). An example of such an RL line is shown here:
 *
 * RL   Int. J. Parasitol. 0:0-0(2005).
 */

public interface JournalArticle extends Citation{

    public JournalName getJournalName();
    public String getFirstPage();
	public String getLastPage();
    public String getVolume();
}


