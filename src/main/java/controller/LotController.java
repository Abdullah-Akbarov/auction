package controller;

import com.google.gson.Gson;
import model.Message;
import model.User;
import service.LotService;
import service.impl.LotServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add-lot")
public class LotController extends HttpServlet {
    private final LotService lotService = LotServiceImpl.getLotService();
    private final Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        String description = req.getParameter("description");
        String initialPrice = req.getParameter("initial_price");
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        Message message = lotService.addLot(model, description, Double.parseDouble(initialPrice), user);
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
