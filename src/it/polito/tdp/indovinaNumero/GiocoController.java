package it.polito.tdp.indovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.indovinaNumero.model.GiocoModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class GiocoController {

	private static final int TENTATIVO_RIUSCITO = 0;
	private static final int TENTATIVO_BASSO = -1;
	private static final int TENTATIVO_ALTO = 1;

	private GiocoModel model;

	public void setModel(GiocoModel model) {
		this.model = model;

		txtMinValue.textProperty().bind(Bindings.convert(model.limiteInferioreProperty()));
		txtMaxValue.textProperty().bind(Bindings.convert(model.limiteSuperioreProperty()));
	}

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

		model.newGame();

		boxGioca.setDisable(model.isGameOn());
		boxInserisciNumero.setDisable(!model.isGameOn());
		txtInserisciNumero.clear();

		txtTentativiRimasti.setText(Integer.toString(model.getnTentativi()));
		txtRisultati.setText("Gioco iniziato.\n");

	}

	@FXML
	void handleOk(ActionEvent event) {
		int numeroProvato;

		// controllo che quanto inserito sia un numero

		try {
			numeroProvato = Integer.parseInt(txtInserisciNumero.getText().trim());

		} catch (NumberFormatException e) {
			txtRisultati.appendText("Non è stato inserito un numero valido.\n");
			txtInserisciNumero.clear();
			return;
		}

		try {
			int risultato = model.tentativo(numeroProvato);
			if (risultato == TENTATIVO_RIUSCITO) {
				txtRisultati.appendText("Hai vinto! Hai indovinato in " + model.getTentativoNumero() + "tentativi.\n");
				quitGame();
			} else if (risultato == TENTATIVO_ALTO) {
				txtRisultati.appendText("Il numero provato è più grande del numero segreto.\n");
				txtInserisciNumero.clear();
			} else {
				txtRisultati.appendText("Il numero provato è più piccolo del numero segreto.\n");
				txtInserisciNumero.clear();
			}
			// aggiorno txtTentativiRimasti
			txtTentativiRimasti.setText(Integer.toString(model.getnTentativi() - model.getTentativoNumero()));

			if (!model.isGameOn()) {
				// la partita è finita
				if (risultato != TENTATIVO_RIUSCITO) {
					txtRisultati.appendText("Hai perso!\n");
					txtRisultati.appendText(
							String.format("Il numero da indovinare era: %d.\n", model.getNumeroDaIndovinare()));
					quitGame();
				}
			}

		} catch (InvalidParameterException e) {
			txtRisultati.appendText(e.getMessage());
			txtInserisciNumero.clear();
			return;
		} catch (IllegalStateException e) {
			txtRisultati.appendText(
					e.getMessage() + String.format(" Il numero segreto era: %d.\n", model.getNumeroDaIndovinare()));
			quitGame();
			return;
		}

	}

	private void quitGame() {
		boxGioca.setDisable(model.isGameOn());
		boxInserisciNumero.setDisable(!model.isGameOn());

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
