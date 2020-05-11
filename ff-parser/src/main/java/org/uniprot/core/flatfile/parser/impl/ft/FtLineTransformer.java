package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.flatfile.parser.LineTransformer;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.uniprotkb.feature.Feature;

import java.util.List;

public class FtLineTransformer implements LineTransformer<Feature> {
    private final UniprotKBLineParser<FtLineObject> parser =
            new DefaultUniprotKBLineParserFactory().createFtLineParser();
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
