/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.services;

import br.com.piovezandev.utils.ConexaoJDBC;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author alan
 */
public class AberturaVenda {

    private int codVenda;
    ConexaoJDBC con = new ConexaoJDBC();

    public void AbrirVenda() {
        con.conectar();
        try {
            PreparedStatement pst = con.conn.prepareStatement("insert into venda(valor_venda) values (?)");
            pst.setDouble(1, 0);
            pst.execute();
            con.executaSQL("select * from venda");
            con.res.last();
            codVenda = con.res.getInt("id_venda");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir a venda" + ex.getMessage());
        }

    }

    /**
     * @return the codVenda
     */
    public int getCodVenda() {
        return codVenda;
    }

    /**
     * @param codVenda the codVenda to set
     */
    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }
}
