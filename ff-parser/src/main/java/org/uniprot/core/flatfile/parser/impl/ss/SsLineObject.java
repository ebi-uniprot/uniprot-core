package org.uniprot.core.flatfile.parser.impl.ss;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * <p/>
 * User: wudong, Date: 22/09/13, Time: 23:12
 */
public class SsLineObject {

	public List<SsLine> ssIALines = new ArrayList<SsLine>() ;
	public List<EvLine> ssEVLines = new ArrayList<EvLine>() ;
	public List<String> ssSourceLines = new ArrayList<String>();

    public static class SsLine {
        public String topic;
        public String text;
    }
	public static class EvLine {
		public String id;
		public String db;
		public String attr_1;
		public String attr_2;
		public LocalDate date;
	}

}
