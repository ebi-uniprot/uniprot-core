package org.uniprot.core.flatfile.parser.impl.entry;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.builder.AbstractCitationBuilder;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject.ReferenceObject;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineConverter;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineConverter;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineConverter;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineConverter;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineConverter;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineConverter;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineConverter;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineConverter;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.builder.UniProtReferenceBuilder;

public class ReferenceObjectConverter extends EvidenceCollector
        implements Converter<EntryObject.ReferenceObject, UniProtReference> {
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
        AbstractCitationBuilder<? extends AbstractCitationBuilder<?, ?>, ? extends Citation>
                builder = rlLineConverter.convert(f.rl);
        if (f.ra != null) {
            List<Author> authors = raLineConverter.convert(f.ra);
            builder.authorsSet(authors);
        }

        if (f.rg != null) {
            builder.authoringGroupsSet(rgLineConverter.convert(f.rg));
        }

        if (f.rt != null) {
            builder.title(rtLineConverter.convert(f.rt));
        }
        if (f.rx != null) {
            builder.citationCrossReferencesSet(rxLineConverter.convert(f.rx));
        }
        List<String> referencePositions = new ArrayList<>();
        if (f.rp != null) {

            referencePositions = rpLineConverter.convert(f.rp);
        }
        List<ReferenceComment> referenceComments = new ArrayList<>();
        if (f.rc != null) {

            referenceComments = rcLineConverter.convert(f.rc);
        }
        UniProtReference uniprotReference =
                new UniProtReferenceBuilder()
                        .referencePositionsSet(referencePositions)
                        .referenceCommentsSet(referenceComments)
                        .evidencesSet(rnLineConverter.convert(f.rn))
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
