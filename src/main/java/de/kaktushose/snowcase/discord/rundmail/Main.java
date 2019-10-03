package de.kaktushose.snowcase.discord.rundmail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.dv8tion.jda.client.JDAClient;
import net.dv8tion.jda.client.entities.Friend;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.User;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {

    public TextArea log;
    public TextField token, message;
    private JDA jda;
    private JDAClient client;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("mainwindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        primaryStage.setTitle("MessageToFriends");
        primaryStage.setScene(new Scene(root, 600, 249));
        primaryStage.setResizable(true);

        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if (jda != null) {
                jda.shutdown();
            }
            primaryStage.close();
        });

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            if (log != null) {
                log.setText(e.toString());
            }
        });

    }

    public void loginAccount() {
        JDABuilder builder = new JDABuilder(AccountType.CLIENT);
        builder.setToken(token.getText());
        try {
            jda = builder.build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            logException(e);
        }
        log.setText("successfully logged in as " + jda.getSelfUser().getName() + "#" +  jda.getSelfUser().getDiscriminator());
        System.out.println("called 2");
        client = jda.asClient();
    }

    public void sendMessage() {
        client.getFriends().stream().map(Friend::getUser).map(User::openPrivateChannel).forEach(restAction -> restAction.queue(channel -> channel.sendMessage(message.getText()).complete()));
        log.setText("successfully sent message \"" + message.getText() + "\" to all friends");
    }

    private void logException(Exception e) {
        log.setText(e.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
