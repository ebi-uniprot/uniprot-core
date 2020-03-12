package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.List;

import org.uniprot.core.flatfile.parser.LineTransformer;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.uniprotkb.feature.Feature;

public class FtLineTransformer implements LineTransformer<Feature> {
    private final UniprotkbLineParser<FtLineObject> parser =
            new DefaultUniprotkbLineParserFactory().createFtLineParser();
    private final FtLineFormater formater = new FtLineFormater();
    private final FtLineConverter converter = new FtLineConverter();

    @Override
    public List<Feature> transform(String lines) {
        return converter.convert(parser.parse(lines));
    }

    @Override
    public List<Feature> transformNoHeader(String lines) {
        return transform(formater.format(lines));
    }
}
