/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jandisson
 */
public class EventoDAO extends DAO {

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa
     * contido em um ResultSet
     *
     * @return List<PessoaT>
     * @param ResultSet rs
     * @exception SQLException
     */
    private List<GaleriaTransfer> rsTolist(ResultSet rs) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {

            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                //galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>Error no metodo rsTolist", ex);
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> ListarTodos() {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                                                              /*dados da tabela pessoa*/\n"
                    + "                                                              pessoa.usuariopessoa,					\n"
                    + "                                                              pessoa.nomepessoa,			\n"
                    + "                                                              pessoa.emailpessoa,			\n"
                    + "                                                              pessoa.datacadastropessoa,			\n"
                    + "                                                              pessoa.datanascimentpessoa,	\n"
                    + "                                                              pessoa.cpfpessoa,			\n"
                    + "                                                              pessoa.sexopessoa,			\n"
                    + "                                                              pessoa.celularpessoa,			\n"
                    + "                                                              pessoa.statuspessoa,			\n"
                    + "                                                              pessoa.tipopessoa,\n"
                    + "                                                              pessoa.cependereco,		\n"
                    + "                                                              pessoa.complementoendereco,\n"
                    + "                                                              pessoa.logradouroendereco,\n"
                    + "                                                              pessoa.cidadeendereco,\n"
                    + "                                                              pessoa.estadoendereco,\n"
                    + "                                                              pessoa.observacaopessoa,\n"
                    + "                                                              /*dados da tabela evento*/\n"
                    + "                                                              evento.nomeevento,\n"
                    + "                                                              evento.dataevento,\n"
                    + "                                                              evento.observacaoevento,\n"
                    + "                                          		      /*dados da tabela pessoaevento*/\n"
                    + "                                                              pessoaevento.idpessoa\n"
                    + "                                                              from projeto.tbpessoa pessoa\n"
                    + "                                                              inner join\n"
                    + "                                                              projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                                                              inner join\n"
                    + "                                                              projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                                                              where (pessoaevento.statuspessoaevento='ANDAMENTO')\n"
                    + "                                                              order by (evento.nomeevento)"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                //galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                //galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> ListarEventosPessoa(int id) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("                                                                                   /*dados da tabela pessoa*/\n"
                    + "select                                                                             pessoa.usuariopessoa,					\n"
                    + "                                                                                   pessoa.nomepessoa,			\n"
                    + "                                                                                   pessoa.emailpessoa,			\n"
                    + "                                                                                   pessoa.datacadastropessoa,			\n"
                    + "                                                                                   pessoa.datanascimentpessoa,	\n"
                    + "                                                                                   pessoa.cpfpessoa,			\n"
                    + "                                                                                   pessoa.sexopessoa,			\n"
                    + "                                                                                   pessoa.celularpessoa,			\n"
                    + "                                                                                   pessoa.statuspessoa,			\n"
                    + "                                                                                   pessoa.tipopessoa,\n"
                    + "                                                                                   pessoa.cependereco,		\n"
                    + "                                                                                   pessoa.complementoendereco,\n"
                    + "                                                                                   pessoa.logradouroendereco,\n"
                    + "                                                                                   pessoa.cidadeendereco,\n"
                    + "                                                                                   pessoa.estadoendereco,\n"
                    + "                                                                                   pessoa.observacaopessoa,\n"
                    + "                                                                                   /*dados da tabela evento*/\n"
                    + "                                                                                   evento.nomeevento,\n"
                    + "                                                                                   evento.dataevento,\n"
                    + "                                                                                   evento.observacaoevento,\n"
                    + "                                                               		           /*dados da tabela pessoaevento*/\n"
                    + "                                                                                   pessoa.idpessoa\n"
                    + "                                                                                   from projeto.tbpessoa pessoa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                                                                                   where (pessoa.idpessoa=?)\n"
                    + "                                                                                   order by (evento.nomeevento)\n"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                //galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                //galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> ListarEventosEPessoa(String nomeevento, int id) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("                                                                                   /*dados da tabela pessoa*/\n"
                    + "select                                                                             pessoa.usuariopessoa,					\n"
                    + "                                                                                   pessoa.nomepessoa,			\n"
                    + "                                                                                   pessoa.emailpessoa,			\n"
                    + "                                                                                   pessoa.datacadastropessoa,			\n"
                    + "                                                                                   pessoa.datanascimentpessoa,	\n"
                    + "                                                                                   pessoa.cpfpessoa,			\n"
                    + "                                                                                   pessoa.sexopessoa,			\n"
                    + "                                                                                   pessoa.celularpessoa,			\n"
                    + "                                                                                   pessoa.statuspessoa,			\n"
                    + "                                                                                   pessoa.tipopessoa,\n"
                    + "                                                                                   pessoa.cependereco,		\n"
                    + "                                                                                   pessoa.complementoendereco,\n"
                    + "                                                                                   pessoa.logradouroendereco,\n"
                    + "                                                                                   pessoa.cidadeendereco,\n"
                    + "                                                                                   pessoa.estadoendereco,\n"
                    + "                                                                                   pessoa.observacaopessoa,\n"
                    + "                                                                                   /*dados da tabela evento*/\n"
                    + "                                                                                   evento.nomeevento,\n"
                    + "                                                                                   evento.dataevento,\n"
                    + "                                                                                   evento.observacaoevento,\n"
                    + "                                                               		           /*dados da tabela pessoaevento*/\n"
                    + "                                                                                   pessoa.idpessoa\n"
                    + "                                                                                   from projeto.tbpessoa pessoa\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                                                                                   inner join\n"
                    + "                                                                                   projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                                                                                   where ((pessoa.idpessoa=?) and (upper(evento.nomeevento) like upper(?)))\n"
                    + "                                                                                   order by (evento.nomeevento)\n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, id);
            pstm.setObject(2, "%" + nomeevento + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                //galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                //galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }


    /*
    *metodo perguntar se existe o id do evento através de um Join com o id de evento na galeria Existem
     */
    public boolean IdEventoGaleriaConfere(Short id, String nomeevento) {
        try {

            strBuffer = new StringBuffer().append("SELECT 	evento.idevento,\n"
                    + "		evento.nomeevento\n"
                    + "  FROM projeto.tbevento evento \n"
                    + "  inner join\n"
                    + "  projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "  WHERE galeria.idgaleria = ?\n"
                    + "	and\n"
                    + "	evento.nomeevento = ?");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, nomeevento);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar verificar IdEventoGaleriaConfere!!!", ex);
        }
        return false;
    }

    /**
     * Metodo retornar evento
     *
     * @exception SQLException
     */
    public boolean buscarPorEvento(String nomeevento) {

        try {

            strBuffer = new StringBuffer().append("SELECT NOMEEVENTO \n"
                    + "FROM   PROJETO.TBEVENTO\n"
                    + "WHERE  NOMEEVENTO = ?");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, nomeevento);
            rs = pstm.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Buscar Evento da Pessoa!!!", ex);
        }
        return false;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa ou nome do evento
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> listarPessoa(String pessoaevento) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();

        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela pessoa */\n"
                    + "pessoa.idpessoa,                    \n"
                    + "pessoa.usuariopessoa,                    \n"
                    + "pessoa.nomepessoa,           \n"
                    + "pessoa.emailpessoa,          \n"
                    + "pessoa.datacadastropessoa,           \n"
                    + "pessoa.datanascimentpessoa,          \n"
                    + "pessoa.cpfpessoa,            \n"
                    + "pessoa.sexopessoa,           \n"
                    + "pessoa.celularpessoa,            \n"
                    + "pessoa.statuspessoa,         \n"
                    + "pessoa.tipopessoa,           \n"
                    + "pessoa.cependereco,          \n"
                    + "pessoa.complementoendereco,          \n"
                    + "pessoa.logradouroendereco,           \n"
                    + "pessoa.cidadeendereco,           \n"
                    + "pessoa.estadoendereco,           \n"
                    + "pessoa.observacaopessoa,\n"
                    + "\n"
                    + "/*dados da tabela evento*/\n"
                    + "evento.nomeevento,\n"
                    + "evento.dataevento,\n"
                    + "evento.observacaoevento\n"
                    + "\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "where upper(pessoa.nomepessoa) \n"
                    + "like upper(?)\n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + pessoaevento + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                //galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                //galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                //galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa ou nome do evento
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> listarEvento(String evento) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela pessoa */\n"
                    + "pessoa.idpessoa,                    \n"
                    + "pessoa.usuariopessoa,                    \n"
                    + "pessoa.nomepessoa,           \n"
                    + "pessoa.emailpessoa,          \n"
                    + "pessoa.datacadastropessoa,           \n"
                    + "pessoa.datanascimentpessoa,          \n"
                    + "pessoa.cpfpessoa,            \n"
                    + "pessoa.sexopessoa,           \n"
                    + "pessoa.celularpessoa,            \n"
                    + "pessoa.statuspessoa,         \n"
                    + "pessoa.tipopessoa,           \n"
                    + "pessoa.cependereco,          \n"
                    + "pessoa.complementoendereco,          \n"
                    + "pessoa.logradouroendereco,           \n"
                    + "pessoa.cidadeendereco,           \n"
                    + "pessoa.estadoendereco,           \n"
                    + "pessoa.observacaopessoa,\n"
                    + "\n"
                    + "/*dados da tabela evento*/\n"
                    + "evento.nomeevento,\n"
                    + "evento.dataevento,\n"
                    + "evento.observacaoevento\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "where upper(evento.nomeevento) \n"
                    + "like upper(?)\n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + evento + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                //galeriaTransfer.setId(rs.getShort("idgaleria"));
                //galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                //galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                //evento
                galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setNomeevento(rs.getString("nomeevento"));
                galeriaTransfer.setObservacaoevento(rs.getString("observacaoevento"));
                galeriaTransfer.setDataevento(rs.getDate("dataevento"));

                //pessoa
                //galeriaTransfer.setId(rs.getShort("idpessoa"));
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                galeriaTransfer.setNomepessoa(rs.getString("nomepessoa"));
                galeriaTransfer.setEmailpessoa(rs.getString("emailpessoa"));
                galeriaTransfer.setCpfpessoa(rs.getString("cpfpessoa"));
                galeriaTransfer.setSexopessoa(rs.getString("sexopessoa"));
                galeriaTransfer.setCelularpessoa(rs.getString("celularpessoa"));
                galeriaTransfer.setStatuspessoa(rs.getString("statuspessoa"));
                galeriaTransfer.setTipopessoa(rs.getString("tipopessoa"));
                galeriaTransfer.setObservacaopessoa(rs.getString("observacaopessoa"));
                galeriaTransfer.setCependereco(rs.getString("cependereco"));
                galeriaTransfer.setComplementoendereco(rs.getString("complementoendereco"));
                galeriaTransfer.setLogradouroendereco(rs.getString("logradouroendereco"));
                galeriaTransfer.setCidadeendereco(rs.getString("cidadeendereco"));
                galeriaTransfer.setEstadoendereco(rs.getString("estadoendereco"));
                galeriaTransfer.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                galeriaTransfer.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa ou nome do evento
     *
     * @return Set<GaleriaTransfer>
     */
    public Set<GaleriaTransfer> listarEventoGaleria(String evento) {
        Set<GaleriaTransfer> listEventoGaleria = new HashSet<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela galeria*/\n"
                    + "galeria.idgaleria,"
                    + "galeria.descricaofotosgaleria\n"
                    + "\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "inner join\n"
                    + "projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "where (evento.nomeevento = ?)  \n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, evento);

            rs = pstm.executeQuery();

            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                galeriaTransfer.setId(rs.getShort("idgaleria"));
                galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                listEventoGaleria.add(galeriaTransfer);

            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar listarEventoGaleria!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    //criacao do metodo itemQuantidadeIdEventoTbPessoaEvento na obtencao de ter o total de id's do Evento
    public int itemQuantidadeIdEventoTbPessoaEvento(int idpessoa) {

        int retornarqtdidevento = 0;
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                     /*dados da tabela evento*/\n"
                    + "                     (pessoaevento.idevento)\n"
                    + "                     \n"
                    + "                     from projeto.tbpessoa pessoa \n"
                    + "                     inner join \n"
                    + "                     projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                     inner join\n"
                    + "                     projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                     inner join\n"
                    + "                     projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "                     where pessoa.idpessoa=?");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, idpessoa);
            rs = pstm.executeQuery();

            while (rs.next()) {
                //indentifica coluna
                rs.getShort("idevento");
                //conte a quantidade de vezes que aparece o id do Evento
                retornarqtdidevento++;
            }

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Listar dados do metodo: itemQuantidadeIdEventoTbPessoaEvento!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retornarqtdidevento;
    }

    //criacao do metodo itemIdEventoTbPessoaEvento na obtencao de ter o id do Evento
    public int itemIdEventoTbPessoaEvento(int idpessoa, String evento) {

        int retornaridevento = 0;
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                     /*dados da tabela evento*/\n"
                    + "                     (pessoaevento.idevento)\n"
                    + "                     \n"
                    + "                     from projeto.tbpessoa pessoa \n"
                    + "                     inner join \n"
                    + "                     projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                     inner join\n"
                    + "                     projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                     where ((pessoa.idpessoa=?) and (evento.nomeevento=?))");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, idpessoa);
            pstm.setObject(2, evento);

            rs = pstm.executeQuery();

            if (rs.next()) {
                //indentifica coluna
                retornaridevento = rs.getShort("idevento");
            }

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Listar dados do metodo: itemQuantidadeIdEventoTbPessoaEvento!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retornaridevento;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> ListarUsuario() {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                                                              /*dados da tabela pessoa*/\n"
                    + "                                                              pessoa.usuariopessoa					\n"
                    + "                                          		      /*dados da tabela pessoaevento*/\n"
                    + "                                                              from projeto.tbpessoa pessoa\n"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                galeriaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa e nome do evento as
     * caracteristicas formadas com as fotos
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> listarPessoaEventoGaleria(String evento, String usuario) {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela pessoa */\n"
                    + "galeria.fotosgaleria,"
                    + "galeria.descricaofotosgaleria\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "inner join\n"
                    + "projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "where ((evento.nomeevento = ?) and (pessoa.usuariopessoa= ?))  \n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, evento);
            pstm.setObject(2, usuario);

            rs = pstm.executeQuery();

            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                galeriaTransfer.setFotosgaleria(rs.getBytes("fotosgaleria"));
                galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar listarPessoaEventoGaleria!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> ListarTodosGaleria() {
        List<GaleriaTransfer> listEventoGaleria = new ArrayList<GaleriaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT idgaleria,\n"
                    + "       descricaofotosgaleria\n"
                    + "       FROM projeto.tbgaleria;"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {

                GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
                //galeria
                galeriaTransfer.setId(rs.getShort("idgaleria"));
                galeriaTransfer.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));

                listEventoGaleria.add(galeriaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar Lista todos os dados!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa ou nome do evento
     *
     * @param idgaleria
     * @return byte[]
     */
    public byte[] listarGaleria(String idgaleria) {
        byte[] image = null;

        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela galeria*/\n"
                    + "galeria.fotosgaleria"
                    + "\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "inner join\n"
                    + "projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "where (galeria.idgaleria = ?)  \n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());

            short id = Short.parseShort(idgaleria);

            pstm.setObject(1, id);

            rs = pstm.executeQuery();

            while (rs.next()) {
                //galeria
                image = (rs.getBytes("fotosgaleria"));
            }
            return image;

        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar listarEventoGaleria!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa,
     * Evento e Galeria, buscando atraves da pessoa ou nome do evento
     *
     * @param idgaleria
     * @return ByteArrayInputStream
     */
    public Set<GaleriaTransfer> downloadGaleria(String idgaleria) {
        Set<GaleriaTransfer> galeriaTransfers = new HashSet<GaleriaTransfer>();

        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "/*dados da tabela galeria*/\n"
                    + "galeria.idgaleria,"
                    + "galeria.fotosgaleria,"
                    + "galeria.descricaofotosgaleria"
                    + "\n"
                    + "from projeto.tbpessoa pessoa \n"
                    + "inner join \n"
                    + "projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "inner join\n"
                    + "projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "inner join\n"
                    + "projeto.tbgaleria galeria on(galeria.idevento = evento.idevento)\n"
                    + "where (galeria.idgaleria = ?)  \n"
            );

            pstm = conexao.prepareStatement(strBuffer.toString());

            short id = Short.parseShort(idgaleria);

            pstm.setObject(1, id);

            rs = pstm.executeQuery();

            while (rs.next()) {
                GaleriaTransfer galeria = new GaleriaTransfer();
                //obtem o id correspondente para a obtencao da visualizacao das imagens
                galeria.setId(rs.getShort("idgaleria"));
                //galeria de fotos e descricoes
                galeria.setFotosgaleria(rs.getBytes("fotosgaleria"));
                galeria.setDescricaofotosgaleria(rs.getString("descricaofotosgaleria"));
                galeriaTransfers.add(galeria);
            }
            
        } catch (SQLException ex) {
            logPrincipal(EventoDAO.class).error(">>>>Error ao tentar listar downloadGaleria!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return galeriaTransfers;
    }
}
