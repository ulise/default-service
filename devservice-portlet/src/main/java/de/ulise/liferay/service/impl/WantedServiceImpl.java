package de.ulise.liferay.service.impl;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.RoleServiceUtil;
import de.ulise.liferay.beans.Talk.TalkBean;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JSONWebService
public class WantedServiceImpl extends BaseServiceImpl {




    WantedServiceImpl(){
        createClientBean();
    }

    private void createClientBean(){
        try {
            InitialContext context = new InitialContext();
            talkBean = (TalkBean) context.lookup("java:module/TalkBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }



    // See gere https://docs.liferay.com/portal/6.1/javadocs/index.html?com/liferay/portal/service/package-summary.html

    private User getServiveUser(){
        try {
            return this.getUser();
        } catch (PortalException e) {
            e.printStackTrace();
            return null;
        } catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }
    @JSONWebService(mode = JSONWebServiceMode.IGNORE)
    public List<Role> getUserRoles2() {

        try {
            try {
                return getUser().getRoles();
            } catch (PortalException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SystemException e) {
            e.printStackTrace();
            return null;
        }
    }

    @JSONWebService(mode = JSONWebServiceMode.IGNORE)
    public List<Role> getUserRoles() {
        try {
            return RoleServiceUtil.getUserRoles(this.getUserId());
        } catch (PortalException e) {
            e.printStackTrace();
            return null;
        } catch (SystemException e) {
            e.printStackTrace();
            return null;        }
    }

    private TalkBean talkBean;




    public Map<String, String> sayGoodbye(){
        Map<String, String> m = new HashMap<String, String>();
        String r = "";
        PermissionChecker checker = null;

        try {
            checker = this.getPermissionChecker();
        } catch (PrincipalException e) {
            e.printStackTrace();
        }

        if(checker != null){

            // Check something
            m.put("UserId", "" + checker.getUserId());
        }

        String speech = talkBean.whatToSay();
        m.put("Goodbye", speech);
        m.put("User", getServiveUser().getFullName());

        List<Role> roles = getUserRoles();
        if( roles != null){
            r = "";
            for(Role role: roles){
                r = r + "." + role.getName();
            }
            m.put("Roles1", r);
        }

        roles = getUserRoles2();
        if( roles != null){
            r = "";
            for(Role role: roles){
                r = r + "." + role.getName();
            }
            m.put("Roles2", r);
        }
        return m;
    }
}
