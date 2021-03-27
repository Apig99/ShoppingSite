package apig.aramian.shoppingcart.model;

import apig.aramian.shoppingcart.model.data.Admin;
import apig.aramian.shoppingcart.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);
}
