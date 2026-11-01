/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan
 */
public class ImprimirItensComanda {

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

            con.res.first();
            output.write("Ordem de Servico: " + con.res.getString("codigo_os") + "  " + con.res.getString("data_venda") + "\n\n");
            output.write(con.res.getString("nome_cli"));
            output.flush();
            output.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex.getMessage());
        }

    }

    public void imprimirItens(int idVenda) {
        FileWriter writer;
        con.conectar();

        String sql = "select * from venda inner join itens_venda_produto on"
                + " venda.id_venda=itens_venda_produto.id_venda inner join"
                + " produto on itens_venda_produto.id_produto = produto.codigo where venda.id_venda=" + idVenda;

        try {
            con.executaSQL(sql);
            writer = new FileWriter("C:/Odontoped/Vendas/venda Nº" + idVenda + ".txt", true); // com o parâmetro true você informa que não deve sobrescrever e sim mesclar.
            PrintWriter out = new PrintWriter(writer);

            out.write("\n\nItens: \n");
            while (con.res.next()) {

                out.write("Colaborador: " + con.res.getString("funcionario") + "\n\n");
                out.write("*** " + con.res.getString("produto") + " ***" + "\n");

                out.write(con.res.getString("cor").length() > 0 ? "\nCor: " + con.res.getString("cor") : "");
                out.write(con.res.getString("adesivo").length() > 0 ? "\nAdesivo: " + con.res.getString("adesivo") : "");
                out.write(con.res.getString("nome_aparelho").equals("S") ? "\nNome no aparelho: Sim " : "\nNome no aparelho: Nao");
                out.write(con.res.getString("glitter_aparelho").equals("S") ? "\nGlitter no aparelho: Sim " : "\nGlitter no aparelho: Nao");
                out.write(con.res.getString("descricao").length() > 0 ? "\nDescricao: \n" + con.res.getString("descricao") : "");
                out.write("\n\nDentista:" + con.res.getString("nome_cli"));
                out.write(con.res.getString("paciente").length() > 0 ? "\nPaciente:" + con.res.getString("paciente") : "\n Paciente:sem nome");

                out.write("\n\n\n");

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
