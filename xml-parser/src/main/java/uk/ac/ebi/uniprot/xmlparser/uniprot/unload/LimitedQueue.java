package uk.ac.ebi.uniprot.xmlparser.uniprot.unload;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * this class is used by ThreadPoolExecutor that using offer rather than using put
 * @author jieluo
 * @date   30 Mar 2017
 * @time   13:21:26
 *
 * @param <E>
 */
 public class LimitedQueue<E> extends LinkedBlockingQueue<E> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public LimitedQueue(int maxSize) {
        super(maxSize);
    }

    @Override
    public boolean offer(E e) {
        // turn offer() and add() into a blocking calls (unless interrupted)
        try {
            put(e);
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
