package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void saveP(Post post);

    void saveC(Candidate candidate);

    //Post createP(Post post);

    //Candidate createC(Candidate candidate);

    //void updateP(Post post);

    //void updateC(Candidate candidate);

    Post findByIdP(int id);

    Candidate findByIdC(int id);

    void setCandidatePhoto(String photoSource, int id);

    void deleteCandidate(int id);

    Collection<User> findAllUsers();

    void saveUser(User user);

    User findByIdUser(int id);

    void deleteUser(int id);
}
