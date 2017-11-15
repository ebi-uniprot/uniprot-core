package uk.ac.ebi.uniprot.domain.citation.factory;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder {
    public static BookBuilder newInstance(){
        return new BookBuilder();
    }
    private  List<String> authoringGroups =new ArrayList<>();
    private  List<Author> authors =new ArrayList<>();
    private  CitationXrefs xrefs;
    private  String title="";
    private  PublicationDate publicationDate;
    private  String bookName;
    private  List<Author> editors =new ArrayList<>();
    private  String firstPage="";
    private  String lastPage="";
    private  String volume="";
    private  String publisher="";
    private  String address="";

    public BookBuilder authoringGroups(List<String> val){
      this.authoringGroups = val;
        return this;
    }
    

    public BookBuilder authors(List<Author> val){
       this.authors = val;
        return this;
    }
    public BookBuilder citationXrefs(CitationXrefs xrefs){
        this.xrefs = xrefs;
        return this;
    }
    public BookBuilder title(String title){
        this.title = title;
        return this;
    }
    public BookBuilder publicationDate(PublicationDate publicationDate){
        this.publicationDate = publicationDate;
        return this;
    }
    public BookBuilder bookName(String bookName){
        this.bookName = bookName;
        return this;
    }
    public BookBuilder editors(List<Author> val){
        this.editors = val;
         return this;
     }
    public BookBuilder firstPage(String firstPage){
        this.firstPage = firstPage;
        return this;
    }
    public BookBuilder lastPage(String lastPage){
        this.lastPage = lastPage;
        return this;
    }
    public BookBuilder volume(String volume){
        this.volume = volume;
        return this;
    }
    public BookBuilder publisher(String publisher){
        this.publisher = publisher;
        return this;
    }
    
    public BookBuilder address(String address){
        this.address = address;
        return this;
    }
}
