/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.ContaAlterarUsuarioDAO;
import br.com.fotografias.dao.EventoDAO;
import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.GaleriaTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import br.com.fotografias.util.Util;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Jandisson
 */
public class AlterarEventoBO {

    //criacao do atributo cadloginDAO
    private EventoDAO eventoDAO = null;

    //criacao do atributo pessoaDAO
    private PessoaDAO pessoaDAO = null;

    //criacao do Atributo adminiAdminCadUsuarioBO
    public AlterarEventoBO() {
        if (eventoDAO == null) {
            eventoDAO = new EventoDAO();
        }
        if (pessoaDAO == null) {
            pessoaDAO = new PessoaDAO();
        }
    }

    /*
     *Metodo que retorna uma lista de dados
     */
    public List<GaleriaTransfer> ListarTodos() {
        return eventoDAO.ListarTodos();
    }

}
