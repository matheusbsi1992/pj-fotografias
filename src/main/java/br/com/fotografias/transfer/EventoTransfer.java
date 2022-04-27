/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.transfer;

import br.com.fotografias.dao.PessoaDAO;
import br.com.fotografias.transfer.BaseTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Jordão Santos
 */
public class EventoTransfer extends PessoaTransfer {

    private short idevento = 0;
    private String nomeevento;
    private Date dataevento;
    private String observacaoevento;
    private List<String> listaPessoaTransfer = new ArrayList<String>();
    private String statusevento = "andamento";

    @Override
    public Short getId() {
        return idevento;
    }

    @Override
    public void setId(Short id) {
        this.idevento = id;
    }

    public String getNomeevento() {
        return nomeevento;
    }

    public void setNomeevento(String nomeevento) {
        this.nomeevento = nomeevento;
    }

    public Date getDataevento() {
        return dataevento;
    }

    public void setDataevento(Date dataevento) {
        this.dataevento = dataevento;
    }

    public void setObservacaoevento(String observacaoevento) {
        this.observacaoevento = observacaoevento;
    }

    public String getObservacaoevento() {
        return observacaoevento;
    }

    public List<String> getListaPessoaTransfer() {
        return listaPessoaTransfer;
    }

    public void setListaPessoaTransfer(List<String> listaPessoaTransfer) {
        this.listaPessoaTransfer = listaPessoaTransfer;
    }

    public void setStatusevento(String statusevento) {
        this.statusevento = statusevento;
    }

    public String getStatusevento() {
        return statusevento;
    }

}
