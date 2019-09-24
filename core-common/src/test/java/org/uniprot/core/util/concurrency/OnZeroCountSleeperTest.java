package org.uniprot.core.util.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created 13/07/19
 *
 * @author Edd
 */
class OnZeroCountSleeperTest {
    private static final Logger LOGGER = getLogger(OnZeroCountSleeperTest.class);

    private OnZeroCountSleeper sleeper;

    @BeforeEach
    void setUp() {
        this.sleeper = new OnZeroCountSleeper();
    }

    @Test
    void canCreateDefaultSleeper() {
        assertThat(sleeper.getCount(), is(0));
    }

    @Test
    void canCreateSleeperWithInitialCount() {
        int initialCount = 6;
        OnZeroCountSleeper onZeroCountSleeper = new OnZeroCountSleeper(initialCount, 1000);
        assertThat(onZeroCountSleeper.getCount(), is(initialCount));
    }

    @Test
    void whenInterruptedItCanHandleException() {
        int initialCount = 6;
        OnZeroCountSleeper onZeroCountSleeper = new OnZeroCountSleeper(initialCount, 1000);
        Thread t = Thread.currentThread();
        new Thread(()-> {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          t.interrupt();}).start();
        onZeroCountSleeper.sleepUntilZero();
        assertThat(onZeroCountSleeper.getCount(), is(initialCount));
    }

    @Test
    void canIncrementSleeper() {
        sleeper.increment();
        assertThat(sleeper.getCount(), is(1));
    }

    @Test
    void canAddToSleeper() {
        int delta = 8;
        sleeper.add(delta);
        assertThat(sleeper.getCount(), is(delta));
    }

    @Test
    void canDecrementSleeper() {
        int delta = 8;
        sleeper.add(delta);
        sleeper.decrement();
        assertThat(sleeper.getCount(), is(delta - 1));
    }

    @Test
    void canMinusFromSleeper() {
        int plusDelta = 8;
        int minusDelta = 5;
        sleeper.add(plusDelta);
        sleeper.minus(minusDelta);
        assertThat(sleeper.getCount(), is(plusDelta - minusDelta));
    }

    @Test
    void willWaitUntilZero() {
    //    OnZeroCountSleeper sleeper = new OnZeroCountSleeper();
        List<Thread> incrementers = Stream
                .generate(() -> new Thread(() -> {
                    int count = sleeper.increment();
                    LOGGER.info("incremented, so that count = {}", count);
                }))
                .limit(5)
                .collect(toList());

        List<Thread> decrementers = Stream
                .generate(() -> new Thread(() -> {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    int count = sleeper.decrement();
                    LOGGER.info("decremented, so that count = {}", count);
                }))
                .limit(5)
                .collect(toList());

        incrementers.forEach(Thread::start);

        decrementers.forEach(Thread::start);

        sleeper.sleepUntilZero();

        assertThat(sleeper.getCount(), is(0));
    }

    @Test
    void willWaitOnlyUntilMaxTimeout() {
        OnZeroCountSleeper sleeper = new OnZeroCountSleeper(0, 1000);
        List<Thread> incrementers = Stream
                .generate(() -> new Thread(() -> {
                    int count = sleeper.increment();
                    LOGGER.info("incremented, so that count = {}", count);
                }))
                .limit(5)
                .collect(toList());

        List<Thread> decrementers = Stream
                .generate(() -> new Thread(() -> {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                    	LOGGER.error("User interrupted", e);
                    }
                    int count = sleeper.decrement();
                    LOGGER.info("decremented, so that count = {}", count);
                }))
                .limit(5)
                .collect(toList());

        incrementers.forEach(Thread::start);

        decrementers.forEach(Thread::start);

        sleeper.sleepUntilZero();

        assertThat(sleeper, is(notNullValue()));
    }
}