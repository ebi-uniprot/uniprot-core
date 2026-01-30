package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineConverter;
import org.uniprot.core.flatfile.parser.impl.ac.UniProtKBAcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineConverter;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineConverter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;


public class AAEntryObjectConverter implements Converter<AAEntryObject, UniProtKBEntry> {
    private static final long serialVersionUID = 1L;
	private final AcLineConverter acLineConverter = new AcLineConverter();
    private final CcLineConverter ccLineConverter ;
    private final DeLineConverter deLineConverter = new DeLineConverter();
    private final FtLineConverter ftLineConverter = new FtLineConverter();
    private final GnLineConverter gnLineConverter = new GnLineConverter();

    private final KwLineConverter kwLineConverter;
   // private final UniProtFactory factory = DefaultUniProtFactory.getInstance();

    public  AAEntryObjectConverter(SupportingDataMap supportingDataMap, boolean ignoreWrong) {
    	ccLineConverter =
                new CcLineConverter(
                        supportingDataMap.getDiseaseMap(),
                        supportingDataMap.getSubcellularLocationMap(),
                        ignoreWrong);
    	 kwLineConverter = new KwLineConverter(supportingDataMap.getKeywordMap(), ignoreWrong);
    }

    @Override
    public UniProtKBEntry convert(AAEntryObject f) {
        clear();

        UniProtKBAcLineObject acLineObj = acLineConverter.convert(f.ac);
        UniProtKBEntryBuilder activeEntryBuilder =
                new UniProtKBEntryBuilder(
                                acLineObj.getPrimaryAccession().getValue(),"", UniProtKBEntryType.AA);

        if (f.cc != null) activeEntryBuilder.commentsSet(ccLineConverter.convert(f.cc));
        if(f.de !=null)
        activeEntryBuilder.proteinDescription(deLineConverter.convert(f.de));
       

        if (f.ft != null) {
            activeEntryBuilder.featuresSet(ftLineConverter.convert(f.ft));
        }
        if (f.gn != null) activeEntryBuilder.genesSet(gnLineConverter.convert(f.gn));

        if (f.kw != null) {
            activeEntryBuilder.keywordsSet(kwLineConverter.convert(f.kw));
        }

        return activeEntryBuilder.build();
    }



    private void clear() {
        ccLineConverter.clear();
        deLineConverter.clear();
        ftLineConverter.clear();
        gnLineConverter.clear();
        kwLineConverter.clear();
       
    }

}
