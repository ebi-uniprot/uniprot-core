package org.uniprot.core.interpro;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
 */

public interface InterProMatch extends Serializable {
	public Integer getFromPos();

	public Integer getToPos();

	public Double getScore();

	public MethodType getMethodType();

	public List<InterProAc> getInterProAcs();

	public String getMethodName();

	public Status getStatus();

	public String getMethodAccession();

	public UniProtKBAccession getUniProtKBAccession();
}
