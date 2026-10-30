package controllers;

import services.AuthService;
import view.AuthVue;

public class AuthController {
    private final AuthService authService;
    private final AuthVue vue;


    public AuthController(AuthService authService, AuthVue vue) {
        this.authService = authService;
        this.vue = vue;
    }

    public void verifAuth(){
        String login = vue.saisieString("Saisir le login : ");
        String pass = vue.saisieString("Saisir le mot de passe : ");
        int compteur = 0;

        while (compteur != 3){

            if(authService.verifLogin(login) && authService.verifPass(pass)){
                vue.afficherSucces("Authentification réussie");
                break;
            }else {
                compteur++;
                vue.afficherErreur("Login ou mot de passe incorrect. Il vous reste " + (compteur - 1) + " tentative(s)");
            }
        }
        System.exit(-1);

    }




}





