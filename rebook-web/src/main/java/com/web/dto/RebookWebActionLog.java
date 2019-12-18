package com.web.dto;

import com.web.utils.GsonUtils;

public class RebookWebActionLog {
  private long eventID;
  private String userID;
  private String hostIP;
  private String eventType;
  private String action;
  private String objectID;
  private String status;
  private String originationEvent;
  private String description;
  private String actionTime;

  public RebookWebActionLog() {
  }

  public RebookWebActionLog(long eventID, String userID, String hostIP, String eventType,
      String action, String objectID, String status, String originationEvent,
      String description, String actionTime) {
    this.eventID = eventID;
    this.userID = userID;
    this.hostIP = hostIP;
    this.eventType = eventType;
    this.action = action;
    this.objectID = objectID;
    this.status = status;
    this.originationEvent = originationEvent;
    this.description = description;
    this.actionTime = actionTime;
  }

  public long getEventID() { return eventID; }

  public void setEventID(long eventID) { this.eventID = eventID; }

  public String getUserID() { return userID; }

  public void setUserID(String userID) { this.userID = userID; }

  public String getHostIP() { return hostIP; }

  public void setHostIP(String hostIP) { this.hostIP = hostIP; }

  public String getEventType() { return eventType; }

  public void setEventType(String eventType) { this.eventType = eventType; }

  public String getAction() { return action; }

  public void setAction(String action) { this.action = action; }

  public String getObjectID() { return objectID; }

  public void setObjectID(String objectID) { this.objectID = objectID; }

  public String getStatus() { return status; }

  public void setStatus(String status) { this.status = status; }

  public String getOriginationEvent() { return originationEvent; }

  public void setOriginationEvent(String originationEvent) { this.originationEvent = originationEvent; }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  public String getActionTime() { return actionTime; }

  public void setActionTime(String actionTime) { this.actionTime = actionTime; }

  public String toJsonString() {
    return GsonUtils.toJsonString(this);
  }
}
