package org.uniprot.core.flatfile.parser.impl.cc;

import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.LineTransformer;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.cv.disease.DiseaseFileReader;
import org.uniprot.cv.subcell.SubcellularLocationFileReader;

public class CcLineTransformer implements LineTransformer<Comment> {
    private final UniprotkbLineParser<CcLineObject> parser =
            new DefaultUniprotkbLineParserFactory().createCcLineParser();
    private final CcLineFormater formater = new CcLineFormater();
    private final CcLineConverter converter;

    public CcLineTransformer() {
        this("", "");
    }

    public CcLineTransformer(String diseaseFile, String subcellularLocationFile) {
        Map<String, String> subcellularLocationMap =
                new SubcellularLocationFileReader()
                        .parseFileToAccessionMap(subcellularLocationFile);
        Map<String, String> diseaseMap =
                new DiseaseFileReader().parseFileToAccessionMap(diseaseFile);
        converter = new CcLineConverter(diseaseMap, subcellularLocationMap);
    }

    @Override
    public List<Comment> transform(String lines) {
        CcLineObject obj = parser.parse(lines);
        return converter.convert(obj);
    }

    @Override
    public List<Comment> transformNoHeader(String lines) {
        return transform(formater.format(lines));
    }
}
