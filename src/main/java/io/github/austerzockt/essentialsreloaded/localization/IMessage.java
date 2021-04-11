package io.github.austerzockt.essentialsreloaded.localization;

public interface IMessage {
    String build();

    IMessage placeholder(String placeholder, String value);

}
