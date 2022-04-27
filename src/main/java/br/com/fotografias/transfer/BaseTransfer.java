/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.fotografias.transfer;

import java.io.Serializable;

/**
 *
 * @author linuxserver
 * @param <T> representa o tipo ID generico
 */
public abstract class BaseTransfer  <T extends Number > implements Serializable{
   
    private static final long serialVersionUID = 1L;

    public abstract T getId();

    public abstract void setId(T id);

    @Override
    public int hashCode() {
        return (getId() != null) ? (getClass().hashCode() + getId().hashCode()) : super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && getId() != null) ? getId().equals(((BaseTransfer<?>) other).getId()) : (other == this);
    }

    @Override
    public String toString() {
        
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

}
