package uk.ac.ebi.uniprot.domain.builder;

import uk.ac.ebi.uniprot.cv.disease.CrossReference;
import uk.ac.ebi.uniprot.cv.disease.Disease;
import uk.ac.ebi.uniprot.cv.disease.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.domain.Builder;

import java.util.List;

public class DiseaseBuilder implements Builder<DiseaseBuilder, Disease> {
    private  String id;
    private String accession;
    private  String acronym;
    private  String definition;
    private List<String> alternativeNames;
    private List<CrossReference> crossReferences;
    private List<Keyword> keywords;
    private Long proteinCount;


    public static DiseaseBuilder newInstance() {
        return new DiseaseBuilder();
    }

    @Override
    public Disease build() {
        return new DiseaseImpl(this.id, this.accession, this.acronym,
                this.definition, this.alternativeNames, this.crossReferences, this.keywords, this.proteinCount);
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
        this.proteinCount = instance.getProteinCount();
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

    public DiseaseBuilder alternativeNames(List<String> alternativeNames){
        this.alternativeNames = alternativeNames;
        return this;
    }

    public DiseaseBuilder crossReferences(List<CrossReference> crossReferences){
        this.crossReferences = crossReferences;
        return this;
    }

    public DiseaseBuilder keywords(List<Keyword> keywords){
        this.keywords = keywords;
        return this;
    }

    public DiseaseBuilder proteinCount(Long proteinCount){
        this.proteinCount = proteinCount;
        return this;
    }

}
