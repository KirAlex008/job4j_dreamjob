package ru.job4j.dream.servlet;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;
import javax.servlet.http.HttpSession;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)

public class PostServletTest {

    private final static String path = "posts.jsp";

    @Test
    public void whenNewGet() throws IOException, ServletException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        PostServlet postServlet = new PostServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher disp = mock(RequestDispatcher.class);
        HttpSession sessionMock  = mock(HttpSession.class);
        Mockito.when(req.getSession()).thenReturn(sessionMock);
        User admin = new User(1, "admin", "email", "pass");
        req.setAttribute("posts", store.findAllPosts());
        req.setAttribute("user", req.getSession().getAttribute(admin.getName()));
        when(req.getRequestDispatcher(path)).thenReturn(disp);
        postServlet.doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher(path);
        verify(disp).forward(req, resp);
    }

    @Test
    public void whenNewPost() throws IOException, ServletException {

        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("Junior Java Job");
        new PostServlet().doPost(req, resp);
        assertThat(store.findAllPosts().iterator().next().getName(), is("Junior Java Job"));
    }
}
