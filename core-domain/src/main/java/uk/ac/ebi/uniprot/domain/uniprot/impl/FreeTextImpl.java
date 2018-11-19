package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.FreeText;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public  class FreeTextImpl implements FreeText{
   private final List<EvidencedValue> texts;
	@JsonCreator
   public FreeTextImpl(@JsonProperty("texts") List<EvidencedValue> texts){
       if((texts ==null) || texts.isEmpty()){
           this.texts = Collections.emptyList();
       }else{
           this.texts = Collections.unmodifiableList(texts);
       }
   }
    public List<EvidencedValue> getTexts() {
        return texts;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((texts == null) ? 0 : texts.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FreeTextImpl other = (FreeTextImpl) obj;
        if (texts == null) {
            if (other.texts != null)
                return false;
        } else if (!texts.equals(other.texts))
            return false;
        return true;
    }
}
