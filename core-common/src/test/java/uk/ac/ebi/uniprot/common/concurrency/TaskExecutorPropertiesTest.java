package uk.ac.ebi.uniprot.common.concurrency;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.ac.ebi.uniprot.common.concurrency.TaskExecutorProperties.*;
import static uk.ac.ebi.uniprot.common.concurrency.TaskExecutorProperties.Builder.createTaskExecutorPropertiesBuilder;

/**
 * Created 23/07/19
 *
 * @author Edd
 */
class TaskExecutorPropertiesTest {
    @Test
    void canCreatePropertiesWithDefaultConstructor() {
        TaskExecutorProperties props = new TaskExecutorProperties();
        assertThat(props, is(notNullValue()));
        correctDefaults(props);
    }

    @Test
    void canCreateBuilder() {
        assertThat(createTaskExecutorPropertiesBuilder(), is(notNullValue()));
    }

    @Test
    void canSetCorePoolSize() {
        int value = 5;
        assertThat(createTaskExecutorPropertiesBuilder().corePoolSize(value).build().getCorePoolSize(), is(value));
    }

    @Test
    void canSetMaxPoolSize() {
        int value = 5;
        assertThat(createTaskExecutorPropertiesBuilder().maxPoolSize(value).build().getMaxPoolSize(), is(value));
    }

    @Test
    void canSetQueueCapacity() {
        int value = 5;
        assertThat(createTaskExecutorPropertiesBuilder().queueCapacity(value).build().getQueueCapacity(), is(value));
    }

    @Test
    void canSetKeepAlive() {
        int value = 5;
        assertThat(createTaskExecutorPropertiesBuilder().keepAliveSeconds(value).build().getKeepAliveSeconds(),
                   is(value));
    }

    @Test
    void canSetAllowCoreThreadTimeout() {
        boolean value = false;
        assertThat(createTaskExecutorPropertiesBuilder().allowCoreThreadTimeout(value).build()
                           .isAllowCoreThreadTimeout(),
                   is(value));
    }

    @Test
    void canSetWaitForCompletion() {
        boolean value = true;
        assertThat(createTaskExecutorPropertiesBuilder().waitForTasksToCompleteOnShutdown(value).build()
                           .isWaitForTasksToCompleteOnShutdown(),
                   is(value));
    }

    @Test
    void canSetThreadNamePrefix() {
        String value = "lovely-day";
        assertThat(createTaskExecutorPropertiesBuilder().threadNamePrefix(value).build()
                           .getThreadNamePrefix(), is(value));
    }

    @Test
    void hasCorrectDefaults() {
        TaskExecutorProperties props = createTaskExecutorPropertiesBuilder().build();
        correctDefaults(props);
    }

    private void correctDefaults(TaskExecutorProperties props) {
        assertThat(props.getCorePoolSize(), is(DEFAULT_CORE_POOL_SIZE));
        assertThat(props.getMaxPoolSize(), is(MAX_POOL_SIZE));
        assertThat(props.getQueueCapacity(), is(QUEUE_CAPACITY));
        assertThat(props.getKeepAliveSeconds(), is(KEEP_ALIVE_SECONDS));
        assertThat(props.isAllowCoreThreadTimeout(), is(ALLOW_CORE_THREAD_TIMEOUT));
        assertThat(props.isWaitForTasksToCompleteOnShutdown(), is(WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN));
        assertThat(props.getThreadNamePrefix(), is(THREAD_NAME_PREFIX));
    }
}