package uk.ac.ebi.uniprot.parser.impl.dt;

import uk.ac.ebi.uniprot.validator.dt.DtDateCheck;
import uk.ac.ebi.uniprot.validator.dt.DtVersionCheck;

import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 12/08/13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
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
