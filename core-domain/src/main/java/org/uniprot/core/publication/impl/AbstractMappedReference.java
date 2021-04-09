package org.uniprot.core.publication.impl;

import java.util.Set;

import org.uniprot.core.publication.MappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class AbstractMappedReference implements MappedReference {
    private static final long serialVersionUID = -4074123500981969799L;
    protected final MappedSource source;
    protected final String citationId;
    protected final UniProtKBAccession uniProtKBAccession;
    protected final Set<String> sourceCategories;

    public AbstractMappedReference(
            MappedSource source,
            String citationId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories) {
        this.source = source;
        this.citationId = citationId;
        this.uniProtKBAccession = uniProtKBAccession;
        this.sourceCategories = Utils.unmodifiableSet(sourceCategories);
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniProtKBAccession;
    }

    @Override
    public MappedSource getSource() {
        return source;
    }

    @Override
    public String getCitationId() {
        return citationId;
    }

    @Override
    public Set<String> getSourceCategories() {
        return sourceCategories;
    }
}
