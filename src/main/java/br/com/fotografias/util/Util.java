/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.util;

import br.com.fotografias.transfer.PessoaTransfer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.UnexpectedTypeException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 *
 * @author DHNSTI02
 */
public class Util {

   

    /**
     * Metodo hashCode
     *
     *
     * @return SimpleHash
     * @param String valorHash
     */
    public SimpleHash hashCode(String valorHash) {

        SimpleHash simplehash = new Md5Hash(valorHash);

        if (simplehash != null && simplehash.toString().length() > 0) {
            return simplehash;
        }
        return null;
    }

    //retorne 3 milisegundos
    public void getTempoMillisSegundos() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Data atual do Sistema
    public Date getPegaDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        return calendar.getTime();
    }

    public java.sql.Date getSqlDate(Date data) {
        if (data == null) {
            return null;
        }
        return java.sql.Date.valueOf(dateToStr(data, "dd/MM/yyyy"));
    }

    public java.sql.Timestamp getSqlTimeStamp(Date data) {
        if (data == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(dateToStr(data, "yyyy-MM-dd hh:mm:ss"));
    }

    public java.sql.Date convertUtilToSql(java.util.Date uDate) {
        if (uDate == null) {
            return null;
        }
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;

    }

    public String dateToStr(Date date, String format) {
        String retorno = null;
        if ((null != date) && (null != format)) {
            SimpleDateFormat formater = new SimpleDateFormat(format);
            retorno = formater.format(date);
        }
        return retorno;
    }

    public double converterValor(double valor) {
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        valor = bd.doubleValue();

        return valor;

    }

    public String decimal(float dec) {
        StringBuffer frase;

        float vlr = (float) dec;
        BigDecimal big = new BigDecimal(vlr);
        big = big.setScale(3, BigDecimal.ROUND_HALF_EVEN);
        String converte = String.valueOf(big);
        int w = converte.length();
        w = (w - 1);
        char r = converte.charAt(w);
        String p = "" + r;
        int it = Integer.parseInt(p);

        /*caso a terceira casa seja menor que 5 corte a 3 casa depois da virgula*/
        if (it < 5) {
            frase = new StringBuffer(converte);
            converte = "" + frase.deleteCharAt(w);
        } /* se a teceira casa for maior que 4 joga para DecimalFormat que ele arredonada automatico para cima sem a terceira casa...*/ else {
            DecimalFormat decimal = new DecimalFormat("0.00");
            double vl_conv = Double.parseDouble(converte);
            converte = (decimal.format(vl_conv));
            return converte = converte.replaceAll(",", ".");
        }
        return null;

    }

}
