package uk.ac.ebi.uniprot.domain.builder;

import uk.ac.ebi.uniprot.domain.Builder;

import java.util.List;

import org.uniprot.core.common.Utils;
import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.disease.impl.DiseaseImpl;
import org.uniprot.core.cv.keyword.Keyword;

public class DiseaseBuilder implements Builder<DiseaseBuilder, Disease> {
    private  String id;
    private String accession;
    private  String acronym;
    private  String definition;
    private List<String> alternativeNames;
    private List<CrossReference> crossReferences;
    private List<Keyword> keywords;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;


    public static DiseaseBuilder newInstance() {
        return new DiseaseBuilder();
    }

    @Override
    public Disease build() {
        return new DiseaseImpl(this.id, this.accession, this.acronym,
                this.definition, this.alternativeNames, this.crossReferences,
                this.keywords, this.reviewedProteinCount, this.unreviewedProteinCount);
    }

    @Override
    public DiseaseBuilder from(Disease instance) {
        this.id = instance.getId();
        this.accession = instance.getAccession();
        this.acronym = instance.getAcronym();
        this.definition = instance.getDefinition();
        this.alternativeNames = instance.getAlternativeNames();
        this.crossReferences = instance.getCrossReferences();
        this.keywords = instance.getKeywords();
        this.reviewedProteinCount = instance.getReviewedProteinCount();
        this.unreviewedProteinCount = instance.getUnreviewedProteinCount();
        return this;
    }

    public DiseaseBuilder id(String id){
        this.id = id;
        return this;
    }

    public DiseaseBuilder accession(String accession){
        this.accession = accession;
        return this;
    }

    public DiseaseBuilder acronym(String acronym){
        this.acronym = acronym;
        return this;
    }

    public DiseaseBuilder definition(String definition){
        this.definition = definition;
        return this;
    }

    // for single string
    public DiseaseBuilder alternativeNames(String alternativeName){
        this.alternativeNames = Utils.nonNullList(this.alternativeNames);
        this.alternativeNames.add(alternativeName);
        return this;
    }

    public DiseaseBuilder alternativeNames(List<String> alternativeNames){
        this.alternativeNames = alternativeNames;
        return this;
    }

    // setter for single object
    public DiseaseBuilder crossReferences(CrossReference crossReference){
        this.crossReferences = Utils.nonNullList(this.crossReferences);
        this.crossReferences.add(crossReference);
        return this;
    }

    public DiseaseBuilder crossReferences(List<CrossReference> crossReferences){
        this.crossReferences = crossReferences;
        return this;
    }

    // setter for single object
    public DiseaseBuilder keywords(Keyword keyword){
        this.keywords = Utils.nonNullList(this.keywords);
        this.keywords.add(keyword);
        return this;
    }

    public DiseaseBuilder keywords(List<Keyword> keywords){
        this.keywords = keywords;
        return this;
    }

    public DiseaseBuilder reviewedProteinCount(Long reviewedProteinCount){
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public DiseaseBuilder unreviewedProteinCount(Long unreviewedProteinCount){
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

}
