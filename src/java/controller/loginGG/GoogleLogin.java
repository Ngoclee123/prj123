package controller.loginGG;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constant.Iconstant;
import model.account.Account;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class GoogleLogin {
    public static String getToken(String code) throws IOException {
        String response = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", Iconstant.GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").getAsString();
    }

    public static Account getUserInfo(String accessToken) throws IOException {
        String link = Iconstant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        System.out.println(response);
        Account account = new Account();
        account.setEmail(json.get("email").getAsString());
        account.setUsername(json.get("email").getAsString().split("@")[0]);
        if (json.has("name")) {
            account.setFullName(json.get("name").getAsString());
        } else {
            account.setFullName(account.getUsername());
        }
        System.out.println("Google User Info - Email: " + account.getEmail() + ", Username: " + account.getUsername() + ", FullName: " + account.getFullName());
        return account;
    }
    
}