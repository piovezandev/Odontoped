/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.piovezandev.services;

/**
 *
 * @author alan
 */
public class ModeloVenda {

    /**
     * @param statusComandaItem the statusComandaItem to set
     */
    public void setStatusComandaItem(String statusComandaItem) {
        this.statusComandaItem = statusComandaItem;
    }

    private int id_venda, idItemVenda;
    private String data, dataComanda;
    private double valor_venda;
    private int id_cliente;
    private String nome_cli;
    private String nome_produto;
    private int QtItem;
    private String paciente;
    private String status_venda;
    private int codigoOs;
    private String cor, adesivo, nome_aparelho, gliter, observacoes, dataEntrega, statusComandaItem, funcionario;
    private double valor_produto;

    /**
     * @return the id_venda
     */
    public int getId_venda() {
        return id_venda;
    }

    /**
     * @param id_venda the id_venda to set
     */
    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the valor_venda
     */
    public double getValor_venda() {
        return valor_venda;
    }

    /**
     * @param valor_venda the valor_venda to set
     */
    public void setValor_venda(double valor_venda) {
        this.valor_venda = valor_venda;
    }

    /**
     * @return the id_cliente
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * @return the nome_cli
     */
    public String getNome_cli() {
        return nome_cli;
    }

    /**
     * @param nome_cli the nome_cli to set
     */
    public void setNome_cli(String nome_cli) {
        this.nome_cli = nome_cli;
    }

    /**
     * @return the nome_produto
     */
    public String getNome_produto() {
        return nome_produto;
    }

    /**
     * @param nome_produto the nome_produto to set
     */
    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    /**
     * @return the QtItem
     */
    public int getQtItem() {
        return QtItem;
    }

    /**
     * @param QtItem the QtItem to set
     */
    public void setQtItem(int QtItem) {
        this.QtItem = QtItem;
    }

    /**
     * @return the paciente
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the status_venda
     */
    public String getStatus_venda() {
        return status_venda;
    }

    /**
     * @param status_venda the status_venda to set
     */
    public void setStatus_venda(String status_venda) {
        this.status_venda = status_venda;
    }

    /**
     * @return the codigoOs
     */
    public int getCodigoOs() {
        return codigoOs;
    }

    /**
     * @param codigoOs the codigoOs to set
     */
    public void setCodigoOs(int codigoOs) {
        this.codigoOs = codigoOs;
    }

    /**
     * @return the cor
     */
    public String getCor() {
        return cor;
    }

    /**
     * @param cor the cor to set
     */
    public void setCor(String cor) {
        this.cor = cor;
    }

    /**
     * @return the adesivo
     */
    public String getAdesivo() {
        return adesivo;
    }

    /**
     * @param adesivo the adesivo to set
     */
    public void setAdesivo(String adesivo) {
        this.adesivo = adesivo;
    }

    /**
     * @return the nome_aparelho
     */
    public String getNome_aparelho() {
        return nome_aparelho;
    }

    /**
     * @param nome_aparelho the nome_aparelho to set
     */
    public void setNome_aparelho(String nome_aparelho) {
        this.nome_aparelho = nome_aparelho;
    }

    /**
     * @return the gliter
     */
    public String getGliter() {
        return gliter;
    }

    /**
     * @param gliter the gliter to set
     */
    public void setGliter(String gliter) {
        this.gliter = gliter;
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * @return the dataEntrega
     */
    public String getDataEntrega() {
        return dataEntrega;
    }

    /**
     * @param dataEntrega the dataEntrega to set
     */
    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    /**
     * @return the statusComandaItem
     */
    public String getStatusComandaItem() {
        return statusComandaItem;
    }

    /**
     * @return the funcionario
     */
    public String getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the idItemVenda
     */
    public int getIdItemVenda() {
        return idItemVenda;
    }

    /**
     * @param idItemVenda the idItemVenda to set
     */
    public void setIdItemVenda(int idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    /**
     * @return the dataComanda
     */
    public String getDataComanda() {
        return dataComanda;
    }

    /**
     * @param dataComanda the dataComanda to set
     */
    public void setDataComanda(String dataComanda) {
        this.dataComanda = dataComanda;
    }

    
    public double getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(double valor_produto) {
        this.valor_produto = valor_produto;
    }
}
