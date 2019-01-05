package utils;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;

import org.omnifaces.util.Messages;

import model.UserCoproprietaire;
import services.UserCopHome;

/**
 * Created by rmpestano on 07/02/17.
 */
@ApplicationScoped
public class UtilsUserCop implements Serializable {

    private List<UserCoproprietaire> UserCoproprietaires;


    @PostConstruct
    public void init() {
        UserCoproprietaires = new UserCopHome().getAllUserCoproprietaire();
    }

   
     public static void addDetailMessage(String message){
       addDetailMessage(message, null);
    }

    public static void addDetailMessage(String message, FacesMessage.Severity severity){

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        if(severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        } else{
            Messages.add(null,facesMessage);
        }
    }

    @Produces
    public List<UserCoproprietaire> getUserCoproprietaires() {
        return UserCoproprietaires;
    }

}
