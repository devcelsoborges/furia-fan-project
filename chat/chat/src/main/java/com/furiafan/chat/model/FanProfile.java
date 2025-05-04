package com.furiafan.chat.model;

import java.util.regex.Pattern;

public class FanProfile {

    private String name;
    private String cpf;
    private String address;
    private String interests;
    private String eventsAndPurchases;
    private String socialLinks;

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");

    public FanProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null && CPF_PATTERN.matcher(cpf.replaceAll("[^\\d]", "")).matches()) {
            this.cpf = cpf.replaceAll("[^\\d]", "");
        } else {
            this.cpf = "CPF inválido";
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests != null ? interests.trim() : null;
    }

    public String getEventsAndPurchases() {
        return eventsAndPurchases;
    }

    public void setEventsAndPurchases(String eventsAndPurchases) {
        this.eventsAndPurchases = eventsAndPurchases != null ? eventsAndPurchases.trim() : null;
    }

    public String getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(String socialLinks) {
        if (socialLinks != null && URL_PATTERN.matcher(socialLinks.trim()).matches()) {
            this.socialLinks = socialLinks.trim();
        } else {
            this.socialLinks = "Link inválido";
        }
    }

    @Override
    public String toString() {
        return "FanProfile{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", address='" + address + '\'' +
                ", interests='" + interests + '\'' +
                ", eventsAndPurchases='" + eventsAndPurchases + '\'' +
                ", socialLinks='" + socialLinks + '\'' +
                '}';
    }
}
