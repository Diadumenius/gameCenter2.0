package de.diadumenius.gamecenter.util;

// this class was created by Diadumenius
public enum Role {

    STARTER("Starter"),
    VIP("VIP"),
    OP("Admin");

    String translation;

    private Role(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
