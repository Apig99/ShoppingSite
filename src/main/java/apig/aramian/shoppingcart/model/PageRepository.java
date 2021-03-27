package apig.aramian.shoppingcart.model;


import apig.aramian.shoppingcart.model.data.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findBySlug(String slug);

//    @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
//    Page findBySlug(int id, String slug);

    Page findBySlugAndIdNot(String slug, int id);

    List<Page> findAllByOrderBySortingAsc();
}
