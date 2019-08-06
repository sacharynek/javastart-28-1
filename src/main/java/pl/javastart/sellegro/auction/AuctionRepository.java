package pl.javastart.sellegro.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value= "SELECT * FROM sellegro.auction order by price desc limit :number", nativeQuery = true)
    List<Auction> findMostExpensiveTop(@Param("number") int number); // Trzeba sparametryzować


    List<Auction> findAll(Sort sort);
    Page<Auction> findAll(Pageable pageable);

   // List<Auction> findAllWithPredicate(Predicate predicate);

    List<Auction> findAll();


}
