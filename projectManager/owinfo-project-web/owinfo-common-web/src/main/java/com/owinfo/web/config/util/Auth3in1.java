package com.owinfo.web.config.util;

import cn.gov.customs.casp.sdk.h4a.AccreditBeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.OguBeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.UserCategory;
import cn.gov.customs.casp.sdk.h4a.entity.FunctionsInRoles;
import cn.gov.customs.casp.sdk.h4a.entity.RolesOfUser;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.RoleCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;


public class Auth3in1 {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static String GetUserGroup(String userGuid) {
        String uuid = userGuid;
        String appValue = "XIAMEN_RISK_APP";
        String viewValue = "CCIC_VIEW";
        String roleValue = "";
        int cutIndex = 0;
        try {
            AccreditBeanReaderHelper temp = new AccreditBeanReaderHelper();
            OguBeanReaderHelper tempTwo = new OguBeanReaderHelper();
            RolesOfUser[] roles = temp.getBeanRolesOfUser(
                    userGuid, UserCategory.USER_GUID,
                    "", OrganizationCategory.NONE,
                    appValue, AccreditCategory.Code,
                    viewValue, AccreditCategory.Code,
                    RoleCategories.All, DelegationCategories.All,
                    "");
            for (RolesOfUser t : roles) {
                roleValue = roleValue + t.getCode_name() + ",";
            }

        } catch (Exception e) {
            return null;
        }
        return roleValue;
    }


    public static String GetUserPower(String roleString) {
        Element returnObject = null;
        String appValue = "XIAMEN_RISK_APP";
        String viewValue = "CCIC_VIEW";
        String roleValue = roleString;
        String powerValue = "";
        AccreditBeanReaderHelper temp = new AccreditBeanReaderHelper();
        try {
            FunctionsInRoles[] powers = temp.getBeanFunctionsInRoles(
                    appValue, AccreditCategory.Code,
                    viewValue, AccreditCategory.Code,
                    roleValue, AccreditCategory.Code,
                    "");
            for (FunctionsInRoles t : powers) {
                powerValue = powerValue + "'" + t.getCode_name() + "',";
            }

        } catch (Exception e) {
            return powerValue;
        }
        return powerValue;
    }


    public static void parseElement(Element element) {
        StringBuilder myStringBuilder = new StringBuilder();
        String tagName = element.getNodeName();

        NodeList children = element.getChildNodes();

        myStringBuilder.append("");

        //element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
        NamedNodeMap map = element.getAttributes();

        //如果该元素存在属性
        if (null != map) {
            for (int i = 0; i < map.getLength(); i++) {
                //获得该元素的每一个属性
                Attr attr = (Attr) map.item(i);

                String attrName = attr.getName();
                String attrValue = attr.getValue();
            }
        }


        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            //获得结点的类型
            short nodeType = node.getNodeType();

            if (nodeType == Node.ELEMENT_NODE) {
                //是元素，继续递归
                parseElement((Element) node);
            } else if (nodeType == Node.TEXT_NODE) {
                //递归出口
            } else if (nodeType == Node.COMMENT_NODE) {

                Comment comment = (Comment) node;

                //注释内容
                String data = comment.getData();
            }
        }

    }


}
