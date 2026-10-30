package view;

import java.util.Scanner;

public class AuthVue extends BaseVue {

    protected AuthVue(Scanner scan) {
        super(scan);
    }

    public String saisirLogin(){
        return saisieString("Saisir login : ");
    }

    public String saisirMdp(){
        return saisieString("Sasir mot de passe : ");
    }

    @Override
    public void afficherSucces(String msg) {
        super.afficherSucces(msg);
    }
}
