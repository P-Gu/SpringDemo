package com.example.demo.api;

import com.example.demo.dto.PersonDto;
import com.example.demo.model.DayInfo;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/demo")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /*@GetMapping(path = "/")
    public String mainPage() {
        //model.addAttribute("name", "van");
        return "index";
    }*/

    @PostMapping(path = "/console/users/add")
    public ResponseEntity<String> addPerson(@Valid @NotNull @RequestBody Person person) throws SQLException {
        try {
            int result = this.personService.addPerson(person);
            return result != 0 ? ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User registration success") : ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Wrong password format");
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("User registration failure");
        }
    }

    //@GetMapping(path = "/console/users/getAll")//, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/")
    //
    //  Calling this controller method will show the actual html page with pie chart for proportion of five days in March
    //
    public String getAllDate(Model model) {
        List<DayInfo> list = personService.getAllDate();
        for (DayInfo info:list){
            if ((!info.getDay().equals("Sun"))&&(!info.getDay().equals("Sat")))
                model.addAttribute(info.getDay(), info.getCount());
        }
        return "index";
        //return personService.getAllPeople();
    }

    @GetMapping(path = "/console/users/getById/{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "/console/users/delById/{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        personService.deletePerson(id);
    }

    @DeleteMapping(path = "/console/users/delByNameContains/{substring}")
    public void deletePersonNameContains(@PathVariable("substring") String substring) {
        personService.deletePerson(substring);
    }

    @PutMapping(path = "/console/users/updateById/{id}")
    public ResponseEntity updatePerson(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Person personToUpdate) {
        try {
            int result = this.personService.updatePerson(id, personToUpdate);
            return result != 0 ? ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User update success") : ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Wrong password format");
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("User update failure");
        }
    }

}
