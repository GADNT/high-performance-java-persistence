package com.vladmihalcea.book.hpjp.hibernate.association;

import com.vladmihalcea.book.hpjp.util.AbstractMySQLIntegrationTest;
import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>ElementCollectionTest</code> - @ElementCollection Test
 *
 * @author Vlad Mihalcea
 */
public class ElementCollectionTest extends AbstractMySQLIntegrationTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[] {
            Post.class
        };
    }

    @Test
    public void testLifecycle() {
        doInJPA(entityManager -> {
            Post post = new Post("First post");

            post.getComments().add("My first review");
            post.getComments().add("My second review");
            post.getComments().add("My third review");

            entityManager.persist(post);
            entityManager.flush();

            /*LOGGER.info("Remove tail");
            post.getComments().remove(2);
            entityManager.flush();*/
            LOGGER.info("Remove head");
            post.getComments().remove(0);
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    public static class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        public Post() {}

        public Post(String title) {
            this.title = title;
        }

        @ElementCollection
        private List<String> comments = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getComments() {
            return comments;
        }
    }
}
