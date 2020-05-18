package org.uniprot.core.antigen;

import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 05/05/2020
 */
public interface AntigenEntry {

    UniProtKBAccession getPrimaryAccession();

    UniProtKBId getUniProtkbId();

    Organism getOrganism();

    Sequence getSequence();

    List<AntigenFeature> getFeatures();

    default boolean hasPrimaryAccession(){
        return Utils.notNull(getPrimaryAccession()) &&
                Utils.notNullNotEmpty(getPrimaryAccession().getValue());
    }

    default boolean hasUniProtkbId(){
        return Utils.notNull(getUniProtkbId()) &&
                Utils.notNullNotEmpty(getUniProtkbId().getValue());
    }

    default boolean hasOrganism(){
        return Utils.notNull(getOrganism());
    }

    default boolean hasSequence(){
        return Utils.notNull(getSequence());
    }

    default boolean hasFeatures(){
        return Utils.notNullNotEmpty(getFeatures());
    }
}
