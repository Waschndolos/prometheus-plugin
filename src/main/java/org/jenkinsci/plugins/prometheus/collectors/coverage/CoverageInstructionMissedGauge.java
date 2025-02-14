package org.jenkinsci.plugins.prometheus.collectors.coverage;

import edu.hm.hafner.coverage.Coverage;
import edu.hm.hafner.coverage.Metric;
import hudson.model.Run;
import io.jenkins.plugins.coverage.metrics.model.Baseline;
import io.prometheus.client.Gauge;
import io.prometheus.client.SimpleCollector;
import org.jenkinsci.plugins.prometheus.collectors.CollectorType;

import java.util.Optional;

public class CoverageInstructionMissedGauge extends CoverageMetricsCollector<Run<?, ?>, Gauge> {

    protected CoverageInstructionMissedGauge(String[] labelNames, String namespace, String subsystem) {
        super(labelNames, namespace, subsystem);
    }

    @Override
    protected CollectorType getCollectorType() {
        return CollectorType.COVERAGE_INSTRUCTION_MISSED;
    }

    @Override
    protected String getHelpText() {
        return "Returns the number of instructions missed";
    }

    @Override
    protected SimpleCollector.Builder<?, Gauge> getCollectorBuilder() {
        return Gauge.build();
    }

    @Override
    public void calculateMetric(Run<?, ?> jenkinsObject, String[] labelValues) {

        Optional<Coverage> optional = getCoverage(jenkinsObject, Metric.INSTRUCTION, Baseline.PROJECT);
        if (optional.isEmpty()) {
            return;
        }

        Coverage coverage = optional.get();
        collector.labels(labelValues).set(coverage.getMissed());
    }
}
