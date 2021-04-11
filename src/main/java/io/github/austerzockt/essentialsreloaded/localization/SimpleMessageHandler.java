package io.github.austerzockt.essentialsreloaded.localization;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.ChatColor;

public class SimpleMessageHandler implements IMessageHandler {
    private final String languageCode;
    private final EssentialsReloaded essentialsReloaded;

    public SimpleMessageHandler(EssentialsReloaded essentialsReloaded, String languageCode) {
        this.essentialsReloaded = essentialsReloaded;
        if (isValidLanguage(languageCode)) {
            this.languageCode = languageCode;
            essentialsReloaded.configHandler().registerConfig("messages_" + this.languageCode);
        } else {
            throw new IllegalArgumentException("The Chosen Language is not supported by the Plugin!");
        }


    }

    @Override
    public IMessage getMessage(String messageId) {
        return new SimpleMessage(essentialsReloaded.configHandler().getConfig("messages_" + this.languageCode).getString(messageId));
    }

    private boolean isValidLanguage(String languagecode) {
        return essentialsReloaded.getResource("messages_" + languagecode + ".yml") != null;
    }

    class SimpleMessage implements IMessage {
        private String message;

        public SimpleMessage(String message) {
            this.message = message;
        }

        @Override
        public IMessage placeholder(String placeholder, String value) {
            this.message = message.replaceAll("\\{" + placeholder + "}", value);
            return this;
        }

        @Override
        public String build() {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

}

