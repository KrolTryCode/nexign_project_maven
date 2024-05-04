package nexign_project_maven.cdr_service.repository;

import nexign_project_maven.cdr_service.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
}
