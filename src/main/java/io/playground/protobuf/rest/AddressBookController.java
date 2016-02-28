package io.playground.protobuf.rest;

import io.playground.protobuf.AddressBookProtos.AddressBook;
import io.playground.protobuf.AddressBookProtos.Person;
import io.playground.protobuf.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/addressbook", produces = "application/x-protobuf")
public class AddressBookController {

    private PersonRepository repository;

    @Autowired
    public AddressBookController(PersonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/{id}", method = GET)
    public AddressBook getContactDetails(@PathVariable int id) {
        return AddressBook.newBuilder().addPerson(repository.find(id)).build();
    }

    @RequestMapping(method = GET)
    public AddressBook getContactDetails() {
        return AddressBook.newBuilder().addAllPerson(repository.findAll()).build();
    }
}
