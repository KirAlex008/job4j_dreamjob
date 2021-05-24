package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Candidate candidate = PsqlStore.instOf().findByIdC(Integer.parseInt(req.getParameter("id")));
        System.out.println(candidate.toString());
        try {
            System.out.println(Files.deleteIfExists(Paths.get(candidate.getPhoto())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PsqlStore.instOf().deleteCandidate(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }


}
