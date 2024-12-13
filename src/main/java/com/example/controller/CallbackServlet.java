package com.example.controller;

import com.microsoft.aad.msal4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class CallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "your-client-id";
    private static final String CLIENT_SECRET = "your-client-secret";
    private static final String TENANT_ID = "your-tenant-id";
    private static final String REDIRECT_URI = "http://localhost:8080/CustomEntraLogin/callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the authorization code from the request
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if (code == null || code.isEmpty()) {
            response.getWriter().write("Authorization code not found");
            return;
        }

        try {
            // Create a ConfidentialClientApplication
            ConfidentialClientApplication app = ConfidentialClientApplication.builder(
                    CLIENT_ID,
                    ClientCredentialFactory.createFromSecret(CLIENT_SECRET))
                    .authority("https://login.microsoftonline.com/" + TENANT_ID)
                    .build();

            // Build the token acquisition parameters
            AuthorizationCodeParameters parameters = AuthorizationCodeParameters.builder(
                    code,
                    new URI(REDIRECT_URI))
                    .scopes(Collections.singleton("https://graph.microsoft.com/.default"))
                    .build();

            // Acquire the token
            IAuthenticationResult result = app.acquireToken(parameters).join();

            // Save the tokens in the session
            request.getSession().setAttribute("accessToken", result.accessToken());
            request.getSession().setAttribute("idToken", result.idToken());

            // Respond to the user
            response.getWriter().write("Login successful! Access Token: " + result.accessToken());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error during token exchange: " + e.getMessage());
        }
    }
}

