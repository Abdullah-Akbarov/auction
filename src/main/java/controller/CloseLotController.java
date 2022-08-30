package controller;

import com.google.gson.Gson;
import model.Message;
import service.LotService;
import service.impl.LotServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/close-lot")
public class CloseLotController extends HttpServlet {
    private final LotService lotService = LotServiceImpl.getLotService();
    private final Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Message message = lotService.closeLot(Integer.valueOf(id));
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
