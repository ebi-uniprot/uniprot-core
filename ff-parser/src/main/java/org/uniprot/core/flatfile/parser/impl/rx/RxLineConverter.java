package org.uniprot.core.flatfile.parser.impl.rx;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.impl.DBCrossReferenceImpl;

public class RxLineConverter
        implements Converter<RxLineObject, List<DBCrossReference<CitationDatabase>>> {
    @Override
    public List<DBCrossReference<CitationDatabase>> convert(RxLineObject f) {
        List<DBCrossReference<CitationDatabase>> xrefs = new ArrayList<>();

        if ((f == null) || (f.rxs == null) || (f.rxs.isEmpty())) return xrefs;
        for (RxLineObject.RX rx : f.rxs) {

            if (rx.type == RxLineObject.DB.PubMed) {
                xrefs.add(new DBCrossReferenceImpl<>(CitationDatabase.PUBMED, rx.value));

            } else if (rx.type == RxLineObject.DB.DOI) {
                xrefs.add(new DBCrossReferenceImpl<>(CitationDatabase.DOI, rx.value));
            } else if (rx.type == RxLineObject.DB.AGRICOLA) {
                xrefs.add(new DBCrossReferenceImpl<>(CitationDatabase.AGRICOLA, rx.value));
            }
        }
        return xrefs;
    }
}
