/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.dao;

import static br.com.fotografias.conexaofactory.Conexao.closeDataSource;
import br.com.fotografias.util.Util;
import br.com.fotografias.transfer.PessoaTransfer;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

/**
 *
 * @author DHNSTI02
 */
public class PessoaDAO extends DAO {

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa
     * contido em um ResultSet
     *
     * @return List<PessoaT>
     * @param ResultSet rs
     * @exception SQLException
     */
    public List<PessoaTransfer> rsTolist(ResultSet rs) {
        List<PessoaTransfer> listCliente = new ArrayList<PessoaTransfer>();
        try {

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setId(rs.getShort("idpessoa"));
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoa.setSenhapessoa(rs.getString("senhapessoa"));
                pessoa.setNomepessoa(rs.getString("nomepessoa"));
                pessoa.setEmailpessoa(rs.getString("emailpessoa"));
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
                pessoa.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                pessoa.setDatanascimento(rs.getDate("datanascimentpessoa"));

                listCliente.add(pessoa);
            }
            return listCliente;

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>Error no metodo rsTolist", ex);
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa e
     * Cliente
     *
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> ListarTodos() {
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "                      FROM projeto.tbpessoa\n"
                    + "                      WHERE (tipopessoa ='USUARIO'\n"
                    + "                      OR  tipopessoa ='CLIENTE' )\n"
                    + "                      ORDER BY nomepessoa;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            rs = pstm.executeQuery();
            return rsTolist(rs);

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error ao tentar Lista todos os dados da Tabela Pessoa", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo retornar de uma lista contendo ID pessoa PFK
     *
     * @param int ID
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> pessoaEventoID() {
        List<PessoaTransfer> listaPessoaID = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT usuariopessoa\n"
                    + "FROM projeto.tbpessoa");
            pstm = conexao.prepareStatement(strBuffer.toString());

            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                listaPessoaID.add(pessoa);
            }
        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error sobre o ID da Pessoa", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoaID;
    }

    /**
     * Metodo retornar ID pessoa
     *
     * @param ID
     * @return int
     */
    public boolean getIdCliente(int ID) {
        try {
            if (pessoaID(ID).getId().equals(ID)) {
                return true;
            }

        } catch (NullPointerException ex) {
            logPrincipal(PessoaDAO.class).error(">>>ERROR AO LISTAR ID DO CLIENTE!", ex);
        }
        return false;
    }

    /**
     * Metodo retornar de uma lista contendo ID pessoa PFK
     *
     * @param int ID
     * @return List<PessoaT>
     * @exception SQLException
     */
    public PessoaTransfer pessoaID(int ID) {
        List<PessoaTransfer> listaPessoaID = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "FROM projeto.tbpessoa"
                    + "WHERE idpessoa = ?;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, ID);
            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setId(rs.getShort("idpessoa"));
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoa.setSenhapessoa(rs.getString("senhapessoa"));
                pessoa.setNomepessoa(rs.getString("nomepessoa"));
                pessoa.setEmailpessoa(rs.getString("emailpessoa"));
                pessoa.setCpfpessoa(rs.getString("cpfpessoa"));
                pessoa.setSexopessoa(rs.getString("sexopessoa"));
                pessoa.setCelularpessoa(rs.getString("celularpessoa"));
                pessoa.setStatuspessoa(rs.getString("statuspessoa"));
                pessoa.setTipopessoa(rs.getString("tipopessoa"));
                pessoa.setObservacaopessoa(rs.getString("observacaopessoa"));
                pessoa.setCependereco(rs.getString("observacaopessoa"));
                pessoa.setComplementoendereco(rs.getString("complementoendereco"));
                pessoa.setLogradouroendereco(rs.getString("logradouroendereco"));
                pessoa.setCidadeendereco(rs.getString("cidadeendereco"));
                pessoa.setEstadoendereco(rs.getString("estadoendereco"));
                pessoa.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                pessoa.setDatanascimento(rs.getDate("datanascimentpessoa"));
                listaPessoaID.add(pessoa);
            }
        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error sobre o ID da Pessoa", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoaID.size() > 0 ? listaPessoaID.get(0) : null;
    }

    /**
     * Metodo retornar de uma lista contendo Dados do Cliente
     *
     * @param String nomePessoa
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> buscarPorNome(String nomePessoa) {
        try {
            strBuffer = new StringBuffer().append("select * \n"
                    + "                    from \n"
                    + "                    projeto.tbpessoa \n"
                    + "                    where upper(nomepessoa) \n"
                    + "                    like upper(?) order by nomepessoa;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, "%" + nomePessoa + "%");
            rs = pstm.executeQuery();

            return rsTolist(rs);

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error ao tentar Buscar nome da Pessoa", ex);
        }
        return null;
    }

    /**
     * Metodo retornar email do usuario cadastrado
     *
     * @param String nomePesso
     * @return List<PessoaT>
     * @exception SQLException
     */
    public boolean buscarPorEmail(String email) {

        try {

            strBuffer = new StringBuffer().append("SELECT emailpessoa\n"
                    + " FROM projeto.tbpessoa"
                    + " WHERE emailpessoa = ?");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, email);
            rs = pstm.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar Buscar email da Pessoa", ex);
        }
        return false;
    }

    /**
     * Metodo retornar de uma boolean contendo CPF de quaisquer Pessoa
     *
     * @param String cpfpessoa
     * @return boolena
     * @exception SQLException
     */
    public boolean buscarPorCPF(String cpfpessoa) {

        try {

            strBuffer = new StringBuffer().append("SELECT cpfpessoa\n"
                    + "                    FROM projeto.tbpessoa \n"
                    + "                    WHERE   cpfpessoa = ?;");

            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, cpfpessoa);
            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar Buscar o CPF da Pessoa", ex);
        }
        return false;
    }

    /**
     * Metodo retornar de uma String contendo CELULAR de quaisquer pessoa
     *
     * @param String celular
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> buscarPorCelular(String celularpessoa) {
        List<PessoaTransfer> pessoaLista = new ArrayList<PessoaTransfer>();
        PessoaTransfer pessoa = new PessoaTransfer();
        try {
            strBuffer = new StringBuffer().append("SELECT pessoa.celularpessoa\n"
                    + "                    FROM   tbpessoa pessoa\n"
                    + "                    WHERE  pessoa.celularpessoa = ?;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, celularpessoa);
            rs = pstm.executeQuery();

            while (rs.next()) {
                pessoa.setCelularpessoa(rs.getString("celularpessoa"));
                pessoaLista.add(pessoa);
            }

            return pessoaLista;

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar Buscar Celular da Pessoa", ex);
        }
        return null;
    }

    /**
     * Metodo responsavel por buscar um usuario no sistema
     *
     * @return boolean
     * @param String usuario
     */
    public boolean existeUsuario(String usuario) {
        try {
            strBuffer = new StringBuffer().append("SELECT  usuariopessoa\n"
                    + "  FROM projeto.tbpessoa\n"
                    + "  WHERE usuariopessoa =?;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, usuario);

            rs = pstm.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>ERROR AO TENTAR BUSCAR O USUARIO!!!", ex);
        }
        return false;
    }

    //metodo perguntar se senha conferi
    public boolean senhaConfere(PessoaTransfer pessoaT) {
        try {

            strBuffer = new StringBuffer().append("select idpessoa, \n"
                    + "	senhapessoa\n"
                    + " from 	projeto.tbpessoa\n"
                    + " where idpessoa 	= ?\n"
                    + " and senhapessoa	= ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, pessoaT.getId());
            pstm.setObject(2, new Util().hashCode(pessoaT.getSenhapessoa()).toHex());
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar verificar se senha Confere da Tabela Pessoa!!!", ex);
        }
        return false;
    }

    //metodo perguntar se IDEmail Existem
    public boolean IdEmailConfere(Short id, String email) {
        try {

            strBuffer = new StringBuffer().append("select idpessoa, \n"
                    + "	emailpessoa\n"
                    + " from 	projeto.tbpessoa\n"
                    + " where idpessoa 	= ?\n"
                    + " and emailpessoa	= ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, email);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar verificar IdEmailConfere!!!", ex);
        }
        return false;
    }

    //metodo perguntar se IDCpfConfere existem
    public boolean IdCpfConfere(Short id, String cpf) {
        try {

            strBuffer = new StringBuffer().append("select idpessoa, \n"
                    + "	cpfpessoa\n"
                    + " from 	projeto.tbpessoa\n"
                    + " where idpessoa 	= ?\n"
                    + " and cpfpessoa	= ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, cpf);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar verificar IdCpfConfere!!!", ex);
        }
        return false;
    }

    //metodo perguntar se IDCpfConfere existem
    public boolean IdUsuarioConfere(Short id, String usuario) {
        try {

            strBuffer = new StringBuffer().append("select idpessoa, \n"
                    + "	usuariopessoa\n"
                    + " from 	projeto.tbpessoa\n"
                    + " where idpessoa 	= ?\n"
                    + " and usuariopessoa	= ?;");

            //pstm = conexao.prepareStatement(tbPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, id);
            pstm.setObject(2, usuario);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar verificar IdUsuarioConfere!!!", ex);
        }
        return false;
    }

    public PessoaTransfer pessoaRetorneIdPessoa(int idpessoa) {
        List<PessoaTransfer> listaPessoaID = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "FROM projeto.tbpessoa"
                    + "WHERE idpessoa = ?;");
            pstm = conexao.prepareStatement(strBuffer.toString());
            pstm.setObject(1, idpessoa);
            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setId(rs.getShort("idpessoa"));
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoa.setSenhapessoa(rs.getString("senhapessoa"));
                pessoa.setNomepessoa(rs.getString("nomepessoa"));
                pessoa.setEmailpessoa(rs.getString("emailpessoa"));
                pessoa.setCpfpessoa(rs.getString("cpfpessoa"));
                pessoa.setSexopessoa(rs.getString("sexopessoa"));
                pessoa.setCelularpessoa(rs.getString("celularpessoa"));
                pessoa.setStatuspessoa(rs.getString("statuspessoa"));
                pessoa.setTipopessoa(rs.getString("tipopessoa"));
                pessoa.setObservacaopessoa(rs.getString("observacaopessoa"));
                pessoa.setCependereco(rs.getString("observacaopessoa"));
                pessoa.setComplementoendereco(rs.getString("complementoendereco"));
                pessoa.setLogradouroendereco(rs.getString("logradouroendereco"));
                pessoa.setCidadeendereco(rs.getString("cidadeendereco"));
                pessoa.setEstadoendereco(rs.getString("estadoendereco"));
                pessoa.setDatacadastropessoa(rs.getDate("datacadastropessoa"));
                pessoa.setDatanascimento(rs.getDate("datanascimentpessoa"));
                listaPessoaID.add(pessoa);
            }
        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error sobre o ID da Pessoa", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoaID.size() > 0 ? listaPessoaID.get(0) : null;
    }

    public PessoaTransfer pessoaRetorneIdPessoaUsaurio(Object usuario) {
        List<PessoaTransfer> listaPessoaID = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT idpessoa\n"
                    + "FROM projeto.tbpessoa\n"
                    + "WHERE usuariopessoa =?;");
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, usuario);
            rs = pstm.executeQuery();

            if (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setId(rs.getShort("idpessoa"));
                listaPessoaID.add(pessoa);
            }
        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class
            ).error(">>>>Error sobre o ID da Pessoa", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoaID.size() > 0 ? listaPessoaID.get(0) : null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa, com
     * a prioridade de trazer o usuario, Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<PessoaTransfer> ListarUsuarioEvento(String nomeEvento) {
        List<PessoaTransfer> listEventoGaleria = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                 /*dados da tabela evento*/\n"
                    + "                 (pessoa.usuariopessoa),\n"
                    + "                 (pessoa.nomepessoa)\n"
                    + "                 from projeto.tbpessoa pessoa \n"
                    + "                 inner join \n"
                    + "                 projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                 inner join\n"
                    + "                 projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                 where (evento.nomeevento=?)"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, nomeEvento);

            rs = pstm.executeQuery();

            while (rs.next()) {

                PessoaTransfer pessoaTransfer = new PessoaTransfer();
                pessoaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoaTransfer.setNomepessoa(rs.getString("nomepessoa"));

                listEventoGaleria.add(pessoaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar ListarUsuarioEvento!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Metodo com a caracteristica de retornar uma lista de Dados de Pessoa, com
     * a prioridade de trazer o usuario, Evento e Galeria
     *
     * @return List<GaleriaTransfer>
     */
    public List<PessoaTransfer> ListarUsuarioEvento(short idpessoa) {
        List<PessoaTransfer> listEventoGaleria = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("select\n"
                    + "                 /*dados da tabela evento*/\n"
                    + "                 (pessoa.usuariopessoa),\n"
                    + "                 (pessoa.nomepessoa)\n"
                    + "                 from projeto.tbpessoa pessoa \n"
                    + "                 inner join \n"
                    + "                 projeto.tbpessoaevento pessoaevento on (pessoa.idpessoa = pessoaevento.idpessoa)\n"
                    + "                 inner join\n"
                    + "                 projeto.tbevento evento on (evento.idevento = pessoaevento.idevento)\n"
                    + "                 where (pessoa.idpessoa=?)"
            );
            pstm = conexao.prepareStatement(strBuffer.toString());

            pstm.setObject(1, idpessoa);

            rs = pstm.executeQuery();

            while (rs.next()) {

                PessoaTransfer pessoaTransfer = new PessoaTransfer();
                pessoaTransfer.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoaTransfer.setNomepessoa(rs.getString("nomepessoa"));

                listEventoGaleria.add(pessoaTransfer);
            }
            return listEventoGaleria;

        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error ao tentar ListarUsuarioEvento!!!", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    
    
    /**
     * Metodo retornar de uma lista contendo usuariopessoa e nomepessoa
     *
     * @return List<PessoaT>
     * @exception SQLException
     */
    public List<PessoaTransfer> pessoaEventoGaleria() {
        List<PessoaTransfer> listaPessoaID = new ArrayList<PessoaTransfer>();
        try {
            strBuffer = new StringBuffer().append("SELECT *\n"
                    + "                                           FROM projeto.tbpessoa\n"
                    + "                                           WHERE (tipopessoa ='USUARIO'\n"
                    + "                                           OR  tipopessoa ='CLIENTE' )\n"
                    + "                                           AND (statuspessoa='A')\n"
                    + "                                           \n"
                    + "                                           ORDER BY nomepessoa;");
            pstm = conexao.prepareStatement(strBuffer.toString());

            rs = pstm.executeQuery();

            while (rs.next()) {
                PessoaTransfer pessoa = new PessoaTransfer();
                pessoa.setUsuariopessoa(rs.getString("usuariopessoa"));
                pessoa.setNomepessoa(rs.getString("nomepessoa"));
                listaPessoaID.add(pessoa);
            }
        } catch (SQLException ex) {
            logPrincipal(PessoaDAO.class).error(">>>>Error sobre o pessoaEventoGaleria", ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPessoaID;
    }

}
