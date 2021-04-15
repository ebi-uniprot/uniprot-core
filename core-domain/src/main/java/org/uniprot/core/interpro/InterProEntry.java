package org.uniprot.core.interpro;

import java.io.Serializable;
import java.util.Set;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
public interface InterProEntry extends Serializable {
    InterProAc getInterProAc();

    boolean isChecked();

    String getName();

    String getShortName();

    Set<UniProtKBAccession> getSwissProtAccessions();

    Set<UniProtKBAccession> getTremblAccessions();

    Abstract getEntryAbstract();
}
