package io.playground.protobuf.repository;

import io.playground.protobuf.AddressBookProtos.Person;
import io.playground.protobuf.AddressBookProtos.Person.PhoneNumber;
import io.playground.protobuf.AddressBookProtos.Person.PhoneType;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

import static io.playground.protobuf.AddressBookProtos.Person.PhoneType.HOME;
import static io.playground.protobuf.AddressBookProtos.Person.PhoneType.MOBILE;

@Repository
public class PersonRepository {

    private Map<Integer, Person> persons = new HashMap<>();

    @PostConstruct
    void init() {
        addPerson("John", "john@gmail.com", phone("123456", MOBILE));
        addPerson("Paul", "paul@gmail.com", phone("999999", MOBILE), phone("888888", HOME));
        addPerson("Steve", "steve@gmail.com", phone("777777", MOBILE));
        addPerson("Peter", "peter@gmail.com", phone("666666", HOME), phone("555555", MOBILE), phone("444444", MOBILE));
    }

    private void addPerson(String name, String email, PhoneNumber...phones) {
        int id = persons.size() + 1;
        Person p = Person.newBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .addAllPhone(Arrays.asList(phones))
                .build();
        persons.put(id, p);
    }

    private PhoneNumber phone(String number, PhoneType type) {
        return PhoneNumber.newBuilder().setNumber(number).setType(type).build();
    }

    public Person find(int id) {
        return persons.get(id);
    }

    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }
}
