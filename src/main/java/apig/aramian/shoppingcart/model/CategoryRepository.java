package apig.aramian.shoppingcart.model;

import apig.aramian.shoppingcart.model.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Category findByName(String name);

    List<Category> findAllByOrderBySortingAsc();

    Category findBySlug(String slug);
}
