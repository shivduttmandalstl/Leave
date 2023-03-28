package com.employee.management.system.Leave.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.employee.management.system.Leave.model.Leaves;
import jakarta.transaction.Transactional;

public interface LeaveRepository extends JpaRepository<Leaves, Integer>{

	List<Leaves> findByEmail(String username);
	Optional<Leaves> findFirstByEmail(String username);
	List<Leaves> findByManagerEmail(String managerEmail);

	@Query(value="select email from users where email =:e",nativeQuery = true)
	Optional<String> getUserDetails(@Param("e") String email);
	
	@Query(value="select email from leaves where leave_id=:i",nativeQuery = true)
	String getEmailByLeaveId(@Param("i") int id);
	

	@Query(value = "select from_date from leaves where email =:e AND from_date BETWEEN :f AND :t",nativeQuery = true)
	List<Object> getFDates(@Param("e") String email,
			@Param("f") LocalDate fromDate,
			@Param("t") LocalDate toDate);
	
	@Query(value = "select to_date from leaves where email =:e AND to_date BETWEEN :f AND :t",nativeQuery = true)
	List<Object> getTDates(@Param("e") String email,
			@Param("f") LocalDate fromDate,
			@Param("t") LocalDate toDate);
	
	@Query(value = "select users.manager_name from users where users.email=:e",nativeQuery = true)
	String getManagerName(@Param("e") String email);
	
	@Query(value = "select users.manager_email from users where users.email=:e",nativeQuery = true)
	String getManagerEmail(@Param("e") String email);
	
	@Modifying
	@Transactional
	@Query(value= "update leaves set manager_name=(select users.manager_name from users where users.email=:e) where email=:e",nativeQuery = true)
	void updateManagerNameByEmail(@Param("e") String email);
	
	@Modifying
	@Transactional
	@Query(value= "update leaves set manager_email=(select users.manager_email from users where users.email=:e) where email=:e",nativeQuery = true)
	void updateManagerEmailByEmail(@Param("e") String email);
	
	@Modifying
	@Transactional
	@Query(value= "update leaves set manager_name=(select users.manager_name from users where users.email=:e) where leave_id=:i",nativeQuery = true)
	void updateManagerNameByEmailAndId(@Param("e") String email,@Param("i") int id);
	
	@Modifying
	@Transactional
	@Query(value= "update leaves set manager_email=(select users.manager_email from users where users.email=:e) where leave_id=:i",nativeQuery = true)
	void updateManagerEmailByEmailAndId(@Param("e") String email,@Param("i") int id);
}
