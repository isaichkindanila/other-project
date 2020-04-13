package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.ChatMessage;
import ru.itis.other.project.repositories.interfaces.ChatMessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatMessageRepositoryJpaImpl implements ChatMessageRepository {

    private static final String FIND_ALL_AFTER_ID =
            "select m from ChatMessage m where m.id > :after order by m.id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ChatMessage save(ChatMessage message) {
        return entityManager.merge(message);
    }

    @Override
    public List<ChatMessage> findAllAfter(long after) {
        var query = entityManager.createQuery(FIND_ALL_AFTER_ID, ChatMessage.class);
        query.setParameter("after", after);

        return query.getResultList();
    }
}
