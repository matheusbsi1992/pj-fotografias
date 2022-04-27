/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.CadastrarEventoBO;
import br.com.fotografias.bo.ValidarUsuarioBO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.paginas.Paginas;
import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@ViewScoped
public class CadastrarEventoMB implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private CadastrarEventoBO cadastrarEventoBO = new CadastrarEventoBO();

    private GaleriaTransfer galeriaTransfer = new GaleriaTransfer();

    private List<PessoaTransfer> categoryNameList;

    private Paginas paginas = null;

    private ValidarUsuarioBO validarUsuarioBO = null;

    //criacao do atributo listaUploadfoto inicializando...
    private List<FileUploadEvent> listaUploadfoto = new ArrayList<FileUploadEvent>();

    private byte[] byte_array = null;

    private boolean isRenderiza = true;

    public CadastrarEventoMB() {

        if (paginas == null) {
            paginas = new Paginas();
        }
        if (validarUsuarioBO == null) {
            validarUsuarioBO = new ValidarUsuarioBO();
        }

    }

    /**
     * @param e
     * @throws java.io.IOException
     * @IOException
     *
     */
    public void enviarImagem(FileUploadEvent e) throws IOException {
        try {
            listaUploadfoto.add(e);

            InputStream in = new ByteArrayInputStream(e.getFile().getContents());

            BufferedImage bImageFromConvert = ImageIO.read(in);

            //cria arquivos na pasta espefica
            ImageIO.write(bImageFromConvert, "PNG",
                    new File("D:\\IMAGENSPROJETO\\" + e.getFile().getFileName()));

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "FEITO UPLOAD DA FOTO: " + e.getFile().getFileName(), ""));
        } catch (Exception erro) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NAO FOI FEITO UPLOAD DA FOTO: " + e.getFile().getFileName(), ""));
        }
    }

    public void salvarGaleria() {

        try {

            for (int i = 0; i < listaUploadfoto.size(); i++) {
                galeriaTransfer.setDescricaofotosgaleria(listaUploadfoto.get(i).getFile().getFileName());

                StringBuffer str = new StringBuffer();
                str.append(listaUploadfoto.get(i).getFile().getFileName());

                try {
                    galeriaTransfer.setFotosgaleria(read(str, 500, 500, str));
                } catch (IOException ex) {
                    Logger.getLogger(CadastrarEventoMB.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    cadastrarEventoBO.inserirGaleria(galeriaTransfer);
                } catch (SQLException ex) {
                    Logger.getLogger(CadastrarEventoMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception erro) {
            erro.getMessage();
        }
    }

    public void cadastrarAction() throws SQLException {

        if (listaUploadfoto.isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EVENTO NAO FOI FINALIZADO!!!", ""));
            return;
        } else {

            cadastrarEventoBO.inserirEvento(galeriaTransfer);

            //salvar a Lista de Imagens
            salvarGaleria();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "EVENTO FINALIZADO COM SUCESSO!!!", ""));
            //reiniciar valores
            galeriaTransfer = new GaleriaTransfer();
            listaUploadfoto = new ArrayList<FileUploadEvent>();
            isRenderiza = true;

        }
    }

    //fazer a transformacao de uma array de bytes das imagens e de um array de bytes de uma marca d'agua
    public byte[] processImage(byte[] imageData) throws IOException {

        File file;
        file = new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\PJFOTOGRAFIAS1.png");
        byte[] waterMarkData = Files.readAllBytes(file.toPath());

        BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(imageData));
        BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(waterMarkData));
        Graphics2D g2d = img1.createGraphics();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        g2d.setComposite(alpha);
        g2d.drawImage(img2, 0, 0, null);
        g2d.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img1, "PNG", bos);

        return bos.toByteArray();
    }

    public byte[] read(String file) throws FileNotFoundException, IOException {
        byte[] buffer = new byte[1024];

        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while (is.read(buffer) != -1) {
            out.write(buffer);
        }
        try {
            return out.toByteArray();
        } finally {
            is.close();
            out.close();
        }

    }

    /**
     * Metodo responsavel por redimensionar a imegam de form que, esta seja de
     * 500x500
     *
     * @param file
     * @param requiredWidth
     * @param image
     * @param requiredHeight
     * @return
     * @throws java.io.IOException
     */
    public byte[] read(StringBuffer file, double requiredWidth, double requiredHeight, StringBuffer image) throws IOException {

        BufferedImage imagem = null;

        //verificar se existe imagem em um diretorio
        File fileexiste = new File("D:\\IMAGENSPROJETO\\" + file);
        if (fileexiste.exists()) {
            imagem = ImageIO.read(fileexiste);
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CAMINHO DAS IMAGENS NAO EXISTE", ""));
        }

        double originalWidth = imagem.getWidth();
        double originalHeight = imagem.getHeight();
        double newWidth = 0;
        double newHeight = 0;
        double diff = 0;

        if (requiredHeight == 0) {
            requiredHeight = requiredWidth;
        }

        if (requiredWidth == 0) {
            requiredWidth = requiredHeight;
        }

        if (originalWidth < requiredWidth && originalHeight < requiredHeight) {
            return read(file.toString());
        }

        if (requiredWidth == 0 && requiredHeight == 0) {
            return read(file.toString());
        }

        if (originalWidth > originalHeight) {
            diff = originalWidth - originalHeight;
            newWidth = requiredWidth;
            diff = diff / originalWidth;
            newHeight = newWidth - (newWidth * diff);
        } else if (originalWidth < originalHeight) {
            diff = originalHeight - originalWidth;
            newHeight = requiredHeight;
            diff = diff / originalHeight;
            newWidth = newHeight - (newHeight * diff);
        } else {
            if (requiredHeight > requiredWidth) {
                requiredHeight = requiredWidth;
            } else if (requiredHeight < requiredWidth) {
                requiredWidth = requiredHeight;
            }
            newHeight = requiredHeight;
            newWidth = requiredWidth;
        }

        int type = BufferedImage.TYPE_INT_RGB;
        boolean isPng = file.toString().toUpperCase().endsWith("PNG");

        if (isPng) {
            type = BufferedImage.BITMASK;
        }

        BufferedImage new_img = new BufferedImage((int) newWidth, (int) newHeight, type);
        Graphics2D g = new_img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(imagem, 0, 0, (int) newWidth, (int) newHeight, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (isPng) {
            ImageIO.write(new_img, "PNG", out);
        } else {
            ImageIO.write(new_img, "JPG", out);
        }

        try {

            setByte_array(out.toByteArray());

            galeriaTransfer.setFotosgaleria(processImage(getByte_array()));
            //saida de um array de bytes para imagem
            ByteArrayInputStream input_stream = new ByteArrayInputStream(galeriaTransfer.getFotosgaleria());
            BufferedImage final_buffered_image = ImageIO.read(input_stream);
            ImageIO.write(final_buffered_image, "PNG", new File("D:\\IMAGENSPROJETO\\" + image));
        } finally {
            out.close();
        }
        return galeriaTransfer.getFotosgaleria();
    }

    public void renderizar(String nomeevento) {
        isRenderiza = (isRenderiza == true || nomeevento.trim().length() <= 3);
    }

    /*
    *Métodos responsáveis pelas validações
     */
    public void validarNomeEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        isRenderiza = validarUsuarioBO.validarNomeEvento(context, component, value);
        renderizar(value.toString());
        value = null;
    }

    public void validarObservacaoEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarEventoObservacao(context, component, this);
    }

    public void validarFotosEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarEventoFotos(context, component, value);
    }

    public void validarData(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarDataEvento(context, component, this);
    }

    /*
    *Métodos getters e setters responsáveis pela obtenção e retorno dos dados
     */
    public GaleriaTransfer getGaleriaTransfer() {
        return galeriaTransfer;
    }

    public void setGaleriaTransfer(GaleriaTransfer galeriaTransfer) {
        this.galeriaTransfer = galeriaTransfer;
    }

    public List<PessoaTransfer> getCategoryNameList() throws SQLException {
        if (categoryNameList == null) {
            categoryNameList = cadastrarEventoBO.buildCategoryNameList();
        }
        return categoryNameList;
    }

    public void setCategoryNameList(List<PessoaTransfer> categoryNameList) {
        this.categoryNameList = categoryNameList;
    }

    public List<FileUploadEvent> getListaUploadfoto() {
        return listaUploadfoto;
    }

    public void setListaUploadfoto(List<FileUploadEvent> listaUploadfoto) {
        this.listaUploadfoto = listaUploadfoto;
    }

    public void setByte_array(byte[] byte_array) {
        this.byte_array = byte_array;
    }

    public byte[] getByte_array() {
        return byte_array;
    }

    public Boolean getIsRenderiza() {
        return isRenderiza;
    }
}
