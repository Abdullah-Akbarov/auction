package controller;

import com.google.gson.Gson;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.Message;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getUserService();
    private static UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Message message = userService.KeyGenerator(username, password);
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
