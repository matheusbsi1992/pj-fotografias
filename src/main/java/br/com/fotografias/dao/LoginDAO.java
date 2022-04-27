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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DHNSTI02
 */
public class LoginDAO extends DAO {

    /**
     * Metodo responsavel por inserir na tabela Login um usuario;
     *
     *
     * @return boolean
     * @param PessoaT pessoa
     */
    public List<PessoaTransfer> inserirlistarLoginUsuario(PessoaTransfer pessoaT) {
        List<PessoaTransfer> listCliente = new ArrayList<PessoaTransfer>();
        try {

            strBuffer = new StringBuffer().append("INSERT INTO projeto.tblogin(loginusuario)\n"
                    + "SELECT 	usuariopessoa\n"
                    + "FROM 	projeto.tbpessoa\n"
                    + "WHERE 	usuariopessoa = ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, pessoaT.getUsuariopessoa().toUpperCase());

            pstm.executeUpdate();

            strBuffer = new StringBuffer().append("select loginusuario"
                    + " from projeto.tblogin"
                    + " where loginusuario = ? ;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, pessoaT.getUsuariopessoa().toUpperCase());
            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();

                pessoa.setUsuariopessoa(rs.getString("loginusuario"));

                listCliente.add(pessoa);
            }
            return listCliente;

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>Error ao inserir Usuario na Tabela Login!!!", ex);
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void deletarContaUsuario(String pessoa) {
        try {

            strBuffer = new StringBuffer().append("\n"
                    + " DELETE FROM projeto.tblogin\n"
                    + " WHERE loginusuario=?;\n");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, pessoa);

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
     * Metodo retornar de uma lista contendo dados da pessoa proveniente do
     * atributo login
     *
     * @param String login
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> pesquisaLogin(String login) throws SQLException {

        List<PessoaTransfer> listaPessoa = new ArrayList<PessoaTransfer>();

        strBuffer = new StringBuffer().append("SELECT  *\n"
                + "  FROM projeto.tbpessoa\n"
                + "  WHERE usuariopessoa =?;");
        pstm = conexao.prepareStatement(strBuffer.toString());
        pstm.setObject(1, login.toUpperCase());
        rs = pstm.executeQuery();

        while (rs.next()) {
            PessoaTransfer pessoa = new PessoaTransfer();
            pessoa.setId(rs.getShort("idpessoa"));
            listaPessoa.add(pessoa);

        }
        rs.close();
        return listaPessoa;

    }

    /**
     * Metodo que retornar boleano do login atributo login
     *
     * @param String login
     * @return List<PessoaT>
     * @exception SQLException
     */
    public PessoaTransfer login(String login, String senha) {
        List<PessoaTransfer> listaPessoa = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "FROM projeto.tbpessoa"
                    + " WHERE usuariopessoa=? and senhapessoa=?;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, login);
            pstm.setObject(2, new Util().hashCode(senha).toHex());

            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setId(rs.getShort("idpessoa"));
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoa.setSenhapessoa(rs.getString("senhapessoa"));
                pessoa.setNomepessoa(rs.getString("nomepessoa"));
                pessoa.setEmailpessoa(rs.getString("emailpessoa"));
                pessoa.setCpfpessoa(rs.getString("cpfpessoa"));
                pessoa.setSexopessoa(rs.getString("sexopessoa"));
                pessoa.setCelularpessoa(rs.getString("celularpessoa"));
                pessoa.setStatuspessoa(rs.getString("statuspessoa"));
                pessoa.setTipopessoa(rs.getString("tipopessoa"));
                pessoa.setObservacaopessoa(rs.getString("observacaopessoa"));
                pessoa.setCependereco(rs.getString("cependereco"));
                pessoa.setComplementoendereco(rs.getString("complementoendereco"));
                pessoa.setLogradouroendereco(rs.getString("logradouroendereco"));
                pessoa.setCidadeendereco(rs.getString("cidadeendereco"));
                pessoa.setEstadoendereco(rs.getString("estadoendereco"));
                pessoa.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                pessoa.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listaPessoa.add(pessoa);
            }

        } catch (SQLException ex) {
            logPrincipal(LoginDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO E A SENHA DA PESSOA", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoa.size() > 0 ? listaPessoa.get(0) : null;
    }

}
