package it.polito.tdp.indovinaNumero.model;

import java.nio.channels.IllegalSelectorException;
import java.security.InvalidParameterException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GiocoModel {
	
	private static final int N_MIN = 1;
	private static final int N_MAX = 100;
	private static final int N_TENTATIVI = 7;
	private static final int TENTATIVO_RIUSCITO = 0;
	private static final int TENTATIVO_BASSO = -1;
	private static final int TENTATIVO_ALTO = 1;
	
	private boolean gameOn = false;
	private int numeroDaIndovinare;

	private IntegerProperty limiteInferiore;
	private IntegerProperty limiteSuperiore;
	private int numeroProvato;
	private int tentativoNumero = 0;
	
	public GiocoModel() {
		gameOn = false;
		
		//costruzione delle Property
		limiteInferiore = new SimpleIntegerProperty();
		limiteSuperiore = new SimpleIntegerProperty();
	}
	
	/**
	 * Avvia nuova partita, inizializzando il numero da indovinare
	 */
	public void newGame() {
		gameOn = true;
		limiteInferiore.set(N_MIN);
		limiteSuperiore.set(N_MAX);
		tentativoNumero = 0;
		
		//Genera il numeroDaIndovinare
    	numeroDaIndovinare = (int)(Math.random()*N_MAX) + 1;
	}
	
	/**
	 * gestisce il singolo tentativo del giocatore
	 * 
	 * @param tentativoUtente numero immesso dal giocatore
	 * @return -1, 0, +1 a seconda che il tentativoUtente sia troppo basso, esatto, troppo alto
	 */
	public int tentativo(int tentativoUtente) {
		numeroProvato = tentativoUtente;
		//controllo se la partita è in corso
		if(!gameOn) {
			throw new IllegalStateException("La partita è terminata.");
		}
		
		//controllo che il numero si in intervallo [limiteInf, limiteSup]
    	if(numeroProvato < limiteInferiore.get() || numeroProvato > limiteSuperiore.get()) {
    		
    		throw new InvalidParameterException(String.format("Il valore inserito è " +
    		"fuori dal range [%d, %d].\n", limiteInferiore, limiteSuperiore));
    	}
    	//gestisce il tentativo
    	tentativoNumero++;
    	
    	if(tentativoNumero == N_TENTATIVI) {
    		gameOn = false;
    	}
    	
    	//se il numero provato è corretto lo comunico vittoria e esco dal gioco.
    	if(numeroDaIndovinare == numeroProvato) {
    		gameOn = false;
    		return TENTATIVO_RIUSCITO;
    	}
    	
    	//se il numero è basso lo comunico e pongo limiteInreriore = numeroProvato
    	//altrimenti comunico che è alto e pongo limiteSuperiore = numeroProvato.
    	if(numeroProvato < numeroDaIndovinare) {
    		limiteInferiore.set(numeroProvato +1);
    		return TENTATIVO_BASSO;
    	} else {
    		limiteSuperiore.set(numeroProvato - 1);
    		return TENTATIVO_ALTO;
    	}
    	
	}

	public int getNumeroDaIndovinare() {
		return numeroDaIndovinare;
	}

	public int getNumeroProvato() {
		return numeroProvato;
	}

	public int getTentativoNumero() {
		return tentativoNumero;
	}

	public int getnTentativi() {
		return N_TENTATIVI;
	}

	public final IntegerProperty limiteInferioreProperty() {
		return this.limiteInferiore;
	}
	

	public final int getLimiteInferiore() {
		return this.limiteInferioreProperty().get();
	}
	

	public final void setLimiteInferiore(final int limiteInferiore) {
		this.limiteInferioreProperty().set(limiteInferiore);
	}
	

	public final IntegerProperty limiteSuperioreProperty() {
		return this.limiteSuperiore;
	}
	

	public final int getLimiteSuperiore() {
		return this.limiteSuperioreProperty().get();
	}
	

	public final void setLimiteSuperiore(final int limiteSuperiore) {
		this.limiteSuperioreProperty().set(limiteSuperiore);
	}

	public boolean isGameOn() {
		return gameOn;
	}

}
