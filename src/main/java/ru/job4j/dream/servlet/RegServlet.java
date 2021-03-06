package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Store store = PsqlStore.instOf();
        User user = store.findByEmail(email);
        //User user = PsqlStore.instOf().findByEmail(email);
        if (user == null) {
            user = new User(0, name, email, password);
            PsqlStore.instOf().createUser(user);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Такой email уже существует, укажите другой");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
