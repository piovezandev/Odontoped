/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.frames;

import br.com.piovezandev.services.ModeloTabela;
import br.com.piovezandev.utils.ConexaoJDBC;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Alan
 */
public class PesquisarOrdemServico extends javax.swing.JDialog {

    /**
     * Creates new form ControleOrdemServico
     */
    ConexaoJDBC con = new ConexaoJDBC();

    public PesquisarOrdemServico() {

        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);

        gerarComboBoxCliente();;
        gerarComboBoxFuncionario();
        dateChooserDataInicial.setSelectedDate(null);
        dateChooserDataFinal.setSelectedDate(null);
        comboFuncionario.setEnabled(false);
    }

    public void preencherTabelaOrdemServico() {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Ordem serviço", "Cliente", "Data lanç.", "Data produção", "Data entrega", "Valor"};

        String dataInicial = dateChooserDataInicial.getText();
        String dataFinal = dateChooserDataFinal.getText();

        try {
            DateFormat formatBR = new SimpleDateFormat("dd/mm/yyyy");
            Date date1 = formatBR.parse(dataInicial);
            Date date2 = formatBR.parse(dataFinal);

            DateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
            String dateFormated1 = formatUS.format(date1);
            String dateFormated2 = formatUS.format(date2);

            con.conectar();

            StringBuilder sql = new StringBuilder();
            sql.append("select * from venda v \n");
            sql.append("inner join itens_venda_produto i on v.id_venda = i.id_venda \n");
            sql.append("left join  produto p on p.codigo = i.id_produto \n");
            sql.append("left join funcionario f on f.nome = i.funcionario \n");

            if (chkFiltrarTrabalho.isSelected()) {
                sql.append("where funcionario like '%" + comboFuncionario.getSelectedItem().toString() + "%' \n");
                if (!dateChooserDataInicial.getText().equals("") && !dateChooserDataFinal.getText().equals("")) {
                    sql.append(" and STR_TO_DATE(data_venda,'%d/%m/%Y') between'" + dateFormated1 + "' \n");
                    sql.append(" and '" + dateFormated2 + "'  \n");
                }
            } else {

                if (txtOrdemServico.getText().equals("")) {
                    if (comboCliente.getSelectedItem().toString().equals("")) {
                        if (dateChooserDataInicial.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Inclua ao menos um dado para filtro");
                        } else {
                            sql.append(" where STR_TO_DATE(data_venda,'%d/%m/%Y') between'" + dateFormated1 + "' \n");
                            sql.append(" and '" + dateFormated2 + "'  \n");
                        }
                    } else {
                        sql.append("where nome_cli like '%" + comboCliente.getSelectedItem().toString() + "%' \n");
                        if (!dateChooserDataInicial.getText().equals("") && !dateChooserDataFinal.getText().equals("")) {
                            sql.append(" and STR_TO_DATE(data_venda,'%d/%m/%Y') between'" + dateFormated1 + "' \n");
                            sql.append(" and '" + dateFormated2 + "'  \n");
                        }
                    }
                } else {
                    sql.append(" where v.codigo_os = " + txtOrdemServico.getText());
                    if (!comboCliente.getSelectedItem().toString().equals("")) {
                        sql.append(" and nome_cli like '%" + comboCliente.getSelectedItem().toString() + "%' \n");
                    }
                    if (!dateChooserDataInicial.getText().equals("") && !dateChooserDataFinal.getText().equals("")) {
                        sql.append(" and STR_TO_DATE(data_venda,'%d/%m/%Y') between'" + dateFormated1 + "' \n");
                        sql.append(" and '" + dateFormated2 + "'  \n");
                    }
                }
            }
            sql.append(" group by v.codigo_os \n");

            con.executaSQL(sql.toString());
            System.out.println(sql.toString());

            if (con.res.next()) {
                con.res.first();
                do {
                    dados.add(new Object[]{con.res.getInt("codigo_os"), con.res.getString("nome_cli"), con.res.getString("data_comanda"), con.res.getString("data_venda"), con.res.getString("data_entrega"), con.res.getDouble("valor_venda")});
                } while (con.res.next());
                con.Desconecta();

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro ao prencher arraylist" + ex);
        } catch (ParseException ex) {
            Logger.getLogger(PesquisarOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);

        tabelaOrdemServico.setModel(modelo);
        tabelaOrdemServico.getColumnModel().getColumn(0).setPreferredWidth(140);
        tabelaOrdemServico.getColumnModel().getColumn(0).setResizable(false);
        tabelaOrdemServico.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabelaOrdemServico.getColumnModel().getColumn(1).setResizable(false);
        tabelaOrdemServico.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaOrdemServico.getColumnModel().getColumn(2).setResizable(false);
        tabelaOrdemServico.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaOrdemServico.getColumnModel().getColumn(3).setResizable(false);
        tabelaOrdemServico.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaOrdemServico.getColumnModel().getColumn(4).setResizable(false);
        tabelaOrdemServico.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaOrdemServico.getColumnModel().getColumn(5).setResizable(false);
        tabelaOrdemServico.getTableHeader().setReorderingAllowed(false);
        tabelaOrdemServico.setAutoResizeMode(tabelaOrdemServico.AUTO_RESIZE_OFF);
        tabelaOrdemServico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void gerarComboBoxCliente() {

        con.conectar();
        try {
            con.executaSQL("select * from cliente order by nome");
            comboCliente.addItem("");
            while (con.res.next()) {
                comboCliente.addItem(con.res.getString("nome"));
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
                comboFuncionario.addItem(con.res.getString("nome"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlterarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaOrdemServico = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtOrdemServico = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        comboCliente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboFuncionario = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dateChooserDataInicial = new datechooser.beans.DateChooserCombo();
        dateChooserDataFinal = new datechooser.beans.DateChooserCombo();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblCliente = new javax.swing.JLabel();
        lblFuncionario = new javax.swing.JLabel();
        lblFunc = new javax.swing.JLabel();
        chkFiltrarTrabalho = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pesquisar Ordem de Serviço");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(241, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        tabelaOrdemServico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ordem serviço", "Cliente", "Data lançamento", "Data produção", "Data entrega", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaOrdemServico);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros de pesquisa"));

        txtOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdemServicoActionPerformed(evt);
            }
        });

        jLabel1.setText("Ordem de serviço:");

        comboCliente.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboClientePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel2.setText("Cliente:");

        jLabel3.setText("Funcionario:");

        comboFuncionario.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFuncionarioPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodo"));

        jLabel4.setText("De:");

        jLabel5.setText("Ate:");

        dateChooserDataInicial.setCurrentView(new datechooser.view.appearance.AppearancesList("Contrast",
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
    dateChooserDataInicial.setFormat(2);

    dateChooserDataFinal.setCurrentView(new datechooser.view.appearance.AppearancesList("Contrast",
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

javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
jPanel4.setLayout(jPanel4Layout);
jPanel4Layout.setHorizontalGroup(
    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(jPanel4Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4)
            .addComponent(jLabel5))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dateChooserDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
            .addComponent(dateChooserDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        .addGap(10, 10, 10))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel4)
                .addComponent(dateChooserDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel5)
                .addComponent(dateChooserDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(16, Short.MAX_VALUE))
    );

    jButton1.setText("Pesquisar");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jButton2.setText("Cancelar");
    jButton2.setToolTipText("Cancela a pesquisa, limpa os campos");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    lblCliente.setText(" ");

    lblFuncionario.setText("jLabel8");
    lblFuncionario.setPreferredSize(new java.awt.Dimension(0, 0));

    lblFunc.setText(" ");

    chkFiltrarTrabalho.setText("Filtrar por trabalho");
    chkFiltrarTrabalho.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            chkFiltrarTrabalhoMouseClicked(evt);
        }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboCliente, 0, 235, Short.MAX_VALUE)
                        .addComponent(comboFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(chkFiltrarTrabalho)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(jButton2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton1)))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton2))
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(lblCliente))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(comboFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblFunc))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(chkFiltrarTrabalho)
            .addContainerGap())
    );

    btnCancelar.setText("Cancelar");
    btnCancelar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelarActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnCancelar)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(btnCancelar)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdemServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrdemServicoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void comboClientePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboClientePopupMenuWillBecomeInvisible
        lblCliente.setText(comboCliente.getSelectedItem().toString());
    }//GEN-LAST:event_comboClientePopupMenuWillBecomeInvisible

    private void comboFuncionarioPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboFuncionarioPopupMenuWillBecomeInvisible
        lblFunc.setText(comboFuncionario.getSelectedItem().toString());
    }//GEN-LAST:event_comboFuncionarioPopupMenuWillBecomeInvisible

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtOrdemServico.setText("");
        lblCliente.setText("");
        lblFunc.setText("");
        comboCliente.setSelectedIndex(0);
        comboFuncionario.setSelectedIndex(0);
        dateChooserDataInicial.setSelectedDate(null);
        dateChooserDataFinal.setSelectedDate(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        preencherTabelaOrdemServico();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkFiltrarTrabalhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkFiltrarTrabalhoMouseClicked
        if (chkFiltrarTrabalho.isSelected()) {
            txtOrdemServico.setEnabled(false);
            comboCliente.setEnabled(false);
            comboFuncionario.setEnabled(true);
        } else {
            txtOrdemServico.setEnabled(true);
            comboCliente.setEnabled(true);
            comboFuncionario.setEnabled(false);
        }
    }//GEN-LAST:event_chkFiltrarTrabalhoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkFiltrarTrabalho;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboFuncionario;
    private datechooser.beans.DateChooserCombo dateChooserDataFinal;
    private datechooser.beans.DateChooserCombo dateChooserDataInicial;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFunc;
    private javax.swing.JLabel lblFuncionario;
    private javax.swing.JTable tabelaOrdemServico;
    private javax.swing.JTextField txtOrdemServico;
    // End of variables declaration//GEN-END:variables
}
