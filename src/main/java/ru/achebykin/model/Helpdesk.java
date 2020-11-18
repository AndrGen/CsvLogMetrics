package ru.achebykin.model;

import java.io.Serializable;
import java.util.Date;

public class Helpdesk implements Serializable {
    private int CaseID;
    private int ActivityID;
    private Date CompleteTimestamp;

    public Helpdesk(int caseID, int activityID, Date completeTimestamp) {
        CaseID = caseID;
        ActivityID = activityID;
        CompleteTimestamp = completeTimestamp;
    }

    public int getCaseID() {
        return CaseID;
    }

    public void setCaseID(int caseID) {
        CaseID = caseID;
    }

    public int getActivityID() {
        return ActivityID;
    }

    public void setActivityID(int activityID) {
        ActivityID = activityID;
    }

    public Date getCompleteTimestamp() {
        return CompleteTimestamp;
    }

    public void setCompleteTimestamp(Date completeTimestamp) {
        CompleteTimestamp = completeTimestamp;
    }
}
