import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JButton btnEscaleras;
    private JButton btnPuntaje;
    private JButton btnRepartir;
    private JButton btnVerificar;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;

    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();
        btnPuntaje = new JButton();
        btnEscaleras = new JButton();

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnEscaleras.setBounds(220, 10, 100, 25);
        btnEscaleras.setText("Escalera");
        btnEscaleras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEscalerasClick(evt);
            }
        });
        btnPuntaje.setBounds(340, 10, 100, 25);
        btnPuntaje.setText("Puntaje");
        btnPuntaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnPuntajeClick(evt);
            }
        });

        

        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);
        getContentPane().add(btnEscaleras);
        getContentPane().add(btnPuntaje);
        jugador1 = new Jugador();
        jugador2 = new Jugador();
    }

    Jugador jugador1, jugador2;

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();
        switch (pestaña) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getGrupos());
                break;

            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getGrupos());
                break;
        }

    }

    private void btnEscalerasClick(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();
        switch (pestaña) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getEscaleras());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getEscaleras());
                break;
        }
    }

    private void btnPuntajeClick(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();
        int puntaje;
        
        if (pestaña == 0) {
            puntaje = jugador1.calcularPuntaje();
        } else {
            puntaje = jugador2.calcularPuntaje();
        }
        
        JOptionPane.showMessageDialog(null, "El puntaje del jugador es: " + puntaje);
    }
    

}