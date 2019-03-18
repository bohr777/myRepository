package com.owinfo.web.core;


import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.ArrayOfTaskOperationMessage;
import com.owinfo.web.webservice.org.tempuri.IMessageCenterService;
import com.owinfo.web.webservice.org.tempuri.MessageCenterService;

/**
 * Created by Bruce on 2018/1/9.
 */
public class InsertAccomplished implements Runnable{
    private String JSONArray;
    private ArrayOfTaskOperationMessage message;
    private int type;

    public void setJSONArray(String JSONArray) {
        this.JSONArray = JSONArray;
    }

    public void setMessage(ArrayOfTaskOperationMessage message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void run() {
        if(type==2){
            this.Update();
        }else if(type==4){
            this.Delete();
        }
    }
    public String Update(){
        MessageCenterService mcs = new MessageCenterService();
        IMessageCenterService imsc = mcs.getBasicHttpBindingIMessageCenterService();
        imsc.mixOperateTasks(message);
        return imsc.insertAccomplishedTasks(JSONArray);
    }

    public  String Delete(){
        MessageCenterService msc = new MessageCenterService();
        IMessageCenterService imcs = msc.getBasicHttpBindingIMessageCenterService();
        imcs.mixOperateTasks(message);
        return imcs.insertAccomplishedTasks(JSONArray);
    }

}
