package br.com.piovezandev.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author alan
 */
public class ImprimirTXT {
//imprime txt

    public void emiteCupom(int idVenda) {
        try {
            java.io.InputStream is = new FileInputStream("C:/Odontoped/Vendas/venda Nº" + idVenda + ".txt");
            Scanner sc = new Scanner(is);
            FileOutputStream fs = new FileOutputStream("//localhost/POS58");
            PrintStream ps = new PrintStream(fs);

            while (sc.hasNextLine()) {
                String linhas = sc.nextLine();
                ps.println(linhas);
            }
            fs.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro encontrado ao imprimir comanda.");
        }
    }
}
