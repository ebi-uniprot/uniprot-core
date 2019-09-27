package org.uniprot.core.flatfile.parser.impl.dt;

import java.time.LocalDate;

import org.uniprot.core.flatfile.validator.DtDateCheck;
import org.uniprot.core.flatfile.validator.DtVersionCheck;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 12/08/13 Time: 13:27 To change this template use
 * File | Settings | File Templates.
 */
@DtDateCheck
@DtVersionCheck
public class DtLineObject {
    public int entry_version;
    public LocalDate entry_date;
    public int seq_version;
    public LocalDate seq_date;
    public LocalDate integration_date;
    public Boolean isSiwssprot;
}
