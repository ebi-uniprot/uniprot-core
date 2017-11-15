package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 * Created by IntelliJ IDEA.
 * User: barrera
 * Date: 25/05/11
 * Time: 15:48
 *
 * The sample source is the orginal biological "container"
 * where the sequence was collected from.
 *
 */

public interface SampleSource extends Value, HasEvidences{
    public SampleSourceType getType();
}
