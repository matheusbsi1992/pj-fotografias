/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.ContaAlterarUsuarioDAO;
import br.com.fotografias.dao.ContaCadastrarUsuarioDAO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.email.ContaEmailCliente;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *
 * @author Jandisson
 */
public class ContaAlterarUsuarioBO {

    private static final Logger LOG = Logger.getLogger(ContaAlterarUsuarioBO.class.getName());

    //criacao do atributo cadloginDAO
    private ContaAlterarUsuarioDAO contaAlterarUsuarioDAO = null;

    //criacao do atributo pessoaDAO
    private PessoaDAO pessoaDAO = null;

    //criacao do atributo validarBO
    private ValidarUsuarioBO validarusuarioBO = null;

    //criacao do atributo Util
    private Util util = null;

    //criacao do atributo con
    private ContaEmailCliente contaClienteUsuarioEmail = null;

    //criacao do Atributo adminiAdminCadUsuarioBO
    public ContaAlterarUsuarioBO() {
        if (contaAlterarUsuarioDAO == null) {
            contaAlterarUsuarioDAO = new ContaAlterarUsuarioDAO();
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

    /*
     *Metodo que retorna uma lista de dados
     */
    public List<PessoaTransfer> ListarTodos() {
        return pessoaDAO.ListarTodos();
    }

    /*
     *Metodo que retorna uma lista de dados contendo nomes
     */
    public List<PessoaTransfer> listarNomes(String nomes) {
        return pessoaDAO.buscarPorNome(nomes);
    }

    /*Metodo responsavel por alterar conta do usuario
     * @param PessoaTransfer pessoaT
     */
    public boolean alterarContaUsuario(PessoaTransfer pessoaT) throws Exception {
        util.getTempoMillisSegundos();
        return contaAlterarUsuarioDAO.alterarContaUsuario(pessoaT) == true;
    }

    /*Metodo responsavel por alterar senha do usuario
     * @param PessoaTransfer pessoaT
     */
    public boolean alterarSenha(PessoaTransfer pessoaT) throws Exception {
    
        //criacao do atributo con
        if (!pessoaT.getStatuspessoa().equalsIgnoreCase("I")) {
            contaClienteUsuarioEmail.alterarConectadoEmail(pessoaT);
        }

        return contaAlterarUsuarioDAO.alterarContaSenha(pessoaT) == true;
    }


    /*Metodo responsavel por excluir usuario
     * @param String idpessoa
     */
    public void excluirContaUsuario(String idpessoa) {
        try {
            contaAlterarUsuarioDAO.deletarContaUsuario(Integer.parseInt(idpessoa));
        } catch (Exception ex) {
            LOG.error(">>>ERROR AO DELETAR USUARIO PESSOA!", ex);
        }
    }

    /*Metodo responsavel por excluir usuario
     * @param String idpessoa
     */
    public void bloquearContaUsuario() {
        try {
            contaAlterarUsuarioDAO.bloquearContaUsuario();
        } catch (Exception ex) {
            LOG.error(">>>ERROR AO BLOQUEAR USUARIO PESSOA!", ex);
        }
    }

}
