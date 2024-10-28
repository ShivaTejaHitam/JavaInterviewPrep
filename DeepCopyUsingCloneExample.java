import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


class Address implements Cloneable {
    String city;
    int pincode;

    Address(String city, int pincode){
        this.city = city;
        this.pincode = pincode;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return this.city + " " + this.pincode;
    }
}

class Person implements Cloneable{
    private String name;
    private  int age;
    private Address address;


    Person(String name,int age,Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Person clonedPerson = (Person) super.clone();
        clonedPerson.address = (Address) address.clone();
        return clonedPerson;
    } 

    @Override
    public String toString(){
        return this.name + " " + this.age + " " + this.address;
    }
}


public class CloneExample {
    public static void main(String[] args) throws CloneNotSupportedException{

        try {
            Person person = new Person("Shiva",18,new Address("Hyd",500090));
            Person person2 = (Person) person.clone();

            System.out.println(person2);
        } catch(CloneNotSupportedException e) {
            System.out.println("Clone not supported");
        }
    }
}
