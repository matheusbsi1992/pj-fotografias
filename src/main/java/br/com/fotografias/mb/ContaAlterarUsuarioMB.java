/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.ContaAlterarUsuarioBO;
import br.com.fotografias.bo.ValidarUsuarioBO;
import br.com.fotografias.paginas.Paginas;
import br.com.fotografias.transfer.PessoaTransfer;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@SessionScoped
public class ContaAlterarUsuarioMB implements Serializable {

    private List<PessoaTransfer> listaPessoaTransfers;

    private Paginas paginas = null;

    private String nome = "";

    private ContaAlterarUsuarioBO alterarUsuarioBO;

    private PessoaTransfer pessoaTransfer;

    private MBBase mbBase = null;

    private ValidarUsuarioBO validarUsuarioBO = null;

    public boolean status;

    public ContaAlterarUsuarioMB() {
        if (alterarUsuarioBO == null) {
            alterarUsuarioBO = new ContaAlterarUsuarioBO();
        }
        if (validarUsuarioBO == null) {
            validarUsuarioBO = new ValidarUsuarioBO();
        }
        if (paginas == null) {
            paginas = new Paginas();
        }
        if (mbBase == null) {
            mbBase = new MBBase();
        }

        listarAction();
    }

    //metodo responsavel por listar os dados
    public void listarAction() {
        try {
            listaPessoaTransfers = alterarUsuarioBO.ListarTodos();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //metodo responsavel por excluir os dados
    public void excluirAction(short id) {
        try {
            alterarUsuarioBO.excluirContaUsuario(String.valueOf(id));

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DELETADO COM SUCESSO!!!", ""));

            //paginas.valorRedirect("consultarusuario.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listarAction();
    }

    //metodo responsavel por bloquear todos os clientes
    public void bloquearAction() {
        try {
            alterarUsuarioBO.bloquearContaUsuario();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "BLOQUEADO COM SUCESSO!!!", ""));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listarAction();
    }

    //metodo responsavel por consultar os dados
    public void consultarAction() {
        try {
            if (nome.trim().length() == 0 || nome.trim().equalsIgnoreCase("")) {
                listarAction();
            } else {
                listaPessoaTransfers = alterarUsuarioBO.listarNomes(nome);
                if (listaPessoaTransfers.size() > 0) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CONSULTADO COM SUCESSO!!!", ""));
                    setNome(null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //metodo alterarDados para chamar o metodo editarUsuario
    public void atualizarSenha() {
        try {
            paginas.valorRedirect("alterarsenhausuario.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //metodo alterarDados para chamar o metodo editarUsuario
    public void alterarDados() {
        try {
            paginas.valorRedirect("editarusuario.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //metodo responsavel por editarUsuario do sistema
    public String editarUsuario() {

        try {

            pessoaTransfer.setId(Short.parseShort(mbBase.valorParam("identificar")));

            alterarUsuarioBO.alterarContaUsuario(pessoaTransfer);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SALVO", "REGISTRO ALTERADO COM SUCESSO"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return paginas.valorRedirect("consultarusuario.xhtml");
    }

    //metodo responsavel por editarSenha do sistema
    public String editarSenha() {

        try {

            pessoaTransfer.setId(Short.parseShort(mbBase.valorParam("identificarsenha")));

            alterarUsuarioBO.alterarSenha(pessoaTransfer);

            FacesContext fc = FacesContext.getCurrentInstance();

            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SALVO", "ALTERADO A SENHA COM SUCESSO"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return paginas.valorRedirect("consultarusuario.xhtml");
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
    *@metodos de validações
     */
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

    public List<PessoaTransfer> getListaPessoaTransfers() {
        return listaPessoaTransfers;
    }

    public void setListaPessoaTransfers(List<PessoaTransfer> listaPessoaTransfers) {
        this.listaPessoaTransfers = listaPessoaTransfers;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
