/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import static br.com.fotografias.conexaofactory.Conexao.closeDataSource;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.MensagemArquivoTexto;
import br.com.fotografias.util.Util;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jandisson
 */
public class ContaCadastrarUsuarioDAO extends DAO {

    /**
     * Metodo responsavel por inserir na tabela Pessoa um usuario;
     *
     *
     * @return boolean
     * @param PessoaT pessoa
     */
    public boolean inserirContaUsuario(PessoaTransfer pessoa) {

        try {

            strBuffer = new StringBuffer().append("INSERT INTO projeto.tbpessoa(\n"
                    + "            usuariopessoa, senhapessoa, nomepessoa, emailpessoa, \n"
                    + "            datacadastropessoa, datanascimentpessoa, cpfpessoa, sexopessoa, \n"
                    + "            celularpessoa, statuspessoa, tipopessoa, cependereco, complementoendereco, \n"
                    + "            logradouroendereco, cidadeendereco, estadoendereco, \n"
                    + "            observacaopessoa)\n"
                    + "    VALUES (?, ?, ?, ?,  \n"
                    + "            ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, \n"
                    + "            ?);");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            
            
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, pessoa.getUsuariopessoa().toUpperCase());
            pstm.setObject(tamPessoa++, new Util().hashCode(pessoa.getSenhapessoa()).toHex());
            pstm.setObject(tamPessoa++, pessoa.getNomepessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getEmailpessoa().toUpperCase());
            pessoa.setDatacadastropessoa(new Util().getPegaDataAtual());
            pstm.setObject(tamPessoa++, new Util().convertUtilToSql(pessoa.getDatacadastropessoa()));
            pstm.setObject(tamPessoa++, new Util().convertUtilToSql(pessoa.getDatanascimento()));
            pstm.setObject(tamPessoa++, pessoa.getCpfpessoa());
            pstm.setObject(tamPessoa++, pessoa.getSexopessoa());
            pstm.setObject(tamPessoa++, pessoa.getCelularpessoa());
            pstm.setObject(tamPessoa++, pessoa.getStatuspessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getTipopessoa().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getCependereco());
            pstm.setObject(tamPessoa++, pessoa.getComplementoendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getLogradouroendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getCidadeendereco().toUpperCase());
            pstm.setObject(tamPessoa++, pessoa.getEstadoendereco());
            pstm.setObject(tamPessoa++, pessoa.getObservacaopessoa().toUpperCase());

            pstm.executeUpdate();

            return true;

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao inserirContaUsuario",ContaCadastrarUsuarioDAO.class.getName(),ex.getMessage());
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaCadastrarUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

}
