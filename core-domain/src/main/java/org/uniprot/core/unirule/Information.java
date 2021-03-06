package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/** @author sahmad */
public interface Information extends Serializable {
    String getVersion();

    String getComment();

    String getOldRuleNum();

    List<String> getUniProtIds();

    DataClassType getDataClass();

    List<String> getNames();

    Fusion getFusion();

    List<String> getRelated();

    List<UniProtKBAccession> getUniProtAccessions();

    List<String> getDuplicates();

    List<String> getPlasmaIds();

    String getInternal();
}
