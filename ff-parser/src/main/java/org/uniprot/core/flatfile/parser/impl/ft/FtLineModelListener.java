package org.uniprot.core.flatfile.parser.impl.ft;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.FtLineParserBaseListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class FtLineModelListener extends FtLineParserBaseListener implements ParseTreeObjectExtractor<FtLineObject> {

	private FtLineObject object;

	private FtLineObject.FT ft ;

    @Override
    public void enterFt_ft(@NotNull FtLineParser.Ft_ftContext ctx) {
        this.object=new FtLineObject();
    }

    @Override
	public void enterFt_line(@NotNull FtLineParser.Ft_lineContext ctx) {
		ft = new  FtLineObject.FT();
	}

	@Override
	public void exitFt_line(@NotNull FtLineParser.Ft_lineContext ctx) {
		ft.ft_text = updateAltSeqText(ft.ft_text);
		object.fts.add(ft);
		ft = null;
	}

	@Override
	public void exitFt_key(@NotNull FtLineParser.Ft_keyContext ctx) {
	   ft.type = FtLineObject.FTType.valueOf(ctx.getText());
	}

//	@Override
//	public void exitEvidence(@NotNull FtLineParser.EvidenceContext ctx) {
//		EvidenceInfo.processEvidence(object.getEvidenceInfo(), ft, ctx.EV_TAG());
//	}

	@Override
	public void exitLoc_end(@NotNull FtLineParser.Loc_endContext ctx) {
		ft.location_end = ctx.FT_LOCATION().getText().trim();
	}

	@Override
	public void exitLoc_start(@NotNull FtLineParser.Loc_startContext ctx) {
		ft.location_start = ctx.FT_LOCATION().getText().trim();
	}

	@Override
	public void exitFt_text(@NotNull FtLineParser.Ft_textContext ctx) {
		String text= ctx.getText();
		String[] values =EvidenceInfo.splitEvidenceString(text);		
		ft.ft_text =   values[0];;

		if (values.length == 2) {
			String evidenceStr = values[1];
			List<String> strings = EvidenceInfo.parseEvidenceFromString(evidenceStr);
			object.getEvidenceInfo().evidences.put(ft, strings);

			//removing the additional dot. when evidence exists.
			//it is possbile that ft_text is empty.
			ft.ft_text = !values[0].isEmpty()?values[0].substring(0, values[0].length()-1) : "";
		}
	}

	@Override
	public void exitFt_id(@NotNull FtLineParser.Ft_idContext ctx) {
		ft.ftId = ctx.FTID_VALUE().getText();

	}

	public FtLineObject getObject() {
		return object;
	}
	
	private String updateAltSeqText(String text) {
		if(!FtLineObject.hasAltSeq(ft.type))
			return text;
		return fixVarSeqSpace(text);
	}
	
	private String fixVarSeqSpace(String value) {
		String temp =value;
		int index = firstNonCapital(value);
		if(index ==-1)
			return value;
		int spaceLocation =-1;
		do {
			 spaceLocation = getSpaceLocation(temp, index);
			 if(spaceLocation ==-1)
				 break;
			 String val = temp.substring(0, spaceLocation) + temp.substring(spaceLocation+1);
			 temp = val;
			 index -=1;
		}while (spaceLocation !=-1);
		
		return temp;
	}
	
	private int getSpaceLocation(String value, int index) {
		for (int i =0 ;i<index; i ++) {
			if(isCapital(value.charAt(i))) {
				if((i+2)<index) {
					if((value.charAt(i+1) ==' ') && isCapital(value.charAt(i+2))){
						return i+1;
					}
				}
			}
		}
		return -1;
	}
	
	private boolean isCapital(char c) {
		return c>='A' && c <='Z';
	}
	private int firstNonCapital(String val) {
		for(int i=0; i< val.length(); i++) {
			char c = val.charAt(i);
			if(isCapital(c))
				continue;
			if((c ==' ') || (c =='-') || (c =='>')) {
				continue;
			}
			return i;
		}
		return -1;
	}
	
}
