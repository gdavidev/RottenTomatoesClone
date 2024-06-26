package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="mainServlet", urlPatterns={"/login", "/register"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath().substring(1); // Remove the leading slash
		request.setAttribute("action", action);
		getServletContext().getRequestDispatcher("/view/auth/loginRegister.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/login")) {
			handleLogin(request, response);
		} else if (action.equals("/register")) {
			handleRegister(request, response);
		}
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// Adicione a lógica de autenticação aqui
		response.getWriter().append("Login feito com sucesso para o email: ").append(email);
	}

	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// Adicione a lógica de registro aqui
		response.getWriter().append("Registro feito com sucesso para o usuário: ").append(name);
	}
}
