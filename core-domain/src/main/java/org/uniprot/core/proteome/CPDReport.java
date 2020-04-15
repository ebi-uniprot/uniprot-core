package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 1 Apr 2020
 */
public interface CPDReport extends Serializable {
    int getProteomeCount();

    double getStdCdss();

    int getAverageCdss();

    int getConfidence();

    CPDStatus getStatus();
}
