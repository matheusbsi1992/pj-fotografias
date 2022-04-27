/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.ContaCadastrarUsuarioDAO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.email.ContaEmailCliente;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 *
 * @author Jandisson
 */
public class ContaCadastrarUsuarioBO {

    private static final Logger LOG = Logger.getLogger(ContaCadastrarUsuarioBO.class.getName());

    //criacao do atributo msg
    private String msg = "";

    //criacao do atributo util
    private Util util = null;

    //criacao do atributo con
    private ContaEmailCliente contaClienteUsuarioEmail = null;

    //criacao do atributo contaCadastrarUsuarioDAO
    private ContaCadastrarUsuarioDAO contaCadastrarUsuarioDAO = null;

    //criacao do atributo 
    private PessoaDAO pessoaDAO = null;

    public ContaCadastrarUsuarioBO() {
        if (contaCadastrarUsuarioDAO == null) {
            contaCadastrarUsuarioDAO = new ContaCadastrarUsuarioDAO();
        }
        if (pessoaDAO == null) {
            pessoaDAO = new PessoaDAO();
        }
        if (util == null) {
            util = new Util();
        }
        if (contaClienteUsuarioEmail == null) {
            contaClienteUsuarioEmail = new ContaEmailCliente();
        }
    }

    //metodo para cadastrar usuario
    public boolean cadastrarUsuario(PessoaTransfer pessoaT) {
        contaCadastrarUsuarioDAO.inserirContaUsuario(pessoaT);
        if (pessoaT.getStatuspessoa().equalsIgnoreCase("A")) {
            contaClienteUsuarioEmail.cadastrarConectadoEmail(pessoaT);
        }
        return true;
    }

}
