package hja.modelo.game;
 
import java.util.ArrayList;
import java.util.Comparator;
 
 
/**
 * <h2 style="color:DodgerBlue;">Clase Mano</h2>
 * 
 * @author Cristo Fernando Lopez Cabañas, Sergio Manzanaro Caraballo y Ernesto
 *         Vivar Laviña
 *
 */
public class Mano {
     
    // ATRIBUTOS
     
    // Array ordenado de cartas
    private ArrayList<Carta> mano;
     
    // Array desordenado de cartas
    private ArrayList<Carta> manoSinOrden;
    
    
    private ArrayList<Carta> noComunes;
    private ArrayList<Carta> board;
     
    /**
     * Constructor, se usa en el modo 4: Omaha
     * @param a: Array de cartas
     */
    public Mano(ArrayList<Carta> a) {
        manoSinOrden = new ArrayList<Carta>(a);
        mano = new ArrayList<Carta>(a);
        mano.sort(new Comparator<Carta>() {
            @Override
            public int compare(Carta a, Carta b) {
                return Carta.compare(a, b);
            }
        });
        
        noComunes = this.cartasNoComunes();
        board = this.cartasBoard();
        board.sort(new Comparator<Carta>() {
            @Override
            public int compare(Carta a, Carta b) {
                return Carta.compare(a, b);
            }
        });
        
    }
     
    /**
     * Constructor, se usa en los tres primeros modos de juego
     * @param cartas: String con la informacion de las cartas
     * @param n: Numero de cartas de la mano
     * @throws ParserException si hay alguna carta que no sea valida en el string
     */
    public Mano(String cartas, int n) {
        int i = 0;
        mano = new ArrayList<Carta>();
        manoSinOrden = new ArrayList<Carta>();
        while(mano.size() != n) {
            Carta aux = new Carta(cartas.charAt(i), cartas.charAt(i + 1));
            mano.add(aux);
            manoSinOrden.add(aux);
            i += 2;
        }
        mano.sort(new Comparator<Carta>() {
            @Override
            public int compare(Carta a, Carta b) {
                return Carta.compare(a, b);
            }
        });
        
        noComunes = this.cartasNoComunes();
        board = this.cartasBoard();
        board.sort(new Comparator<Carta>() {
            @Override
            public int compare(Carta a, Carta b) {
                return Carta.compare(a, b);
            }
        });
    }
     
    /**
     * Devuelve la carta en la posicion i de la mano
     * @param i: Posicion de la carta
     * @return Carta en la posicion i-esima en la mano
     */
    public Carta dimeCarta(int i) {
        return mano.get(i);
    }
     
    /**
     * Comprueba si hay color
     * @param e: Array de cartas a comprobar
     * @return True o False dependiendo de si hay o no color
     */
    public static boolean esColor(ArrayList<Carta> e) {
        boolean color = true;
        int i = 1;
        while(color && i < e.size()) {
            if(!e.get(0).suited(e.get(i))) color = false;
            i++;
        }
        return color;
    }
     
    /**
     * Comprueba si la escalera es real
     * @param e: Array de cartas a comprobar
     * @return True o False dependiendo de si la escalera es real o no
     */
    public static boolean esReal(ArrayList<Carta> e) {
        return esColor(e) && e.size() >= 2 && e.get(0).getFigura() == 'A' && e.get(1).getFigura() == 'K';
    }
     
    /**
     * Comprueba si el draw de escalera es el denominado Open Ended
     * @param e: Array de cartas a comprobar
     * @return True o False dependiendo de si es o no open ended
     */
    public static boolean openEnded(ArrayList<Carta> e) {
        boolean loEs = true;
        if(e.get(0).getFigura() == 'A') {
            if(e.get(1).getFigura() == '4' || e.get(1).getFigura() == 'K') {
                int i = 1;
                while(loEs && i < e.size() - 1) {
                    if(e.get(i).getValor() - 1 != e.get(i + 1).getValor()) loEs = false;
                    i++;
                }
            }
            else {
                loEs = false;
            }
        }
        else {
            int j = 0;
            while(loEs && j < e.size() - 1) {
                if(e.get(j).getValor() - 1 != e.get(j + 1).getValor()) loEs = false;
                j++;
            }
        }
        return loEs;
    }
     
    /**
     * Compara dos manos con escaleras para determinar la mejor
     * @param e1: Array de cartas 1 a comprobar
     * @param e2: Array de cartas 2 a comprobar
     * @return True o False dependiendo de si e1 es una escalera mejor que e2
     */
    private boolean esMejorEscalera(ArrayList<Carta> e1, ArrayList<Carta> e2) {
        boolean esMejor = false;
        if(e1.size() > e2.size())
            esMejor = true;
        else if (e1.size() == e2.size()){
            if(esReal(e1)) {
                if(!esReal(e2)) esMejor = true;
            }
            else if(esColor(e1)) {
                if(!esColor(e2)) esMejor = true;
            }
            else {
                if(!esColor(e2)) {
                    if(e1.get(0).getValor() > e2.get(0).getValor()) esMejor = true;
                    else if(e1.get(0).getValor() == e2.get(0).getValor()) {
                        if(e1.size() > 1 && e1.get(1).getValor() > e2.get(1).getValor()) esMejor = true;
                    }
                }
            }
        }
        return esMejor;
    }
     
    /**
     * Devuelve un array de cartas que forman una escalera
     * @return mejorEscalera o null, dependiendo de si hay o no escalera
     */
    public ArrayList<Carta> escalera(){
        ArrayList<Carta> escalera = new ArrayList<Carta>();
        ArrayList<Carta> mejorEscalera = new ArrayList<Carta>();
        for(int i = 0; i < this.mano.size() - 3; ++i) {
            int gutshot = 1;
            int j = i + 1;
            escalera.add(mano.get(i));
            boolean parar = false;
            while(!parar && j < mano.size() && escalera.size() < 5) {
                Carta cola = escalera.get(escalera.size() - 1);
                Carta actual = mano.get(j);
                if(cola.getFigura() == 'A') { // tenemos un as en la cola
                    if(actual.getFigura() == '5') {
                        escalera.add(actual);
                    }
                    else if(actual.getFigura() == '4') {
                        gutshot--;
                        escalera.add(actual);
                    }
                    else {
                        int v = actual.getValor();
                        if(v + 1 == cola.getValor()) {
                            escalera.add(actual);
                        }
                        else if(v + 2 == cola.getValor()) {
                            gutshot--;
                            escalera.add(actual);
                        }
                    }
                }
                else { // cualquier otra carta en la cola
                    if(cola.getValor() == actual.getValor()) { // 2 cartas iguales
                        if(escalera.size() > 1) {
                            if(actual.suited(escalera.get(escalera.size() - 2))){
                                escalera.remove(escalera.size() - 1);
                                escalera.add(actual);
                            }
                        }
                    }
                    else if(cola.getValor() == actual.getValor() + 1) { // siguiente carta
                        if(escalera.size() == 4 && gutshot == 0) { // si ya hay un gutshot de escalera
                            parar = true;
                        }
                        else {
                            escalera.add(actual);
                        }
                    }
                    else if(cola.getValor() == actual.getValor() + 2) { // siguiente de la siguiente
                        if(gutshot > 0) {
                        	if(escalera.size() == 4) {
                        		parar = true;
                        	}
                        	else {
	                            gutshot--;
	                            escalera.add(actual);
                            }
                        }
                        else {
                            parar = true;
                        }
                    }
                }
                j++;
            }
            if(!this.usoCartaComun(escalera)) {
            	escalera.clear();
            }
            if(esMejorEscalera(escalera, mejorEscalera)) {
                mejorEscalera.clear();
                mejorEscalera.addAll(escalera);
            }
            escalera.clear();
        } // fin for
        if(mejorEscalera.size() < 4)
            mejorEscalera = null;
        return mejorEscalera;
    }
     
     
    /**
     * Devuelve un array de cartas que forman color
     * @return mejorColor o null, dependiendo de si hay o no color
     */
    public ArrayList<Carta> color(){
         
        ArrayList<Carta> color = new ArrayList<Carta>();
        ArrayList<Carta> mejorColor = new ArrayList<Carta>();
        int i = 0;
        while(i < mano.size() - 3 && mejorColor.size() < 5) { // mientras no tengamos color encontrado
            int j = i + 1;
            color.add(mano.get(i)); // empezamos a mirar
            while(j < mano.size() && color.size() < 5) {
                if(mano.get(i).suited(mano.get(j))) // encontramos carta del mismo color
                    color.add(mano.get(j));
                 
                j++;
            }
            if(!this.usoCartaComun(color)) {
            	color.clear();
            }
            if(color.size() > mejorColor.size()) { // si tenemos un array mejor que el que ya habiamos guardado
                mejorColor.clear(); // desechamos el antiguo
                mejorColor.addAll(color); // añadimos el nuevo
            }
            color.clear(); // vaciamos el array actual
            i++;
        }
         
        if(mejorColor.size() < 4)
            mejorColor = null;
        return mejorColor;
         
    }
     
 
     
     
     
    /**
     * metodo que devuelve la primera pareja
     * @param indice pos desde la que empezar a buscar
     * @return la pareja o doble pareja o null si no hay
     */
    private ArrayList<Carta> pareja(int indice){
        ArrayList<Carta> par = new ArrayList<Carta>();
        int i = indice;
        boolean encontrado = false;
        while(!encontrado && i < mano.size() - 1) {
            if(mano.get(i).getFigura() == mano.get(i + 1).getFigura()) {
                encontrado = true;
                par.add(mano.get(i));
                par.add(mano.get(i + 1));
            }
            i++;
        }
        if(par.size() != 2) {
            par = null;
        }
        return par;
    }
     
     
     
    /**
     * metodo que devuelve la primera posicion de una figura en una mano
     * @param c carta a buscar
     * @return la posicion que ocupa o -1 si no esta
     */
    private int buscaEnManoFigura(Carta c) {
        int pos = 0;
        while(pos < mano.size() && mano.get(pos).getFigura() != c.getFigura()) {
            pos++;
        }
        if(pos == mano.size())
            pos = -1;
        return pos;
    }
 
     
     
     
    /**
     * metodo que devuelve las parejas que hay en una mano, como maximo 2 (doble pareja)
     * @param indice pos desde la que empezar a buscar
     * @return la pareja, doble pareja o null si no hay
     */
    public ArrayList<Carta> doblesParejas(int indice){
    	ArrayList<Carta> par = new ArrayList<Carta>();
        
        int i = indice;
        while(i < mano.size() - 1 && par.size() < 4) {
            if(mano.get(i).getFigura() == mano.get(i + 1).getFigura()) {
            	ArrayList<Carta> aux = new ArrayList<Carta>();
                aux.add(mano.get(i));
                aux.add(mano.get(i + 1));
                if(this.usoCartaComun(aux)) {
                	par.addAll(aux);
                }
            }
            i++;
        }
        if(par.size() == 0 || par.size() % 2 != 0) {
            par = null;
        }
        return par;
    }
     
     
     
    /**
     * metodo que devuelve el mejor trio que hay en una mano
     * @return el trio o null si no hay
     */
    private ArrayList<Carta> trio(){
        ArrayList<Carta> trio = new ArrayList<Carta>();
        int i = 0;
        boolean encontrado = false;
        while(!encontrado && i < mano.size() - 2) {
            if(mano.get(i).getFigura() == mano.get(i + 2).getFigura()) {
                encontrado = true;
                trio.add(mano.get(i));
                trio.add(mano.get(i + 1));
                trio.add(mano.get(i + 2));
            }
            i++;
        }
        if(trio.size() != 3) {
            trio = null;
        }
        return trio;
    }/***/
    
    
    public ArrayList<Carta> trioMesa(){
        ArrayList<Carta> trio = new ArrayList<Carta>();
        int i = 0;
        boolean encontrado = false;
        while(!encontrado && i < mano.size() - 2) {
            if(mano.get(i).getFigura() == mano.get(i + 2).getFigura()) {
                encontrado = true;
                trio.add(mano.get(i));
                trio.add(mano.get(i + 1));
                trio.add(mano.get(i + 2));
                if(!this.usoCartaComun(trio)) {
                	encontrado = false;
                	trio.clear();
                }
            }
            i++;
        }
        if(trio.size() != 3) {
            trio = null;
        }
        return trio;
    }
     
     
     
     
    /**
     * metodo que devuelve el mejor, si hay, full en la mano
     * @return el full o null si no hay
     */
    public ArrayList<Carta> full(){
    	boolean usoCartaComun = false;
        ArrayList<Carta> full = trio(); // buscamos trio
        if(full != null) { // tenemos trio ya
        	
        	if(this.usoCartaComun(full)) {
        		usoCartaComun = true;
            }
             
            ArrayList<Carta> pareja = this.pareja(0); // buscamos pareja
            boolean parar = false;
            
            while(pareja != null && !parar) {
	            
	            if(pareja.get(0).getFigura() == full.get(0).getFigura()) { // si hemos encontrado la pareja del trio
	                pareja.clear(); // limpiamos el array
	                int pos = this.buscaEnManoFigura(full.get(0)); // buscas la posicion de la carta
	                pos = pos + 2;
	                pareja = this.pareja(pos); // buscas la pareja a partir del trio
	            }
	            else { // la pareja es otra al trio
	            	if(usoCartaComun) {
	            		full.addAll(pareja);
	            		parar = true;
	            	}
	            	else {
		                if(!this.usoCartaComun(pareja)) {
		                	int pos = this.buscaEnManoFigura(pareja.get(0)); // buscas la posicion de la carta
			                pos = pos + 2;
			                pareja = this.pareja(pos); // buscas la pareja a partir del trio
		                }
		                else {
		                	full.addAll(pareja);
		                	parar = true;
		                	usoCartaComun = true;
		                }
	            	}
	            }
            }
            if(pareja == null)
            	full = null;
            
        }
        return full;
    }
     
     
     
    /**
     * metodo que comprueba que tengamos un poker en la mano
     * @return el array que contiene las cartas que forman el poker o null si no hay poker
     */
    public ArrayList<Carta> poker(){
        ArrayList<Carta> poker = new ArrayList<Carta>();
        int i = 0;
        boolean encontrado = false;
        while(!encontrado && i < mano.size() - 3) {
            if(mano.get(i).getFigura() == mano.get(i + 3).getFigura()) {
                encontrado = true;
                poker.add(mano.get(i));
                poker.add(mano.get(i + 1));
                poker.add(mano.get(i + 2));
                poker.add(mano.get(i + 3));
                if(!this.usoCartaComun(poker)) {
                	encontrado = false;
                	poker.clear();
                }
            }
            i++;
        }
        if(poker.size() != 4)
            poker = null;
        return poker;
    }
     
     
    /**
     * metodo que devuelve un array con la mano desordenada (la mano segun la entrada)
     * @return el array con la mano
     */
    public ArrayList<Carta> dimeMano(){
        return manoSinOrden;
    }
     
     
     
    /**
     * metodo que devuelve un array para la salida del apartado 2
     * @param j array con la mejor jugada de la mano
     * @return el array con la mano de 5 cartas que el jugador juega
     *
    public Mano normaliza(ArrayList<Carta> j){
        ArrayList<Carta> ord = new ArrayList<Carta>(mano);
        ArrayList<Carta> desOrd = new ArrayList<Carta>(manoSinOrden);
        int i = ord.size() - 1;
        while(ord.size() > 5) {
            if(!j.contains(ord.get(i)))
                ord.remove(i);
            i--;
        }
        desOrd.retainAll(ord);
        return new Mano(desOrd);
    }*/
     
     
    /**
     * metodo toString de la mano que devuelve un string las cartas desordenadas
     */
    public String toString() {
        String result = "";
        for(Carta c : manoSinOrden) {
            result += c;
        }
        return result;
    }
    
    
    public String getCartasTablero() {
    	String result = "";
    	for(int i = 0; i < 2; i++) {
    		result += this.manoSinOrden.get(i);
    	}    	
    	return result;
    }
    
    
    /*************************************************************************************/
    
    // al menos siempre hay 3 cartas
    private ArrayList<Carta> cartasBoard(){
    	ArrayList<Carta> result = new ArrayList<Carta>();
    	for(int i = 2; i < manoSinOrden.size(); i++){
    		result.add(manoSinOrden.get(i));
    	}
    	return result;
    }
    
    
    private ArrayList<Carta> cartasNoComunes(){
    	ArrayList<Carta> result = new ArrayList<Carta>();
    	result.add(this.manoSinOrden.get(0));
    	result.add(this.manoSinOrden.get(1));
    	return result;
    }
    
    
    private boolean usoCartaComun(ArrayList<Carta> v) {
    	boolean uso = false;
    	int pos = 0;
    	while(pos < v.size() && !uso) {
    		if(v.get(pos).getFigura() == noComunes.get(0).getFigura() &&
    				v.get(pos).getColor() == noComunes.get(0).getColor()) {
    			uso = true;
    		}
    		else if(v.get(pos).getFigura() == noComunes.get(1).getFigura() &&
					v.get(pos).getColor() == noComunes.get(1).getColor()) {
				uso = true;
			}
    		pos++;
    	}
    	return uso;
    }
    

    
    public Carta cartaMasAltaBoard() {
    	return board.get(0);
    }
    
    public Carta segundaCartaMasAltaBoard() {
    	return board.get(1);
    }

    
    public static boolean esOverPair(ArrayList<Carta> v, Carta mayor) {
    	boolean loEs = false;
    	if(v.get(0).getValor() > mayor.getValor()) loEs = true;  	
    	return loEs;
    }
    
    public static boolean esPocket(ArrayList<Carta> v, String m) {
    	Carta a = v.get(0);
    	Carta b = v.get(1);
    	String r1 = a.toString() + b.toString();
    	String r2 = b.toString() + a.toString();
    	boolean loEs = false;
    	if(a.getValor() == b.getValor() && r1.equals(m) || r2.equals(m)) loEs = true;
    	return loEs;
    }
    
    public static boolean esPocketPairBelowTop(ArrayList<Carta> v, Carta mayor, String m) {
    	boolean loEs = false;
    	if(v.get(0).getValor() < mayor.getValor()) loEs = true;
    	return esPocket(v, m) && loEs;
    }
    
    
    public static boolean esMiddlePair(ArrayList<Carta> v, Carta segun) {
    	boolean loEs = false;
    	if(v.get(0).getValor() == segun.getValor()) loEs = true;
    	//System.out.println(v.get(0).getValor() + " ,,,, " + segun.getValor() + " ,,,, " + loEs);
    	return loEs;
    }
    
    
    public static boolean esTopPair(ArrayList<Carta> v, Carta mayor) {
    	boolean loEs = false;
    	if(v.get(0).getValor() == mayor.getValor()) loEs = true;  	
    	return loEs;
    }
    
 
    
    
    
    
    
    
    
}