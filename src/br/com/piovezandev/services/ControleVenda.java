package br.com.piovezandev.services;

import br.com.piovezandev.utils.ConexaoJDBC;
import br.com.piovezandev.utils.ImprimirItensComanda;
import br.com.piovezandev.utils.ImprimirProdutos;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.print.PrintService;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author alan
 */
public class ControleVenda {

    String telefone;
    String data, nomeCli;
    String nomeProduto;
    String endereco;
    PrintService impressora;
    int codProduto, codCliente;
    int totalVenda, codigoOs;
    String aux;
    ConexaoJDBC con = new ConexaoJDBC();
    ModeloVenda teste = new ModeloVenda();
    ImprimirProdutos csv = new ImprimirProdutos();
    ImprimirItensComanda imprimirItensComanda = new ImprimirItensComanda();
    ImprimirTXT imp = new ImprimirTXT();
    private int id = 0;

    public void adicionaItem(ModeloVenda modelo, JTextField paciente) {

        achaCodProduto(modelo.getNome_produto());

        con.conectar();
        try {
            PreparedStatement pst = con.conn.prepareStatement("insert into itens_venda_produto(id_venda,id_produto,quantidade_prod,paciente,produto,codigo_os,cor,"
                    + "adesivo,descricao,nome_aparelho,glitter_aparelho,data_entrega,status_comanda_item,funcionario,id_itens_venda,valor_produto) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            pst.setInt(1, modelo.getId_venda());
            pst.setInt(2, codProduto);
            pst.setInt(3, modelo.getQtItem());
            pst.setString(4, paciente.getText());
            pst.setString(5, nomeProduto);
            pst.setInt(6, modelo.getCodigoOs());
            pst.setString(7, modelo.getCor());
            pst.setString(8, modelo.getAdesivo());
            pst.setString(9, modelo.getObservacoes());
            pst.setString(10, modelo.getNome_aparelho());
            pst.setString(11, modelo.getGliter());
            pst.setString(12, modelo.getDataEntrega());
            pst.setString(13, modelo.getStatusComandaItem());
            pst.setString(14, modelo.getFuncionario());
            pst.setInt(15, modelo.getIdItemVenda());
            pst.setDouble(16, modelo.getValor_produto());
            pst.execute();
            con.Desconecta();
        } catch (SQLException ex) {
            con.Desconecta();
            JOptionPane.showMessageDialog(null, "erro no metodo adicionaItem " + ex);
        }

    }

    public void achaCodProduto(String nome) {

        con.conectar();
        con.executaSQL("select * from produto where nome= '" + nome + "'");
        try {
            con.res.first();
            codProduto = con.res.getInt("codigo");
            do {
                nomeProduto = con.res.getString("nome");
            } while (con.res.next());

            con.Desconecta();

        } catch (SQLException ex) {
            con.Desconecta();

            JOptionPane.showMessageDialog(null, "erro no metodo achacodProduto" + ex);

        }

    }

    public void fechaVenda(ModeloVenda modelo, String tipoSistema) {
        achaCliente(modelo.getNome_cli());
        aux = modelo.getNome_cli();

        con.conectar();

        try {
            PreparedStatement pst = con.conn.prepareStatement("update venda set data_venda=?,valor_venda=?,id_cliente=?,nome_cli=?,status_venda=?,codigo_os=?, data_comanda=? where id_venda=?");
            pst.setString(1, modelo.getData());
            pst.setDouble(2, modelo.getValor_venda());
            pst.setInt(3, codCliente);
            pst.setString(4, nomeCli);
            pst.setString(5, modelo.getStatus_venda());
            pst.setInt(6, modelo.getCodigoOs());
            pst.setString(7, modelo.getDataComanda());
            pst.setInt(8, modelo.getId_venda());

            pst.execute();
            data = modelo.getData();
            //tipo venda
            if (tipoSistema.equals("venda")) {
                //escreve no arquivo todos os dados do cupom
                csv.imprimirLogo(modelo.getId_venda());
                csv.format(modelo.getId_venda());
                csv.imprimirPacientes(modelo.getId_venda());

                imp.emiteCupom(modelo.getId_venda());

                //thread para imprimir a segunda via
                try {
                    Thread.sleep(4000);
                    imp.emiteCupom(modelo.getId_venda());

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Imprimir 2º via " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Venda Finalizada");
            } else {
                //tipocomanda
                JOptionPane.showMessageDialog(null, "Abertura de Ordem de Serviço realizada!");

                int escolha = JOptionPane.showConfirmDialog(null, "Deseja imprimir essa comanda?:\n ", "Impressão comanda", JOptionPane.YES_NO_OPTION);

                if (escolha == 0) {
                    imprimirItensComanda.imprimirLogo(modelo.getId_venda());
                    imprimirItensComanda.imprimirItens(modelo.getId_venda());
                    imp.emiteCupom(modelo.getId_venda());

                } else {
                    return;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro Metodo FechaVenda " + ex.getMessage());
        }
        con.Desconecta();
    }

    public void achaCliente(String nome) {
        con.conectar();
        try {
            con.executaSQL("select * from cliente where nome='" + nome + "'");
            con.res.first();
            codCliente = con.res.getInt("codigo");
            nomeCli = con.res.getString("nome");
            endereco = con.res.getString("endereco");
            telefone = con.res.getString("telefone");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro! Cliente Não Seleciionado");
        }
        con.Desconecta();
    }

    public void cancelarVenda(int idVenda) {
        con.conectar();

        try {
            PreparedStatement pst = con.conn.prepareStatement("delete from venda where id_venda = ? ");
            pst.setInt(1, idVenda);
            pst.execute();
            PreparedStatement sta = con.conn.prepareStatement("delete from itens_venda_produto where id_venda =?");
            sta.setInt(1, idVenda);
            sta.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cancelar a venda " + ex.getMessage());
        }
        con.Desconecta();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
