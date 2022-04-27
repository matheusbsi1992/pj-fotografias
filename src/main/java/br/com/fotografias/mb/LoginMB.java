/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.CadastrarEventoBO;
import br.com.fotografias.bo.ContaAlterarUsuarioBO;
import br.com.fotografias.bo.LoginBO;
import br.com.fotografias.bo.ValidarUsuarioBO;
import br.com.fotografias.paginas.Paginas;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.faces.application.Application;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.validator.ValidatorException;
import javax.persistence.PreRemove;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {

    private PessoaTransfer pessoaTransfer = new PessoaTransfer();

    private LoginBO loginBO = null;

    private ValidarUsuarioBO validarUsuarioBO = null;

    private Paginas paginas = null;

    private ContaAlterarUsuarioBO contaAlterarUsuarioBO = new ContaAlterarUsuarioBO();

    private HttpSession session = null;

    private List<GaleriaTransfer> eventogaleria = new ArrayList<GaleriaTransfer>();

    private List<GaleriaTransfer> eventogaleria1 = new ArrayList<GaleriaTransfer>();

    private CadastrarEventoBO cadastrarEventoBO = new CadastrarEventoBO();

    private String nomeevento = null;

    public LoginMB() {

        if (loginBO == null) {
            loginBO = new LoginBO();
        }
        if (validarUsuarioBO == null) {
            validarUsuarioBO = new ValidarUsuarioBO();
        }
        if (paginas == null) {
            paginas = new Paginas();
        }
    }

//metodo responsavel por consultar os dados atraves do nome do cliente ou do evento
    public void consultarAction() {
        try {
            if (nomeevento.trim().length() == 0 || nomeevento.trim().equalsIgnoreCase("")) {
                getEventogaleria();
            } else {
                eventogaleria = cadastrarEventoBO.ListarEventosEPessoa(nomeevento.toUpperCase(), pessoaTransfer.getId());
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //obter aplicatiomap e sessionmap
    /*
    *Alterado na data : 12/11/2018
     */
//    private void putUserInSession(PessoaTransfer user) {
//        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(String.valueOf(user.getId()), user.getUsuariopessoa());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(String.valueOf(user.getId()), user);
//    }
    public String paginaLogin() {
        return "http://localhost:8080/pjfotografia/index.xhtml";
    }

    //remove o usuario que esta logado e mata a sessao
    public void logOff() {
        //FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove(String.valueOf(pessoaTransfer.getId()));
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext fc = FacesContext.getCurrentInstance();
        session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        paginas.valorRedirect("/pjfotografia");
    }

    /**
     *
     * @return Usuario do login
     */
    public String loginUsuario() {
        pessoaTransfer = loginBO.loginUsuario(pessoaTransfer.getUsuariopessoa().toUpperCase(), pessoaTransfer.getSenhapessoa());
        if (pessoaTransfer == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUARIO OU SENHA INVALIDOS", "LOGIN INEXISTENTE"));
            return null;
        } else {
            if (!pessoaTransfer.getStatuspessoa().equals("A")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUARIO INATIVO. FAVOR ENTRAR EM CONTATO COM O ADMINISTRADOR", "LOGIN INEXISTENTE"));
                return null;
            } else {
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                if (session != null) {
                    session.setAttribute("usuario", pessoaTransfer);
                }
            }
        }
        return paginas.valorRedirect("photostyle.xhtml");
    }
    //metodo alterarSenhaDados para chamar o metodo loginUsuarioEditar

    public String alterarSenhaDados() {
        try {
            paginas.valorRedirect("/pjfotografia/pages/projeto/editarsenhausuario.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public void loginUsuarioEditarSenha() {

        try {
            String usuarioId = String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(String.valueOf(pessoaTransfer.getId())));
            //pessoaTransfer = (PessoaTransfer) session.getAttribute("usuario");
            if (usuarioId != null) {
                contaAlterarUsuarioBO.alterarSenha(pessoaTransfer);
            }
            FacesContext fc = FacesContext.getCurrentInstance();

            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ALTERADO", "SENHA ALTERADA COM SUCESSO"));

            logOff();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //metodo alterarSenhaDados para chamar o metodo loginUsuarioEditar
    public String alterarDados() {
        try {
            paginas.valorRedirect("/pjfotografia/pages/projeto/editarusuariologado.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //metodo responsavel por editarUsuario do sistema
    public String editarUsuarioLogado() {

        try {

            contaAlterarUsuarioBO.alterarContaUsuario(pessoaTransfer);

            FacesContext fc = FacesContext.getCurrentInstance();

            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REGISTRO ALTERADO COM SUCESSO", ""));
            //logOff();
            paginas.valorRedirect("/pjfotografia/photostyle.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Método responsável por retornar os dados do usuário ao qual está
     * vinculado a um/mais eventos
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> getEventogaleria() {
        if (nomeevento != null && pessoaTransfer.getId() > 0) {
            return eventogaleria = cadastrarEventoBO.ListarEventosEPessoa(nomeevento, pessoaTransfer.getId());
        }
        return eventogaleria = cadastrarEventoBO.ListarEventosPessoa(pessoaTransfer.getId());
    }

    public void setEventogaleria(List<GaleriaTransfer> eventogaleria) {
        this.eventogaleria = eventogaleria;
    }

    /**
     * Validações
     */
    public void validarUsuarioSenhaLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        validarUsuarioBO.validarUsuarioSenhaLogin(context, component, this);
    }

    public void validarAlterarSenhaLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        validarUsuarioBO.validarSenhaEditar(context, component, this);
    }

    public void validarNome(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarNome(context, component, this);
    }

    public void validarUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarUsuario(context, component, this);
    }

    public void validarCPF(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarCPF(context, component, this);
    }

    public void validarEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarEmail(context, component, this);
    }

    public void validarSenha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarSenhaEditar(context, component, this);
    }

    public void validarTipo(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarTipo(context, component, this);
    }

    public void validarComplemento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarComplemento(context, component, this);
    }

    public void validarLogradouro(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarLogradouro(context, component, this);
    }

    public void validarCidade(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarCidade(context, component, this);
    }

    public void validarObservacao(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarObservacao(context, component, this);
    }

    public void validarData(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validarUsuarioBO.validarData(context, component, this);
    }

    //metodo para o botao reset 
    public void limparAction(UIComponent component) {
        if (component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            evh.setSubmittedValue(null);
            evh.setValue(null);
            evh.setLocalValueSet(false);
            evh.setValid(true);
        }
        if (component.getChildCount() > 0) {
            for (UIComponent child : component.getChildren()) {
                this.limparAction(child);
            }
        }
    }


    /*
     *Metodos getters e setters relacionados a Pessoa
     */
    public PessoaTransfer getPessoaTransfer() {
        if (pessoaTransfer == null) {
            pessoaTransfer = new PessoaTransfer();
        }
        return pessoaTransfer;
    }

    public void setPessoaTransfer(PessoaTransfer pessoaTransfer) {
        this.pessoaTransfer = pessoaTransfer;
    }

    public void setNomeevento(String nomeevento) {
        this.nomeevento = nomeevento;
    }

    public String getNomeevento() {
        return nomeevento;
    }

    //verificar se usuario esta na sessao
//public Object currentUser() {
//
//        FacesContext fc = FacesContext.getCurrentInstance();
//        session = (HttpSession) fc.getExternalContext().getSession(false);
//        Object currentUser = session.getAttribute("usuario");
//        if (currentUser == null) {
//            return null;
//        }
//        return currentUser;
//    }
//
    /**
     *
     * @param event
     */
//    @Override
//    public void afterPhase(PhaseEvent event) {
//        FacesContext ctx = event.getFacesContext();
//        ExternalContext ec = ctx.getExternalContext();
//        HttpServletResponse response = (HttpServletResponse) ec.getResponse();
//        String currentPage = ctx.getViewRoot().getViewId();
//
//        boolean isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);
//
//        if (isLoginPage && currentUser() != null) {
//            NavigationHandler nh = ctx.getApplication().getNavigationHandler();
//            nh.handleNavigation(ctx, null, "photostyle.xhtml?faces-redirect=true");
//        } else {
//            if (!isLoginPage && currentUser() == null) {
//                NavigationHandler nh = ctx.getApplication().getNavigationHandler();
//                nh.handleNavigation(ctx, null, "index.xhtml?faces-redirect=true");
//            }
//        }
//
//        response.setHeader("Expires", "-1");
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
//        response.setHeader("Pragma", "no-cache");
//    }
//
//    @Override
//    public void beforePhase(PhaseEvent event) {
//
//    }
//
//    @Override
//    public PhaseId getPhaseId() {
//        return PhaseId.RESTORE_VIEW;
//    }
}
