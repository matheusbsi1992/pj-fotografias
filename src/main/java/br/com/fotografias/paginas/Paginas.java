/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.paginas;

import br.com.fotografias.mb.MBBase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jandisson
 */
public class Paginas {

    
    //redirecionar para pagina especifica
    public String valorRedirect(String paramPage) {
        try {
            if (paramPage != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(paramPage);
                return paramPage;
            }

        } catch (IOException ex) {
            Logger.getLogger(MBBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "";
    }


}
