package web.db.kpi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.db.kpi.backend.enums.Role;
import web.db.kpi.backend.models.RoleUser;

import java.util.Optional;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    @Query("select c from RoleUser c where c.user.login = :term")
    Optional<RoleUser> findByLogin(@Param("term") String login);

    @Query("select c from RoleUser c where c.user.login = :term1 and c.role = :term2")
    Optional<RoleUser> findByLoginAndRole(@Param("term1") String login, @Param("term2") Role role);

}
