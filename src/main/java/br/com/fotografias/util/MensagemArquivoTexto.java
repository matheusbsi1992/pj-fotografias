/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jandisson
 */
public class MensagemArquivoTexto {

    private File arquivo;
    private FileReader fileReader;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;
    private FileWriter fileWriter;

    public MensagemArquivoTexto(Object arquivo, Object classe, Object sql) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datahrs = format.format(Calendar.getInstance().getTime());
        logPrincipal(arquivo, classe, sql, datahrs);
    }

    private void logPrincipal(Object arq, Object classe, Object sql, Object formatDate) {

        StringBuffer dadosarquivo = new StringBuffer();
        dadosarquivo.append(formatDate).append("[").append(classe).append("],").append(arq).append(",[").append(sql).append("]");

        try {

            arquivo = new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\pjfotografias\\logs\\log.log");
            fileReader = new FileReader(arquivo);
            bufferedReader = new BufferedReader(fileReader);
            List<Object> objects = new ArrayList<Object>();
            while (bufferedReader.ready()) {
                objects.add(bufferedReader.readLine());
            }
            fileWriter = new FileWriter(arquivo);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Object object : objects) {
                bufferedWriter.write(object.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.write(dadosarquivo.toString());
            closedBuffer();
        } catch (FileNotFoundException ex) {
            try {
                arquivo.createNewFile();
                logPrincipal(arq, classe, sql, formatDate);
            } catch (IOException ex1) {
                System.exit(0);
            }
        } catch (IOException ex) {
            System.exit(0);
        }
    }

    public static void closedBuffer() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MensagemArquivoTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
