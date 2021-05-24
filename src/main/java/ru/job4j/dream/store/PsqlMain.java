package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.net.CacheRequest;
import java.net.CacheResponse;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        /*store.saveP(new Post(0, "Java Job"));
        store.saveP(new Post(0, "Java Job2"));
        store.saveP(new Post(1, "Java Job3"));*/

        store.saveC(new Candidate(0, "Ivan"));
        store.saveC(new Candidate(0, "John"));
        store.saveC(new Candidate(0, "Hans"));

        /*for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }*/
        /*Post post = store.findByIdP(1);
        System.out.println(post.getId() + " " + post.getName());*/

        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        /*Candidate candidate = store.findByIdC(1);
        System.out.println(candidate.getId() + " " + candidate.getName());*/
    }
}
