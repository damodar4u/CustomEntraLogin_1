package com.example.controller;

import com.example.util.MsalUtils;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CallbackServlet extends HttpServlet {

    private static final String REDIRECT_URI = "http://localhost:8080/CustomEntraLogin/callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.getWriter().write("Authorization code not found");
            return;
        }

        try {
            // Use MsalUtils to acquire tokens
            IAuthenticationResult result = MsalUtils.acquireTokenByAuthCode(code, REDIRECT_URI);

            // Store tokens in session
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

