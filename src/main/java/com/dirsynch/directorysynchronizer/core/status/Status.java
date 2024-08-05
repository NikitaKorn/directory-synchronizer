package com.dirsynch.directorysynchronizer.core.status;

import com.dirsynch.directorysynchronizer.di.annotation.Component;

@Component
public class Status {
    private String status = StatusConstants.UNDEFINED.getTitle();

    synchronized public void setStatus(String status){
        this.status = status;
    }

    synchronized public void setStatus(StatusConstants status){
        this.status = status.getTitle();
    }

    synchronized public String getCurrentStatus(){
        return status;
    }
}
