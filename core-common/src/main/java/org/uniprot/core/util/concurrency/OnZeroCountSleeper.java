package org.uniprot.core.util.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents an instance that behaves similarly to a {@link CountDownLatch}, which is used to
 * coordinate the completion of tasks on worker threads. Completion occurs when an internal counter reaches zero,
 * or some timeout is exceeded. The difference to {@link CountDownLatch} is that counting up is also allowed. The
 * difference is that this representation uses {@link Thread#sleep(long)} to wait until the zero is reached.
 * <p>
 * Usage:
 * <ol>
 * <li>Create instance</li>
 * <li>Create worker threads which call either {@link #add(int)} or {@link #increment()}, and cause the value of the internal counter to be inreased</li>
 * <li>Worker threads then call {@link #minus(int)} or {@link #decrement()} to reduce the value of the internal counter</li>
 * <li>Meanwhile, on a main thread, someone can call {@link #sleepUntilZero()}, which will cause the thread to sleep until the internal counter has reached zero, or some timeout has expired.</li>
 * </ol>
 * <p>
 * Created 13/07/19
 *
 * @author Edd
 */
public class OnZeroCountSleeper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnZeroCountSleeper.class);
    private static final int SLEEP_MILLIS = 5 * 1000;
    private static final int TIMEOUT_MILLIS_MAX = 10 * 60 * 1000;
    private final int timeoutMillisMax;
    private final AtomicInteger counter;

    public OnZeroCountSleeper() {
        this.counter = new AtomicInteger(0);
        this.timeoutMillisMax = TIMEOUT_MILLIS_MAX;
    }

    public OnZeroCountSleeper(int initialCount, int timeoutMillisMax) {
        this.counter = new AtomicInteger(initialCount);
        this.timeoutMillisMax = timeoutMillisMax;
    }

    public int add(int delta) {
        return counter.addAndGet(delta);
    }

    public int increment() {
        return counter.incrementAndGet();
    }

    public int minus(int delta) {
        return counter.addAndGet(-delta);
    }

    public int decrement() {
        return counter.addAndGet(-1);
    }

    public int getCount() {
        return counter.get();
    }

    public void sleepUntilZero() {
        int timeoutMillisCounter = 0;

        int currentCount;
        while ((currentCount = counter.get()) > 0) {
            LOGGER.info("Waiting {} ms for counter (currently {}), to reach 0", SLEEP_MILLIS, currentCount);
            if (timeoutMillisCounter > timeoutMillisMax) {
                LOGGER.info("Sleeping stopped after timeout reached {} ms", timeoutMillisMax);
                break;
            }
            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                LOGGER.error("User interrupted.", e);
            }
            timeoutMillisCounter += SLEEP_MILLIS;
        }
    }
}
