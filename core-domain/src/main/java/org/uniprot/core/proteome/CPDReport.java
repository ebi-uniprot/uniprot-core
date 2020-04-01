package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 1 Apr 2020
 *
*/

public interface CPDReport {
	int getProteomeCount();
	double getStdCdss();
	int getAverageCdss(); 
	int getConfidence();
	CPDStatus getStatus();
	
	
	public static enum CPDStatus implements EnumDisplay<CPDStatus> {
		CLOSE_TO_STANDARD(1, "Close to Standard"),
		STANDARD(2, "Standard"),
		OUTLIER (3, "Outlier"),
		UNKNOWN (4, "Unknown");
		private int id;
		private String displayName;
		CPDStatus(int id, String displayName){
			this.id = id;
			this.displayName = displayName;
		}
		public int getId() {
			return id;
		}
		@Override
		public String toDisplayName() {
			return displayName;
		}
		
	}
}

