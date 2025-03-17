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
        // Crear un arreglo para almacenar las cartas por pinta
        int[][] valoresPorPinta = new int[Pinta.values().length][TOTAL_CARTAS];
        int[] conteoPorPinta = new int[Pinta.values().length];
    
        // Llenar el arreglo con los valores de las cartas
        for (int i = 0; i < cartas.length; i++) {
            int indicePinta = cartas[i].getPinta().ordinal();
            valoresPorPinta[indicePinta][conteoPorPinta[indicePinta]] = cartas[i].getNombre().ordinal();
            conteoPorPinta[indicePinta]++;
        }
    
        String resultado = "No se encontraron escaleras";
        boolean hayEscaleras = false;
    
        // Verificar escaleras en cada grupo de pinta sin ordenar
        for (int i = 0; i < valoresPorPinta.length; i++) {
            if (conteoPorPinta[i] >= 3) { // Solo analizamos si hay al menos 3 cartas en la pinta
                int[] contador = new int[13]; // Contador para ver qué valores existen
    
                // Contar cuántas veces aparece cada valor en esta pinta
                for (int j = 0; j < conteoPorPinta[i]; j++) {
                    contador[valoresPorPinta[i][j]] = 1;
                }
    
                // Buscar escaleras recorriendo el contador
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
    
                // Verificar si la última escalera fue válida
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
        int puntaje = 0;
        boolean[] esParteDeGrupo = new boolean[TOTAL_CARTAS];
        boolean[] esParteDeEscalera = new boolean[TOTAL_CARTAS];
    
        // Verificar cartas que forman parte de grupos (pares, ternas, etc.)
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            for (int j = i + 1; j < TOTAL_CARTAS; j++) {
                if (cartas[i].getNombre() == cartas[j].getNombre()) {
                    esParteDeGrupo[i] = true;
                    esParteDeGrupo[j] = true;
                }
            }
        }
    
        // Verificar cartas que forman parte de escaleras
        int[][] valoresPorPinta = new int[Pinta.values().length][TOTAL_CARTAS];
        int[] conteoPorPinta = new int[Pinta.values().length];
    
        for (int i = 0; i < cartas.length; i++) {
            int indicePinta = cartas[i].getPinta().ordinal();
            valoresPorPinta[indicePinta][conteoPorPinta[indicePinta]] = cartas[i].getNombre().ordinal();
            conteoPorPinta[indicePinta]++;
        }
    
        for (int i = 0; i < valoresPorPinta.length; i++) {
            if (conteoPorPinta[i] >= 3) {
                int[] contador = new int[13];
                for (int j = 0; j < conteoPorPinta[i]; j++) {
                    contador[valoresPorPinta[i][j]] = 1;
                }
    
                for (int j = 0; j < contador.length; j++) {
                    if (contador[j] == 1) {
                        for (int k = 0; k < cartas.length; k++) {
                            if (cartas[k].getPinta().ordinal() == i && cartas[k].getNombre().ordinal() == j) {
                                esParteDeEscalera[k] = true;
                            }
                        }
                    }
                }
            }
        }
    
        // Sumar solo cartas que no están en grupos ni en escaleras
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!esParteDeGrupo[i] && !esParteDeEscalera[i]) {
                int valor = cartas[i].getNombre().ordinal() + 1;
                if (valor > 10) { 
                    valor = 10; // AS, JACK, QUEEN y KING valen 10
                }
                puntaje += valor;
            }
        }
    
        return puntaje;
    }
    
    
    
    
    
    
    
}
