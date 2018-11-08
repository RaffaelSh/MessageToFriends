package com.snowcase.rundmail.Rundmail;

import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;

import net.dv8tion.jda.client.JDAClient;
import net.dv8tion.jda.client.entities.Friend;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	private JDABuilder builder;
	private JDA jda;

	public static void main(String[] args) {
		try {
			new Main().init();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void init() throws LoginException, InterruptedException {
		this.builder = new JDABuilder(AccountType.CLIENT);
		String token = JOptionPane.showInputDialog("Token needed.");
		this.builder.setToken(token);
		
		this.jda = builder.build().awaitReady();
		JDAClient client = jda.asClient();
		for (Friend friend : client.getFriends()) {
			friend.getUser().openPrivateChannel().queue((channel) -> {
				String s = JOptionPane.showInputDialog("What do you want to send to your friends?");
				channel.sendMessage(s).queue();
				System.out.println("Successfully send messages!");
			});
		}
		System.out.println("Successfully loaded.");
		System.exit(0);
	}

}