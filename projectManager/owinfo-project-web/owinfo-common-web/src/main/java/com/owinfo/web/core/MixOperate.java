package com.owinfo.web.core;


import com.owinfo.web.controller.ActivitiController;
import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.ArrayOfTaskOperationMessage;
import com.owinfo.web.webservice.org.tempuri.IMessageCenterService;
import com.owinfo.web.webservice.org.tempuri.MessageCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bruce on 2018/1/5.
 */
public class MixOperate implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(MixOperate.class);

    private String JSONArray;
    private ArrayOfTaskOperationMessage message;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setJSONArray(String JSONArray) {
        this.JSONArray = JSONArray;
    }

    public void setMessage(ArrayOfTaskOperationMessage message) {
        this.message = message;
    }

    public String Update(){
        MessageCenterService mcs = new MessageCenterService();
        IMessageCenterService imsc = mcs.getBasicHttpBindingIMessageCenterService();
        imsc.mixOperateTasks(message);
       return imsc.mixOperateTasksOfJson(JSONArray);
    }

    public String Delete(){
        MessageCenterService msc = new MessageCenterService();
        IMessageCenterService imcs = msc.getBasicHttpBindingIMessageCenterService();
        imcs.mixOperateTasks(message);
        return imcs.mixOperateTasksOfJson(JSONArray);
    }

    @Override
    public void run() {
        if(type==2){
            this.Update();
            System.out.println("   Update    "+ this.Update());
            logger.info("   Update    "+ this.Update());
        }else if(type==4){
            this.Delete();
            System.out.println("   Delete    "+ this.Delete());
            logger.info("   Delete    "+ this.Delete());
        }
    }
}

