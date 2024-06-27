package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;

import dataAccess.DAOUsers;

@WebServlet(name="mainServlet", urlPatterns={"/login", "/register"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOUsers daoUsers = new DAOUsers();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath().substring(1); // Remove the leading slash
		String erro = request.getParameter("erro");
		String registro = request.getParameter("registro");
		request.setAttribute("action", action);
		request.setAttribute("erro", erro);
		request.setAttribute("registro", registro);
		
		getServletContext().getRequestDispatcher("/view/auth/loginRegister.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath().substring(1);
		if (action.equals("login")) {
			handleLogin(request, response);
		} else if (action.equals("register")) {
			handleRegister(request, response);
		}
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = daoUsers.loga(email, password);
		
		if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            response.sendRedirect("home");
        } else {
            response.sendRedirect("login?erro=true");
        }
	}

	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean success = daoUsers.registra(name, email, password);

        if (success) {
            response.sendRedirect("login?registro=true");
        } else {
            response.sendRedirect("register?erro=true");
        }
    }
}
