package br.com.piovezandev.frames;

import br.com.piovezandev.services.AberturaVenda;
import br.com.piovezandev.services.ControleVenda;
import br.com.piovezandev.services.ModeloTabela;
import br.com.piovezandev.services.ModeloVenda;
import br.com.piovezandev.utils.ConexaoJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author alan Piovezan
 */
public class Venda extends javax.swing.JDialog {

    private final static String TIPO_SISTEMA = "venda";
    String nomeCli, aux;
    ControleVenda controle = new ControleVenda();
    ModeloVenda modelovenda = new ModeloVenda();
    AberturaVenda abrir = new AberturaVenda();
    ConexaoJDBC con = new ConexaoJDBC();
    int flag, codVenda, quantidade, id_produto, quantidade_prod, id;
    double valorTotal = 0;
    float precoProduto, total, valor_venda;
    String paciente, nomeProduto, nomeCliente, nomeProd;

    public Venda() {

        initComponents();
        setSize(700, 735);
        setResizable(false);
        setLocationRelativeTo(null);

        //criando o modelo da data
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        //pegando a data atual do sistema
        Date dataAtual = new Date();
        //setando no Jformatted Data
        jFormattedData.setText(formatoData.format(dataAtual));

    }
//Metodo utilizado para preencher a primeira tabela de itens

    public void preencherTabela(String sql) {
        ArrayList dados = new ArrayList();

        String[] colunas = new String[]{"Codigo", "Nome"};
        con.conectar();
        con.executaSQL(sql);

        try {
            if (con.res.next()) {
                con.res.first();
                do {
                    dados.add(new Object[]{con.res.getString("codigo"), con.res.getString("nome")});
                } while (con.res.next());
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "erro ao prencher arraylist" + ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        TabelaPesquisa.setModel(modelo);
        TabelaPesquisa.getColumnModel().getColumn(0).setPreferredWidth(159);
        TabelaPesquisa.getColumnModel().getColumn(0).setResizable(false);
        TabelaPesquisa.getColumnModel().getColumn(1).setPreferredWidth(500);
        TabelaPesquisa.getColumnModel().getColumn(1).setResizable(false);
        TabelaPesquisa.getTableHeader().setReorderingAllowed(false);
        TabelaPesquisa.setAutoResizeMode(TabelaPesquisa.AUTO_RESIZE_OFF);
        TabelaPesquisa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }
//Metodo utilizado para preencher a segunda tabela de venda

    public void preencherTabelaVenda(String sql) {
        ArrayList dados = new ArrayList();

        String[] colunas = new String[]{"Descrição", "Quantidade", "Valor Total"};
        con.conectar();
        con.executaSQL(sql);

        try {
            if (con.res.next()) {
                con.res.first();
                do {
                    double valorProduto = con.res.getDouble("preco_produto");
                    quantidade = con.res.getInt("quantidade_prod");
                    quantidade = +1;
                    dados.add(new Object[]{con.res.getString("nome"), quantidade, valorProduto});
                } while (con.res.next());
                con.Desconecta();

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "erro ao prencher arraylist" + ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        TabelaVenda.setModel(modelo);
        TabelaVenda.getColumnModel().getColumn(0).setPreferredWidth(200);
        TabelaVenda.getColumnModel().getColumn(0).setResizable(false);
        TabelaVenda.getColumnModel().getColumn(1).setPreferredWidth(370);
        TabelaVenda.getColumnModel().getColumn(1).setResizable(false);
        TabelaVenda.getTableHeader().setReorderingAllowed(false);
        TabelaVenda.setAutoResizeMode(TabelaPesquisa.AUTO_RESIZE_OFF);
        TabelaVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //  Somando();

    }

    public void achaValorVenda(int IdVenda) {
        con.conectar();
        try {
            con.executaSQL("select * from venda where id_venda =" + IdVenda);
            con.res.first();
            valor_venda = con.res.getInt("valor_venda");

        } catch (Exception e) {
        }
    }

    public void achaDadosItemVenda(int IdVenda) {
        con.conectar();
        try {
            con.executaSQL("select * from itens_venda_produto where id_venda =" + IdVenda);
            con.res.first();
            id_produto = con.res.getInt("id_produto");
            quantidade_prod = con.res.getInt("quantidade_prod");
            paciente = con.res.getString("paciente");
            nomeProduto = con.res.getString("produto");
        } catch (Exception e) {
        }
    }

    public void remove() {

        int id_venda = modelovenda.getId_venda();

        //  nomeProduto = alan;
        String sql = "DELETE  FROM itens_venda_produto WHERE"
                + " id_venda =? AND produto = ?  AND paciente=? limit 1 ";
        String TESTE = "" + TabelaVenda.getValueAt(TabelaVenda.getSelectedRow(), 0);

        achaDadosItemVenda(id_venda);
        JOptionPane.showMessageDialog(null, "PRODUTO:" + TESTE);
        con.conectar();
        String valor = "" + TabelaVenda.getValueAt(TabelaVenda.getSelectedRow(), 2);

        total = total - Float.parseFloat(valor);

        try {

            PreparedStatement pst = con.conn.prepareStatement(sql);
            pst.setInt(1, id_venda);
            pst.setString(2, TESTE);
            pst.setString(3, paciente);

            pst.execute();
            JOptionPane.showMessageDialog(null, "Excluido");

            preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                    + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + codVenda + " ");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no preparedStatment:" + ex);
        }
        con.Desconecta();

        DecimalFormat df = new DecimalFormat("#,###.00");
        jtTotal.setText(String.valueOf(df.format(total)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtProduto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtValorItem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaPesquisa = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btFinalizar = new javax.swing.JToggleButton();
        btCancelar = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jtTotal = new javax.swing.JTextField();
        jFormattedData = new javax.swing.JFormattedTextField();
        btBuscarNome = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        BtAdicionar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTPaciente = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaVenda = new javax.swing.JTable();
        jTCodigo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jlabelComissionado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Formulario de Venda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(jLabel9)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Nome:");

        jtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeActionPerformed(evt);
            }
        });
        jtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtNomeKeyReleased(evt);
            }
        });

        jLabel3.setText("Data:");

        jLabel4.setText("Produto:");

        jLabel1.setText("Valor por Item:");

        TabelaPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TabelaPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TabelaPesquisaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TabelaPesquisaFocusLost(evt);
            }
        });
        TabelaPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaPesquisaMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TabelaPesquisaMouseReleased(evt);
            }
        });
        TabelaPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TabelaPesquisaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaPesquisa);

        jLabel6.setText("Tabela de Pesquisa:");

        jLabel7.setText("Itens da venda:");

        btFinalizar.setText("Finaliza a Venda");
        btFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFinalizarActionPerformed(evt);
            }
        });

        btCancelar.setText("Cancelar Venda Atual");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        jLabel8.setText("Total da Venda");

        jtTotal.setEditable(false);
        jtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTotalActionPerformed(evt);
            }
        });

        jFormattedData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedDataFocusGained(evt);
            }
        });
        jFormattedData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedDataActionPerformed(evt);
            }
        });

        btBuscarNome.setText("Buscar");
        btBuscarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarNomeActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        BtAdicionar.setText("Adicionar");
        BtAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAdicionarActionPerformed(evt);
            }
        });

        jLabel5.setText("Paciente:");

        jButton2.setText("Excluir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        TabelaVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descricao", "Quantidade", "Total"
            }
        ));
        TabelaVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaVendaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TabelaVenda);

        jTCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCodigoActionPerformed(evt);
            }
        });

        jLabel10.setText("Nome Produto:");

        jlabelComissionado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlabelComissionado.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(330, 330, 330)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btBuscarNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel3)
                        .addGap(113, 113, 113)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jFormattedData, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jTPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(BtAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btFinalizar)
                        .addGap(18, 18, 18)
                        .addComponent(btCancelar)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jlabelComissionado, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btBuscarNome)
                        .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1))
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtAdicionar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabelComissionado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btCancelar)
                            .addComponent(btFinalizar)
                            .addComponent(jButton2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void preencherPaciente() {
        con.conectar();
        try {
            PreparedStatement pst = con.conn.prepareStatement("update itens_venda_produto  inner join produto set itens_venda_produto.paciente = ?"
                    + "where itens_venda_produto.id_produto = produto.codigo ;");
            pst.setString(1, jTPaciente.getText());
            pst.execute();
            con.Desconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no preparedStatment:" + ex);
        }

    }

    private void btFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFinalizarActionPerformed
        modelovenda.setId_cliente(abrir.getCodVenda());

        modelovenda.setNome_cli(jtNome.getText());
        modelovenda.setData(jFormattedData.getText());

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        //pegando a data atual do sistema
        Date dataAtual = new Date();
        //setando no Jformatted Data

        modelovenda.setDataComanda(formatoData.format(dataAtual));

        //como o banco nao aceita ","
        //FORMATANDO O VALOR PARA QUE RECEBA "." AO INVES DE ","
        /* DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(); 
  decimalFormatSymbols.setDecimalSeparator('.');
  DecimalFormat df = new DecimalFormat("#,###.00",decimalFormatSymbols);
  String Total = df.format(total);*/
        modelovenda.setValor_venda(total);
        modelovenda.setStatus_venda("fechada");
        controle.fechaVenda(modelovenda, TIPO_SISTEMA);
        JOptionPane.showMessageDialog(null, "Numero da Venda:" + modelovenda.getId_venda());
        dispose();
    }//GEN-LAST:event_btFinalizarActionPerformed

    public void Somando() {
        total = total + Float.parseFloat(jtValorItem.getText());
        DecimalFormat df = new DecimalFormat("#,###.00");
        jtTotal.setText(String.valueOf(df.format(total)));
    }

    public void subtraindo() {
        String valor = "" + TabelaVenda.getValueAt(TabelaVenda.getSelectedRow(), 2);
        JOptionPane.showMessageDialog(null, valor);
    }


    private void TabelaPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaPesquisaMouseClicked
        if (flag == 1) {
            nomeCli = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);
            jtNome.setText(nomeCli);

        }

        if (flag == 2) {

            con.conectar();
            try {
                id = 3;
                nomeProd = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);

                con.executaSQL("select * from produto where nome ='" + nomeProd + "'");
                con.res.first();
                jtProduto.setText(nomeProd);
                precoProduto = con.res.getFloat("preco_produto");
                jtValorItem.setText(String.valueOf(precoProduto));
                if (con.res.getString("comissionado").trim().equals("S")) {
                    jlabelComissionado.setText("Item comissionado");
                } else {
                    jlabelComissionado.setText("");
                }
                con.Desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir preço" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_TabelaPesquisaMouseClicked

    private void jFormattedDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedDataActionPerformed
    }//GEN-LAST:event_jFormattedDataActionPerformed

    private void jFormattedDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedDataFocusGained
        double valortotalAux;
        valortotalAux = Double.parseDouble(jtValorItem.getText());
        jtTotal.setText(String.valueOf(valortotalAux));

    }//GEN-LAST:event_jFormattedDataFocusGained

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        controle.cancelarVenda(modelovenda.getId_venda());
        dispose();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void jtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNomeActionPerformed

    }//GEN-LAST:event_jtNomeActionPerformed

    public ResultSet pesquisarClientes(String texto) throws SQLException {
        String sql = "Select * from cliente where nome like ?";
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.conn.prepareStatement(sql);
            pst.setString(1, texto + "%");
            rs = pst.executeQuery();
        } catch (SQLDataException error) {
            JOptionPane.showMessageDialog(null, error);
        }
        return rs;
    }


    private void btBuscarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarNomeActionPerformed
        flag = 1;
        preencherTabela("select * from cliente where nome like '%" + jtNome.getText() + "%' and ativo = 'S' order by nome");
        abrir.AbrirVenda();

    }//GEN-LAST:event_btBuscarNomeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        flag = 2;
        preencherTabela("select * from produto where nome like '%" + jtProduto.getText() + "%'  and ativo = 'S' order by nome");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAdicionarActionPerformed
        if (jtNome.getText().equals(nomeCli)) {

            if (jtProduto.getText().equals(nomeProd)) {
                // JOptionPane.showMessageDialog(rootPane, "IF CERTO :");  
                codVenda = abrir.getCodVenda();

                modelovenda.setNome_produto(jtProduto.getText());

                modelovenda.setQtItem(quantidade);
                modelovenda.setId_venda(codVenda);
                modelovenda.setValor_produto(Double.parseDouble(jtValorItem.getText()));
                
                controle.adicionaItem(modelovenda, jTPaciente);
                Somando();
                preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                        + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + codVenda);
                //preencherPaciente();
            } else {
                JOptionPane.showMessageDialog(rootPane, "FAVOR CLICAR NO PRODUTO ANTES DE ADICIONAR !");

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "FAVOR CLICAR NO DENTISTA ANTES DE ADICIONAR O PRODUTO !");
            jtProduto.setText("");
            jtValorItem.setText("");
        }

    }//GEN-LAST:event_BtAdicionarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        remove();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void TabelaVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaVendaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabelaVendaMouseClicked

    private void jtNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtNomeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtNomeKeyReleased

    private void jTCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCodigoActionPerformed

        con.conectar();
        if (jtNome.getText().equals(nomeCli)) {
            con.executaSQL("select * from produto where codigo like '" + jTCodigo.getText() + "%';");

            codVenda = abrir.getCodVenda();

            try {
                if (con.res.first()) {
                    jtProduto.setText(con.res.getString("nome"));
                    jtValorItem.setText(con.res.getString("preco_produto"));

                    modelovenda.setNome_produto(jtProduto.getText());

                    modelovenda.setQtItem(quantidade);
                    modelovenda.setId_venda(codVenda);

                    controle.adicionaItem(modelovenda, jTPaciente);

                    Somando();

                    preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                            + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + codVenda);
                } else {
                    JOptionPane.showMessageDialog(null, "Produto não cadastrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro if");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "FAVOR CLICAR NO DENTISTA ANTES DE ADICIONAR O PRODUTO !");
        }

        //preencherPaciente();
        //limpar campos
        jTCodigo.setText("");


    }//GEN-LAST:event_jTCodigoActionPerformed

    private void jtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtTotalActionPerformed


    private void TabelaPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabelaPesquisaFocusGained

    }//GEN-LAST:event_TabelaPesquisaFocusGained

    private void TabelaPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelaPesquisaKeyReleased
        if (flag == 1) {
            nomeCli = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);
            jtNome.setText(nomeCli);

        }
        if (flag == 2) {
            con.conectar();
            try {
                id = 3;
                nomeProd = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);

                con.executaSQL("select * from produto where nome ='" + nomeProd + "'");
                con.res.first();
                jtProduto.setText(nomeProd);
                precoProduto = con.res.getFloat("preco_produto");
                jtValorItem.setText(String.valueOf(precoProduto));
                if (con.res.getString("comissionado").trim().equals("S")) {
                    jlabelComissionado.setText("Item comissionado");
                } else {
                    jlabelComissionado.setText("");
                }
                con.Desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir preço" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_TabelaPesquisaKeyReleased

    private void TabelaPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabelaPesquisaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_TabelaPesquisaFocusLost

    private void TabelaPesquisaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaPesquisaMouseReleased
        if (flag == 1) {
            nomeCli = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);
            jtNome.setText(nomeCli);

        }
        if (flag == 2) {
            con.conectar();
            try {
                id = 3;
                nomeProd = "" + TabelaPesquisa.getValueAt(TabelaPesquisa.getSelectedRow(), 1);

                con.executaSQL("select * from produto where nome ='" + nomeProd + "'");
                con.res.first();
                jtProduto.setText(nomeProd);
                precoProduto = con.res.getFloat("preco_produto");
                jtValorItem.setText(String.valueOf(precoProduto));
                if (con.res.getString("comissionado").trim().equals("S")) {
                    jlabelComissionado.setText("Item comissionado");
                } else {
                    jlabelComissionado.setText("");
                }
                con.Desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir preço" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_TabelaPesquisaMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAdicionar;
    private javax.swing.JTable TabelaPesquisa;
    private javax.swing.JTable TabelaVenda;
    private javax.swing.JButton btBuscarNome;
    private javax.swing.JToggleButton btCancelar;
    private javax.swing.JToggleButton btFinalizar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFormattedData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTCodigo;
    private javax.swing.JTextField jTPaciente;
    private javax.swing.JLabel jlabelComissionado;
    private javax.swing.JTextField jtNome;
    private javax.swing.JTextField jtProduto;
    private javax.swing.JTextField jtTotal;
    private javax.swing.JTextField jtValorItem;
    // End of variables declaration//GEN-END:variables
}
