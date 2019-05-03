
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
	
	@Query("select p from Position p where p.isFinal = true")
	Collection<Position> getPositionsIsFinal();
	
	@Query("select p from Position p where p.isFinal = false")
	Collection<Position> getPositionsIsDraft();
	
	@Query("select p from Position p where p.company.id =?1")
	Collection<Position> getPositionsByCompany(int id);
	
	@Query("select p from Position p where p.company.id =?1 and p.isFinal=true")
	Collection<Position> getPublishedPositionsByCompany(int id);
	
	@Query("select a from Application a where a.position.id =?")
	Collection<Application> getApplicationsByPosition(int id);
	
	@Query("select avg(c.positions.size) from Company c")
	Double getAvgPositionsPerCompany();

	@Query("select min(c.positions.size) from Company c")
	Integer getMinPositionsPerCompany();
	
	@Query("select max(c.positions.size) from Company c")
	Integer getMaxPositionsPerCompany();
	
	@Query("select stddev(c.positions.size) from Company c")
	Double getStdevPositionsPerCompany();
		
	@Query("select avg(p.salary) from Position p")
	Double getAvgSalaryPerPosition();

	@Query("select min(p.salary) from Position p")
	Integer getMinSalaryPerPosition();
	
	@Query("select max(p.salary) from Position p")
	Integer getMaxSalaryPerPosition();
	
	@Query("select stddev(p.salary) from Position p")
	Double getStdevSalaryPerPosition();
	
	@Query("select distinct p from Position p where p.salary=(select max(p1.salary) from Position p1)")
	Position getBestSalaryPosition();
	
	@Query("select distinct p from Position p where p.salary=(select min(p1.salary) from Position p1)")
	Position getWorstSalaryPosition();
	
	@Query("select p from Position p where p.isFinal=true and " +
			"((:keyword is null or :keyword like '' ) or " +
				"(p.title like %:keyword% " +
				"or p.description like %:keyword%))")
	
	Collection<Position> searchPositions(@Param("keyword") String keyword);
	
	@Query("select p from Position p where p.isFinal=true and " +
			"((:keyword is null or :keyword like '' ) or " +
				"(p.title like %:keyword% " +
				"or p.description like %:keyword% " +
				"or p.profile like %:keyword% " +
				"or p.skills like %:keyword% " +
				"or p.technologies like %:keyword% " +
				"or p.company.commercialName like %:keyword%)) " +
			"and (p.company.id =:companyId)")
	Collection<Position> searchPositionsWCompany(@Param("keyword") String keyword,@Param("companyId") Integer companyId);

}
