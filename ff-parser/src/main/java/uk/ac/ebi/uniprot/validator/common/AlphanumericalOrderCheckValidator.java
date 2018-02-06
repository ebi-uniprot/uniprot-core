package uk.ac.ebi.uniprot.validator.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Iterator;

/**
 * Check the given iterator is sorted alphanumerically and does not contains duplication.
 */
public class AlphanumericalOrderCheckValidator implements
        ConstraintValidator<AlphanumericalOrderCheck, Iterable<? extends Comparable>> {

    private AlphanumericalOrderCheck.Order order;

    @Override
    public void initialize(AlphanumericalOrderCheck orderCheck) {
        order = orderCheck.order();
    }

    @Override
    public boolean isValid(Iterable<? extends Comparable> comparables, ConstraintValidatorContext constraintValidatorContext) {
        return isSorted(comparables, order == AlphanumericalOrderCheck.Order.DESC);
    }

    public static <T extends Comparable<? super T>>
    boolean isSorted(Iterable<T> iterable, boolean desc) {
        Iterator<T> iter = iterable.iterator();
        if (!iter.hasNext()) {
            return true;
        }
        T t = iter.next();
        while (iter.hasNext()) {
            T t2 = iter.next();
            if (desc) {
            }
            if (t.compareTo(t2) > 0) {
                return false;
            } else {
                if (t.compareTo(t2) < 0) {
                    return false;
                }
            }
            t = t2;
        }

        return true;
    }

}
