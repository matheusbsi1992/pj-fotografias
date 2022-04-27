/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.CadastrarEventoBO;
import br.com.fotografias.bo.ValidarUsuarioBO;
import br.com.fotografias.paginas.Paginas;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ApplicationScoped
public final class AlterarEventoMB implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private CadastrarEventoBO cadastrarEventoBO = new CadastrarEventoBO();

    private Paginas paginas = new Paginas();

    private ValidarUsuarioBO validarUsuarioBO = new ValidarUsuarioBO();

    private GaleriaTransfer galeriaTransfer = new GaleriaTransfer();

    private List<GaleriaTransfer> listarTodosEventos = new ArrayList<GaleriaTransfer>();

    private Set<GaleriaTransfer> listaDownloadImages = new HashSet<GaleriaTransfer>();

    private Set<String> listarUsuariosEvento = new HashSet<String>();

    private Set<GaleriaTransfer> listafotos = new HashSet<GaleriaTransfer>();

    private StreamedContent image;

    private StreamedContent imagedownload;

    private String pessoaevento = "";

    /**
     *
     * Construtor
     *
     */
    public AlterarEventoMB() {
        listarAction();
    }

    //metodo responsavel por listar os dados
    public void listarAction() {
        try {
            listarTodosEventos = cadastrarEventoBO.ListarTodos();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //metodo responsavel por consultar os dados atraves do nome do cliente ou do evento
    public void consultarAction() {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            if (pessoaevento.trim().length() == 0 || pessoaevento.trim().equalsIgnoreCase("")) {
                listarAction();
            } else {
                listarTodosEventos = cadastrarEventoBO.listarPessoaEvento(pessoaevento);
                if (listarTodosEventos.size() > 0) {
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                    setPessoaevento(null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo responsavel por remover a imagem direcionada de cada usuario
     * representada por suas imagens
     */
    public void removerActionImages() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String idObjeto = params.get("paramid");
            cadastrarEventoBO.excluirImagemGaleria(Integer.valueOf(idObjeto));
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ELIMINADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NAO HOUVE EXITO!!!", ""));
        }
    }

    /**
     * Metodo responsavel por remover a imagem que o usuario nao se identificou
     */
    public void removerActionImageCliente(String idObjeto) {
        GaleriaTransfer galeriatransferimagecliente = new GaleriaTransfer();
        try {
            galeriatransferimagecliente.setId(Short.parseShort(idObjeto));
            listaDownloadImages.remove(galeriatransferimagecliente);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ELIMINADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NAO HOUVE EXITO!!!", ""));
        }
    }

    //metodo responsavel por excluir os dados
    public void excluirAction(short id, String evento) {
        try {
            cadastrarEventoBO.excluirPessoaEvento(String.valueOf(id), evento);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DELETADO COM SUCESSO!!!", ""));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listarAction();
    }

    /**
     * Metodo responsavel por fazer o Download das imagens referente ao usuario
     * cliente da sessao, isto com o id da requisicao da imagem.
     *
     * @param idpessoa
     * @param idimage
     */
    public void selecionarImages() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Short idimage = Short.parseShort(params.get("pid"));
        //informa os valor de: imagem, evento e id da pessoa
        //cadastrarEventoBO.inserirPessoaEventoGaleria(idimage, galeriaTransfer.getNomeevento(), idpessoa);
        //cria um set com o id da imagem
        for (GaleriaTransfer listaDownloadImage : listaDownloadImages) {
            if (Objects.equals(listaDownloadImage.getId(), idimage)) {
                idimage = 0;
                return;
            }
        }

        Set<GaleriaTransfer> galeriadownload = cadastrarEventoBO.downloadGaleria(String.valueOf(idimage));
        //adiciona os dados referentes ao retorno das imagens em uma lista
        listaDownloadImages.add(galeriadownload.iterator().next());

        //obtem o contexto do response para baixar as imagens correspondentes
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            response.setHeader("Content-Disposition", "attachment; filename=" + galeriadownload.iterator().next().getDescricaofotosgaleria());
//            response.setContentType("image/jpeg");
//            response.setContentLength(galeriadownload.iterator().next().getFotosgaleria().length);
//            response.getOutputStream().write(galeriadownload.iterator().next().getFotosgaleria());
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//            FacesContext.getCurrentInstance().responseComplete();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "IMAGEM:" + galeriadownload.iterator().next().getDescricaofotosgaleria() + " .SELECIONADA COM SUCESSO!!!", ""));

    }

    public String visualizarEvento() {
        try {
            return "consultarfotosevento.xhtml";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo responsavel pela obtencao das imagens
     *
     * @return StreamedContent
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public StreamedContent getImage() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String idObjeto = params.get("pid");

        if (idObjeto == null) {
            return new DefaultStreamedContent();
        } else {
            byte[] imagem = cadastrarEventoBO.listarGaleria(idObjeto);
            return new DefaultStreamedContent(new ByteArrayInputStream(imagem));
        }
    }

    /**
     * Metodo responsavel por visualizar as fotos feitos com o download das
     * imagens
     *
     * @return StreamedContent
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public StreamedContent getImagedownload() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String idObjeto = params.get("pidimagedownload");

        if (idObjeto == null) {
            return new DefaultStreamedContent();
        } else {
            byte[] imagem = cadastrarEventoBO.listarGaleria(idObjeto);
            return new DefaultStreamedContent(new ByteArrayInputStream(imagem));
        }
    }

    /*
    *Métodos responsáveis pelas validações
     */
    public void validarNomeEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarNomeEvento(context, component, this);
    }

    public void validarObservacaoEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarEventoObservacao(context, component, this);
    }

    public void validarFotosEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarEventoFotos(context, component, this);
    }

    public void validarData(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarDataEvento(context, component, this);
    }

    /**
     * Métodos getters e setters
     *
     */
    public GaleriaTransfer getGaleriaTransfer() {
        return galeriaTransfer;
    }

    public void setGaleriaTransfer(GaleriaTransfer galeriaTransfer) {
        this.galeriaTransfer = galeriaTransfer;
    }

    public List<GaleriaTransfer> getListarTodosEventos() {
        return listarTodosEventos;
    }

    public void setListarTodosEventos(List<GaleriaTransfer> listarTodosEventos) {
        this.listarTodosEventos = listarTodosEventos;
    }

    public String getPessoaevento() {
        return pessoaevento;
    }

    public void setPessoaevento(String pessoaevento) {
        this.pessoaevento = pessoaevento;
    }

    public Set<String> getListarUsuariosEvento() {
        List<PessoaTransfer> listarUsuario = cadastrarEventoBO.listarUsuarioEvento(galeriaTransfer.getNomeevento());
        listarUsuariosEvento = new HashSet<String>();

        for (PessoaTransfer pessoaTransfer : listarUsuario) {
            listarUsuariosEvento.add(pessoaTransfer.getUsuariopessoa());
        }
        return listarUsuariosEvento;
    }

    /**
     * Listar um unico usuario atraves do seu id
     */
    public String getListarUsuarioEvento() {
        List<PessoaTransfer> listarUsuario = cadastrarEventoBO.listarUsuarioEventoId(galeriaTransfer.getId());
        listarUsuariosEvento = new HashSet<String>();

        for (PessoaTransfer pessoaTransfer : listarUsuario) {
            return (pessoaTransfer.getNomepessoa());
        }
        return null;
    }

    public void setListarUsuariosEvento(Set<String> listarUsuariosEvento) {
        this.listarUsuariosEvento = listarUsuariosEvento;
    }

    public Set<GaleriaTransfer> getListafotos() {
        listafotos = cadastrarEventoBO.listarEventoGaleria(galeriaTransfer.getNomeevento());
        return listafotos;
    }

    public void setListafotos(Set<GaleriaTransfer> listafotos) {
        this.listafotos = listafotos;
    }

    public void setImage(StreamedContent Image) {
        this.image = Image;
    }

    public void setImagedownload(StreamedContent imagedownload) {
        this.imagedownload = imagedownload;
    }

    public Set<GaleriaTransfer> getListaDownloadImages() {
        return listaDownloadImages;
    }

    public void setListaDownloadImages(Set<GaleriaTransfer> listaDownloadImages) {
        this.listaDownloadImages = listaDownloadImages;
    }

}
