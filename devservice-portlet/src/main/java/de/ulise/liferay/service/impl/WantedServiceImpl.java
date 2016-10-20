package de.ulise.liferay.service.impl;


import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import de.ulise.liferay.beans.Talk.TalkBean;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;


@JSONWebService
public class WantedServiceImpl {

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


    private TalkBean talkBean;

    public Map<String, String> sayGoodbye(){
        Map<String, String> m = new HashMap<String, String>();

        String speech = talkBean.whatToSay();
        m.put("Goodbye", speech);
        return m;
    }
}
