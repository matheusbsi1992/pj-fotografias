package br.com.fotografias.dao;




import br.com.fotografias.conexaofactory.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

/**
 *
 * Classe de responsavel por conexao e log
 *
 * @author linuxserver
 *
 */
public class DAO {

    protected Connection conexao = Conexao.getConexao();
    protected ResultSet rs;
    protected PreparedStatement pstm;
    protected StringBuffer strBuffer;

    public Logger logPrincipal(Class classe) {
        if (classe != null) {
            Logger LOG = Logger.getLogger(classe.getName());
            return LOG;
        }
        return null;
    }

}
