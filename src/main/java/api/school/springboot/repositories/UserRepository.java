package api.school.springboot.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import api.school.springboot.models.UserModel;

@Repository
public interface UserRepository<id> extends JpaRepository<UserModel, UUID> {

}
