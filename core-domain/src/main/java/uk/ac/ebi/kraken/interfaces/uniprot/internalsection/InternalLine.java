package uk.ac.ebi.kraken.interfaces.uniprot.internalsection;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 * Created by IntelliJ IDEA.
 * User: jerven
 * Date: May 24, 2006
 * Time: 3:32:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InternalLine extends Value {
    public InternalLineType getInternalLineType();

    public void setInternalLineType(InternalLineType type);


}
