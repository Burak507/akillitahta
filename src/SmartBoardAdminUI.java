import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SmartBoardAdminUI extends Application {

    private Pane createHomePane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Sınıflar ve tahtaların genel durumu buradan görüntülenebilir."));
        return pane;
    }

    private Pane createSchoolPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Okul bilgilerini buraya giriniz."));
        return pane;
    }

    private Pane createNoticePane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Duyurular ve haberler buradan yönetilir."));
        return pane;
    }

    private Pane createSettingsPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Program ayarlarını buradan değiştirin."));
        return pane;
    }

    private Pane createToolsPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Akıllı tahtaları kontrol araçları."));
        return pane;
    }

    private Pane createSetupPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Kurulum işlemleri buradan yapılır."));
        return pane;
    }

    private Pane createAboutPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(10);
        pane.setPadding(new Insets(20));
        pane.getChildren().add(new Label("Yazılım hakkında bilgiler burada gösterilir."));
        return pane;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Akıllı Tahta Yönetim Yazılımı - Yönetici Paneli");

        // Menü sekmeleri
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

                Tab tabHome = new Tab("🏠 Ana Sayfa", createHomePane());
Tab tabSchool = new Tab("🏫 Kurum Bilgileri", createSchoolPane());
Tab tabNotices = new Tab("📢 Duyurular", createNoticePane());
Tab tabSettings = new Tab("⚙️ Ayarlar", createSettingsPane());
Tab tabTools = new Tab("🛠️ Araçlar", createToolsPane());
Tab tabSetup = new Tab("🔧 Kurulum", createSetupPane());
Tab tabAbout = new Tab("ℹ️ Hakkında", createAboutPane());

tabPane.getTabs().addAll(tabHome, tabSchool, tabNotices, tabSettings, tabTools, tabSetup, tabAbout);

        BorderPane root = new BorderPane();
        
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        // Tema geçiş butonu
        Button toggleThemeButton = new Button("🌗 Tema Değiştir");
        toggleThemeButton.setOnAction(_ -> {
            String currentStyle = tabPane.getStyle();
            boolean isDark = currentStyle.contains("#2c3e50");

            String tabPaneStyle = isDark
                ? "-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-tab-min-width: 100px; -fx-tab-max-width: 200px;"
                : "-fx-background-color: #2c3e50; -fx-border-color: #2980b9; -fx-tab-min-width: 100px; -fx-tab-max-width: 200px;";
            tabPane.setStyle(tabPaneStyle);
            VBox topBar = new VBox(toggleThemeButton, tabPane);
            root.setTop(topBar);
            topBar.setStyle(isDark
                ? "-fx-background-color: #fefefe;"
                : "-fx-background-color: #1e272e;");

            toggleThemeButton.setStyle(isDark
                ? "-fx-background-color: #ffffff; -fx-text-fill: black;"
                : "-fx-background-color: #34495e; -fx-text-fill: white;");

            for (Tab tab : tabPane.getTabs()) {
                if (tab.getContent() instanceof VBox pane) {
                    pane.setStyle(isDark
                        ? "-fx-background-color: #ffffff; -fx-text-fill: black; padding: 15px;"
                        : "-fx-background-color: #2c3e50; -fx-text-fill: white; padding: 15px;");

                    for (javafx.scene.Node node : pane.getChildren()) {
                        if (node instanceof Button btn) {
                            btn.setStyle(isDark
                                ? "-fx-background-color: #ffffff; -fx-text-fill: black;"
                                : "-fx-background-color: #34495e; -fx-text-fill: white;");
                        } else if (node instanceof TextField tf) {
                            tf.setStyle(isDark
                                ? "-fx-background-color: #ffffff; -fx-text-fill: black;"
                                : "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-border-color: white;");
                        } else if (node instanceof Label lbl) {
                            lbl.setStyle(isDark
                                ? "-fx-text-fill: black;"
                                : "-fx-text-fill: white;");
                        }
                    }
                }
            }
        });
        VBox topBar = new VBox(toggleThemeButton, tabPane);
        root.setTop(topBar);
        primaryStage.show();
    }

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
