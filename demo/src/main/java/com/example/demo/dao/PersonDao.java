package com.example.demo.dao;

import com.example.demo.model.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    int insertPerson(UUID id, Person person) throws SQLException;

    default int insertPerson(Person person) throws SQLException {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);

    int deletePersonById(UUID id);

    int deletePersonNameContains(String substring);

    int updatePersonById(UUID id, Person person);
}
