package br.com.piovezandev.utils;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author alan
 */
public class ConexaoJDBC {

    public Statement smt;
    public ResultSet res;
    private String driver = "com.mysql.jdbc.Driver";
    private String caminho = "jdbc:mysql://127.0.0.1/odontoped";
    private String usuario = "root";
    private String senha = "root";
    public Connection conn;
    PreparedStatement aux;

    public void conectar() {

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(caminho, usuario, senha);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro Driver Mysql");

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "erro metodo conecta ");

        }

    }

    public void Desconecta() {
        try {
            conn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro metodo desconecta ");
        }

    }

    public void executaSQL(String sql) {
        try {
            smt = conn.createStatement();
            res = smt.executeQuery(sql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro metodo ExecutaSQL");
        }

    }

};
