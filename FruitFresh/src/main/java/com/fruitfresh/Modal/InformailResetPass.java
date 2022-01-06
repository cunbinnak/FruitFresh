package com.fruitfresh.Modal;

public class InformailResetPass {
    private String name;
    private String resetLink;

    public InformailResetPass() {
    }

    public InformailResetPass(String name, String resetLink) {
        this.name = name;
        this.resetLink = resetLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResetLink() {
        return resetLink;
    }

    public void setResetLink(String resetLink) {
        this.resetLink = resetLink;
    }
}
