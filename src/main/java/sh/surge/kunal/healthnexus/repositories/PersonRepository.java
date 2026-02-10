package sh.surge.kunal.healthnexus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sh.surge.kunal.healthnexus.models.Person;
// JpaRepository is used to perform database operations
// Consists all the crud operations
public interface PersonRepository extends JpaRepository<Person, String> {

}
