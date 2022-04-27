/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.bo;

import br.com.fotografias.dao.ContaCadastrarUsuarioDAO;
import br.com.fotografias.dao.ListarMetodosPesquisaDAO;
import br.com.fotografias.transfer.PessoaTransfer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jordão Santos
 */
public class ListarMetodosPesquisaBO {

    public List<PessoaTransfer> getAllPessoa() throws Exception {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getAllPessoa();
    }

    public List<PessoaTransfer> getByLocalidade(String Cidade, String Estado) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        System.out.println(" Passou pelo ListarMetodosPesquisaBO;");
        return listaTodos.getByLocalidade(Cidade, Estado);

    }

    public List<PessoaTransfer> getByCpf(String CPF) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByCpf(CPF);
    }

    public List<PessoaTransfer> getByEmail(String Email) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByEmail(Email);
    }

    public List<PessoaTransfer> getByNome(String pessoaNome) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByNome(pessoaNome);
    }

    public List<PessoaTransfer> getByStatus(String pessoaStatus) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByStatus(pessoaStatus);
    }

    public List<PessoaTransfer> getByTipo(String pessoaTipo) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByTipo(pessoaTipo);
    }

    public List<PessoaTransfer> getByUsuario(String pessoaUsuario) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByUsuario(pessoaUsuario);
    }

    public List<PessoaTransfer> getByIntervaloDataNascimento(Date Data1, Date Data2) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByIntervalodeDataNascimento(Data1, Data2);
    }

    public List<PessoaTransfer> getByIntervaloDataCadastro(Date Data1, Date Data2) {
        ListarMetodosPesquisaDAO listaTodos = new ListarMetodosPesquisaDAO();
        return listaTodos.getByIntervalodeDataCadastro(Data1, Data2);
    }

}
