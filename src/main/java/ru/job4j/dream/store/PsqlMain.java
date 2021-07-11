package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.net.CacheRequest;
import java.net.CacheResponse;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        /*store.saveP(new Post(0, "Java Job"));
        store.saveP(new Post(0, "Java Job2"));
        store.saveP(new Post(1, "Java Job3"));*/

        store.saveC(new Candidate(0, "Ivan", 3, "1.jpg"));
        store.saveC(new Candidate(0, "John", 2, "2.jpg"));
        store.saveC(new Candidate(0, "Hans",1, "3.jpg"));

        /*for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }*/
        /*Post post = store.findByIdP(1);
        System.out.println(post.getId() + " " + post.getName());*/

/*        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }*/
        /*Candidate candidate = store.findByIdC(1);
        System.out.println(candidate.getId() + " " + candidate.getName());*/

        for (City city : store.findAllCities()) {
            System.out.println(city.getId() + " " + city.getName());
        }
    }
}
