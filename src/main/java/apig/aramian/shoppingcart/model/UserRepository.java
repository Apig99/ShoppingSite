package apig.aramian.shoppingcart.model;

import apig.aramian.shoppingcart.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
