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

@WebServlet("/view-bids")
public class ViewBidsController extends HttpServlet {
    private final BidService bidService = BidServiceImpl.getBidService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lotId = req.getParameter("id");
        Message message = bidService.getBids(Integer.valueOf(lotId));
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
