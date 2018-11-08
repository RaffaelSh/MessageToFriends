package com.snowcase.rundmail.Rundmail;

import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;

import net.dv8tion.jda.client.JDAClient;
import net.dv8tion.jda.client.entities.Friend;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

public class Main {

	public static void main(String[] args) {
		try {
			new Main().init();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws LoginException, InterruptedException {
		JDABuilder builder = new JDABuilder(AccountType.CLIENT);
		String token = JOptionPane.showInputDialog("Insert token!");
		builder.setToken(token);

		JDA jda = builder.build().awaitReady();
		JDAClient client = jda.asClient();
		
		String s = JOptionPane.showInputDialog("What do you want to send to your friends?");
		
		client.getFriends().stream().map(Friend::getUser).map(User::openPrivateChannel).forEach(restAction -> restAction.queue(channel -> channel.sendMessage(s).complete()));
		
		System.exit(0);
	}

}
