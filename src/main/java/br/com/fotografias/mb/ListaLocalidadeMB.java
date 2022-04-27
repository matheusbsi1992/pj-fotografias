/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.ListarMetodosPesquisaBO;
import br.com.fotografias.transfer.PessoaTransfer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jordão Santos
 */
@ManagedBean
@ViewScoped
//@SessionScoped
public class ListaLocalidadeMB implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PessoaTransfer> listaLocalidade = new ArrayList<PessoaTransfer>();
    private PessoaTransfer pessoaLocalidade = new PessoaTransfer();
    private String Cidade;
    private String Estado;

    /**
     * * @return the listaLocalidade
     */
    public List<PessoaTransfer> getListaLocalidade() {
        return listaLocalidade;
    }

    /**
     * * @param listaLocalidade the listaLocalidade to set
     */
    public void setListaLocalidade(List<PessoaTransfer> listaLocalidade) {
        this.listaLocalidade = listaLocalidade;
    }

    //@PostConstruct
    public void listar() {
        ListarMetodosPesquisaBO projetoeventos = new ListarMetodosPesquisaBO();
        try {
            listaLocalidade = projetoeventos.getByLocalidade(Cidade, Estado);
            //Messages.addGlobalInfo("Fotos Guardadas com sucesso");
            System.out.println("Salvo com Sucesso");
        } catch (Exception ex) {
           /// Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado");
            Logger.getLogger(ListaLocalidadeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarLocalidade() {
        ListarMetodosPesquisaBO projetoeventos = new ListarMetodosPesquisaBO();
        try {
            listaLocalidade = projetoeventos.getByLocalidade(Cidade, Estado);
            //Messages.addGlobalInfo("Fotos Guardadas com sucesso");
            System.out.println("Salvo com Sucesso");
        } catch (Exception ex) {
           /// Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado");
            Logger.getLogger(ListaLocalidadeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the Cidade
     */
    public String getCidade() {
        return Cidade;
    }

    /**
     * @param Cidade the Cidade to set
     */
    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    /**
     * @return the Estado
     */
    public String getEstado() {
        return Estado;
    }

    /**
     * @param Estado the Estado to set
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

}
