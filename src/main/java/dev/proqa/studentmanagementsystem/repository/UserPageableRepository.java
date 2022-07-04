package dev.proqa.studentmanagementsystem.repository;

import dev.proqa.studentmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPageableRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
}
