package javaFX2webView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {

	@FXML
	private Button btnBack;
	@FXML
	private Button btnForward;
	@FXML
	private Button btnReload;
	@FXML
	private TextField txfURL;

	@FXML
	private Label lblWebStatus;

	@FXML
	private WebView wbv;

	private WebEngine engine;

	@FXML
	void initialize() {
		assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'WebView.fxml'.";
		assert btnForward != null : "fx:id=\"btnForward\" was not injected: check your FXML file 'WebView.fxml'.";
		assert btnReload != null : "fx:id=\"btnReload\" was not injected: check your FXML file 'WebView.fxml'.";
		assert txfURL != null : "fx:id=\"txfURL\" was not injected: check your FXML file 'WebView.fxml'.";
		assert lblWebStatus != null : "fx:id=\"lblWebStatus\" was not injected: check your FXML file 'WebView.fxml'.";
		assert wbv != null : "fx:id=\"wbv\" was not injected: check your FXML file 'WebView.fxml'.";

		this.btnBack.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("res/leftArrow.png"))));
		this.btnForward.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("res/rightArrow.png"))));
		this.btnReload.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("res/reload.png"))));

		engine = wbv.getEngine();
		wbv.setPrefSize(800, 400);

		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> obsValue, Worker.State oldStatus,
					Worker.State newStatus) {

				if (newStatus.equals(Worker.State.SUCCEEDED)) {
					txfURL.setText(engine.getLocation());
					txfURL.positionCaret(txfURL.getText().length());
				}
				lblWebStatus.setText(newStatus.toString());
			}
		});

		txfURL.setText("http://javafx.com");
		engine.load(txfURL.getText());
	}

	@FXML
	void txfURLKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			engine.load(txfURL.getText());
		}
	}

	@FXML
	void btnBackOnAction(ActionEvent e) {
		if (engine.getHistory().getCurrentIndex() > 0) {
			engine.getHistory().go(-1);
		}
	}

	@FXML
	void btnForwardOnAction(ActionEvent e) {
		if ((engine.getHistory().getEntries().size() - 1) > engine.getHistory().getCurrentIndex()) {
			engine.getHistory().go(1);
		}
	}

	@FXML
	void btnReloadOnAction(ActionEvent e) {
		engine.reload();
	}
}
