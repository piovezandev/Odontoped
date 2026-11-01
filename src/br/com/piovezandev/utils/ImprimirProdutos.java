/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author alan
 */
public class ImprimirProdutos {

    ConexaoJDBC con = new ConexaoJDBC();
    BufferedWriter bw = null;

    public void imprimirLogo(int idVenda) {
        FileWriter writer;
        con.conectar();
        String dadosEmpresa = "    \t\t\t\t Laboratrio OrtoQueen"
                + "\nFONE: (11) 4523-1095"
                + "\natendimento@laboratorioortoqueen.com.br\n\n";
        try {
            con.executaSQL("select * from venda where id_venda = " + idVenda);
            writer = new FileWriter("C:/Odontoped/Vendas/venda Nº" + idVenda + ".txt", true); // com o parâmetro true você informa que não deve sobrescrever e sim mesclar.
            PrintWriter output = new PrintWriter(writer);

            output.write(dadosEmpresa);
            con.res.first();
            output.write(con.res.getString("data_comanda") + "\n\n");
            output.write(con.res.getString("nome_cli") + "\n\n");
            output.flush();
            output.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        }

    }

    public void imprimirPacientes(int idVenda) {
        FileWriter writer;
        con.conectar();
        String sql = "select distinct paciente,valor_venda, venda.codigo_os from venda inner join itens_venda_produto on "
                + "venda.id_venda=itens_venda_produto.id_venda where venda.id_venda = " + idVenda;
        try {
            con.executaSQL(sql);
            writer = new FileWriter("C:/Odontoped/Vendas/venda Nº" + idVenda + ".txt", true); // com o parâmetro true você informa que não deve sobrescrever e sim mesclar.
            PrintWriter output = new PrintWriter(writer);
            //aqui faz a correção do ponto decimal ex(10.0 fica 10,00)
            DecimalFormat df = new DecimalFormat("#,###.00");

            output.write("\nPaciente: \n");

            while (con.res.next()) {
                con.res.getString("paciente");

                if (con.res.getString("codigo_os").equals("0") && con.res.getString("paciente").length() == 0) {
                    output.write("sem nome \n");
                } else if (con.res.getString("codigo_os").equals("0") && con.res.getString("paciente").length() > 0) {
                    output.write(con.res.getString("paciente") + "\n");
                } else if (!con.res.getString("codigo_os").equals("0") && con.res.getString("paciente").length() == 0) {
                    output.write(con.res.getString("codigo_os") + " - " + "sem nome \n");
                } else {
                    output.write(con.res.getString("codigo_os") + " - " + con.res.getString("paciente") + "\n");
                }
            }

            con.res.last();
            float total = con.res.getFloat("valor_venda");
            output.write("\n");
            output.write("Total: " + df.format(total));
            //JOptionPane.showMessageDialog(null, "total 1: "+total);
            //JOptionPane.showMessageDialog(null, "total 2: " +df.format(total));

            output.write("\n\n\n");
            output.write("\n\n");
            output.flush();
            output.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        }

    }

    public void format(int idVenda) {
        FileWriter writer;
        con.conectar();

        String sql = "select * from venda inner join itens_venda_produto on"
                + " venda.id_venda=itens_venda_produto.id_venda inner join"
                + " produto on itens_venda_produto.id_produto = produto.codigo where venda.id_venda=" + idVenda;

        try {
            con.executaSQL(sql);
            writer = new FileWriter("C:/Odontoped/Vendas/venda Nº" + idVenda + ".txt", true); // com o parâmetro true você informa que não deve sobrescrever e sim mesclar.
            PrintWriter out = new PrintWriter(writer);
            //aqui faz a correção do ponto decimal ex(10.0 fica 10,00)
            DecimalFormat df = new DecimalFormat("#,###.00");
            //delimita o formato para impressao ficar alinhada
            String formato = "%1$-25s %2$-20s\n";

            out.write("Produto" + "              Valor Unit.\n");

            while (con.res.next()) {

                String nomeProd = con.res.getString("produto.nome");
                Float precoProd = con.res.getFloat("produto.preco_produto");

                if (nomeProd.length() >= 24) {

                    out.format(formato, nomeProd.substring(0, 24) + ".", df.format(precoProd));
                } else {
                    out.format(formato, nomeProd, df.format(precoProd));
                }
            }
            out.flush();
            out.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        }
    }
}
