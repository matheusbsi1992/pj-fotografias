/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.email;

import br.com.fotografias.transfer.EmailTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Jandisson
 */
public class ContaEmailCliente {

    public ContaEmailCliente() {

    }

    private static HtmlEmail mail = new HtmlEmail();
    private static final StringBuffer strB = new StringBuffer();
    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String USERNAME = " ";
    private static final String PASSWORD = " ";
    private static final String EMAILORIGEM = " ";
    private static String RESPOSTA = "INVALIDO";

    public void cadastrarConectadoEmail(PessoaTransfer pessoaTransfer) {

        try {
            //criacao de escrita pré-visualização dos dados
            strB.append("<br></br>");
            strB.append("<h1>Olá, tudo bem ?</h1>");
            strB.append("<h4>Sua conta foi cadastrada.</h4>");
            strB.append("<h4>Para acesso ao sistema mobile é preciso:</h4>");
            strB.append("<p>Usuário: <b>").append(pessoaTransfer.getUsuariopessoa().toUpperCase()).append("</b> e senha: <b>").append(pessoaTransfer.getSenhapessoa()).append("</b> ;");
            strB.append("<br></br>");
            strB.append("<center><img src=\"\"></center>");
            strB.append("<br></br>");
            strB.append("<center><font size=\"3px\">Por favor, não responda diretamente a este e-mail.</font></center>");
            strB.append("<center><font size=\"3px\">Esse e-mail foi enviado de um endereço apenas para notificação que não pode aceitar e-mails de entrada.</font></center>");
            strB.append("<center><font size=\"3px\">Se tiver perguntas ou precisar de ajuda, pergunte aqui (xx) xxxx-xxx.</font></center>");
            strB.append("<center><font size=\"3px\">Localidade</font></center>");
            strB.append("<center><font size=\"3px\"> . Todos os direitos reservados.</font></center>");
            strB.append("<center><font size=\"3px\">Empresa é marca comercial ou marca registrada no BR.</font></center>");

            //escreve dados para o envio do email;
            EmailTransfer emailTransfer = new EmailTransfer();
            emailTransfer.setTitulo("USUÁRIO E SENHA DE ACESSO PJFOTOGRAFIAS");
            emailTransfer.setMensagem(strB.toString());
            emailTransfer.setEmailpessoa(pessoaTransfer.getEmailpessoa().toUpperCase());

            enviarEmail(emailTransfer);

            System.out.println(emailTransfer.getTitulo());
            System.out.println(emailTransfer.getMensagem());
            System.out.println(emailTransfer.getEmailpessoa());

            System.out.println("Resposta = " + RESPOSTA);
        } catch (Exception ex) {
            Logger.getLogger(ContaEmailCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alterarConectadoEmail(PessoaTransfer pessoaTransfer) {

        try {
            strB.append("<br></br>");
            strB.append("<h1>Olá, tudo bem ?</h1>");
            strB.append("<h4>Sua senha foi alterada.</h4>");
            strB.append("<h4>Para acesso ao sistema mobile é preciso:</h4>");
            strB.append("<p>Usuário: <b>").append(pessoaTransfer.getUsuariopessoa().toUpperCase()).append("</b> e senha: <b>").append(pessoaTransfer.getSenhapessoa()).append("</b> ;");
            strB.append("<br></br>");
            strB.append("<center><img src=\"\"></center>");
            strB.append("<br></br>");
            strB.append("<center><font size=\"3px\">Por favor, não responda diretamente a este e-mail.</font></center>");
            strB.append("<center><font size=\"3px\">Esse e-mail foi enviado de um endereço apenas para notificação que não pode aceitar e-mails de entrada.</font></center>");
            strB.append("<center><font size=\"3px\">Se tiver perguntas ou precisar de ajuda, pergunte aqui (xx) xxxx-xxx.</font></center>");
            strB.append("<center><font size=\"3px\">Localidade</font></center>");
            strB.append("<center><font size=\"3px\"> . Todos os direitos reservados.</font></center>");
            strB.append("<center><font size=\"3px\">Empresa é marca comercial ou marca registrada no BR.</font></center>");

            //escreve dados para o envio do email;
            EmailTransfer emailTransfer = new EmailTransfer();
            emailTransfer.setTitulo("USUÁRIO E SENHA DE ALTERAÇÃO PJFOTOGRAFIAS");
            emailTransfer.setMensagem(strB.toString());
            emailTransfer.setEmailpessoa(pessoaTransfer.getEmailpessoa().toUpperCase());

            enviarEmail(emailTransfer);

            System.out.println(emailTransfer.getTitulo());
            System.out.println(emailTransfer.getMensagem());
            System.out.println(emailTransfer.getEmailpessoa());

            System.out.println("Resposta = " + RESPOSTA);
        } catch (Exception ex) {
            Logger.getLogger(ContaEmailCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarEmail(EmailTransfer email) throws EmailException, Exception {
        try {
            //abri a conexao de Email
            mail = conectarEmail();
            //escrever informacoes a serem passadas
            mail.setSubject(email.getTitulo());
            mail.setHtmlMsg(email.getMensagem());
            mail.addTo(email.getEmailpessoa());

            RESPOSTA = mail.send();

        } catch (Exception ex) {
            Logger.getLogger(ContaEmailCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HtmlEmail conectarEmail() throws EmailException, Exception {
        try {
            ///email
            mail.setHostName(HOSTNAME);
            //porta
            mail.setSmtpPort(587);
            //usuario e senha 
            mail.setAuthentication(USERNAME, PASSWORD);
            //protocolo
            mail.setTLS(true);
            //mail.setDebug(true);
            //remetente do email
            mail.setFrom("PJFOTOGRAFIAS");
            return mail;
        } catch (Exception ex) {
            Logger.getLogger(ContaEmailCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
