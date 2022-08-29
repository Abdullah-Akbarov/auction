package component;

import com.google.gson.Gson;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.Message;
import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@WebFilter("/*")
public class InitialFilter implements Filter {
    private final Gson gson = new Gson();
    private final UserDao userDao = UserDaoImpl.getUserDao();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (uri.startsWith("/login") || uri.startsWith("/view") || uri.startsWith("/bid") || uri.startsWith("/lot-list")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String key = request.getHeader("key");
            if (key == null || key.isEmpty() || key.isBlank()) {
                response.getWriter().print(gson.toJson(new Message(401, "Unauthorized", null)));
                response.getWriter().close();
            } else {
                String decode = new String(Base64.getDecoder().decode(key.getBytes()));
                String[] split = decode.split("&");
                if (split.length == 2) {
                    Optional<User> userOptional = userDao.findByUsernameAndPassword(split[0], split[1]);
                    if (userOptional.isPresent() && userOptional.get().isAdmin()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", userOptional.get());
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        response.getWriter().print(gson.toJson(new Message(401, "Admin role required", null)));
                        response.getWriter().close();
                    }
                }
            }
        }
    }
}
