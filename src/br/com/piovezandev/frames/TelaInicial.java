package br.com.piovezandev.frames;

import br.com.piovezandev.seguranca.Login;
import br.com.piovezandev.services.ModeloTabela;
import br.com.piovezandev.services.ModeloVenda;
import br.com.piovezandev.relatorios.RelatorioDeTrabalhos;
import br.com.piovezandev.utils.ConexaoJDBC;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author alan
 */
public class TelaInicial extends javax.swing.JFrame {

    ModeloVenda modeloVenda = new ModeloVenda();
    ConexaoJDBC con = new ConexaoJDBC();
    FormComanda formComanda = new FormComanda();

    public TelaInicial() {
        initComponents();

        //inicia maximizado
        carregaTabelaVendasAbertas();
        carregaDadosOrdemServico();
        this.setExtendedState(MAXIMIZED_BOTH);

    }
    String suporte = " Para Suporte:"
            + "\n\n Red Monkey Software"
            + "\n Contato:Alan Piovezan"
            + "\n Telefone:(11)99633-2943"
            + "\n Email:alan.log.piovezan@gmail.com"
            + "\n versao 2.0";

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaOrdemSevico = new javax.swing.JTable();
        btnAtualizar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/Stomatologist-icon.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/artificial-denture_small.jpg"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/service_1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/Edit_Male_User_Icon_128.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/dent2.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/sair.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cadastrar Cliente");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Cadastar Produto");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Ordem de  Serviço");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Editar Cliente");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Editar Produto");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Sair");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/icone-cancelamento.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Estorno de Cupom");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/icone-impressao_126.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Reimpressao de Nota");

        tabelaOrdemSevico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº", "Cliente", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaOrdemSevico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaOrdemSevicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaOrdemSevico);

        btnAtualizar.setText("Atualizar");
        btnAtualizar.setToolTipText("Atualiza a tabela de Ordens de serviço abertas");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel10.setText("Ordens de serviço abertas");

        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu1.setText("Arquivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/user_add.png"))); // NOI18N
        jMenuItem1.setText("Cadastar Doutor");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/group.png"))); // NOI18N
        jMenuItem3.setText("Alterar Doutor");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/user_delete.png"))); // NOI18N
        jMenuItem16.setText("Remover Doutor");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/dentadura.jpg"))); // NOI18N
        jMenuItem10.setText("Cadastrar Item");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/Altera Produto.png"))); // NOI18N
        jMenuItem11.setText("Alterar Item");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/delete.png"))); // NOI18N
        jMenuItem17.setText("Remover Item");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/funcionario.png"))); // NOI18N
        jMenuItem22.setText("Cadastrar Funcionario");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem22);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Venda");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/money.png"))); // NOI18N
        jMenuItem4.setText("Nova Venda");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/refresh.png"))); // NOI18N
        jMenuItem18.setText("Extorno de Venda Impressa");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem18);

        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/funciona3_126.png"))); // NOI18N
        jMenuItem20.setText("Reimpressão de Nota");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem20);

        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/search-flat.png"))); // NOI18N
        jMenuItem24.setText("Pesquisar Ordem de Serviço");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem24);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatorio");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/document_edit.png"))); // NOI18N
        jMenuItem5.setText("Relatorio de Dentista");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/paste.png"))); // NOI18N
        jMenuItem6.setText("Relatorio de Produto");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/report.png"))); // NOI18N
        jMenu5.setText("Relatorio de Venda");

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/calendar.png"))); // NOI18N
        jMenuItem7.setText("Venda por Data");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/calculator.png"))); // NOI18N
        jMenuItem15.setText("Relatorio Mensal ");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/nf-e.png"))); // NOI18N
        jMenuItem19.setText("Relatorio para NF-e");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/calendario_pastoral.png"))); // NOI18N
        jMenuItem13.setText("Relatorio Conferencia");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuItem21.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/dinheiro2.png"))); // NOI18N
        jMenuItem21.setText("Relatorio totalizadores mensal");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem21);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/dinheiro.png"))); // NOI18N
        jMenuItem14.setText("Produtos Comissionados");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/report user.png"))); // NOI18N
        jMenuItem8.setText("Venda por Cliente");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/report 3.png"))); // NOI18N
        jMenuItem9.setText("Relatorio Descritivo");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenu3.add(jMenu5);

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/calendar-512.png"))); // NOI18N
        jMenuItem23.setText("Trabalhos do dia");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem23);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ajuda");

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/help.png"))); // NOI18N
        jMenuItem12.setText("Suporte");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/piovezandev/frames/img/close.png"))); // NOI18N
        jMenuItem2.setText("Sair");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel6)
                                .addGap(82, 82, 82)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel9)
                                .addGap(65, 65, 65)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAtualizar)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel9)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAtualizar))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        CadastroCliente cadastrar = new CadastroCliente();

        cadastrar.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        AlterarCliente alterar = new AlterarCliente();
        alterar.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Venda vender = new Venda();
        vender.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new Login("relatorio_nome").setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new Login("relatorio_data").setVisible(true);

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new Login("relatorio_dentista").setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new Login("relatorio_produto").setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new Login("relatorio_descritivo").setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        CadastroProduto cadastro = new CadastroProduto();
        cadastro.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        AlterarProduto altera = new AlterarProduto();
        altera.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        JOptionPane.showMessageDialog(null, suporte);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

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
        formComanda.tabelaItens.setModel(modelo);
        formComanda.tabelaItens.getColumnModel().getColumn(0).setPreferredWidth(35);
        formComanda.tabelaItens.getColumnModel().getColumn(0).setResizable(false);
        formComanda.tabelaItens.getColumnModel().getColumn(1).setPreferredWidth(360);
        formComanda.tabelaItens.getColumnModel().getColumn(1).setResizable(false);
        formComanda.tabelaItens.getColumnModel().getColumn(2).setPreferredWidth(150);
        formComanda.tabelaItens.getColumnModel().getColumn(2).setResizable(false);
        formComanda.tabelaItens.getColumnModel().getColumn(3).setPreferredWidth(150);
        formComanda.tabelaItens.getColumnModel().getColumn(3).setResizable(false);
        formComanda.tabelaItens.getColumnModel().getColumn(4).setPreferredWidth(100);
        formComanda.tabelaItens.getColumnModel().getColumn(4).setResizable(false);
        formComanda.tabelaItens.getTableHeader().setReorderingAllowed(false);
        formComanda.tabelaItens.setAutoResizeMode(formComanda.tabelaItens.AUTO_RESIZE_OFF);
        formComanda.tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void bloqueiaCampos() {
        //botoes
        formComanda.btnRemoverItem.setEnabled(false);
        formComanda.btnEditarItem.setEnabled(true);
        formComanda.btnSalvarComanda.setEnabled(false);
        formComanda.btnCancelarEdicaoItem.setEnabled(false);

        //campos
        formComanda.jtfPaciente.setEditable(false);
        formComanda.txtCor.setEditable(false);
        formComanda.txtAdesivo.setEditable(false);
        formComanda.chkNomeNoAparelho.setEnabled(false);
        formComanda.chkGlitterNoAparelho.setEnabled(false);
        formComanda.jcbxProduto.setEnabled(false);
        formComanda.txtAreaObservacao.setEditable(false);
        formComanda.jftfValorUnitario.setEditable(false);

    }

    private void carregaDadosOrdemServico() {

        tabelaOrdemSevico.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    bloqueiaCampos();
                    con.conectar();
                    con.executaSQL("select * from venda v inner join itens_venda_produto ivp \n"
                            + "on v.id_venda = ivp.id_venda where v.id_venda = " + tabelaOrdemSevico.getValueAt(tabelaOrdemSevico.getSelectedRow(), 0));
                    try {
                        con.res.first();
                        formComanda.codVenda = con.res.getInt("id_venda");
                        formComanda.setVisible(true);

                        formComanda.jcbxCliente.setSelectedItem(con.res.getString("nome_cli").toString());
                        formComanda.ftfData.setText(con.res.getString("data_venda"));
                        formComanda.jtfPaciente.setText(con.res.getString("paciente"));
                        formComanda.txtCodigoOS.setText(String.valueOf(con.res.getInt("codigo_os")));
                        formComanda.jcbxFuncionario.setSelectedItem(con.res.getString("funcionario").toString());
                        formComanda.jcbxProduto.addItem("");
                        formComanda.jcbxProduto.setSelectedIndex(0);
                        String dateValue = con.res.getString("data_entrega");
                        java.util.Date date;
                        try {
                            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            formComanda.ftfDataEntrega.setSelectedDate(cal);
                        } catch (ParseException ex) {
                            Logger.getLogger(FormComanda.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        DecimalFormat df = new DecimalFormat("#,###.00");
                        formComanda.jtfTotal.setText(String.valueOf(df.format(con.res.getDouble("valor_venda"))));
                        formComanda.total = con.res.getDouble("valor_venda");
                        preencherTabelaVenda("select * from produto inner join itens_venda_produto on produto.codigo = itens_venda_produto.id_produto"
                                + " inner join venda on venda.id_venda = itens_venda_produto.id_venda where venda.id_venda=" + con.res.getInt("id_venda"));
                        //   modeloVenda.setId_venda(con.res.getInt("id_venda"));
                        con.Desconecta();
                        formComanda.jcbxCliente.setEnabled(false);
                        formComanda.jcbxFuncionario.setEnabled(false);

                    } catch (SQLException ex) {
                        Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

    }

    public void carregaTabelaVendasAbertas() {

        con.conectar();

        ArrayList dados = new ArrayList();

        String sql = "select id_venda,nome_cli,data_venda,codigo_os from venda \n"
                + "where status_venda = \"aberta\" order by data_venda desc	";

        String[] colunas = new String[]{"Nº", "Cliente", "Ordem serviço", "Data"};

        con.executaSQL(sql);

        try {
            if (con.res.next()) {
                con.res.first();
                do {
                    modeloVenda.setId_venda(con.res.getInt("id_venda"));
                    dados.add(new Object[]{con.res.getInt("id_venda"), con.res.getString("nome_cli"), con.res.getString("codigo_os"), con.res.getString("data_venda")});
                } while (con.res.next());
                con.Desconecta();

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "erro ao prencher arraylist" + ex);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        tabelaOrdemSevico.setModel(modelo);
        tabelaOrdemSevico.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabelaOrdemSevico.getColumnModel().getColumn(0).setResizable(false);
        tabelaOrdemSevico.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabelaOrdemSevico.getColumnModel().getColumn(1).setResizable(false);
        tabelaOrdemSevico.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaOrdemSevico.getColumnModel().getColumn(2).setResizable(false);
        tabelaOrdemSevico.getColumnModel().getColumn(3).setPreferredWidth(110);
        tabelaOrdemSevico.getColumnModel().getColumn(3).setResizable(false);
        tabelaOrdemSevico.getTableHeader().setReorderingAllowed(false);
        tabelaOrdemSevico.setAutoResizeMode(tabelaOrdemSevico.AUTO_RESIZE_OFF);
        tabelaOrdemSevico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //  Somando();
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        CadastroCliente cadastrar = new CadastroCliente();

        cadastrar.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CadastroProduto cadastro = new CadastroProduto();
        cadastro.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new FormComanda().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AlterarCliente alterar = new AlterarCliente();
        alterar.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        AlterarProduto altera = new AlterarProduto();
        altera.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed

        new Login("relatorio_mensal_nome").setVisible(true);

    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        RemoverCliente remove = new RemoverCliente();
        remove.setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        RemoverProduto remover = new RemoverProduto();
        remover.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        EstornoVenda estorno = new EstornoVenda();
        estorno.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        EstornoVenda estorno = new EstornoVenda();
        estorno.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        new Login("relatorio_nota_fiscal").setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        ReimpressaoNota reimpressao = new ReimpressaoNota();
        reimpressao.setVisible(true);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        ReimpressaoNota reimpressao = new ReimpressaoNota();
        reimpressao.setVisible(true);

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        new Login("relatorio_conferencia").setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        new Login("relatorio_item_comissionado").setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        new Login("relatorio_totalizador_mensal").setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void tabelaOrdemSevicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaOrdemSevicoMouseClicked

    }//GEN-LAST:event_tabelaOrdemSevicoMouseClicked

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregaTabelaVendasAbertas();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        new CadastroFuncionario().setVisible(true);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        new RelatorioDeTrabalhos().setVisible(true);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        new PesquisarOrdemServico().setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAtualizar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaOrdemSevico;
    // End of variables declaration//GEN-END:variables
}
