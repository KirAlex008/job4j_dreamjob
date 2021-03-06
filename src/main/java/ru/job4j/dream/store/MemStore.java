package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class  MemStore implements Store {

    private static final MemStore INST = new MemStore();

    private static final AtomicInteger POST_ID = new AtomicInteger(4);

    private static final AtomicInteger CAND_ID = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }


    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public void saveP(Post post) {

    }

    @Override
    public void saveC(Candidate candidate) {

    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void save(Candidate cand) {
        if (cand.getId() == 0) {
            cand.setId(CAND_ID.incrementAndGet());
        }
        candidates.put(cand.getId(), cand);
    }


    public Post findByIdP(int id) {
        return posts.get(id);
    }

    public Candidate findByIdC(int id) {
        return candidates.get(id);
    }

    @Override
    public void setCandidatePhoto(String photoSource, int id) {

    }

    @Override
    public void deleteCandidate(int id) {

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public City findCityById(int id) {
        return null;
    }

    @Override
    public Collection<City> findAllCities() {
        return null;
    }

}
