package uk.ac.ebi.uniprot.xmlparser.uniprot.unload;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

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
