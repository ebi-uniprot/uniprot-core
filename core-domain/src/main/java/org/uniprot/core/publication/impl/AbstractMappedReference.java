package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.MappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

import java.util.Set;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class AbstractMappedReference implements MappedReference {
    protected final MappedSource source;
    protected final String pubMedId;
    protected final UniProtKBAccession uniProtKBAccession;
    protected final Set<String> sourceCategories;

    public AbstractMappedReference(
            MappedSource source,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories) {
        this.source = source;
        this.pubMedId = pubMedId;
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
    public String getPubMedId() {
        return pubMedId;
    }

    @Override
    public Set<String> getSourceCategories() {
        return sourceCategories;
    }
}
