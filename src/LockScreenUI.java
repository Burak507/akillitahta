import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;

public class LockScreenUI extends Application {

    private Label clockLabel;
    private String currentVerificationCode = "";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Akıllı Tahta - Kilit Ekranı");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px;");

        try {
            Image logo = new Image(new FileInputStream("logo.png"));
            ImageView logoView = new ImageView(logo);
            logoView.setFitWidth(120);
            logoView.setPreserveRatio(true);
            root.getChildren().add(logoView);
        } catch (FileNotFoundException e) {
            System.out.println("Logo dosyası bulunamadı.");
        }

        Label title = new Label("Akıllı Tahta Kilitli");
        title.setFont(Font.font(24));
        title.setStyle("-fx-text-fill: white;");

        Label announcementLabel = new Label("📢 Duyurular ve Haberler");
        announcementLabel.setFont(Font.font(18));
        announcementLabel.setStyle("-fx-text-fill: white;");

        TextArea announcements = new TextArea();
        announcements.setEditable(false);
        announcements.setPrefHeight(150);
        announcements.setWrapText(true);
        announcements.setText("Duyurular yükleniyor...");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest duyuruRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/duyuru"))
            .build();

        client.sendAsync(duyuruRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(body -> announcements.setText("Güncel Duyurular:\n" + body));

        clockLabel = new Label();
        clockLabel.setFont(Font.font(16));
        clockLabel.setStyle("-fx-text-fill: white;");
        startClock();

        Label enterCode = new Label("Kilit Açmak İçin Anahtar Kodunu Girin:");
        enterCode.setStyle("-fx-text-fill: white;");

        PasswordField codeField = new PasswordField();
        Button unlockButton = new Button("Kilidi Aç");

        unlockButton.setOnAction(_ -> {
            String code = codeField.getText();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/kilit/kontrol?kod=" + code))
                .GET()
                .build();

            try {
                HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 && response.body().equals("ACILDI")) {
                    new Alert(Alert.AlertType.INFORMATION, "Kilit Açıldı!").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Geçersiz Kod!").showAndWait();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Sunucuya erişilemedi!").showAndWait();
            }
        });

        
        ImageView qrView = new ImageView();
        try {
            HttpRequest qrRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/kilit/qr"))
                .GET()
                .build();

            HttpResponse<String> qrResponse = client.send(qrRequest, HttpResponse.BodyHandlers.ofString());
            currentVerificationCode = qrResponse.body();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(currentVerificationCode, BarcodeFormat.QR_CODE, 150, 150);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (Exception ex) {
            System.out.println("QR kod oluşturulamadı: " + ex.getMessage());
        }

        root.getChildren().addAll(title, announcementLabel, announcements, clockLabel, qrView, enterCode, codeField, unlockButton);

        Scene scene = new Scene(root, 600, 750);
        stage.setScene(scene);
        stage.show();
    }

    private void startClock() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalTime time = LocalTime.now();
                String timeString = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                javafx.application.Platform.runLater(() -> clockLabel.setText(timeString));
            }
        }, 0, 1000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
