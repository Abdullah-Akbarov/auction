package controller;

import com.google.gson.Gson;
import model.Message;
import service.BidService;
import service.impl.BidServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bid")
public class GetBidController extends HttpServlet{
    private final BidService bidService = new BidServiceImpl();
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Message bidById = bidService.getBidById(Integer.parseInt(id));
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(bidById));
        resp.getWriter().close();
    }
}
