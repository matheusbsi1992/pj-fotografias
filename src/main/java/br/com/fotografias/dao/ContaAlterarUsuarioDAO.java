/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import static br.com.fotografias.conexaofactory.Conexao.closeDataSource;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jandisson
 */
public class ContaAlterarUsuarioDAO extends DAO {

    /**
     * Metodo responsavel por alterar na tabela Pessoa um usuario;
     *
     * @return boolean
     * @param PessoaT pessoa
     */
    public boolean alterarContaUsuario(PessoaTransfer pessoa) {

        try {

            strBuffer = new StringBuffer().append("UPDATE projeto.tbpessoa\n"
                    + "   SET   nomepessoa=?, "
                    + "         emailpessoa=?, \n"
                    + "         datanascimentpessoa=?,"
                    + "         cpfpessoa=?,"
                    + "         usuariopessoa=?,"
                    + "         sexopessoa=?,"
                    + "         celularpessoa=?,"
                    + "         statuspessoa=?,"
                    + "         tipopessoa=?,"
                    + "         cependereco=?,"
                    + "         complementoendereco=?,"
                    + "         logradouroendereco=?,"
                    + "         cidadeendereco=?,"
                    + "         estadoendereco=?,"
                    + "         observacaopessoa=?\n"
                    + " WHERE idpessoa=?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, pessoa.getNomepessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getEmailpessoa().toUpperCase());
            pstm.setObject(tamPessoa++, new Util().convertUtilToSql(pessoa.getDatanascimento()));
            pstm.setObject(tamPessoa++, pessoa.getCpfpessoa());
            pstm.setObject(tamPessoa++, pessoa.getUsuariopessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getSexopessoa());
            pstm.setObject(tamPessoa++, pessoa.getCelularpessoa());
            pstm.setObject(tamPessoa++, pessoa.getStatuspessoa());
            pstm.setObject(tamPessoa++, pessoa.getTipopessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getCependereco());
            pstm.setObject(tamPessoa++, pessoa.getComplementoendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getLogradouroendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getCidadeendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getEstadoendereco());
            pstm.setObject(tamPessoa++, pessoa.getObservacaopessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getId());

            pstm.executeUpdate();

            return true;

        } catch (SQLException ex) {
            logPrincipal(ContaAlterarUsuarioDAO.class).error(">>>>Error ao alterar Usuario na Tabela Pessoa!!!", ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaAlterarUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa e
     * Cliente
     *
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> getALL() {
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "	FROM projeto.tbpessoa"
                    + " ORDER BY nomepessoa;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            return new PessoaDAO().rsTolist(rs);

        } catch (SQLException ex) {
            logPrincipal(ContaAlterarUsuarioDAO.class).error(">>>>Error ao tentar Lista todos os dados do Usuario", ex);
        }

        return null;
    }

    /**
     * Metodo responsavel por deletarUsuario da tabela Pessoa
     *
     * @param int idpessoa
     */
    public void deletarContaUsuario(int idpessoa) {
        try {

            strBuffer = new StringBuffer().append("\n"
                    + " DELETE FROM projeto.tbpessoa\n"
                    + " WHERE idpessoa = ?;\n");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, idpessoa);

            pstm.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(ContaAlterarUsuarioDAO.class).error(">>>>Error ao alterar Deletar Usuario da Tabela Pessoa!!!", ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaAlterarUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo responsavel por bloquearContaUsuario da tabela Pessoa
     *
     *
     */
    public void bloquearContaUsuario() {
        try {

            strBuffer = new StringBuffer().append(" UPDATE projeto.tbpessoa"
                    + " SET statuspessoa='I'"
                    + " WHERE tbpessoa.tipopessoa ='CLIENTE'; "
            );

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.executeUpdate();

        } catch (SQLException ex) {
            logPrincipal(ContaAlterarUsuarioDAO.class).error(">>>>Error AO Bloquear Todos os Clientes da Tabela Pessoa!!!", ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaAlterarUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo responsavel por alterar a senha do Usuario
     *
     * @param pessoaT
     *
     */
    public boolean alterarContaSenha(PessoaTransfer pessoaT) {
        try {
            strBuffer = new StringBuffer().append("UPDATE projeto.tbpessoa\n"
                    + " SET senhapessoa = ?\n"
                    + " WHERE idpessoa = ?;");
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, new Util().hashCode(pessoaT.getSenhapessoa()).toHex());
            pstm.setObject(2, pessoaT.getId());

            pstm.executeUpdate();

            return true;

        } catch (SQLException ex) {
            logPrincipal(ContaAlterarUsuarioDAO.class).error(">>>>Error ao tentar Alterar Senha do Usuario!!!", ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaAlterarUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}
