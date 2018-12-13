package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeywordImpl extends EvidencedValueImpl implements Keyword {
	private final  String id;
	public static final String DEFAULT_ACCESSION ="KW-00000";
	@JsonCreator
	public KeywordImpl(@JsonProperty("id")  String id,
			@JsonProperty("value") String value, 
			@JsonProperty("evidences") List<Evidence> evidences)  {
        super(value, evidences);
        if(Strings.isNullOrEmpty(id)) {
        	this.id = DEFAULT_ACCESSION;
        }else
        	this.id = id;
    }
	public String getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeywordImpl other = (KeywordImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
