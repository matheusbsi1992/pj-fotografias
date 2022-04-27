/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import static br.com.fotografias.conexaofactory.Conexao.conectionCommitOrRoolback;
import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.MensagemArquivoTexto;
import br.com.fotografias.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 *
 * @author Jordão Santos
 */
public class CadastrarEventoDAO extends DAO {

    /**
     * Metodo responsavel por inserir na tabela Evento, PessoaEvento e Galeria
     * um evento ou mais eventos;
     *
     *
     * @param galeriaTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirEvento(GaleriaTransfer galeriaTransfer) throws SQLException {
        EventoTransfer eventoTransfer = new EventoTransfer();

        try {

            strBuffer = new StringBuffer().append("INSERT INTO projeto.tbevento"
                    + "           (nomeevento,"
                    + "            dataevento,"
                    + "            observacaoevento)"
                    + "    VALUES (?,"
                    + "            ?,"
                    + "            ?);"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());
            int tamevento = 1;
            //obtencao dos dados do Evento
            pstm.setObject(tamevento++, galeriaTransfer.getNomeevento().toUpperCase());
            pstm.setObject(tamevento++, new Util().convertUtilToSql(galeriaTransfer.getDataevento()));
            pstm.setObject(tamevento++, galeriaTransfer.getObservacaoevento().toUpperCase());

            //insira na tabela evento
            pstm.executeUpdate();

            //insercao na para a indicação na tabelaPessoa
            for (int i = 0; i < galeriaTransfer.getListaPessoaTransfer().size(); i++) {
                //variavel p recebe o usuario
                Object p = galeriaTransfer.getListaPessoaTransfer().get(i);
                //pesquisa pelo nome do usuario e retorna o id do usuario pesquisado
                PessoaTransfer pessoa = new PessoaDAO().pessoaRetorneIdPessoaUsaurio(p);

                int tampessoaevento = 1;

                strBuffer = new StringBuffer().append("SELECT idevento"
                        + "                            FROM projeto.tbevento"
                        + "                            ORDER BY idevento"
                        + "                            DESC LIMIT 1;");
                pstm = conexao.prepareStatement(strBuffer.toString());
                rs = pstm.executeQuery();
                rs.next();

                eventoTransfer.setId(rs.getShort("idevento"));

                strBuffer = new StringBuffer().append("INSERT INTO projeto.tbpessoaevento(\n"
                        + "                        idpessoa,"
                        + "                        idevento,"
                        + "                        statuspessoaevento)"
                        + "                        VALUES(?,"
                        + "                               ?,"
                        + " ?);");
                pstm = conexao.prepareStatement(strBuffer.toString());

                pstm.setObject(tampessoaevento++, pessoa.getId());
                pstm.setObject(tampessoaevento++, eventoTransfer.getId());
                pstm.setObject(tampessoaevento++, galeriaTransfer.getStatusevento().toUpperCase());
                //insira na tabela pessoaevento
                pstm.executeUpdate();
            }

            return true;

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao inserirEventoImagens", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }

        return false;
    }

    /**
     * Metodo responsavel por inserir na tabela Evento, PessoaEvento e Galeria
     * um evento ou mais eventos;
     *
     *
     * @param galeriaTransfer
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirGaleria(GaleriaTransfer galeriaTransfer) throws SQLException {
        EventoTransfer eventoTransfer = new EventoTransfer();

        try {

            strBuffer = new StringBuffer().append("SELECT idevento"
                    + "                            FROM projeto.tbevento"
                    + "                            ORDER BY idevento"
                    + "                            DESC LIMIT 1;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            rs.next();

            eventoTransfer.setId(rs.getShort("idevento"));

            //insercacao das fotos
            strBuffer = new StringBuffer().append("INSERT INTO projeto.tbgaleria(\n"
                    + "            fotosgaleria,\n"
                    + "            idevento,\n"
                    + "            descricaofotosgaleria)\n"
                    + "    VALUES (?,"
                    + "             ?,\n"
                    + "            ?);");
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamgaleria = 1;
            pstm.setBytes(tamgaleria++, galeriaTransfer.getFotosgaleria());
            pstm.setObject(tamgaleria++, eventoTransfer.getId());
            pstm.setObject(tamgaleria++, galeriaTransfer.getDescricaofotosgaleria());

            pstm.executeUpdate();

            return true;

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao inserirGaleria", CadastrarEventoDAO.class,
                    ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }
        return false;
    }

    /**
     * Metodo responsavel por inserir na tabela PessoaEventoGaleria um evento ou
     * mais eventos, com a respectiva pessoa. Mas atualizando o evento
     * existente;
     *
     *
     * @param idfoto
     * @param idgaleria
     * @param nomeevento
     * @param idpessoa
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean inserirPessoaEventoGaleria(Short idfoto, String nomeevento, Short idpessoa) throws SQLException {
        EventoTransfer eventoTransfer = new EventoTransfer();

        //pesquisar evento
        try {
            strBuffer = new StringBuffer().append("SELECT idevento"
                    + "                            FROM projeto.tbevento"
                    + "                            WHERE nomeevento= ?");

            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, nomeevento);

            rs = pstm.executeQuery();

            rs.next();

            eventoTransfer.setId(rs.getShort("idevento"));

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao pesquisar evento as fotos", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }

        //insercacao das fotos,evento e pessoa
        try {
            strBuffer = new StringBuffer().append("INSERT INTO projeto.tbpessoaeventogaleria(\n"
                    + "            idgaleria,\n"
                    + "            idevento,\n"
                    + "            idpessoa)\n"
                    + "    VALUES  (?,"
                    + "             ?,\n"
                    + "             ?);");
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamgaleria = 1;
            pstm.setObject(tamgaleria++, idfoto);
            pstm.setObject(tamgaleria++, eventoTransfer.getId());
            pstm.setObject(tamgaleria++, idpessoa);

            //finalizar processo a base de dados
            pstm.executeUpdate();

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error na insercacao das fotos", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }
        return true || false;
    }

    /**
     * Metodo responsavel por retornar os dados do usuario
     */
    public List<PessoaTransfer> buildCategoryNameList() throws SQLException {
        List<PessoaTransfer> pessoaTransfers = new PessoaDAO().pessoaEventoGaleria();
        return pessoaTransfers;
    }

    /**
     * Metodo responsavel por deletar Evento das tabelas: Evento, PessoaEvento e
     * Galeria
     *
     * @param idpessoa
     * @param evento
     */
    public void deletarPessoaEventoGaleria(int idpessoa, String evento) {
        try {
            EventoDAO eventoDAO = new EventoDAO();

            //retorne o id do evento
            int idevento = eventoDAO.itemIdEventoTbPessoaEvento(idpessoa, evento);
            //retorne a quantidade de eventos
            int idqtdeventopessoa = eventoDAO.itemQuantidadeIdEventoTbPessoaEvento(idpessoa);

            strBuffer = new StringBuffer().append("\n "
                    + "DELETE FROM "
                    + "projeto.tbpessoaevento\n "
                    + "WHERE idpessoa= ? and "
                    + "idevento=").append(idevento);

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, idpessoa);

            //remova id da pessoa da tabela PessoaEvento 
            pstm.executeUpdate();

            //pergunta se o a quantidade de id's correspondem com que é pedido para que haja a ultima remocao da tabela evento
            if (idqtdeventopessoa == 1) {

                strBuffer = new StringBuffer().append("\n"
                        + " DELETE FROM projeto.tbevento\n"
                        + " WHERE idevento= ?\n");

                //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
                pstm = conexao.prepareStatement(strBuffer.toString());

                int tamevento = 1;

                pstm.setObject(tamevento++, idevento);

                //remova ultimo dado do Evento
                pstm.executeUpdate();
            } else {
                return;
            }
        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao deletarPessoaEventoGaleria", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(CadastrarEventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo responsavel por deletar as imagens de um determinado usuario
     * relacionado com a Galeria
     *
     * @param idimagem
     */
    public boolean deletarImagemGaleria(int idimagem) {
        try {

            strBuffer = new StringBuffer().append("DELETE FROM \n"
                    + "projeto.tbgaleria\n "
                    + "WHERE idgaleria = ?");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamPessoa = 1;

            pstm.setObject(tamPessoa++, idimagem);

            //remova id da galeria tabela Galeria
            pstm.executeUpdate();

            return true;
        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao deletarImagemGaleria", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            try {
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(CadastrarEventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Metodo responsavel por alterar o status da PessoaEvento. Com a relacao do
     * evento, para a obtencao do idevento. Isto produz o retorno do id do
     * evento, para que seja informado no objeto do evento, isto contido com o
     * id da pessoa, para a alterado dos status de andamento a finalizado
     *
     *
     * @param idfoto
     * @param nomeevento
     * @param idpessoa
     * @return boolean
     * @throws java.sql.SQLException
     */
    public boolean alterarStatusPessoaEvento(Short idfoto, String nomeevento, Short idpessoa) throws SQLException {
        //update pesssoa e evento
        EventoTransfer eventoTransfer = new EventoTransfer();

        //pesquisar evento
        try {
            strBuffer = new StringBuffer().append("SELECT idevento"
                    + "                            FROM projeto.tbevento"
                    + "                            WHERE nomeevento= ?");

            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, nomeevento);

            rs = pstm.executeQuery();

            rs.next();

            eventoTransfer.setId(rs.getShort("idevento"));
        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error ao pesquisar evento as fotos", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }

        try {
            strBuffer = new StringBuffer().append("UPDATE projeto.tbpessoaevento\n"
                    + "   SET statuspessoaevento='FINALIZADO'\n"
                    + " WHERE (idpessoa=? and\n"
                    + "       idevento=?);");

            pstm = conexao.prepareStatement(strBuffer.toString());

            int tamgaleria = 1;
            pstm.setObject(tamgaleria++, idpessoa);
            pstm.setObject(tamgaleria++, eventoTransfer.getId());
            //finalizar processo base de dados
            pstm.executeUpdate();

        } catch (SQLException ex) {
            new MensagemArquivoTexto(">>>>Error update pesssoa e evento", CadastrarEventoDAO.class, ex.getMessage());
        } finally {
            pstm.close();
            rs.close();
        }
        return true || false;
    }
}
