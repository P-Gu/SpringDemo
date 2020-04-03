package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.DayInfo;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("mysql") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person) throws SQLException {
        if (person.getPassword().length() <= 8) return 0;
        return personDao.insertPerson(person);
    }

    /*public List<Person> getAllPeople() {
        return personDao.selectAllPeople();
    }*/
    public List<DayInfo> getAllDate() {
        int sum = 0;
        List<DayInfo> list = personDao.countDay();
        for (DayInfo info:list) {
            sum += info.getCount();
        }
        // Turn counts into proportions
        for (DayInfo info:list) {
            info.setCount(Math.round(info.getCount()*100/sum));
        }
        return list;
    }


    public Optional<Person> getPersonById(UUID id) {
        return personDao.selectPersonById(id);
    }

    public int deletePerson(UUID id) {
        return personDao.deletePersonById(id);
    }

    public int deletePerson(String substring) {
        return personDao.deletePersonNameContains(substring);
    }

    public int updatePerson(UUID id, Person newPerson) {
        if (newPerson.getPassword().length() <= 8) return 0;
        return personDao.updatePersonById(id, newPerson);
    }

}
