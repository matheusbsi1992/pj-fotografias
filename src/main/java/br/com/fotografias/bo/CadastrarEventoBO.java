/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.CadastrarEventoDAO;
import br.com.fotografias.dao.ContaCadastrarUsuarioDAO;
import br.com.fotografias.dao.EventoDAO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.mb.MBBase;
import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Jordão Santos
 */
public class CadastrarEventoBO {

    //criacao do atributo contaCadastrarUsuarioDAO
    private CadastrarEventoDAO contaCadastrarEventoDAO = null;

    //criacao do atributo eventoDAO
    private EventoDAO eventoDAO = null;

    //criacao do atributo pessoaDAO
    private PessoaDAO pessoaDAO = null;

    public CadastrarEventoBO() {
        if (contaCadastrarEventoDAO == null) {
            contaCadastrarEventoDAO = new CadastrarEventoDAO();
        }
        if (eventoDAO == null) {
            eventoDAO = new EventoDAO();
        }
        if (pessoaDAO == null) {
            pessoaDAO = new PessoaDAO();
        }
    }

    public boolean inserirEvento(GaleriaTransfer galeriaTransfer) throws SQLException {
        return contaCadastrarEventoDAO.inserirEvento(galeriaTransfer) == true;
    }

    public boolean inserirGaleria(GaleriaTransfer galeriaTransfer) throws SQLException {
        return contaCadastrarEventoDAO.inserirGaleria(galeriaTransfer) == true;
    }

    public boolean inserirPessoaEventoGaleria(String idimagem, String nomeevento, String idpessoa) throws SQLException {
        return contaCadastrarEventoDAO.inserirPessoaEventoGaleria(Short.valueOf(idimagem), nomeevento, Short.valueOf(idpessoa));
    }

    public boolean alterarStatusPessoaEvento(String idfoto, String nomeevento, String idpessoa) throws SQLException {
        return contaCadastrarEventoDAO.alterarStatusPessoaEvento(Short.valueOf(idfoto), nomeevento, Short.valueOf(idpessoa));
    }

    public List<PessoaTransfer> buildCategoryNameList() throws SQLException {
        return contaCadastrarEventoDAO.buildCategoryNameList();
    }

    /*
     *Metodo que retorna uma lista de dados referente ao Evento
     */
    public List<GaleriaTransfer> ListarTodos() {
        return eventoDAO.ListarTodos();
    }

    /*
     *Metodo que retorna uma lista de dados referente ao Evento e ID da pessoa
     */
    public List<GaleriaTransfer> ListarEventosPessoa(int id) {
        return eventoDAO.ListarEventosPessoa(id);
    }

    /*
     *Metodo que retorna uma lista de dados referente ao Evento e ID da pessoa
     */
    public List<GaleriaTransfer> ListarEventosEPessoa(String nomeevento, int id) {
        return eventoDAO.ListarEventosEPessoa(nomeevento, id);
    }

    /*Metodo responsavel por excluir evento, galeria e pessoaevento
     * @param String idevento
     */
    public void excluirPessoaEvento(String idpessoa, String evento) {
        contaCadastrarEventoDAO.deletarPessoaEventoGaleria(Integer.parseInt(idpessoa), evento);
    }

    /*Metodo responsavel por excluir evento, galeria e pessoaevento
     * @param String idevento
     */
    public boolean excluirImagemGaleria(int idgaleria) {
        return contaCadastrarEventoDAO.deletarImagemGaleria(idgaleria);
    }

    /**
     * Metodo responsavel por retornar ByteArrayInputStream contido com a image
     * do cliente
     *
     * @param idimage
     * @return ByteArrayInputStream
     */
    public Set<GaleriaTransfer> downloadGaleria(String idimage) {
        return eventoDAO.downloadGaleria(idimage);        
    }

    /*
     *Metodo que retorna uma lista de dados referente a Pessoa ou  Evento
     */
    public List<GaleriaTransfer> listarPessoaEvento(String pessoaevento) {
        //evento
        List<GaleriaTransfer> evento = eventoDAO.listarEvento(pessoaevento.trim().toUpperCase());

        if (evento.size() > 0) {
            return evento;
        } else {
            //pessoa
            return eventoDAO.listarPessoa(pessoaevento.trim().toUpperCase());
        }
    }

    /*
     *Metodo que retorna uma lista de dados referente ao   Evento
     */
    public List<GaleriaTransfer> listarEvento(String evento) {
        //evento
        List<GaleriaTransfer> eventogaleria = eventoDAO.listarEvento(evento.trim().toUpperCase());
        return eventogaleria;
    }

    /*
     *Metodo que retorna uma lista de dados referente ao Usuario passando o evento
     */
    public List<PessoaTransfer> listarUsuarioEvento(String evento) {
        //pessoa
        List<PessoaTransfer> eventogaleria = pessoaDAO.ListarUsuarioEvento(evento.trim().toUpperCase());
        return eventogaleria;
    }

    /*
     *Metodo que retorna uma lista de dados referente ao Usuario passando o seu id
     */
    public List<PessoaTransfer> listarUsuarioEventoId(short id) {
        List<PessoaTransfer> eventogaleria = pessoaDAO.ListarUsuarioEvento(id);
        return eventogaleria;
    }


    /*
     *Metodo que retorna uma lista de dados referente ao   Evento
     */
    public Set<GaleriaTransfer> listarEventoGaleria(String evento) {
        //evento
        Set<GaleriaTransfer> eventogaleria = eventoDAO.listarEventoGaleria(evento.trim().toUpperCase());
        return eventogaleria;
    }

    /*
     *Metodo que retorna uma lista de dados referente as fotos do usuario
     */
    public List<GaleriaTransfer> listarPessoaEventoGaleria(String evento, String usuario) {
        //evento e usuario/pessoa
        List<GaleriaTransfer> eventopessoagaleria = eventoDAO.listarPessoaEventoGaleria(evento.trim().toUpperCase(), usuario.trim().toUpperCase());
        return eventopessoagaleria;
    }

    /**
     *
     * @return List<GaleriaTransfer>
     */
    public List<GaleriaTransfer> listarGaleria() {
        return eventoDAO.ListarTodosGaleria();
    }

    /**
     *
     * @return listarGaleria
     */
    public byte[] listarGaleria(String idgaleria) {
        return eventoDAO.listarGaleria(idgaleria);
    }

}