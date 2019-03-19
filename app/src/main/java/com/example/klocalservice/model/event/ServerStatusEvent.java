package com.example.klocalservice.model.event;

/**
 * 服务器状态事件
 */
public class ServerStatusEvent {
    private int status;
    private String error;

    public ServerStatusEvent(int status) {
        this.status = status;
    }

    public ServerStatusEvent(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public ServerStatusEvent setStatus(int status) {
        this.status = status;
        return this;
    }
}