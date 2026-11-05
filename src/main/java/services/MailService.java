package services;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MailService {
    // constantes pour les chemins de fichiers
    private static final String PROPERTIES_FILE = "mail.properties";
    private static final String JSON_FILE = "mail.json";

    // constantes expéditeurs
    private static final String EMAIL_EXPEDITEUR = "contact@greta-bretagne-sud.org";
    private static final String EMAIL_EXPEDITEUR_NOM = "Application flotte de véhicules de service";
    private static final String EMAIL_DESTINATAIRE_NOM = ".*.*.*";

    // attributs chargés dynamiquement
    private String apiKey;
    private String emailSujet;
    private String emailDestinataire;
    private String emailCorps;

    public MailService() {
        try {
            chargerConfiguration();
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'initialiser le service de mail");
        }
    }

    public void chargerConfiguration() throws IOException {
        ClassLoader classLoader = MailService.class.getClassLoader();
        Properties props = new Properties();

        try (InputStream in = classLoader.getResourceAsStream(PROPERTIES_FILE)) {
            if (in == null) {
                throw new IOException(PROPERTIES_FILE + " introuvable");
            }
            props.load(in);
        }

        this.apiKey = props.getProperty("apiBrevo");
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(this.apiKey);

        try (InputStream in = classLoader.getResourceAsStream(JSON_FILE)) {
            if (in == null) {
                throw new IOException(JSON_FILE + " introuvable");
            }
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> donnees = mapper.readValue(in, new TypeReference<Map<String, Object>>() {
            });

            this.emailDestinataire = (String) donnees.get("destinataire");
            this.emailSujet = (String) donnees.get("sujet");
            this.emailCorps = (String) donnees.get("corps");
        }
    }

    public String envoyer(String cheminFichier) throws ApiException, IOException {
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        // CORRECTION : On instancie l'email builder tout de suite au début
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        // config expéditeur
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(EMAIL_EXPEDITEUR);
        sender.setName(EMAIL_EXPEDITEUR_NOM);

        System.out.println(emailDestinataire);

        // config destinataire
        List<SendSmtpEmailTo> toList = new ArrayList<>();
        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(this.emailDestinataire);
        to.setName(EMAIL_DESTINATAIRE_NOM);
        toList.add(to);

        // pièce jointe
        if (cheminFichier != null && !cheminFichier.trim().isEmpty()) {
            SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();

            // Extraction du nom du fichier seul
            String cheminFichierSansDossier = Paths.get(cheminFichier).getFileName().toString();
            attachment.setName(cheminFichierSansDossier);

            // Conversion
            byte[] encode = Files.readAllBytes(Paths.get(cheminFichier));
            attachment.setContent(encode);

            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<>();
            attachmentList.add(attachment);

            // CORRECTION : sendSmtpEmail existe maintenant, on peut lui ajouter la liste
            sendSmtpEmail.setAttachment(attachmentList);
        }

        // entête et paramètres
        java.util.Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("Some-Custom-Name", "unique-id-1234");

        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("subject", emailSujet);

        // Remplissage final de l'email
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(toList);
        sendSmtpEmail.setHeaders(headers);
        sendSmtpEmail.setParams(params);
        sendSmtpEmail.setHtmlContent(emailCorps);
        sendSmtpEmail.setSubject("{{params.subject}}");

        CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
        return response.toString();

    }
}
