package org.uniprot.core.flatfile.writer.impl;

import java.util.*;

import org.uniprot.core.flatfile.parser.impl.ac.ACLineBuilder;
import org.uniprot.core.flatfile.parser.impl.cc.CCLineBuilder;
import org.uniprot.core.flatfile.parser.impl.de.DELineBuilder;
import org.uniprot.core.flatfile.parser.impl.dr.DRLineBuilder;
import org.uniprot.core.flatfile.parser.impl.dt.DTLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ft.FTLineBuilder;
import org.uniprot.core.flatfile.parser.impl.gn.GNLineBuilder;
import org.uniprot.core.flatfile.parser.impl.id.IDLineBuilder;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KWLineBuilder;
import org.uniprot.core.flatfile.parser.impl.oc.OCLineBuilder;
import org.uniprot.core.flatfile.parser.impl.og.OGLineBuilder;
import org.uniprot.core.flatfile.parser.impl.oh.OHLineBuilder;
import org.uniprot.core.flatfile.parser.impl.os.OSLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ox.OXLineBuilder;
import org.uniprot.core.flatfile.parser.impl.pe.PELineBuilder;
import org.uniprot.core.flatfile.parser.impl.sq.SQLineBuilder;
import org.uniprot.core.flatfile.parser.impl.ss.SSLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;


public class UniProtFlatfileWriter implements FlatfileWriter<UniProtEntry>{

	    public static final Collection<LineType> ALL_LINES_NO_COPYRIGHT = Arrays.asList(LineType.AC,LineType.CC,
	            LineType.DE,LineType.DR,LineType.DT,LineType.FT,LineType.GN,LineType.ID,LineType.KW,LineType.OC,
	            LineType.OG,LineType.OH,LineType.OS,LineType.OX,LineType.PE,LineType.RA,LineType.RC,LineType.RL,
	            LineType.RG,LineType.RN,LineType.RP,LineType.RT,LineType.RX,LineType.SQ,LineType.EV,LineType.STAR_STAR);
	    public static final Collection<LineType> ALL_LINES_NO_EVIDENCES_NO_COPYRIGHT = Arrays.asList(LineType.AC,LineType.CC,
	            LineType.DE,LineType.DR,LineType.DT,LineType.FT,LineType.GN,LineType.ID,LineType.KW,LineType.OC,
	            LineType.OG,LineType.OH,LineType.OS,LineType.OX,LineType.PE,LineType.RA,LineType.RC,LineType.RL,
	            LineType.RG,LineType.RN,LineType.RP,LineType.RT,LineType.RX,LineType.SQ);
	    public static final Collection<LineType> ALL_LINES_NO_EVIDENCES = Arrays.asList(LineType.AC,LineType.CC,
	            LineType.DE,LineType.DR,LineType.DT,LineType.FT,LineType.GN,LineType.ID,LineType.KW,LineType.OC,
	            LineType.OG,LineType.OH,LineType.OS,LineType.OX,LineType.PE,LineType.RA,LineType.RC,LineType.RL,
	            LineType.RG,LineType.RN,LineType.RP,LineType.RT,LineType.RX,LineType.SQ,LineType.CR);
	private static final ACLineBuilder acLineBuilder = new ACLineBuilder();
	private static final CCLineBuilder ccLineBuilder =new CCLineBuilder();
	private static final DELineBuilder deLineBuilder =new DELineBuilder();
	private static final DRLineBuilder drLineBuilder =new DRLineBuilder();
	private static final DTLineBuilder dtLineBuilder =new DTLineBuilder();
	private static final FTLineBuilder ftLineBuilder =new FTLineBuilder();
	private static final GNLineBuilder gnLineBuilder =new GNLineBuilder();
	private static final IDLineBuilder idLineBuilder =new IDLineBuilder();
	private static final KWLineBuilder kwLineBuilder =new KWLineBuilder();
	private static final OCLineBuilder ocLineBuilder =new OCLineBuilder();
	
	private static final OGLineBuilder ogLineBuilder =new OGLineBuilder();
	private static final OHLineBuilder ohLineBuilder =new OHLineBuilder();
	private static final OSLineBuilder osLineBuilder =new OSLineBuilder();
	private static final OXLineBuilder oxLineBuilder =new OXLineBuilder();
	private static final RLineBuilder rLineBuilder =new RLineBuilder();
	private static final SSLineBuilder ssLineBuilder =new SSLineBuilder();
	private static final SQLineBuilder sqLineBuilder =new SQLineBuilder();
	private static final PELineBuilder peLineBuilder =new PELineBuilder();
	 public static Collection<LineType> ALL_LINES = Arrays.asList(LineType.values());
	 
    private static final List<String> COPY_RIGHT = Arrays.asList( new String[]
    		{
    			"CC   -----------------------------------------------------------------------",
    	        "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms",
    	        "CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License",
    	        "CC   -----------------------------------------------------------------------"
    			}
    		);
    private static final FFLine copyRightLine = FFLines.create(COPY_RIGHT);
    private static final String ENTRY_END ="//";
    public static String write(UniProtEntry entry){
    	return write(entry, true, true);
    }
    public static String write(UniProtEntry entry, Collection<LineType> types){
    	FFLine entryLines = FFLines.create();
    	if(types.contains(LineType.ID)) {
    		IdLineObject idlineObject = new IdLineObject(entry.getUniProtId().getValue(), 
    				entry.getEntryType() == UniProtEntryType.SWISSPROT,
    				 entry.getSequence().getLength()) ;
    		entryLines.add(idLineBuilder.build(idlineObject));
    	}
    	if(types.contains(LineType.AC)){
    		List<UniProtAccession> acc = new ArrayList<UniProtAccession>();
    		acc.add(entry.getPrimaryAccession());
    		acc.addAll(entry.getSecondaryAccessions());
    		entryLines.add(acLineBuilder.buildWithEvidence(acc));
    	}
    	if(types.contains(LineType.DT)){
    		Map.Entry<EntryAudit, UniProtEntryType> dt = new AbstractMap.SimpleEntry<>(entry.getEntryAudit(), entry.getEntryType());
    		entryLines.add(dtLineBuilder.buildWithEvidence(dt));
    	}
    	if(types.contains(LineType.DE))
    		entryLines.add(deLineBuilder.buildWithEvidence(entry.getProteinDescription()));
    	if(types.contains(LineType.GN))
    		entryLines.add(gnLineBuilder.buildWithEvidence(entry.getGenes()));
    	if(types.contains(LineType.OS))
    		entryLines.add(osLineBuilder.buildWithEvidence(entry.getOrganism()));
    	if(types.contains(LineType.OG))
    	entryLines.add(ogLineBuilder.buildWithEvidence(entry.getGeneLocations()));
    	if(types.contains(LineType.OC))
    		entryLines.add(ocLineBuilder.buildWithEvidence(entry.getOrganism().getLineage()));
    	if(types.contains(LineType.OX))
    		entryLines.add(oxLineBuilder.buildWithEvidence(entry.getOrganism()));
    	if(types.contains(LineType.OH))
    		entryLines.add(ohLineBuilder.buildWithEvidence(entry.getOrganismHosts()));
    	if ((types.contains(LineType.RA)) ||
    			(types.contains(LineType.RC)) ||
    			(types.contains(LineType.RL)) ||
    			(types.contains(LineType.RN)) ||
    			(types.contains(LineType.RP)) ||
    			(types.contains(LineType.RT)) ||
    			(types.contains(LineType.RG)) ||
    			(types.contains(LineType.RX))) {
    		int citationNum =0;
    		for( UniProtReference reference:entry.getReferences()){
    			citationNum ++;
    			rLineBuilder.setRN(citationNum);
    			entryLines.add(rLineBuilder.buildWithEvidence(reference));
    		}
    	}
    	if(types.contains(LineType.CC))
    		entryLines.add(buildComment(entry, true));
    	
    	if(types.contains(LineType.CR))
    		entryLines.add(copyRightLine);
  
    	if(types.contains(LineType.DR))
    		entryLines.add(buildDRLines(entry, true));
    	if(types.contains(LineType.PE))
    		entryLines.add(peLineBuilder.buildWithEvidence(entry.getProteinExistence()));
    	if(types.contains(LineType.KW))
    		entryLines.add(kwLineBuilder.buildWithEvidence(entry.getKeywords()));
    	if(types.contains(LineType.FT))
    		entryLines.add(ftLineBuilder.buildWithEvidence(entry.getFeatures()));
    	if(types.contains(LineType.STAR_STAR))
         	entryLines.add(ssLineBuilder.buildWithEvidence(entry.getInternalSection()));
    	if(types.contains(LineType.SQ))
    		entryLines.add(sqLineBuilder.buildWithEvidence(entry.getSequence()));
    	
    	entryLines.add(ENTRY_END);
    	
    	return entryLines.toString();
    }
    public static String write(UniProtEntry entry, LineType type){
    	Collection<LineType> types = new ArrayList<>();
    	types.add(type);
    	return write(entry, types);
    }

    @Override 
    public String write(UniProtEntry entry, boolean isPublic ) {
    		if(isPublic) {
    			return write(entry, isPublic, false);
    		}else {
    			return write(entry, isPublic, true);
    		}
    }
    public static String write(UniProtEntry entry, boolean hasCopyRight, boolean hasInternalSection){
  	  Collection<LineType> set = new HashSet<LineType>(ALL_LINES);
  	  if(!hasCopyRight)
  		  set.remove(LineType.CR);//remove copyright notice
  	  if(!hasInternalSection)
        set.remove(LineType.STAR_STAR);//remove star star line

        return write(entry, set);

    }
  
    private static FFLine buildComment(UniProtEntry entry, boolean showEvidence){
    	FFLine ccLines = FFLines.create();
    	for (CommentType commentType : CommentType.values()) {
    		List<Comment> comments = entry.getCommentByType(commentType);
    		if(!comments.isEmpty()){
    			if(showEvidence)
    				ccLines.add(ccLineBuilder.buildWithEvidence(comments));
    			else
    				ccLines.add(ccLineBuilder.build(comments));

    		}
    	}
    	return ccLines;
    }
         
    private static FFLine buildDRLines(UniProtEntry entry, boolean showEvidence){
    	FFLine drLines = FFLines.create();
    	drLines.add(drLineBuilder.build(entry.getDatabaseCrossReferences()));
   
        // add **PROSITE if it exists
    		InternalSection internalSection = entry.getInternalSection();
    		if((internalSection  ==null) || internalSection.getInternalLines() ==null) {
    			return drLines;
    		}
        for (InternalLine line :internalSection.getInternalLines()) {
            if (InternalLineType.PROSITE==line.getType()){
            	StringBuilder sb =new StringBuilder();
                sb.append("**   ");
                sb.append(line.getType());
                sb.append("; ");
                sb.append(line.getValue());
                drLines.add(sb.toString());
            }
        }
       
    	return drLines;
    }
}