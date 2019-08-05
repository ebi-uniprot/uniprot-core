package uk.ac.ebi.uniprot.flatfile.parser.impl.entry;


import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject.ReferenceObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ra.RaLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rc.RcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rg.RgLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rl.RlLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rn.RnLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rp.RpLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rt.RtLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineConverter;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.builder.AbstractCitationBuilder;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.builder.UniProtReferenceBuilder;

public class ReferenceObjectConverter extends EvidenceCollector implements Converter<EntryObject.ReferenceObject, UniProtReference> {
    private final RaLineConverter raLineConverter = new RaLineConverter();
    private final RcLineConverter rcLineConverter = new RcLineConverter();
    private final RgLineConverter rgLineConverter = new RgLineConverter();
    private final RlLineConverter rlLineConverter = new RlLineConverter();
    private final RnLineConverter rnLineConverter = new RnLineConverter();
    private final RpLineConverter rpLineConverter = new RpLineConverter();
    private final RtLineConverter rtLineConverter = new RtLineConverter();
    private final RxLineConverter rxLineConverter = new RxLineConverter();

    @Override
    public UniProtReference convert(ReferenceObject f) {
        AbstractCitationBuilder<? extends AbstractCitationBuilder<?, ?>, ? extends Citation> builder = rlLineConverter
                .convert(f.rl);
        if (f.ra != null) {
            List<Author> authors = raLineConverter.convert(f.ra);
            builder.authors(authors);
        }


        if (f.rg != null) {
            builder.authoringGroups(rgLineConverter.convert(f.rg));
        }

        if (f.rt != null) {
            builder.title(rtLineConverter.convert(f.rt));
        }
        if (f.rx != null) {
            builder.citationXrefs(rxLineConverter.convert(f.rx));
        }
        List<String> referencePositions = new ArrayList<>();
        if (f.rp != null) {

            referencePositions = rpLineConverter.convert(f.rp);
        }
        List<ReferenceComment> referenceComments = new ArrayList<>();
        if (f.rc != null) {

            referenceComments = rcLineConverter.convert(f.rc);
        }
        UniProtReference uniprotReference = new UniProtReferenceBuilder()
                .positions(referencePositions)
                .comments(referenceComments)
                .evidences(rnLineConverter.convert(f.rn))
                .citation(builder.build())
                .build();

        updateEvidences();
        return uniprotReference;
    }

    private void updateEvidences() {
        this.add(rnLineConverter.getEvidences());
        this.add(rcLineConverter.getEvidences());
    }

    @Override
    public void clear() {
        super.clear();
        rnLineConverter.clear();
        rcLineConverter.clear();
    }

}
