package org.uniprot.core.util.concurrency;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.uniprot.core.util.concurrency.TaskExecutorProperties.*;
import static org.uniprot.core.util.concurrency.TaskExecutorProperties.Builder.createTaskExecutorPropertiesBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created 23/07/19
 *
 * @author Edd
 */
class TaskExecutorPropertiesTest {
    private TaskExecutorProperties taskExecutorProperties = null;

    @BeforeEach
    void setup() {
        taskExecutorProperties = new TaskExecutorProperties();
    }

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
        assertThat(
                createTaskExecutorPropertiesBuilder().corePoolSize(value).build().getCorePoolSize(),
                is(value));
    }

    @Test
    void canSetCorePoolSizeFromProperty() {
        int value = 7;
        taskExecutorProperties.setCorePoolSize(value);

        assertThat(taskExecutorProperties.getCorePoolSize(), is(value));
    }

    @Test
    void canSetMaxPoolSize() {
        int value = 5;
        assertThat(
                createTaskExecutorPropertiesBuilder().maxPoolSize(value).build().getMaxPoolSize(),
                is(value));
    }

    @Test
    void canSetMaxPoolSizeFromProperty() {
        int value = 8;
        taskExecutorProperties.setMaxPoolSize(value);

        assertThat(taskExecutorProperties.getMaxPoolSize(), is(value));
    }

    @Test
    void canSetQueueCapacity() {
        int value = 5;
        assertThat(
                createTaskExecutorPropertiesBuilder()
                        .queueCapacity(value)
                        .build()
                        .getQueueCapacity(),
                is(value));
    }

    @Test
    void canSetQueueCapacityFromProperty() {
        int value = 9;
        taskExecutorProperties.setQueueCapacity(value);

        assertThat(taskExecutorProperties.getQueueCapacity(), is(value));
    }

    @Test
    void canSetKeepAlive() {
        int value = 5;
        assertThat(
                createTaskExecutorPropertiesBuilder()
                        .keepAliveSeconds(value)
                        .build()
                        .getKeepAliveSeconds(),
                is(value));
    }

    @Test
    void canSetKeepAliveFromProperty() {
        int value = 11;
        taskExecutorProperties.setKeepAliveSeconds(value);

        assertThat(taskExecutorProperties.getKeepAliveSeconds(), is(value));
    }

    @Test
    void canSetAllowCoreThreadTimeout() {
        boolean value = false;
        assertThat(
                createTaskExecutorPropertiesBuilder()
                        .allowCoreThreadTimeout(value)
                        .build()
                        .isAllowCoreThreadTimeout(),
                is(value));
    }

    @Test
    void canSetAllowCoreThreadTimeoutFromProperty() {
        boolean value = true;
        taskExecutorProperties.setAllowCoreThreadTimeout(value);

        assertThat(taskExecutorProperties.isAllowCoreThreadTimeout(), is(value));
    }

    @Test
    void canSetWaitForCompletion() {
        boolean value = true;
        assertThat(
                createTaskExecutorPropertiesBuilder()
                        .waitForTasksToCompleteOnShutdown(value)
                        .build()
                        .isWaitForTasksToCompleteOnShutdown(),
                is(value));
    }

    @Test
    void canSetWaitForCompletionFromProperty() {
        boolean value = false;
        taskExecutorProperties.setWaitForTasksToCompleteOnShutdown(value);

        assertThat(taskExecutorProperties.isWaitForTasksToCompleteOnShutdown(), is(value));
    }

    @Test
    void canSetThreadNamePrefix() {
        String value = "lovely-day";
        assertThat(
                createTaskExecutorPropertiesBuilder()
                        .threadNamePrefix(value)
                        .build()
                        .getThreadNamePrefix(),
                is(value));
    }

    @Test
    void canSetThreadNamePrefixFromProperty() {
        String value = "awesome-day";
        taskExecutorProperties.setThreadNamePrefix(value);

        assertThat(taskExecutorProperties.getThreadNamePrefix(), is(value));
    }

    @Test
    void hasCorrectDefaults() {
        TaskExecutorProperties props = createTaskExecutorPropertiesBuilder().build();
        correctDefaults(props);
    }

    @Test
    void hasCorrectDefaultsConstructor() {
        correctDefaults(taskExecutorProperties);
    }

    private void correctDefaults(TaskExecutorProperties props) {
        assertThat(props.getCorePoolSize(), is(DEFAULT_CORE_POOL_SIZE));
        assertThat(props.getMaxPoolSize(), is(MAX_POOL_SIZE));
        assertThat(props.getQueueCapacity(), is(QUEUE_CAPACITY));
        assertThat(props.getKeepAliveSeconds(), is(KEEP_ALIVE_SECONDS));
        assertThat(props.isAllowCoreThreadTimeout(), is(ALLOW_CORE_THREAD_TIMEOUT));
        assertThat(
                props.isWaitForTasksToCompleteOnShutdown(),
                is(WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN));
        assertThat(props.getThreadNamePrefix(), is(THREAD_NAME_PREFIX));
    }
}
