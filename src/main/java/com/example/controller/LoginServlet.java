package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // Constants for Azure AD configuration
    private static final String CLIENT_ID = "your-client-id";
    private static final String TENANT_ID = "your-tenant-id";
    private static final String REDIRECT_URI = "http://localhost:8080/CustomEntraLogin/callback";
    private static final String AUTHORIZATION_ENDPOINT = "https://login.microsoftonline.com/" + TENANT_ID + "/oauth2/v2.0/authorize";
    private static final String SCOPE = "openid profile User.Read";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Build the authorization URL
        String state = "12345"; // Replace with a secure random value in production
        String authorizationUrl = AUTHORIZATION_ENDPOINT + "?" +
                "client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_mode=query" +
                "&scope=" + SCOPE +
                "&state=" + state;

        // Redirect the user to Azure AD for login
        response.sendRedirect(authorizationUrl);
    }
}

