package uk.ac.ebi.uniprot.domain.uniprot.citation;

/**
 * For unpublished observations the format of the RL line is:
 *
 * RL   Unpublished observations (MMM-YYYY).
 * Where 'MMM' is the month and 'YYYY' is the year.
 *
 * We use the 'unpublished observations' RL line to cite communications by scientists
 * to Swiss-Prot of unpublished information concerning various aspects of a sequence entry.
 *
 */

public interface UnpublishedObservations extends Citation {

}
