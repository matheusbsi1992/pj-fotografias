/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import br.com.fotografias.transfer.EventoTransfer;
import br.com.fotografias.transfer.PessoaTransfer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jordão Santos
 */
public class ListarMetodosPesquisaDAO extends DAO {

    public List<PessoaTransfer> rsToPessoa(ResultSet rs) throws Exception {
        List<PessoaTransfer> listaPessoa = new ArrayList<PessoaTransfer>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date today = new java.util.Date();
        String stringDate = df.format(today);
        while (rs.next()) {
            // PalavrasT palavraT = new PalavrasT();
            PessoaTransfer pessoa = new PessoaTransfer();
            pessoa.setId((short) rs.getInt("idpessoa"));
            pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
            pessoa.setNomepessoa(rs.getString("nomepessoa"));
            pessoa.setEmailpessoa(rs.getString("emailpessoa"));
            //stringDate=   df.format(rs.getDate("datacadastropessoa"));

            //      pessoa.setDatacadastropessoa(df.format(rs.getDate("datacadastropessoa")) );
            pessoa.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
            pessoa.setDatanascimento(rs.getDate("datanascimentpessoa"));
            pessoa.setCpfpessoa(rs.getString("cpfpessoa"));
            pessoa.setSexopessoa(rs.getString("sexopessoa"));
            pessoa.setCelularpessoa(rs.getString("celularpessoa"));
            pessoa.setStatuspessoa(rs.getString("statuspessoa"));
            pessoa.setTipopessoa(rs.getString("tipopessoa"));
            pessoa.setObservacaopessoa(rs.getString("observacaopessoa"));
            pessoa.setCependereco(rs.getString("cependereco"));
            pessoa.setComplementoendereco(rs.getString("complementoendereco"));
            pessoa.setLogradouroendereco(rs.getString("logradouroendereco"));
            pessoa.setCidadeendereco(rs.getString("cidadeendereco"));
            pessoa.setEstadoendereco(rs.getString("estadoendereco"));

            listaPessoa.add(pessoa);

        }
        return listaPessoa;
    }

    public List<PessoaTransfer> getAllPessoa() throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa order by nomepessoa";
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
        return null;

    }

    public List<PessoaTransfer> getByLocalidade(String Cidade, String Estado) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa where upper(cidadeendereco) like upper(?) and upper(estadoendereco) like upper(?) order by cidadeendereco;";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + Cidade + "%");
            ps.setObject(2, "%" + Estado + "%");
            rs = ps.executeQuery();

            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql);");
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByCpf(String Cpf) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa where  upper(cpfpessoa) like upper(?) order by nomepessoa;";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + Cpf + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            // throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByEmail(String pessoaEmail) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa)  order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa where upper(emailpessoa)  like upper(?) order by nomepessoa;";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + pessoaEmail + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByNome(String pessoaNome) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = ""
                    + "select "
                    + " * "
                    + " from "
                    + " projeto.tbpessoa "
                    + " where upper(nomepessoa) "
                    + " like upper(?) order by nomepessoa";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + pessoaNome + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByStatus(String pessoaStatus) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa where upper(statuspessoa) like upper(?) order by nomepessoa ;";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + pessoaStatus + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByTipo(String pessoaTipo) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbpessoa where upper(tipopessoa)  like upper(?) order by nomepessoa ;";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + pessoaTipo + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByUsuario(String pessoaUsuario) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String sql = "select "
                    + "* "
                    + "from "
                    + "projeto.tbpessoa "
                    + "where "
                    + "upper(usuariopessoa) "
                    + "like upper(?) order by usuariopessoa";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + pessoaUsuario + "%");
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByIntervalodeDataNascimento(Date Data1, Date Data2) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //  String sql = "select * from projeto.tbpessoa where upper(usuariopessoa) like upper(?) order by usuariopessoa";
            String sql = "SELECT * FROM projeto.tbpessoa WHERE datanascimentpessoa >= (?) and  datanascimentpessoa <= (?)";
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(Data1.getTime()));
            ps.setDate(2, new java.sql.Date(Data2.getTime()));

            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByIntervalodeDataCadastro(Date Data1, Date Data2) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            String sql = "SELECT "
                    + " *   "
                    + " FROM"
                    + " projeto.tbpessoa "
                    + " WHERE datacadastropessoa >= (?) and  datacadastropessoa <= (?)";
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(Data1.getTime()));
            ps.setDate(2, new java.sql.Date(Data2.getTime()));

            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }
    //////////////////// METODOS RELACIONADOS AOS EVENTOS////////////////

    public List<EventoTransfer> rsToEventos(ResultSet rs) throws Exception {
        List<EventoTransfer> listaPessoaEvento = new ArrayList<EventoTransfer>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date today = new java.util.Date();
        String stringDate = df.format(today);
        while (rs.next()) {
            // PalavrasT palavraT = new PalavrasT();
            EventoTransfer pessoaEvento = new EventoTransfer();
            pessoaEvento.setUsuariopessoa(rs.getString("usuariopessoa"));
            pessoaEvento.setNomepessoa(rs.getString("nomepessoa"));
            pessoaEvento.setEmailpessoa(rs.getString("emailpessoa"));
            //stringDate=   df.format(rs.getDate("datacadastropessoa"));

            //      pessoa.setDatacadastropessoa(df.format(rs.getDate("datacadastropessoa")) );
            pessoaEvento.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
            pessoaEvento.setDatanascimento(rs.getDate("datanascimentpessoa"));
            pessoaEvento.setCpfpessoa(rs.getString("cpfpessoa"));
            pessoaEvento.setSexopessoa(rs.getString("sexopessoa"));
            pessoaEvento.setCelularpessoa(rs.getString("celularpessoa"));
            pessoaEvento.setStatuspessoa(rs.getString("statuspessoa"));
            pessoaEvento.setTipopessoa(rs.getString("tipopessoa"));
            pessoaEvento.setObservacaopessoa(rs.getString("observacaopessoa"));
            pessoaEvento.setCependereco(rs.getString("cependereco"));
            pessoaEvento.setComplementoendereco(rs.getString("complementoendereco"));
            pessoaEvento.setLogradouroendereco(rs.getString("logradouroendereco"));

            pessoaEvento.setCidadeendereco(rs.getString("cidadeendereco"));
            pessoaEvento.setEstadoendereco(rs.getString("estadoendereco"));

            // Evento //
            pessoaEvento.setNomeevento(rs.getString("nomeevento"));
            pessoaEvento.setDataevento(rs.getDate("dataevento"));
            //pessoaEvento.setIdevento(rs.getInt("qtdfotosevento"));

            listaPessoaEvento.add(pessoaEvento);

        }
        return listaPessoaEvento;
    }

    public List<EventoTransfer> getByNomeEventos(String nomeEvento) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            String sql = "select "
                    + " pes.datacadastropessoa,"
                    + " pes.nomepessoa,"
                    + " pes.idpessoa, "
                    + " eve.nomeevento,"
                    + " eve.dataevento,"
                    + " eve.qtdfotosevento,"
                    + " eve.idpessoa "
                    + " from projeto.tbpessoa AS pes "
                    + " inner JOIN projeto.tbevento AS eve ON pes.idpessoa = eve.idpessoa "
                    + " WHERE upper(eve.nomeevento)like upper(?)  order by eve.nomeevento";
            ps = conexao.prepareStatement(sql);
            ps.setObject(1, "%" + nomeEvento + "%");

            rs = ps.executeQuery();
            return rsToEventos(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getByDataEventos(Date Data1, Date Data2) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            /* select pes.datacadastropessoa, pes.nomepessoa, pes.idpessoa, eve.nomeevento,eve.dataevento,eve.qtdfotosevento,eve.idpessoa 
  from projeto.tbpessoa AS pes 
  inner JOIN projeto.tbevento AS eve ON pes.idpessoa = eve.idpessoa*/
            String sql = "SELECT * FROM projeto.tbpessoa WHERE datacadastropessoa >= (?) and  datacadastropessoa <= (?)";
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(Data1.getTime()));
            ps.setDate(2, new java.sql.Date(Data2.getTime()));

            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public List<PessoaTransfer> getAllEventos() throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //   "SELECT * FROM projeto.tbpessoa where upper(nomepessoa) like upper(?) order by nomepessoa ;"
            String sql = "select * from projeto.tbEvento order by nomeevento";
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            return rsToPessoa(rs);
        } catch (Exception e) {
            System.out.println(" Passou pelo ps = conexao.prepareStatement(sql); ERROOOOOO..:::: " + e);
            //throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
        return null;

    }

}
