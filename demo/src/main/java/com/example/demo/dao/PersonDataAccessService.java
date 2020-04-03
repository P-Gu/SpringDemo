package com.example.demo.dao;

import com.example.demo.model.DayInfo;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("mysql")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;
    /*private final Connection con;
    @Autowired
    private final DataSource dataSource;*/

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate){//, Connection con, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        /*this.dataSource = dataSource;
        this.con = DataSourceUtils.getConnection(this.dataSource);*/
    }

    @Override
    public int insertPerson(UUID id, Person person) throws SQLException {
        final String sql = "insert into person (id, name, password) values (UUID_TO_BIN(?), ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{id.toString(), person.getName(), person.getPassword()});
    }

    //@Override
    /*public List<Person> selectAllPeople() {
        final String sql = "select BIN_TO_UUID(id) as id, name, password from person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            return new Person(id, name, password);
        });
    }*/

    //
    // Perform query that counts occurrences of each day in March in the MySQL database
    //
    @Override
    public List<DayInfo> countDay() {
        final String sql = "select count(*) as count, day from mar_date group by day";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            int count = Integer.parseInt(resultSet.getString("count"));
            String day = resultSet.getString("day");
            return new DayInfo(day, count);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "select BIN_TO_UUID(id) as id, name, password from person where id = UUID_TO_BIN(?)";
        Person person = jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
            UUID personId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            return new Person(personId, name, password);
        });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "delete from person where id = UUID_TO_BIN(?)";
        return jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public int deletePersonNameContains(String substring) {
        final String sql = "delete from person where name like CONCAT('%', ?, '%')";
        return jdbcTemplate.update(sql, new Object[]{substring});
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sql = "update person set name = ?, password = ? where id = UUID_TO_BIN(?)";
        return jdbcTemplate.update(sql, new Object[]{person.getName(), person.getPassword(), id});
    }
}
