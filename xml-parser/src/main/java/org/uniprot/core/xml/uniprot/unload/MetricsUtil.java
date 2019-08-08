package org.uniprot.core.xml.uniprot.unload;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.JmxReporter;

/**
 * Created 24/05/2016
 *
 * @author wudong
 */
public class MetricsUtil {
    public final MetricRegistry metrics = new MetricRegistry();

    private static final MetricsUtil instance = new MetricsUtil();

    public MetricsUtil() {
        final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();
    }

    public static MetricRegistry getMetricRegistryInstance() {
        return instance.metrics;
    }
}
