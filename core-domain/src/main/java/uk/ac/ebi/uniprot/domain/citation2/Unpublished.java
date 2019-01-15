package uk.ac.ebi.uniprot.domain.citation2;

/**
 * For unpublished observations the format of the RL line is:
 * <p>
 * RL   Unpublished observations (MMM-YYYY).
 * Where 'MMM' is the month and 'YYYY' is the year.
 * <p>
 * We use the 'unpublished observations' RL line to cite communications by scientists
 * to Swiss-Prot of unpublished information concerning various aspects of a sequence entry.
 */
public interface Unpublished extends Citation {

}
