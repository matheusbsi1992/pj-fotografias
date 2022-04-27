/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.mb;

import br.com.fotografias.bo.ContaCadastrarUsuarioBO;
import br.com.fotografias.bo.ValidarUsuarioBO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Jandisson
 */
@ManagedBean
@RequestScoped
public class ContaCadastrarUsuarioMB implements Serializable {

    private ContaCadastrarUsuarioBO contaCadastrarUsuarioBO = null;

    private ValidarUsuarioBO validarUsuarioBO = null;

    private PessoaTransfer pessoaTransfer;

    private ContaAlterarUsuarioMB contaAlterarUsuarioMB = null;

    public ContaCadastrarUsuarioMB() {
        if (contaCadastrarUsuarioBO == null) {
            contaCadastrarUsuarioBO = new ContaCadastrarUsuarioBO();
        }
        if (validarUsuarioBO == null) {
            validarUsuarioBO = new ValidarUsuarioBO();
        }

        if (contaAlterarUsuarioMB == null) {
            contaAlterarUsuarioMB = new ContaAlterarUsuarioMB();
        }

    }

    public void cadastrarAction() {
        try {
            contaCadastrarUsuarioBO.cadastrarUsuario(pessoaTransfer);

            FacesContext fc = FacesContext.getCurrentInstance();

            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REGISTRO SALVO COM SUCESSO", ""));

            pessoaTransfer = new PessoaTransfer();
        } catch (Exception ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO NAO FOI SALVO COM SUCESSO", ""));
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
        validarUsuarioBO.validarSenhaCadastrar(context, component, this);
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

}
