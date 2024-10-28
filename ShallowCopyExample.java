/*
A shallow copy of an object creates a new object that is a copy of the original object, 
  but it does not create copies of the objects referenced by the original object. 
  Instead, it copies the references to those objects. 
  As a result, both the original and the copied object share the same references to the mutable objects.
*/

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}

public class ShallowCopyExample {
    public static void main(String[] args) {
        Address address = new Address("New York");
        Person original = new Person("John", address);

        // Creating a shallow copy
        Person shallowCopy = original; // Both refer to the same object

        // Modify the address in the shallow copy
        shallowCopy.address.city = "Los Angeles";

        // Output the original person's address
        System.out.println(original.address.city); // Outputs: Los Angeles
    }
}
