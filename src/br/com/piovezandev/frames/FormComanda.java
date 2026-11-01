/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.frames;

import br.com.piovezandev.services.AberturaVenda;
import br.com.piovezandev.services.ControleVenda;
import br.com.piovezandev.services.ImprimirTXT;
import br.com.piovezandev.services.ModeloTabela;
import br.com.piovezandev.services.ModeloVenda;
import br.com.piovezandev.utils.ConexaoJDBC;
import br.com.piovezandev.utils.ImprimirProdutos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Alan
 */
public class FormComanda extends javax.swing.JDialog {

    private final static String TIPO_SISTEMA = "comanda";
    ConexaoJDBC con = new ConexaoJDBC();
    ControleVenda controle = new ControleVenda();
    ModeloVenda modeloVenda = new ModeloVenda();
    AberturaVenda abrir = new AberturaVenda();
    int codVenda, codigoProduto, idItensVenda;
    double precoProduto, total;
    String nomeProduto, statusComandaItem;
    private boolean isEdicao = false;
    ImprimirProdutos csv = new ImprimirProdutos();
    ImprimirTXT imp = new ImprimirTXT();

    public FormComanda() {
        abrir.AbrirVenda();

        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        formataData();

        gerarComboBoxCliente();
        gerarComboBoxProduto();
        gerarComboBoxFuncionario();
        perguntarAoFechar();
    }

    private void formataData() {
        //criando o modelo da data
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        //pegando a data atual do sistema
        Date dataAtual = new Date();
        //setando no Jformatted Data
        ftfData.setText(formatoData.format(dataAtual));

    }

    public void gerarComboBoxCliente() {

        con.conectar();
        try {
            con.executaSQL("select * from cliente order by nome");
            while (con.res.next()) {
                jcbxCliente.addItem(con.res.getString("nome"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlterarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gerarComboBoxProduto() {
        con.conectar();
        try {
            con.executaSQL("select * from produto order by nome");
            jcbxProduto.addItem("");
            while (con.res.next()) {
                jcbxProduto.addItem(con.res.getString("nome"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlterarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void gerarComboBoxFuncionario() {
        con.conectar();
        try {
            con.executaSQL("select * from funcionario order by nome");
            while (con.res.next()) {
                jcbxFuncionario.addItem(con.res.getString("nome"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlterarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void formataValor() {
        try {
            MaskFormatter form = new MaskFormatter("#,###.00");
            jftfValorUnitario.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "erro " + ex.getMessage());
        }
    }

    public void preencherTabelaVenda(String sql) {
        ArrayList dados = new ArrayList();

        String[] colunas = new String[]{"id", "Descrição", "Valor", "Total item", "Status do item"};
        con.conectar();
        con.executaSQL(sql);

        try {

            if (con.res.next()) {
                con.res.first();
                do {
                    dados.add(new Object[]{con.res.getInt("id_itens_venda"), con.res.getString("nome"), con.res.getDouble("preco_produto"),
                        con.res.getDouble("preco_produto"), con.res.getString("status_comanda_item")});
                } while (con.res.next());
                con.Desconecta();

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "erro ao prencher arraylist" + ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        tabelaItens.setModel(modelo);
        tabelaItens.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabelaItens.getColumnModel().getColumn(0).setResizable(false);
        tabelaItens.getColumnModel().getColumn(1).setPreferredWidth(360);
        tabelaItens.getColumnModel().getColumn(1).setResizable(false);
        tabelaItens.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaItens.getColumnModel().getColumn(2).setResizable(false);
        tabelaItens.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabelaItens.getColumnModel().getColumn(3).setResizable(false);
        tabelaItens.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaItens.getColumnModel().getColumn(4).setResizable(false);
        tabelaItens.getTableHeader().setReorderingAllowed(false);
        tabelaItens.setAutoResizeMode(tabelaItens.AUTO_RESIZE_OFF);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void Somando() {
        total = total + Float.parseFloat(jftfValorUnitario.getText());
        DecimalFormat df = new DecimalFormat("#,###.00");
        jtfTotal.setText(String.valueOf(df.format(total)));
    }

    public void subtraindo() {
        String valor = "" + tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 2);
        JOptionPane.showMessageDialog(null, valor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCodigoOS = new javax.swing.JTextField();
        jcbxCliente = new javax.swing.JComboBox<>();
        ftfData = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfPaciente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcbxFuncionario = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        ftfDataEntrega = new datechooser.beans.DateChooserCombo();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jcbxProduto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaObservacao = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        chkGlitterNoAparelho = new javax.swing.JCheckBox();
        chkNomeNoAparelho = new javax.swing.JCheckBox();
        txtCor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAdesivo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jftfValorUnitario = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnRemoverItem = new javax.swing.JButton();
        btnEditarItem = new javax.swing.JButton();
        btnSalvarComanda = new javax.swing.JButton();
        btnFecharComanda = new javax.swing.JButton();
        jtfTotal = new javax.swing.JFormattedTextField();
        btnCancelarComanda = new javax.swing.JButton();
        btnCancelarEdicaoItem = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ordem de Serviço");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(jLabel1)
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCodigoOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoOSActionPerformed(evt);
            }
        });
        txtCodigoOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoOSKeyPressed(evt);
            }
        });

        jcbxCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxClienteActionPerformed(evt);
            }
        });

        jLabel2.setText("Codigo OS:");

        jLabel3.setText("Cliente:");

        jLabel4.setText("Data Produção:");

        jLabel13.setText("Paciente:");

        jLabel6.setText("Data para entrega:");

        jLabel14.setText("Funcionario que ira executar o trabalho:");

        ftfDataEntrega.setCurrentView(new datechooser.view.appearance.AppearancesList("Contrast",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    ftfDataEntrega.setFormat(2);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jcbxCliente, 0, 478, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addComponent(jcbxFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel13)
                    .addGap(30, 30, 30)
                    .addComponent(jtfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(54, 54, 54)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(txtCodigoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(52, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(ftfDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap())))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jcbxFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ftfDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(13, 13, 13)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel13)
                .addComponent(jtfPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel3.setPreferredSize(new java.awt.Dimension(865, 61));

    jLabel5.setText("Serviço:");

    jcbxProduto.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
        public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
        }
        public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            jcbxProdutoPopupMenuWillBecomeInvisible(evt);
        }
        public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
        }
    });
    jcbxProduto.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jcbxProdutoActionPerformed(evt);
        }
    });

    jLabel7.setText("Valor:");

    btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/1.png"))); // NOI18N
    btnAdicionar.setToolTipText("Adicionar item ");
    btnAdicionar.setMaximumSize(new java.awt.Dimension(53, 29));
    btnAdicionar.setMinimumSize(new java.awt.Dimension(53, 29));
    btnAdicionar.setPreferredSize(new java.awt.Dimension(53, 29));
    btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnAdicionarActionPerformed(evt);
        }
    });

    jLabel8.setText("Adicionar:");

    txtAreaObservacao.setColumns(20);
    txtAreaObservacao.setRows(5);
    jScrollPane2.setViewportView(txtAreaObservacao);

    jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    chkGlitterNoAparelho.setText("Glitter");
    chkGlitterNoAparelho.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            chkGlitterNoAparelhoActionPerformed(evt);
        }
    });

    chkNomeNoAparelho.setText("Nome ");

    jLabel9.setText("Cor:");

    jLabel11.setText("Adesivo:");

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(chkNomeNoAparelho)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(jLabel9))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtAdesivo, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(txtCor))
                    .addGap(24, 24, 24))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(chkGlitterNoAparelho)
                    .addGap(0, 0, Short.MAX_VALUE))))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtAdesivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel11))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(chkNomeNoAparelho)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(chkGlitterNoAparelho)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jLabel12.setText("Observações:");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(41, 41, 41)
                    .addComponent(jLabel12)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addGap(54, 54, 54))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jcbxProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(jftfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(35, 35, 35)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGap(55, 55, 55))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jftfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jcbxProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jScrollPane2)
                    .addGap(9, 9, 9))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel12)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 17, Short.MAX_VALUE))))
    );

    tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Descrição", "Valor", "Total item", "Status do item"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    tabelaItens.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tabelaItensMouseClicked(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            tabelaItensMouseReleased(evt);
        }
    });
    jScrollPane1.setViewportView(tabelaItens);

    jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel10.setText("Total:");

    jPanel4.setBackground(new java.awt.Color(0, 102, 153));

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 32, Short.MAX_VALUE)
    );

    btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/6.png"))); // NOI18N
    btnRemoverItem.setToolTipText("Remover item");
    btnRemoverItem.setMaximumSize(new java.awt.Dimension(53, 29));
    btnRemoverItem.setMinimumSize(new java.awt.Dimension(53, 29));
    btnRemoverItem.setPreferredSize(new java.awt.Dimension(53, 29));
    btnRemoverItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnRemoverItemActionPerformed(evt);
        }
    });

    btnEditarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/3.png"))); // NOI18N
    btnEditarItem.setToolTipText("Editar item");
    btnEditarItem.setEnabled(false);
    btnEditarItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnEditarItemActionPerformed(evt);
        }
    });

    btnSalvarComanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/save.png"))); // NOI18N
    btnSalvarComanda.setToolTipText("Salvar comanda");
    btnSalvarComanda.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSalvarComandaActionPerformed(evt);
        }
    });

    btnFecharComanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/5.png"))); // NOI18N
    btnFecharComanda.setToolTipText("Fechar comanda");
    btnFecharComanda.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnFecharComandaActionPerformed(evt);
        }
    });

    jtfTotal.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jtfTotalActionPerformed(evt);
        }
    });

    btnCancelarComanda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/2.png"))); // NOI18N
    btnCancelarComanda.setToolTipText("Cancelar comanda");
    btnCancelarComanda.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelarComandaActionPerformed(evt);
        }
    });

    btnCancelarEdicaoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/cancel-icon.png"))); // NOI18N
    btnCancelarEdicaoItem.setToolTipText("Cancelar edição do item");
    btnCancelarEdicaoItem.setEnabled(false);
    btnCancelarEdicaoItem.setMaximumSize(new java.awt.Dimension(53, 29));
    btnCancelarEdicaoItem.setMinimumSize(new java.awt.Dimension(53, 29));
    btnCancelarEdicaoItem.setPreferredSize(new java.awt.Dimension(53, 29));
    btnCancelarEdicaoItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelarEdicaoItemActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnSalvarComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnEditarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnCancelarEdicaoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnFecharComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(24, 24, 24)
                    .addComponent(jLabel10)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(1, 1, 1)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnEditarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvarComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFecharComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelarComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelarEdicaoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(9, 9, 9))
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoOSActionPerformed

    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverItemActionPerformed
        remove();
// TODO add your handling code here:
    }//GEN-LAST:event_btnRemoverItemActionPerformed

    private void chkGlitterNoAparelhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGlitterNoAparelhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGlitterNoAparelhoActionPerformed

    private void txtCodigoOSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoOSKeyPressed
        txtCodigoOS.setText(txtCodigoOS.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtCodigoOSKeyPressed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        if (jftfValorUnitario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor selecionar o item para ser adicionado");
            return;
        }
        codVenda = abrir.getCodVenda();
        jcbxCliente.setEnabled(false);
        modeloVenda.setNome_produto(jcbxProduto.getSelectedItem().toString());

        modeloVenda.setQtItem(1);
        modeloVenda.setId_venda(codVenda);
        if (txtCodigoOS.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor inserir o codigo da ordem de serviço");
            txtCodigoOS.requestFocus();
            return;
        } else {
            modeloVenda.setCodigoOs(Integer.parseInt(txtCodigoOS.getText()));
        }

        modeloVenda.setStatus_venda("aberta");
        modeloVenda.setCor(txtCor.getText());
        modeloVenda.setAdesivo(txtAdesivo.getText());
        modeloVenda.setObservacoes(txtAreaObservacao.getText());

        modeloVenda.setStatusComandaItem("aberto");
        if (chkNomeNoAparelho.isSelected()) {
            modeloVenda.setNome_aparelho("S");
        } else {
            modeloVenda.setNome_aparelho("N");
        }
        if (chkGlitterNoAparelho.isSelected()) {
            modeloVenda.setGliter("S");
        } else {
            modeloVenda.setGliter("N");
        }
        modeloVenda.setFuncionario(jcbxFuncionario.getSelectedItem().toString());
        if (ftfDataEntrega.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor inserir a data de agendamento");
            ftfDataEntrega.requestFocus();
            return;
        } else {
            modeloVenda.setDataEntrega(ftfDataEntrega.getText());
        }
        modeloVenda.setIdItemVenda(getIdItensVendaProduto() + 1);
        controle.adicionaItem(modeloVenda, jtfPaciente);
        Somando();
        preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + codVenda);

        limparCampos();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    public void pegaItemBanco(String nome) {
        con.conectar();
        con.executaSQL("select * from produto where nome = '" + nome + "'");
        try {
            con.res.first();
            precoProduto = con.res.getFloat("preco_produto");
            codigoProduto = con.res.getInt("codigo");

            do {
                nomeProduto = con.res.getString("nome");
            } while (con.res.next());

            con.Desconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro metodo pegaItemBanco " + ex);
        }
    }


    private void btnFecharComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharComandaActionPerformed

        if (jcbxProduto.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione um item para fechar");
            return;
        } else {

            pegaItemBanco(jcbxProduto.getSelectedItem().toString());

            String sql = "update itens_venda_produto set status_comanda_item='fechado' where id_produto=? and id_venda = ?";

            int saida = JOptionPane.showConfirmDialog(null, "Confirma o fechamento do item:\n " + jcbxProduto.getSelectedItem().toString(), "Fechamento item comanda", JOptionPane.YES_NO_OPTION);

            if (saida == 0) {
                con.conectar();
                PreparedStatement pst;
                try {
                    pst = con.conn.prepareStatement(sql);
                    pst.setInt(1, codigoProduto);
                    pst.setInt(2, codVenda);
                    pst.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
                }
                con.Desconecta();
                JOptionPane.showMessageDialog(null, "Item da comanda fechado");
                preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                        + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + codVenda);

            } else {
                return;
            }
            con.conectar();
            try {
                con.executaSQL("select count(status_comanda_item) as qtde from itens_venda_produto where id_venda=" + codVenda + " and status_comanda_item='aberto'");

                con.res.first();

                if (con.res.getInt("qtde") == 0) {
                    saida = JOptionPane.showConfirmDialog(null, "Não existem mais itens em aberto, deseja fechar a Ordem de serviço Nº" + txtCodigoOS.getText(), "Fechamento comanda", JOptionPane.YES_NO_OPTION);
                    if (saida == 0) {
                        String query = "update venda set status_venda='fechada' where id_venda = ?";

                        PreparedStatement pst = con.conn.prepareStatement(query);
                        pst.setDouble(1, codVenda);
                        pst.execute();
                        FileWriter writer;
                        try {
                            writer = new FileWriter("C:/Odontoped/Vendas/venda Nº" + codVenda + ".txt", false); // com o parâmetro true você informa que não deve sobrescrever e sim mesclar.
                            PrintWriter output = new PrintWriter(writer);

                            output.write("");
                        } catch (IOException ex) {
                            Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        csv.imprimirLogo(codVenda);
                        csv.format(codVenda);
                        csv.imprimirPacientes(codVenda);
                        imp.emiteCupom(codVenda);

                        //thread para imprimir a segunda via
                        try {
                            Thread.sleep(4000);
                            imp.emiteCupom(codVenda);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao Imprimir 2º via " + e.getMessage());
                        }

                        JOptionPane.showMessageDialog(null, "Comanda fechada, numero da venda: " + codVenda);
                        pst.close();
                        dispose();
                    } else {
                        return;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
            }

            con.Desconecta();
        }

    }//GEN-LAST:event_btnFecharComandaActionPerformed

    private void jcbxProdutoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcbxProdutoPopupMenuWillBecomeInvisible

        pegaItemBanco(jcbxProduto.getSelectedItem().toString());
        jftfValorUnitario.setText(String.valueOf(precoProduto));
    }//GEN-LAST:event_jcbxProdutoPopupMenuWillBecomeInvisible

    private void btnSalvarComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarComandaActionPerformed

        if (isEdicao) {
            editarComanda(codVenda);
        } else {

            if (txtCodigoOS.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo OS não pode ficar vazio");
                txtCodigoOS.requestFocus();
            } else {

                if (tabelaItens.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "Necessário incluir pelo menos 1 item para salvar comanda");
                } else {

                    modeloVenda.setId_cliente(abrir.getCodVenda());

                    modeloVenda.setNome_cli(jcbxCliente.getSelectedItem().toString());
                    modeloVenda.setData(ftfData.getText());
                    modeloVenda.setValor_venda(total);
                    modeloVenda.setCodigoOs(Integer.parseInt(txtCodigoOS.getText()));
                    modeloVenda.setStatus_venda("aberta");

                    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                    //pegando a data atual do sistema
                    Date dataAtual = new Date();
                    //setando no Jformatted Data

                    modeloVenda.setDataComanda(formatoData.format(dataAtual));

                    controle.fechaVenda(modeloVenda, TIPO_SISTEMA);
                    JOptionPane.showMessageDialog(null, "Numero da Venda:" + modeloVenda.getId_venda());
                    limpaDadosVendaVazio();
                    dispose();
                }
            }
        }
    }//GEN-LAST:event_btnSalvarComandaActionPerformed

    private void jtfTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfTotalActionPerformed

    private void tabelaItensMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaItensMouseReleased

        try {
            con.conectar();
            con.executaSQL(" select ivp.produto,ivp.paciente,ivp.cor,ivp.adesivo,ivp.descricao,ivp.nome_aparelho,ivp.data_entrega,ivp.funcionario, \n"
                    + "ivp.glitter_aparelho,ivp.codigo_os,p.preco_produto,v.valor_venda from itens_venda_produto ivp  \n"
                    + "inner join produto p  on ivp.id_produto = p.codigo "
                    + "inner join venda v on ivp.id_venda = v.id_venda "
                    + "where ivp.produto ='" + tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 1) + "' \n"
                    + " and ivp.codigo_os =" + txtCodigoOS.getText().trim() + " \n"
                    + " and ivp.id_itens_venda =" + tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 0) + " \n"
                    + " order by ivp.id_venda");

            con.res.first();

            jcbxProduto.setSelectedItem(con.res.getString("produto").toString());
            jtfPaciente.setText(con.res.getString("paciente"));
            jftfValorUnitario.setText(String.valueOf(con.res.getDouble("preco_produto")));
            txtCor.setText(con.res.getString("cor"));
            txtAdesivo.setText(con.res.getString("adesivo"));
            txtAreaObservacao.setText(con.res.getString("descricao"));

            if (con.res.getString("nome_aparelho").trim().equals("S")) {
                chkNomeNoAparelho.setSelected(true);
            } else {
                chkNomeNoAparelho.setSelected(false);
            }
            if (con.res.getString("glitter_aparelho").trim().equals("S")) {
                chkGlitterNoAparelho.setSelected(true);
            } else {
                chkGlitterNoAparelho.setSelected(false);
            }

            String dateValue = con.res.getString("data_entrega");
            java.util.Date date;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                ftfDataEntrega.setSelectedDate(cal);
            } catch (ParseException ex) {
                Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
            }

            jcbxFuncionario.setSelectedItem(con.res.getString("funcionario").toString());
            con.Desconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar item " + ex.getMessage());
        }


    }//GEN-LAST:event_tabelaItensMouseReleased

    private void tabelaItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaItensMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaItensMouseClicked

    private void btnCancelarComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarComandaActionPerformed
        int saida = JOptionPane.showConfirmDialog(null, "Deseja cancelar a comanda atual?", "Cancelar comanda", JOptionPane.YES_NO_OPTION);

        if (saida == 0) {
            System.out.println(codVenda);
            controle.cancelarVenda(codVenda);
            dispose();
        } else {
            return;
        }


    }//GEN-LAST:event_btnCancelarComandaActionPerformed

    public void ativaDesativaCamposComanda(boolean valor) {
        btnRemoverItem.setEnabled(valor);
        btnSalvarComanda.setEnabled(valor);
        btnCancelarEdicaoItem.setEnabled(valor);
        jtfPaciente.setEditable(valor);
        txtCor.setEditable(valor);
        txtAdesivo.setEditable(valor);
        chkNomeNoAparelho.setEnabled(valor);
        chkGlitterNoAparelho.setEnabled(valor);
        jcbxProduto.setEnabled(valor);
        txtAreaObservacao.setEditable(valor);
        jftfValorUnitario.setEditable(valor);
        jcbxFuncionario.setEnabled(valor);

    }

    private void btnEditarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarItemActionPerformed

        if (tabelaItens.getSelectedRowCount() > 0) {
            ativaDesativaCamposComanda(true);
            isEdicao = true;
            btnEditarItem.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar o item para realizar a edição");
        }

    }//GEN-LAST:event_btnEditarItemActionPerformed

    private void jcbxProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxProdutoActionPerformed

    private void btnCancelarEdicaoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarEdicaoItemActionPerformed
        ativaDesativaCamposComanda(false);
        isEdicao = false;
        btnEditarItem.setEnabled(true);

        con.conectar();
        con.executaSQL("select v.*,ivp.* from venda v inner join itens_venda_produto ivp \n"
                + "on v.id_venda = ivp.id_venda where v.id_venda = " + codVenda + " and ivp.produto='" + tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 0).toString() + "'");
        try {
            con.res.first();
            ftfData.setText(con.res.getString("data_venda"));
            jtfPaciente.setText(con.res.getString("paciente"));
            txtCodigoOS.setText(String.valueOf(con.res.getInt("codigo_os")));
            txtCor.setText(con.res.getString("cor"));
            txtAdesivo.setText(con.res.getString("adesivo"));
            txtAreaObservacao.setText(con.res.getString("descricao"));

            if (con.res.getString("nome_aparelho").equals("S")) {
                chkNomeNoAparelho.setSelected(true);
            } else {
                chkNomeNoAparelho.setSelected(false);
            }

            if (con.res.getString("glitter_aparelho").equals("S")) {
                chkGlitterNoAparelho.setSelected(true);
            } else {
                chkGlitterNoAparelho.setSelected(false);
            }

            jcbxFuncionario.setSelectedItem(con.res.getString("funcionario").toString());

            String dateValue = con.res.getString("data_entrega");
            java.util.Date date;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                ftfDataEntrega.setSelectedDate(cal);
            } catch (ParseException ex) {
                Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
            }
            DecimalFormat df = new DecimalFormat("#,###.00");
            jtfTotal.setText(String.valueOf(df.format(con.res.getDouble("valor_venda"))));

            preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                    + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + con.res.getInt("id_venda"));
            //   modeloVenda.setId_venda(con.res.getInt("id_venda"));
            con.Desconecta();

        } catch (SQLException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnCancelarEdicaoItemActionPerformed

    private void jcbxClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxClienteActionPerformed

    public void remove() {

        String sql = "DELETE  FROM itens_venda_produto WHERE id_venda =? AND produto = ?  AND paciente=? AND id_itens_venda = ? limit 1 ";

        total = total - Float.parseFloat(tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 2).toString());
        con.conectar();
        try {

            PreparedStatement pst = con.conn.prepareStatement(sql);
            pst.setInt(1, codVenda);
            pst.setString(2, tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 1).toString());
            pst.setString(3, jtfPaciente.getText().trim());
            pst.setInt(4, Integer.parseInt(tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 0).toString()));

            pst.execute();
            JOptionPane.showMessageDialog(null, "Excluido");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na remoção do item:" + ex);
        } finally {
            preencherTabelaVenda("SELECT i.id_itens_venda,i.id_venda,i.produto as nome, i.id_produto,p.preco_produto,i.status_comanda_item  FROM itens_venda_produto i inner join produto p \n"
                    + "on p.codigo = i.id_produto WHERE i.id_venda=" + codVenda + " ");

            con.Desconecta();
            System.out.println(total);

            con.conectar();

            DecimalFormat df = new DecimalFormat("#,###.00");
            jtfTotal.setText(String.valueOf(df.format(total)));

        }
    }

    public void editarComanda(int codigoVenda) {
        con.conectar();

        try {
            PreparedStatement pst = con.conn.prepareStatement("update itens_venda_produto set paciente=?,cor=?,adesivo=?,descricao=?,nome_aparelho=?,glitter_aparelho=?,funcionario=?,data_entrega=? "
                    + " where id_venda=? and produto=? and id_itens_venda=?");
            pst.setString(1, jtfPaciente.getText().trim());
            pst.setString(2, txtCor.getText().trim());
            pst.setString(3, txtAdesivo.getText().trim());
            pst.setString(4, txtAreaObservacao.getText().trim());
            pst.setString(5, chkNomeNoAparelho.isSelected() ? "S" : "N");
            pst.setString(6, chkGlitterNoAparelho.isSelected() ? "S" : "N");
            pst.setString(7, jcbxFuncionario.getSelectedItem().toString());
            pst.setString(8, ftfDataEntrega.getText());
            pst.setInt(9, codigoVenda);
            pst.setString(10, tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 1).toString());
            pst.setString(11, tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 0).toString());

            pst.execute();
            JOptionPane.showMessageDialog(null, "Comanda atualizada");
            ativaDesativaCamposComanda(false);
            btnEditarItem.setEnabled(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro " + ex.getMessage());
        }
        con.Desconecta();

    }

    public void limparCampos() {

        jtfPaciente.setText("");
        txtCor.setText("");
        txtAdesivo.setText("");
        chkNomeNoAparelho.setSelected(false);
        chkGlitterNoAparelho.setSelected(false);
        txtAreaObservacao.setText("");
        jftfValorUnitario.setText("");
        ftfDataEntrega.setText("");
        isEdicao = false;

    }

    public void limpaDadosVendaVazio() {
        con.conectar();
        try {
            String sql = "delete from venda where data_venda=0 and id_cliente=0 and nome_cli =0";
            PreparedStatement pst = con.conn.prepareStatement(sql);

            pst.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro : " + ex);
        }
        con.Desconecta();
    }

    public int getIdItensVendaProduto() {
        con.conectar();

        con.executaSQL("select max(id_itens_venda) as id from itens_venda_produto ");

        try {
            while (con.res.next()) {
                idItensVenda = con.res.getInt("id");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro ao gerar id item venda");
        }

        return idItensVenda;
    }

    private void perguntarAoFechar() {
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                    int selectedOption = JOptionPane.showConfirmDialog(null, "Ao fechar a comanda atual sera cancelada \n deseja realmente sair ?", "Cancelar comanda", JOptionPane.YES_NO_OPTION);
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        controle.cancelarVenda(codVenda);
                        dispose();
                    }
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelarComanda;
    public javax.swing.JButton btnCancelarEdicaoItem;
    public javax.swing.JButton btnEditarItem;
    private javax.swing.JButton btnFecharComanda;
    public javax.swing.JButton btnRemoverItem;
    public javax.swing.JButton btnSalvarComanda;
    public javax.swing.JCheckBox chkGlitterNoAparelho;
    public javax.swing.JCheckBox chkNomeNoAparelho;
    public javax.swing.JTextField ftfData;
    public datechooser.beans.DateChooserCombo ftfDataEntrega;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    public javax.swing.JComboBox<String> jcbxCliente;
    public javax.swing.JComboBox<String> jcbxFuncionario;
    public javax.swing.JComboBox<String> jcbxProduto;
    public javax.swing.JFormattedTextField jftfValorUnitario;
    public javax.swing.JTextField jtfPaciente;
    public javax.swing.JFormattedTextField jtfTotal;
    public javax.swing.JTable tabelaItens;
    public javax.swing.JTextField txtAdesivo;
    public javax.swing.JTextArea txtAreaObservacao;
    public javax.swing.JTextField txtCodigoOS;
    public javax.swing.JTextField txtCor;
    // End of variables declaration//GEN-END:variables
}
