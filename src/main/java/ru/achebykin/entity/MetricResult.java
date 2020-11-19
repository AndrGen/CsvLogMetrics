package ru.achebykin.entity;


import javax.persistence.*;

@Entity
@Table(name = "metric_result")
public class MetricResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="metric_name")
    private String metricName;

    @Column(name="metric_value")
    private String metricValue;


    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public long getId() {
        return id;
    }

    public String getMetricName() {
        return metricName;
    }

    public String getMetricValue() {
        return metricValue;
    }
}
