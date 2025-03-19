import java.util.Random;

import javax.swing.JPanel;

public class Jugador {
    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicionHorizontal = MARGEN + cartas.length * DISTANCIA;

        for (Carta c : cartas) {
            c.mostrar(pnl, posicionHorizontal, MARGEN);
            posicionHorizontal -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;

        for (int c : contadores) {
            if (c >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos){
            mensaje = "se encontraron los siguientes grupos: \n";
            int p = 0;
            for (int c : contadores){
                if (c>= 2){
                    mensaje += Grupo.values()[c] + " de "+ NombreCarta.values()[p]+ "\n";
                }
                p++;
            }
        }

        return mensaje;

    }

    public String getEscaleras() {
       
        int[][] valoresPorPinta = new int[Pinta.values().length][TOTAL_CARTAS];
        int[] conteoPorPinta = new int[Pinta.values().length];
    
        
        for (int i = 0; i < cartas.length; i++) {
            int indicePinta = cartas[i].getPinta().ordinal();
            valoresPorPinta[indicePinta][conteoPorPinta[indicePinta]] = cartas[i].getNombre().ordinal();
            conteoPorPinta[indicePinta]++;
        }
    
        String resultado = "No se encontraron escaleras";
        boolean hayEscaleras = false;
    
        
        for (int i = 0; i < valoresPorPinta.length; i++) {
            if (conteoPorPinta[i] >= 3) { 
                int[] contador = new int[13]; 
    
                
                for (int j = 0; j < conteoPorPinta[i]; j++) {
                    contador[valoresPorPinta[i][j]] = 1;
                }
    
                
                int inicio = -1;
                int fin = -1;
                int longitud = 0;
    
                for (int j = 0; j < contador.length; j++) {
                    if (contador[j] == 1) {
                        if (inicio == -1) {
                            inicio = j;
                        }
                        fin = j;
                        longitud++;
                    } else {
                        if (longitud >= 3) {
                            if (!hayEscaleras) {
                                resultado = "Se encontraron las siguientes escaleras:\n";
                                hayEscaleras = true;
                            }
                            resultado += "Escalera en " + Pinta.values()[i] + ": " +
                                         NombreCarta.values()[inicio] + " a " + 
                                         NombreCarta.values()[fin] + "\n";
                        }
                        inicio = -1;
                        fin = -1;
                        longitud = 0;
                    }
                }
    
                
                if (longitud >= 3) {
                    if (!hayEscaleras) {
                        resultado = "Se encontraron las siguientes escaleras:\n";
                        hayEscaleras = true;
                    }
                    resultado += "Escalera en " + Pinta.values()[i] + ": " +
                                 NombreCarta.values()[inicio] + " a " + 
                                 NombreCarta.values()[fin] + "\n";
                }
            }
        }
    
        return resultado;
    }
    
    public int calcularPuntaje() {
        int puntajeTotal = 0;
    int[] contadores = new int[NombreCarta.values().length];
    int[][] cartasPorPinta = new int[4][NombreCarta.values().length]; 

    
    for (int i = 0; i < cartas.length; i++) {
        int indice = cartas[i].getNombre().ordinal();
        contadores[indice]++;
        int pinta = cartas[i].getPinta().ordinal();
        cartasPorPinta[pinta][indice] = 1; 
    }

    
    int[] cartasEnEscalera = new int[NombreCarta.values().length];
    for (int p = 0; p < 4; p++) {
        int consecutivas = 0;
        for (int v = 0; v < cartasPorPinta[p].length; v++) {
            if (cartasPorPinta[p][v] == 1) {
                consecutivas++;
                if (consecutivas >= 3) { 
                    for (int k = 0; k < 3; k++) {
                        cartasEnEscalera[v - k] = 1;
                    }
                }
            } else {
                consecutivas = 0; 
            }
        }
    }

    // Sumar solo las cartas que NO están en grupo ni en escalera
    for (int i = 0; i < contadores.length; i++) {
        if (contadores[i] == 1 && cartasEnEscalera[i] == 0) { 
            if (i == 0 || i >= 10) { // AS, JACK, QUEEN, KING valen 10 puntos
                puntajeTotal += 10;
            } else {
                puntajeTotal += (i + 1);
            }
        }
    }

    return puntajeTotal;
    }
    
    
    
    
    
    
    
    
    
}
