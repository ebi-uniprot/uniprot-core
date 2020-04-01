package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 1 Apr 2020
 *
*/

public interface BuscoReport extends Serializable {
	int getComplete();
	int getCompleteSingle();
	int getCompleteDuplicated();
	int getFragmented();
	int getMissing();
	int getTotal();
	String getLineageDb();
	
	//to be implemented can move this to implement class;
	default String calculateSummary() {
		StringBuilder sb = new StringBuilder();
		//for data: 3775(C)	1639(CS)	2136(CD)	64(F)	111(M)	3950(T)
		//The summary: C:95.6%[S:41.5%,D:54.1%],F:1.6%,M:2.8%,n:3950
		return sb.toString();
	}
}

