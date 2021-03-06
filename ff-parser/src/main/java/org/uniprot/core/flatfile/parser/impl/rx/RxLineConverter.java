package org.uniprot.core.flatfile.parser.impl.rx;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.impl.CrossReferenceBuilder;

public class RxLineConverter
        implements Converter<RxLineObject, List<CrossReference<CitationDatabase>>> {
    @Override
    public List<CrossReference<CitationDatabase>> convert(RxLineObject f) {
        List<CrossReference<CitationDatabase>> xrefs = new ArrayList<>();

        if ((f == null) || (f.rxs == null) || (f.rxs.isEmpty())) return xrefs;
        for (RxLineObject.RX rx : f.rxs) {

            if (rx.type == RxLineObject.DB.PubMed) {
                xrefs.add(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.PUBMED)
                                .id(rx.value)
                                .build());

            } else if (rx.type == RxLineObject.DB.DOI) {
                xrefs.add(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.DOI)
                                .id(rx.value)
                                .build());
            } else if (rx.type == RxLineObject.DB.AGRICOLA) {
                xrefs.add(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.AGRICOLA)
                                .id(rx.value)
                                .build());
            }
        }
        return xrefs;
    }
}
