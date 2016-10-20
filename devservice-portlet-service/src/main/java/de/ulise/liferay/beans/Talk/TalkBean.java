package de.ulise.liferay.beans.Talk;


import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TalkBean {


    @EJB
    private ScreamBean screamBean;



    public String whatToSay(){
        System.out.println("screamBean: " + screamBean);
        return screamBean.screamMyName();
    }

}
