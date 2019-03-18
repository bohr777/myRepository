package com.owinfo.web.config.util;

import cn.gov.customs.casp.sdk.h4a.OguXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ViewCategory;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.ObjectCategory;
import cn.gov.customs.casp.sdk.h4a.sso.IPassportSSO;
import cn.gov.customs.casp.sdk.h4a.sso.passport.Ticket;
import org.w3c.dom.Element;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static cn.gov.customs.casp.sdk.h4a.ogu.ws.OrganizationCategory.NONE;

public class PassportSSO implements IPassportSSO {
    public void LogOut(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute("currentUser", null);
        request.getSession().removeAttribute("currentUser");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String path = cookie.getPath();
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }


    public void Login(Ticket arg0, HttpServletRequest arg1, HttpServletResponse arg2, String arg3) {
        // TODO Auto-generated method stub

        String loginName = arg0.getLn();
        HttpSession session = arg1.getSession();
        if (session.getAttribute("currentUser") != null) {
            ThrInOneEntity userInfo = (ThrInOneEntity) session.getAttribute("currentUser");
        } else {

            ThrInOneEntity userinfo = new ThrInOneEntity();
            String viewValue = "CCIC_VIEW";
            String currentUserNameString = "";
            currentUserNameString = loginName;
//            String objValuesViewCategory = currentUserNameString + "&forms";
            String objValuesViewCategory = loginName + "&" + arg0.getAbm();
            String parentOrgValues = "F9F9E720-BA2B-8B9D-45B7-13BC9AD4D537";//部署时要修改成海关的在三统一的GUID
            Element returnObject = null;

            try {
                returnObject = OguXmlReaderHelper.getXmlObjectsDetail(
                        viewValue, ViewCategory.ViewCode,
                        objValuesViewCategory, ObjectCategory.USER_IDENTITY,
                        "", NONE,
                        "E_MAIL");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (returnObject != null) {
                String userName = returnObject.getElementsByTagName("DISPLAY_NAME").item(0).getFirstChild().getNodeValue();
                String uuId = returnObject.getElementsByTagName("USER_GUID").item(0).getFirstChild().getNodeValue();
//				String role=returnObject.getElementsByTagName("PARA_DES").item(0).getFirstChild().getNodeValue();
                String allPathName = returnObject.getElementsByTagName("ALL_PATH_NAME").item(0).getFirstChild().getNodeValue().replaceAll("\\\\", "/");
                //String userGroup = getUserGroup(uuId);
                //String userPower = getUserPower(userGroup)
                userinfo.setLoginName(loginName);
                userinfo.setUserName(userName);
                userinfo.setAllPathName(allPathName);
                userinfo.setUuid(uuId);

            }
            session.setAttribute("currentUser", userinfo);
        }
    }

//	private String getUserGroup(String uuid) {
//		String userGroup="";
//		try {
//			userGroup = Auth3in1.GetUserGroup(uuid);
//		} catch (Exception ex) {
//
//		}
//		return userGroup;
//	}
//	private String getUserPower(String usergroup) {
//		String userGroup="";
//		try {
//			userGroup = Auth3in1.GetUserPower(usergroup);
//		} catch (Exception ex) {
//
//		}
//		return userGroup;
//	}
    /*private String getUserPart(String usergroup) {
        String userGroup="";
		try {
			userGroup = Auth3in1.
		} catch (Exception ex) {

		}
		return userGroup;
	}*/

}
