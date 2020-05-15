package org.uniprot.core.uniprotkb.interaction;

import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public interface InteractionMatrixItem extends Serializable {
    UniProtKBAccession getPrimaryAccession();

    UniProtKBId getUniProtKBId();

    Organism getOrganism();

    ProteinExistence getProteinExistence();

    List<Interaction> getInteractions();

    List<DiseaseComment> getDiseases();

    List<SubcellularLocationComment> getSubcellularLocations();
}
