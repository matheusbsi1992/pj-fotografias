/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fotografias.conexaofactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Jandisson
 */
public final class Conexao {

    //cria variavel tipo DataSource
    private static HikariDataSource hkdatasource = new HikariDataSource();

    //cria variavel tipo LOGGER
    private static final Logger LOG = Logger.getLogger(Conexao.class.getName());
    
    static {
        HikariConfig hkconfig = new HikariConfig();
        
        hkconfig.setJdbcUrl("jdbc:postgresql://localhost:5432/projeto");
        hkconfig.setUsername("postgres");
        hkconfig.setPassword("root");
        hkconfig.setDriverClassName("org.postgresql.Driver");
        hkconfig.setMaxLifetime(5000);
        hkconfig.setMinimumIdle(20);
        hkconfig.setMaximumPoolSize(100);
        hkconfig.setLeakDetectionThreshold(5000);
        hkconfig.addDataSourceProperty("cachePrepStmts", "true");
        hkconfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hkconfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        hkdatasource = new HikariDataSource(hkconfig);
        
    }

    /**
     * Obtem uma conexao do datasource
     *
     * @return A conexao para acesso a base de dados
     * @throws SQLException Lancada caso nao seja possivel obter a conexao
     */
    public static Connection getConexao() {
        try {
            return hkdatasource.getConnection();
        } catch (SQLException ex) {
            LOG.error(">>>>Error de Conexao", ex);
        }
        return null;
    }

    /**
     * Fecha todas as conexoes abertas no pool
     *
     */
    public static void closeDataSource() {
        if (hkdatasource != null && hkdatasource instanceof HikariDataSource) {
            ((HikariDataSource) hkdatasource).close();
        }
        hkdatasource.close();
    }

    /**
     * Não commita os dados
     *
     * @param trueboolean
     * @throws java.sql.SQLException
     */
    public static void conectionCommitOrRoolback(boolean trueboolean) throws SQLException {
        if (trueboolean == false) {
            hkdatasource.setAutoCommit(false);
        }
    }
    
}
