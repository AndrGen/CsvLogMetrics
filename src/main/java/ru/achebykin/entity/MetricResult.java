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
    private int metricValue;


    public void setMetricValue(int metricValue) {
        this.metricValue = metricValue;
    }
}
