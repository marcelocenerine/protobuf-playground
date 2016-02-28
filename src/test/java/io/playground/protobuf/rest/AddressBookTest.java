package io.playground.protobuf.rest;

import io.playground.protobuf.AddressBookApplication;
import io.playground.protobuf.AddressBookProtos.AddressBook;
import io.playground.protobuf.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AddressBookApplication.class)
@WebIntegrationTest
public class AddressBookTest {

    private static final String ENDPOINT_URL = "http://localhost:8080/addressbook";

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ProtobufHttpMessageConverter protobufConverter;

    private RestTemplate restTemplate;

    @PostConstruct
    void setUp() {
        restTemplate = new RestTemplate(asList(protobufConverter));
    }

    @Test
    public void shouldRetrieveContactDetailsOfAllPersons() {
        AddressBook addressBook = restTemplate.getForObject(ENDPOINT_URL, AddressBook.class);

        assertThat(addressBook.getPersonList(), is(repository.findAll()));
    }

    @Test
    public void shouldRetrieveContactDetailsOfSinglePerson() {
        AddressBook addressBook = restTemplate.getForObject(ENDPOINT_URL + "/1", AddressBook.class);

        assertThat(addressBook.getPersonCount(), is(1));
        assertThat(addressBook.getPerson(0), is(repository.find(1)));
    }
}
