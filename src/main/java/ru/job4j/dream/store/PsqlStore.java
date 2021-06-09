package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getString("photo")));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return candidates;
    }

    @Override
    public void saveP(Post post) {
        if (post.getId() == 0) {
            createP(post);
        } else {
            updateP(post);
        }
    }

    @Override
    public void saveC(Candidate candidate) {
        if (candidate.getId() == 0) {
            createC(candidate);
        } else {
            updateC(candidate);
        }
    }

    private Post createP(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return post;
    }

    private Candidate createC(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return candidate;
    }

    private void updateP(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("update post as u set name=?  where u.id=?")) {
            st.setString(1, post.getName());
            st.setInt(2, post.getId());
            st.executeUpdate();
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
    }

    private void updateC(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("update candidate as u set name=?  where u.id=?")) {
            st.setString(1, candidate.getName());
            st.setInt(2, candidate.getId());
            st.executeUpdate();
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
    }

    @Override
    public Post findByIdP(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM post where id=?")) {
            st.setInt(1, id);
            try (ResultSet it = st.executeQuery()) {
                if (it.next()) {
                    post = new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return post;
    }

    @Override
    public Candidate findByIdC(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("SELECT * FROM candidate where id=?")) {
            st.setInt(1, id);
            try (ResultSet it = st.executeQuery()) {
                if (it.next()) {
                    candidate = new Candidate(it.getInt("id"), it.getString("name"), it.getString("photo"));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return candidate;
    }

    @Override
    public void setCandidatePhoto(String photo, int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE candidate as c SET photo=? WHERE c.id = ?;")
        ) {
            ps.setString(1, photo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return user;
    }


    @Override
    public User findByEmailUser(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("SELECT * FROM users where email=?")) {
            st.setString(1, email);
            try (ResultSet it = st.executeQuery()) {
                if (it.next()) {
                    user = new User(it.getInt("id"), it.getString("name"),it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return user;
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("DELETE FROM candidate WHERE id = ?;")
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
