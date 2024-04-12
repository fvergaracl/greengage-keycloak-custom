package com.greengage.spi.authentication;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.OTPFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;


public class AndroidLoginForm extends OTPFormAuthenticator {

    private static final String FIRST_LOGIN_GREENGAGE = "first-login-greegage.ftl";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String[] attributesToCheck = {
            "ageRange", "digitalToolsAcquaintance", "disadvantagedGroup",
            "educationLevel", "gender", "organizationType",
            "thematicRole", "workStatus", "zipCode"
        };
        
        boolean allAttributesPresent = true;
        for (String attribute : attributesToCheck) {
            if (user.getFirstAttribute(attribute) == null) {
                allAttributesPresent = false;
                break;
            }
        }
        
        if (!allAttributesPresent) {
            // Redirect to register.ftl
            context.challenge(context.form().createForm(FIRST_LOGIN_GREENGAGE));
            // Continue the flow
            context.success();
        }
    }
    

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }

}