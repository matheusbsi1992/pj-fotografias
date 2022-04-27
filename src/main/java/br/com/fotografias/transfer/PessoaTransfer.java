/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.transfer;

import java.util.Date;

/**
 *
 * @author DHNSTI02
 */
public class PessoaTransfer extends BaseTransfer<Short> {

    private short idpessoa = 0;
    private String usuariopessoa;
    private String senhapessoa;
    private String senhaconfirmacao;
    private String nomepessoa;
    private String emailpessoa;
    private String cpfpessoa;
    private String cependereco;
    private String sexopessoa;
    private String celularpessoa;
    private String statuspessoa;
    private String tipopessoa;
    private String observacaopessoa;
    private String complementoendereco;
    private String logradouroendereco;
    private String cidadeendereco;
    private String estadoendereco;
    private Date datacadastropessoa;
    private Date datanascimento;

    public PessoaTransfer() {
    }

    @Override
    public Short getId() {
        return idpessoa;
    }

    @Override
    public void setId(Short id) {
        this.idpessoa = id;
    }

    public String getUsuariopessoa() {
        return usuariopessoa;
    }

    public void setUsuariopessoa(String usuariopessoa) {
        this.usuariopessoa = usuariopessoa;
    }

    public String getSenhapessoa() {
        return senhapessoa;
    }

    public void setSenhapessoa(String senhapessoa) {
        this.senhapessoa = senhapessoa;
    }

    public void setSenhaconfirmacao(String senhaconfirmacao) {
        this.senhaconfirmacao = senhaconfirmacao;
    }

    public String getSenhaconfirmacao() {
        return senhaconfirmacao;
    }

    public String getNomepessoa() {
        return nomepessoa;
    }

    public void setNomepessoa(String nomepessoa) {
        this.nomepessoa = nomepessoa;
    }

    public String getCpfpessoa() {
        return cpfpessoa;
    }

    public void setCpfpessoa(String cpfpessoa) {
        this.cpfpessoa = cpfpessoa;
    }

    public String getSexopessoa() {
        return sexopessoa;
    }

    public void setSexopessoa(String sexopessoa) {
        this.sexopessoa = sexopessoa;
    }

    public String getCelularpessoa() {
        return celularpessoa;
    }

    public void setCelularpessoa(String celularpessoa) {
        this.celularpessoa = celularpessoa;
    }

    public String getStatuspessoa() {
        return statuspessoa;
    }

    public void setStatuspessoa(String statuspessoa) {
        this.statuspessoa = statuspessoa;
    }

    public String getTipopessoa() {
        return tipopessoa;
    }

    public void setTipopessoa(String tipopessoa) {
        this.tipopessoa = tipopessoa;
    }

    public String getObservacaopessoa() {
        return observacaopessoa;
    }

    public void setObservacaopessoa(String observacaopessoa) {
        this.observacaopessoa = observacaopessoa;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getEmailpessoa() {
        return emailpessoa;
    }

    public void setEmailpessoa(String emailpessoa) {
        this.emailpessoa = emailpessoa;
    }

    public String getCependereco() {
        return cependereco;
    }

    public void setCependereco(String cependereco) {
        this.cependereco = cependereco;
    }

    public String getComplementoendereco() {
        return complementoendereco;
    }

    public void setComplementoendereco(String complementoendereco) {
        this.complementoendereco = complementoendereco;
    }

    public String getLogradouroendereco() {
        return logradouroendereco;
    }

    public void setLogradouroendereco(String logradouroendereco) {
        this.logradouroendereco = logradouroendereco;
    }

    public String getCidadeendereco() {
        return cidadeendereco;
    }

    public void setCidadeendereco(String cidadeendereco) {
        this.cidadeendereco = cidadeendereco;
    }

    public String getEstadoendereco() {
        return estadoendereco;
    }

    public void setEstadoendereco(String estadoendereco) {
        this.estadoendereco = estadoendereco;
    }

    public Date getDatacadastropessoa() {
        return datacadastropessoa;
    }

    public void setDatacadastropessoa(Date datacadastropessoa) {
        this.datacadastropessoa = datacadastropessoa;
    }

}
