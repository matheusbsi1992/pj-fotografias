/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.EventoDAO;
import br.com.fotografias.dao.LoginDAO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jandisson
 */
public class ValidarUsuarioBO {

    private String msg = null;

    private PessoaDAO pessoaDAO = null;

    private PessoaTransfer pessoaTransfer = null;

    private LoginDAO loginDAO = null;

    private EventoDAO eventoDAO = null;

    private GaleriaTransfer galeriaTransfer = null;

    private String cpf = "";

    public ValidarUsuarioBO() {
        if (pessoaDAO == null) {
            pessoaDAO = new PessoaDAO();
        }
        if (pessoaTransfer == null) {
            pessoaTransfer = new PessoaTransfer();
        }
        if (loginDAO == null) {
            loginDAO = new LoginDAO();
        }
        if (galeriaTransfer == null) {
            galeriaTransfer = new GaleriaTransfer();
        }
        if (eventoDAO == null) {
            eventoDAO = new EventoDAO();
        }
    }

    /*
    *Metodos de Validacoes
     */
    //metodo para Validar Nome do usuario
    public void validarNome(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object nome = ((UIInput) context.getViewRoot().findComponent("formulario:nome")).getSubmittedValue();
        pessoaTransfer.setNomepessoa(nome.toString().toUpperCase());

        msg = "INFORME NOME";
        if ((pessoaTransfer.getNomepessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getNomepessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "O NOME SO DEVE TER 5 E 100 DE DIGITOS";
        if (pessoaTransfer.getNomepessoa().trim().length() <= 4 || pessoaTransfer.getNomepessoa().trim().length() > 100) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo validar Usuario
    public void validarUsuario(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object usuario = ((UIInput) context.getViewRoot().findComponent("formulario:usuario")).getSubmittedValue();
        pessoaTransfer.setId(Short.valueOf(id.toString()));
        pessoaTransfer.setUsuariopessoa(usuario.toString().toUpperCase());

        msg = "INFORME USUARIO";
        if ((pessoaTransfer.getUsuariopessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getUsuariopessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));

        }
        msg = "O USUARIO SO DEVE TER 5 E 100 DE DIGITOS";
        if (pessoaTransfer.getUsuariopessoa().trim().length() <= 4 || pessoaTransfer.getUsuariopessoa().trim().length() > 100) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        if (pessoaDAO.IdUsuarioConfere(pessoaTransfer.getId(), pessoaTransfer.getUsuariopessoa()) || pessoaDAO.existeUsuario(pessoaTransfer.getUsuariopessoa()) == false) {
            return;
        } else {
            msg = "USUARIO EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

    }

    //metodo validar CPF
    public void validarCPF(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();
        Object cpf = ((UIInput) context.getViewRoot().findComponent("formulario:cpf")).getSubmittedValue();
        pessoaTransfer.setId(Short.valueOf(id.toString()));
        pessoaTransfer.setCpfpessoa(cpf.toString());

        msg = "INFORME CPF";
        if ((pessoaTransfer.getCpfpessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getCpfpessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "CPF INVALIDO";
        if (validarCpf(pessoaTransfer.getCpfpessoa()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        if (pessoaDAO.IdCpfConfere(pessoaTransfer.getId(), pessoaTransfer.getCpfpessoa()) || pessoaDAO.buscarPorCPF(pessoaTransfer.getCpfpessoa()) == false) {
            return;
        } else {
            msg = "CPF EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

    }

    //metodo validar Email
    public void validarEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object email = ((UIInput) context.getViewRoot().findComponent("formulario:email")).getSubmittedValue();
        Object id = ((UIInput) context.getViewRoot().findComponent("formulario:id")).getLocalValue();

        pessoaTransfer.setId(Short.valueOf(id.toString()));
        pessoaTransfer.setEmailpessoa(email.toString().toUpperCase());

        msg = "INFORME EMAIL";
        if ((pessoaTransfer.getEmailpessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getEmailpessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "O EMAIL SO DEVE TER 5 E 100 DE DIGITOS";
        if (pessoaTransfer.getEmailpessoa().trim().length() <= 4 || pessoaTransfer.getEmailpessoa().trim().length() > 100) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "FORMATO DE EMAIL INVALIDO";
        if (validarEmailPessoa(pessoaTransfer.getEmailpessoa()) == false) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        if (pessoaDAO.IdEmailConfere(pessoaTransfer.getId(), pessoaTransfer.getEmailpessoa()) || pessoaDAO.buscarPorEmail(pessoaTransfer.getEmailpessoa()) == false) {
            return;
        } else {
            msg = "EMAIL EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo validar Data Nascimento
    public void validarData(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object data = ((UIInput) context.getViewRoot().findComponent("formulario:nascimento")).getSubmittedValue();
        Util util = new Util();

        msg = "INFORME NASCIMENTO";
        if (pessoaTransfer.toString().trim().length() == 0 || "".equalsIgnoreCase(data.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return;
        }

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadorano = new SimpleDateFormat("yyyy");
        Date dataFormatada = null;
        try {
            dataFormatada = formatador.parse(data.toString());
        } catch (ParseException ex) {
            Logger.getLogger(ValidarUsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        pessoaTransfer.setDatanascimento(dataFormatada);

        msg = "O NASCIMENTO DEVE SER MENOR OU IGUAL A HOJE";
        if (pessoaTransfer.getDatanascimento().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        int valorAno = Integer.parseInt(formatadorano.format(pessoaTransfer.getDatanascimento()));
        msg = "O NASCIMENTO DEVE ESTA ENTRE 1919 E 2019";
        if (valorAno < 1919 || valorAno > 2019) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

    }

    //metodo validar Data do Evento
    public void validarDataEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object data = ((UIInput) context.getViewRoot().findComponent("formulario:dataevento")).getSubmittedValue();
        Util util = new Util();

        msg = "INFORME DATA EVENTO";
        if (data.toString().trim().length() == 0 || "".equalsIgnoreCase(data.toString())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return;
        }
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatadorano = new SimpleDateFormat("yyyy");
        Date dataFormatada = null;
        try {
            dataFormatada = formatador.parse(data.toString());
        } catch (ParseException ex) {
            Logger.getLogger(ValidarUsuarioBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        galeriaTransfer.setDataevento(dataFormatada);

        msg = "A DATA DO EVENTO DEVE SER MENOR OU IGUAL A HOJE";
        if (galeriaTransfer.getDataevento().after(util.getPegaDataAtual()) == true) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        int valorAno = Integer.parseInt(formatadorano.format(galeriaTransfer.getDataevento()));
        msg = "A DATA DO EVENTO DEVE ESTA ENTRE 2009 E 2019";
        if (valorAno < 2009 || valorAno > 2019) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

    }

    //metodo validar SENHA de Cadastramento
    public void validarSenhaCadastrar(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formulario:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formulario:senha")).getSubmittedValue();
        pessoaTransfer.setSenhapessoa(senha.toString());
        pessoaTransfer.setSenhaconfirmacao(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((pessoaTransfer.getSenhapessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getSenhapessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        msg = "A SENHA SO DEVE TER 5 E 20 DIGITOS";
        if (pessoaTransfer.getSenhapessoa().trim().length() <= 4 || pessoaTransfer.getSenhapessoa().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        msg = "INFORME SENHA DE CONFIRMACAO";
        if ((pessoaTransfer.getSenhaconfirmacao() == null || "".equalsIgnoreCase(pessoaTransfer.getSenhaconfirmacao()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "SENHA E CONFIRMACAO DE SENHA SAO DIFERENTES";
        if (!pessoaTransfer.getSenhapessoa().equalsIgnoreCase(pessoaTransfer.getSenhaconfirmacao())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo validar SENHA para Edicao
    public void validarSenhaEditar(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object senha = ((UIInput) context.getViewRoot().findComponent("formsenha:password")).getSubmittedValue();
        Object senhaconfirmar = ((UIInput) context.getViewRoot().findComponent("formsenha:senha")).getSubmittedValue();
        pessoaTransfer.setSenhapessoa(senha.toString());
        pessoaTransfer.setSenhaconfirmacao(senhaconfirmar.toString());

        msg = "INFORME SENHA";
        if ((pessoaTransfer.getSenhapessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getSenhapessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        msg = "A SENHA SO DEVE TER 5 E 20 DIGITOS";
        if (pessoaTransfer.getSenhapessoa().trim().length() <= 4 || pessoaTransfer.getSenhapessoa().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        msg = "INFORME SENHA DE CONFIRMACAO";
        if ((pessoaTransfer.getSenhaconfirmacao() == null || "".equalsIgnoreCase(pessoaTransfer.getSenhaconfirmacao()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        msg = "SENHA E CONFIRMACAO DE SENHA SAO DIFERENTES";
        if (!pessoaTransfer.getSenhapessoa().equalsIgnoreCase(pessoaTransfer.getSenhaconfirmacao())) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo validar Usuario e Senha do Login
    public void validarUsuarioSenhaLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException, SQLException {
        Object usuario = ((UIInput) context.getViewRoot().findComponent("formlogin:usuario")).getSubmittedValue();
        Object senha = ((UIInput) context.getViewRoot().findComponent("formlogin:senha")).getSubmittedValue();

        pessoaTransfer.setUsuariopessoa(usuario.toString().toUpperCase());
        pessoaTransfer.setSenhapessoa(senha.toString());

        if ((pessoaTransfer.getSenhapessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getSenhapessoa())) && (pessoaTransfer.getUsuariopessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getUsuariopessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "INFORME USUARIO E SENHA", "INFORME USUARIO E SENHA"));
        } else if (pessoaTransfer.getUsuariopessoa().trim().length() <= 4 || pessoaTransfer.getUsuariopessoa().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "O USUARIO SO DEVE TER 5 E 100 DIGITOS", "O USUARIO SO DEVE TER 5 E 100 DIGITOS"));
        } else if (pessoaTransfer.getSenhapessoa().trim().length() <= 4 || pessoaTransfer.getSenhapessoa().trim().length() > 20) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "A SENHA SO DEVE TER 5 E 20 DIGITOS", "A SENHA SO DEVE TER 5 E 20 DIGITOS"));
        } else if (loginDAO.pesquisaLogin(pessoaTransfer.getUsuariopessoa()) == null) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUARIO INEXISTENTE", "USUARIO INEXISTENTE"));
        }
    }

    //metodo validar Tipo
    public void validarTipo(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object tipo = ((UIInput) context.getViewRoot().findComponent("formulario:tipo")).getSubmittedValue();
        pessoaTransfer.setTipopessoa(tipo.toString().toUpperCase());

        msg = "INFORME TIPO";
        if ((pessoaTransfer.getTipopessoa() == null || "".equalsIgnoreCase(pessoaTransfer.getTipopessoa()))) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //validarComplemtno
    public void validarComplemento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object complemento = ((UIInput) context.getViewRoot().findComponent("formulario:complemento")).getSubmittedValue();

        pessoaTransfer.setComplementoendereco(complemento.toString().toUpperCase());
        msg = "O COMPLEMENTO SO DEVE TER NO MAXIMO 200 DE DIGITOS";
        if (pessoaTransfer.getComplementoendereco().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //validarComplemtno
    public void validarLogradouro(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object logradouro = ((UIInput) context.getViewRoot().findComponent("formulario:logradouro")).getSubmittedValue();
        pessoaTransfer.setLogradouroendereco(logradouro.toString().toUpperCase());

        msg = "O LOGRADOURO SO DEVE TER NO MAXIMO 200 DE DIGITOS";
        if (pessoaTransfer.getLogradouroendereco().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //validarComplemtno
    public void validarCidade(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object cidade = ((UIInput) context.getViewRoot().findComponent("formulario:cidade")).getSubmittedValue();
        pessoaTransfer.setCidadeendereco(cidade.toString().toUpperCase());

        msg = "A CIDADE SO DEVE TER NO MAXIMO 100 DE DIGITOS";
        if (pessoaTransfer.getCidadeendereco().trim().length() > 100) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    public void validarObservacao(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object observacao = ((UIInput) context.getViewRoot().findComponent("formulario:observacao")).getSubmittedValue();
        pessoaTransfer.setObservacaopessoa(observacao.toString().toUpperCase());

        msg = "A OBSERVACAO SO DEVE TER NO MAXIMO 200 DE DIGITOS";
        if (pessoaTransfer.getObservacaopessoa().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo para Validar Nome do Evento
    public void validarUsuarioEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //Object nomeeventousuario = ((UIInput) context.getViewRoot().findComponent("formulario:multiple")).getSubmittedValue();
        String nomeusuarioevento = String.valueOf(value);

        msg = "INFORME CLIENTE(S)";
        if (nomeusuarioevento.trim().length() == 0) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //metodo para Validar Nome do Evento
    public boolean validarNomeEvento(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object idgaleria = ((UIInput) context.getViewRoot().findComponent("formulario:idgaleria")).getLocalValue();
        // Object nomeevento = ((UIInput) context.getViewRoot().findComponent("formulario:nomeevento")).getLocalValue();

        if (idgaleria == null) {
            idgaleria = 0;
        }

        //galeriaTransfer.setNomeevento(nomeevento.toString().toUpperCase());
        msg = "INFORME NOME EVENTO";
        if (null == value.toString().toUpperCase() || "".equals(value.toString().toUpperCase())) {
            ((UIInput) component).setValid(false);
            context.addMessage((component.getClientId(context)), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        } else 
        msg = "O NOME EVENTO SO DEVE TER 3 E 50 DE DIGITOS";
        if (value.toString().toUpperCase().trim().length() <= 3 || value.toString().toUpperCase().trim().length() > 50) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
        } else 
        galeriaTransfer.setId(Short.valueOf(idgaleria.toString()));
        if (eventoDAO.IdEventoGaleriaConfere(galeriaTransfer.getId(), value.toString().toUpperCase().toUpperCase()) || eventoDAO.buscarPorEvento(value.toString().toUpperCase()) == false) {
            return false;
        } else 
            msg = "EVENTO EXISTE";
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return true;
    }

    public void validarEventoObservacao(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object observacao = ((UIInput) context.getViewRoot().findComponent("formulario:observacaoevento")).getSubmittedValue();
        galeriaTransfer.setObservacaoevento(observacao.toString().toUpperCase());

        msg = "A OBSERVACAO SO DEVE TER NO MAXIMO 200 DE DIGITOS";
        if (galeriaTransfer.getObservacaoevento().trim().length() > 200) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    public void validarEventoFotos(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //Object fotosevento = ((UIInput) context.getViewRoot().findComponent("formulario:fotos")).getSubmittedValue();
        System.out.println("MEU VALOR" + value.toString());
        msg = "INFORME FOTO(S)";
        if (value == null || "".equalsIgnoreCase(value.toString()) || value.toString().trim().length() == 0) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    //validar email
    private boolean validarEmailPessoa(String pessoaemail) {
        Pattern pattern = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
        Matcher matcher = pattern.matcher(pessoaemail.toUpperCase());
        boolean email = matcher.find();

        if (email == true) {
            return true;
        } else {
            if (email == false) {
                return false;
            }
            return false;
        }
    }

    public boolean validarCpf(String cpf) {
        if (cpf == null) {
            return false;
        } else {
            String cpfGerado = "";
            this.cpf = (cpf);
            removerCaracteres();
            if (verificarSeTamanhoInvalido(this.cpf)) {
                return false;
            }
            if (verificarSeDigIguais(this.cpf)) {
                return false;
            }
            cpfGerado = this.cpf.substring(0, 9);
            cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));
            cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));

            if (!cpfGerado.equals(this.cpf)) {
                return false;
            }
        }
        return true;
    }

    private void removerCaracteres() {
        this.cpf = this.cpf.replace("-", "");
        this.cpf = this.cpf.replace(".", "");
    }

    private boolean verificarSeTamanhoInvalido(String cpf) {

        return cpf.equals("00000000000")
                || cpf.equals("11111111111")
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999")
                || cpf.trim().length() != 11;

    }

    private boolean verificarSeDigIguais(String cpf) {
        //char primDig = cpf.charAt(0);
        char primDig = '0';
        char[] charCpf = cpf.toCharArray();
        for (char c : charCpf) {
            if (c != primDig) {
                return false;
            }
        }
        return true;
    }

    private String calculoComCpf(String cpf) {
        int digGerado = 0;
        int mult = cpf.length() + 1;
        char[] charCpf = cpf.toCharArray();
        for (int i = 0; i < cpf.length(); i++) {
            digGerado += (charCpf[i] - 48) * mult--;
        }
        if (digGerado % 11 < 2) {
            digGerado = 0;
        } else {
            digGerado = 11 - digGerado % 11;
        }
        return String.valueOf(digGerado);
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
                + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    
}
