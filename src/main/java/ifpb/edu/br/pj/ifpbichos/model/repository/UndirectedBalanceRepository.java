package ifpb.edu.br.pj.ifpbichos.model.repository;

import ifpb.edu.br.pj.ifpbichos.model.entity.UndirectedBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UndirectedBalanceRepository extends JpaRepository<UndirectedBalance, Long>{
    Optional<UndirectedBalance> findFirstByOrderByIdAsc();
}

