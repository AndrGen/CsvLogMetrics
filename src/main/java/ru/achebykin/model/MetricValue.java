package ru.achebykin.model;

public class MetricValue {
    private String EventCount;
    private String ProcessCount;
    private String AvgTimeProcess;

    public String getEventCount() {
        return EventCount;
    }

    public void setEventCount(String eventCount) {
        EventCount = eventCount;
    }

    public String getProcessCount() {
        return ProcessCount;
    }

    public void setProcessCount(String processCount) {
        ProcessCount = processCount;
    }

    public String getAvgTimeProcess() {
        return AvgTimeProcess;
    }

    public void setAvgTimeProcess(String avgTimeProcess) {
        AvgTimeProcess = avgTimeProcess;
    }

    public MetricValue() {
    }

    public MetricValue(String eventCount, String processCount, String avgTimeProcess) {
        EventCount = eventCount;
        ProcessCount = processCount;
        AvgTimeProcess = avgTimeProcess;
    }
}
