package services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);
    private ObjectMapper mapper = new ObjectMapper();
    private AuthService auth = mapper.readValue(new File("src/main/resources/user.json"), AuthService.class); //pour récuperer login/mdp du fichier
    private String loginUser = auth.getLoginUser();
    private String  hashPass = auth.getHashPass();


    public AuthService() {
    }

    public boolean verifPass(String pass){
        try {
            BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(),hashPass);
            return result.verified;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean verifLogin(String login){
        return loginUser.equals(login);
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }






}
