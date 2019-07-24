package uk.ac.ebi.uniprot.common.concurrency;

import java.util.concurrent.ExecutorService;

import static uk.ac.ebi.uniprot.common.concurrency.TaskExecutorProperties.Builder.createTaskExecutorPropertiesBuilder;

/**
 * Records properties that can be used to configure a {@code TaskExecutor} or {@link ExecutorService}.
 *
 * Created 23/01/17
 * @author Edd
 */
public class TaskExecutorProperties {
    static final int DEFAULT_CORE_POOL_SIZE = 3;
    static final int MAX_POOL_SIZE = 15;
    static final int QUEUE_CAPACITY = 100;
    static final int KEEP_ALIVE_SECONDS = 20 * 60;
    static final boolean ALLOW_CORE_THREAD_TIMEOUT = false;
    static final boolean WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN = true;
    static final String THREAD_NAME_PREFIX = "async-";

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private int keepAliveSeconds;
    private boolean allowCoreThreadTimeout;
    private boolean waitForTasksToCompleteOnShutdown;
    private String threadNamePrefix;

    public TaskExecutorProperties() {
        this(createTaskExecutorPropertiesBuilder());
    }

    private TaskExecutorProperties(Builder builder) {
        this.keepAliveSeconds = builder.keepAliveSeconds;
        this.allowCoreThreadTimeout = builder.allowCoreThreadTimeout;
        this.maxPoolSize = builder.maxPoolSize;
        this.threadNamePrefix = builder.threadNamePrefix;
        this.corePoolSize = builder.corePoolSize;
        this.waitForTasksToCompleteOnShutdown = builder.waitForTasksToCompleteOnShutdown;
        this.queueCapacity = builder.queueCapacity;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public boolean isAllowCoreThreadTimeout() {
        return allowCoreThreadTimeout;
    }

    public boolean isWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
        this.allowCoreThreadTimeout = allowCoreThreadTimeout;
    }

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public static final class Builder {
        private int corePoolSize = DEFAULT_CORE_POOL_SIZE;
        private int maxPoolSize = MAX_POOL_SIZE;
        private int queueCapacity = QUEUE_CAPACITY;
        private int keepAliveSeconds = KEEP_ALIVE_SECONDS;
        private boolean allowCoreThreadTimeout = ALLOW_CORE_THREAD_TIMEOUT;
        private boolean waitForTasksToCompleteOnShutdown = WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN;
        private String threadNamePrefix = THREAD_NAME_PREFIX;

        private Builder() {
        }

        public static Builder createTaskExecutorPropertiesBuilder() {
            return new Builder();
        }

        public Builder corePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        public Builder maxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder queueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public Builder keepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
            return this;
        }

        public Builder allowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
            this.allowCoreThreadTimeout = allowCoreThreadTimeout;
            return this;
        }

        public Builder waitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
            this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
            return this;
        }

        public Builder threadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            return this;
        }

        public TaskExecutorProperties build() {
            return new TaskExecutorProperties(this);
        }
    }
}