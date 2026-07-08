package core;

import entidade.InterfaceGrafica;
import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da Calculadora de Circuito RC.
 * Inicializa a interface gráfica na thread de eventos do Swing (EDT).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceGrafica::new);
    }
}
