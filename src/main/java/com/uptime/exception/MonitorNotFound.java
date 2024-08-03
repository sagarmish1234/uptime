package com.uptime.exception;

public class MonitorNotFound extends RuntimeException{

    public MonitorNotFound(){
        super("Monitor not found");
    }

}
