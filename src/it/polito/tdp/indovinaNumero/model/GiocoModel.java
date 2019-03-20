package it.polito.tdp.indovinaNumero.model;

import java.nio.channels.IllegalSelectorException;
import java.security.InvalidParameterException;

public class GiocoModel {
	
	private static final int N_MIN = 1;
	private static final int N_MAX = 100;
	private static final int N_TENTATIVI = 7;
	private static final int TENTATIVO_RIUSCITO = 0;
	private static final int TENTATIVO_BASSO = -1;
	private static final int TENTATIVO_ALTO = 1;
	
	private boolean gameOn = false;
	private int numeroDaIndovinare;
	private int limiteInferiore = N_MIN;
	private int limiteSuperiore = N_MAX;
	private int numeroProvato;
	private int tentativoNumero = 0;
	
	public GiocoModel() {
		gameOn = false;
	}
	
	/**
	 * Avvia nuova partita, inizializzando il numero da indovinare
	 */
	public void newGame() {
		gameOn = true;
		limiteInferiore = N_MIN;
		limiteSuperiore = N_MAX;
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
    	if(numeroProvato < limiteInferiore || numeroProvato > limiteSuperiore) {
    		
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
    		return TENTATIVO_RIUSCITO;
    	}
    	
    	//se il numero è basso lo comunico e pongo limiteInreriore = numeroProvato
    	//altrimenti comunico che è alto e pongo limiteSuperiore = numeroProvato.
    	if(numeroProvato < numeroDaIndovinare) {
    		limiteInferiore = numeroProvato +1;
    		return TENTATIVO_BASSO;
    	} else {
    		limiteSuperiore = numeroProvato - 1;
    		return TENTATIVO_ALTO;
    	}
    	
	}

	public boolean isGameOn() {
		return gameOn;
	}

	public int getNumeroDaIndovinare() {
		return numeroDaIndovinare;
	}

	public int getLimiteInferiore() {
		return limiteInferiore;
	}

	public int getLimiteSuperiore() {
		return limiteSuperiore;
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

}
