package uk.ac.ebi.uniprot.parser.tsv.taxonomy;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyEntry;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStrain;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxonomyEntryMap implements NamedValueMap {

    private final TaxonomyEntry taxonomyEntry;

    public TaxonomyEntryMap(TaxonomyEntry taxonomyEntry){
        this.taxonomyEntry = taxonomyEntry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("id",String.valueOf(taxonomyEntry.getTaxonId()));
        map.put("parent",String.valueOf(taxonomyEntry.getParentId()));
        map.put("mnemonic", getOrDefaultEmpty(taxonomyEntry.getMnemonic()));
        map.put("scientific_name",getOrDefaultEmpty(taxonomyEntry.getScientificName()));
        map.put("common_name",getOrDefaultEmpty(taxonomyEntry.getCommonName()));
        map.put("synonym",getOrDefaultEmpty(taxonomyEntry.getSynonyms()));
        map.put("other_names",getOrDefaultEmpty(taxonomyEntry.getOtherNames()));
        map.put("link",getOrDefaultEmpty(taxonomyEntry.getLinks()));
        map.put("rank",getRank());
        map.put("reviewed",getReviewed());
        map.put("lineage",getLineage());
        map.put("strain",getStrains());
        map.put("host",getHosts());
        return map;
    }

    private String getReviewed() {
        if(taxonomyEntry.hasStatistics() && taxonomyEntry.getStatistics().hasReviewedProteinCount()){
            return "reviewed";
        }else if(taxonomyEntry.hasStatistics() && taxonomyEntry.getStatistics().hasUnreviewedProteinCount()){
            return "annotated";
        }else {
            return "";
        }
    }

    private String getHosts() {
        if(Utils.notEmpty(taxonomyEntry.getHosts())){
            return taxonomyEntry.getHosts().stream()
                    .map(Taxonomy::getScientificName)
                    .collect(Collectors.joining(", "));
        }else{
            return "";
        }
    }

    private String getStrains() {
        if(Utils.notEmpty(taxonomyEntry.getStrains())){
            return taxonomyEntry.getStrains().stream()
                    .map(TaxonomyStrain::getName)
                    .collect(Collectors.joining(", "));
        }else{
            return "";
        }
    }

    private String getLineage() {
        if(Utils.notEmpty(taxonomyEntry.getLineage())){
            return taxonomyEntry.getLineage().stream()
                    .map(TaxonomyLineage::getScientificName)
                    .collect(Collectors.joining(", "));
        }else{
            return "";
        }
    }

    private String getRank(){
        if(taxonomyEntry.getRank() != null){
            return taxonomyEntry.getRank().toDisplayName();
        }else{
            return "";
        }
    }

    private String getOrDefaultEmpty(String input){
        if(Utils.notEmpty(input)){
            return input;
        }else{
            return "";
        }
    }

    private String getOrDefaultEmpty(List<String> input){
        if(Utils.notEmpty(input)){
            return String.join(", ", input);
        }else{
            return "";
        }
    }
}
