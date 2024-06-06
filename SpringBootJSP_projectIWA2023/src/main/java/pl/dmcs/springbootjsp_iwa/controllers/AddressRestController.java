package pl.dmcs.springbootjsp_iwa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.model.Student;
import pl.dmcs.springbootjsp_iwa.model.Address;
import pl.dmcs.springbootjsp_iwa.repository.StudentRepository;
import pl.dmcs.springbootjsp_iwa.repository.AddressRepository;
import pl.dmcs.springbootjsp_iwa.repository.TeamRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/address")
public class AddressRestController {
/*
    private AddressRepository addressRepository;
    private StudentRepository studentRepository;


    @Autowired
    public AddressRestController(StudentRepository studentRepository, AddressRepository addressRepository, TeamRepository teamRepository) {
        this.addressRepository = addressRepository;
        this.studentRepository = studentRepository;
    }

    //Find all addresses*/
    //@RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    /*
    public List<Address> findAllAddresses() { return addressRepository.findAll();
    }

    //Find an address, enter the id of the address
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Address findOneAddress(@RequestBody Address address, @PathVariable("id") long id) { return addressRepository.findById(id);
    }


    //Add an Address
    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        addressRepository.save(address);
        return new ResponseEntity<Address>(address, HttpStatus.CREATED);
    }




    private void partialUpdate(Address address, Map<String, Object> updates) {
        if (updates.containsKey("city")) {
            address.setCity((String) updates.get("city"));
        }
        if (updates.containsKey("street")) {
            address.setStreet((String) updates.get("street"));
        }
        if (updates.containsKey("number")) {
            address.setNumber((String) updates.get("number"));
        }
        if (updates.containsKey("postalCode")) {
            address.setPostalCode((String) updates.get("postalCode"));
        }
        addressRepository.save(address);
    }*/




}





