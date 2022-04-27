/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.LoginDAO;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author DHNSTI02
 */
public class LoginBO {

    private static final Logger LOG = Logger.getLogger(LoginBO.class.getName());

    //criacao do atributo loginDAO
    private LoginDAO loginDAO = null;

    //criacao atributo PessoaTransfer
    private PessoaTransfer pessoaT = null;

    //criacao do atributo Util
    private Util util = null;

    public LoginBO() {
        if (loginDAO == null) {
            loginDAO = new LoginDAO();
        }
        if (util == null) {
            util = new Util();
        }
    }

    /**
     * Metodo retornar de uma dados sobre o usuario atributo login.
     *
     * @param String login
     * @return PessoaTransfer
     * @exception Exception
     */
    public List<PessoaTransfer> Sessao(String login) {
        try {
            return loginDAO.pesquisaLogin(login);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LoginBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Metodo retornar de uma lista contendo dados da pessoa proveniente do
     * login. Indicacao de que o usuario e senha realmente existem
     *
     * @param PessoaTransfer pessoaT
     *
     * @return true or false
     */
    public PessoaTransfer loginUsuario(String login, String senha) {
        return loginDAO.login(login, senha);
    }

    /**
     * Metodo retornar de uma lista contendo dados da pessoa proveniente do
     * login. Indicacao de que o usuario e senha realmente existem
     *
     * @param pessoaT
     * @param PessoaTransfer pessoaT
     * @return
     *
     *
     */
    public int usuarioBuscarInserirLogin(PessoaTransfer pessoaT) {
        List<PessoaTransfer> listaPessoa = loginDAO.inserirlistarLoginUsuario(pessoaT);
        return listaPessoa.size();
    }

    public void deletarUsuario(String login) {
        loginDAO.deletarContaUsuario(login);
    }

}
