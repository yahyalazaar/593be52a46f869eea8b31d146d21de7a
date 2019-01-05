/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import static com.github.adminfaces.template.util.Assert.has;
import static utils.Utils.addDetailMessage;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import model.Ville;
import services.VilleHome;


@Named
@ViewScoped
public class VilleFormMB implements Serializable {


    private Integer id;
    private Ville ville;


    @Inject
    VilleHome villehome;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            ville = villehome.findById(id);
        } else {
            ville = new Ville();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ville getville() {
        return ville;
    }

    public void setville(Ville ville) {
        this.ville = ville;
    }



    public void save() {
        String msg ;
        
        if (ville.getIdVille() == null) {
        	villehome.insert(ville);
            msg = "Ville <b>" + ville.getNomVille() + "</b> created successfully";
        } else {
        	villehome.update(ville);
            msg = "Ville <b>" + ville.getNomVille() + "</b> updated successfully";
        }
       
        addDetailMessage(msg);
    }

    public void clear() {
        ville = new Ville();
    }

    public boolean isNew() {
        return ville == null || ville.getIdVille() == null;
    }


}
