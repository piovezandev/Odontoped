package br.com.piovezandev.relatorios;

import br.com.piovezandev.utils.ConexaoJDBC;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author alan
 */
public class Relatorios {

    ConexaoJDBC con = new ConexaoJDBC();

    public void RelatorioCliente() {
        try {
            con.conectar();
            con.executaSQL("select * from cliente");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(con.res);
            JasperPrint jprint = JasperFillManager.fillReport("c:/Odontoped/relatorios/Relatorio de Cliente.jasper", new HashMap(), relatResul);
            JasperViewer jview = new JasperViewer(jprint, false);
            jview.setVisible(true);
            jview.toFront();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "erro " + ex.getMessage());
        }
        con.Desconecta();

    }

    public void RelatorioProduto() {
        try {
            con.conectar();
            con.executaSQL("select * from produto");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(con.res);
            JasperPrint jprint = JasperFillManager.fillReport("c:/Odontoped/relatorios/Relatorio de produtos.jasper", new HashMap(), relatResul);
            JasperViewer jview = new JasperViewer(jprint, false);
            jview.setVisible(true);
            jview.toFront();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "erro " + ex.getMessage());
        }
        con.Desconecta();

    }

    public void RelatorioDescritivo() {
        try {
            con.conectar();
            con.executaSQL("select * from venda inner join itens_venda_produto on venda.id_venda=itens_venda_produto.id_venda inner join produto on itens_venda_produto.id_produto=produto.codigo;");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(con.res);
            JasperPrint jprint = JasperFillManager.fillReport("c:/Odontoped/relatorios/Relatorio Descritivo.jasper", new HashMap(), relatResul);
            JasperViewer jview = new JasperViewer(jprint, false);
            jview.setVisible(true);
            jview.toFront();
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "erro " + ex.getMessage());
        }
        con.Desconecta();

    }
}
