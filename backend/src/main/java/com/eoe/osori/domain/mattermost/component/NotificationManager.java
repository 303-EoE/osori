package com.eoe.osori.domain.mattermost.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private Logger log = LoggerFactory.getLogger(NotificationManager.class);

    private MatterMostSender mmSender;

    @Autowired
    public NotificationManager(MatterMostSender mmSender){
        this.mmSender = mmSender;
    }

    public void sendNotification(Exception e, String uri, String params){
        log.info("#### SEND Notification");
        mmSender.sendMessage(e, uri, params);
    }
}
