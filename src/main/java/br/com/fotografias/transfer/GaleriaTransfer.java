/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.transfer;

/**
 *
 * @author Jordão Santos
 */
public class GaleriaTransfer extends EventoTransfer {

    private short idgaleria = 0;
    private String descricaofotosgaleria;
    private byte[] fotosgaleria;

    public GaleriaTransfer() {
    }

    @Override
    public Short getId() {
        return idgaleria;
    }

    @Override
    public void setId(Short id) {
        this.idgaleria = id;
    }

    public String getDescricaofotosgaleria() {
        return descricaofotosgaleria;
    }

    public void setDescricaofotosgaleria(String descricaofotosgaleria) {
        this.descricaofotosgaleria = descricaofotosgaleria;
    }

    public byte[] getFotosgaleria() {
        return fotosgaleria;
    }

    public void setFotosgaleria(byte[] fotosgaleria) {
        this.fotosgaleria = fotosgaleria;
    }
}
