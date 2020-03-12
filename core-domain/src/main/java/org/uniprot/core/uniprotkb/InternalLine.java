package org.uniprot.core.uniprotkb;

import org.uniprot.core.Value;

public interface InternalLine extends Value {
    InternalLineType getType();
}
