package uk.ac.ebi.uniprot.parser.impl.dt;

import java.time.LocalDate;

import uk.ac.ebi.uniprot.validator.DtDateCheck;
import uk.ac.ebi.uniprot.validator.DtVersionCheck;

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
