package it.polito.tdp.indovinaNumero;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class GiocoController {
	
	private static final int N_MIN = 1;
	private static final int N_MAX = 100;
	private static final int N_TENTATIVI = 7;
	
	private boolean isGameOn = false;
	private int numeroDaIndovinare;
	private int limiteInferiore = N_MIN;
	private int limiteSuperiore = N_MAX;
	private int numeroProvato;
	private int tentativoNumero = 0;
	
	@FXML
    private HBox boxGioca;

    @FXML
    private HBox boxInserisciNumero;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtInserisciNumero;

    @FXML
    private TextField txtMinValue;

    @FXML
    private TextField txtMaxValue;
    
    @FXML
    private TextArea txtRisultati;

    @FXML
    private TextField txtTentativiRimasti;

    @FXML
    void handleGioca(ActionEvent event) {
    	isGameOn = true;
    	boxGioca.setDisable(isGameOn);
    	boxInserisciNumero.setDisable(!isGameOn);
    	txtInserisciNumero.clear();
    	limiteInferiore = N_MIN;
    	limiteSuperiore = N_MAX;
    	txtMinValue.setText(Integer.toString(N_MIN));
    	txtMaxValue.setText(Integer.toString(N_MAX));
    	txtRisultati.clear();
    	txtRisultati.appendText("Gioco iniziato.\n");
    	tentativoNumero = 0;
    	txtTentativiRimasti.setText(Integer.toString(N_TENTATIVI));
    	
    	//Genera il numeroDaIndovinare
    	numeroDaIndovinare = (int)(Math.random()*N_MAX) + 1;

    }
    
    @FXML
    void handleOk(ActionEvent event) {
    	
    	//controllo che quanto inserito sia un numero
    	try {
    		numeroProvato = Integer.parseInt(txtInserisciNumero.getText().trim());
    	} catch(NumberFormatException e){
    		txtRisultati.appendText("Non è stato inserito un numero valido.\n");
    		txtInserisciNumero.clear();
    		return;
    	}
    	
    	//controllo che il numero si in intervallo [limiteInf, limiteSup]
    	if(numeroProvato < limiteInferiore || numeroProvato > limiteSuperiore) {
    		txtRisultati.appendText("Numero fuori dall'intervallo corrente.\n");
    		txtInserisciNumero.clear();
    		return;
    	}
    	
    	// aggiorno txtTentativiRimasti
    	tentativoNumero++;
    	txtTentativiRimasti.setText(Integer.toString(N_TENTATIVI - tentativoNumero));
    	
    	//se il numero provato è corretto lo comunico vittoria e esco dal gioco.
    	if(numeroDaIndovinare == numeroProvato) {
    		txtRisultati.appendText("Complimenti. HAI VINTO! Hai usato " + tentativoNumero + " tentativi.\n");
    		quitGame();
    		return;
    	}
    	
    	//se il numero è basso lo comunico e pongo limiteInreriore = numeroProvato
    	//altrimenti comunico che è alto e pongo limiteSuperiore = numeroProvato.
    	if(numeroProvato < numeroDaIndovinare) {
    		txtRisultati.appendText("Il numero provato è più piccolo del numero segreto.\n");
    		limiteInferiore = numeroProvato;
    		txtMinValue.setText(Integer.toString(numeroProvato + 1));
    		txtInserisciNumero.clear();
    	} else {
    		txtRisultati.appendText("Il numero provato è più grande del numero segreto.\n");
    		limiteSuperiore = numeroProvato;
    		txtMaxValue.setText(Integer.toString(numeroProvato - 1));
    		txtInserisciNumero.clear();
    	}
    	
    	//testo se il numero di tentativi == N_TENTIVI. Se si esco dal gioco.
    	if(tentativoNumero == N_TENTATIVI) {
    		txtRisultati.appendText("Tentativi esauriti. HAI PERSO! Il numero da indovinare era: " + 
    	numeroDaIndovinare + "\n");
    		quitGame();
    		return;
    	}
    	

    }

    private void quitGame() {
		isGameOn = false;
		boxGioca.setDisable(isGameOn);
		boxInserisciNumero.setDisable(!isGameOn);
		
	}

	@FXML
    void initialize() {
    	assert boxGioca != null : "fx:id=\"boxGioca\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert boxInserisciNumero != null : "fx:id=\"boxInserisciNumero\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert txtInserisciNumero != null : "fx:id=\"txtInserisciNumero\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert txtMinValue != null : "fx:id=\"txtMinValue\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert txtMaxValue != null : "fx:id=\"txtMaxValue\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert txtRisultati != null : "fx:id=\"txtRisultati\" was not injected: check your FXML file 'Gioco.fxml'.";
        assert txtTentativiRimasti != null : "fx:id=\"txtTentativiRimasti\" was not injected: check your FXML file 'Gioco.fxml'.";

    }
}

