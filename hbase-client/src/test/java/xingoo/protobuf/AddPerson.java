package xingoo.protobuf;

import com.example.tutorial.AddressBookProtos.Person;
import org.mortbay.util.ajax.JSON;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class AddPerson {
  public static final String FILE = "/Users/xingoo/Documents/source/hbase/hbase-client/src/test/java/xingoo/protobuf/cache";

  public static void main(String[] args) throws Exception {
    write();

    read();
  }

  static void write() throws IOException {
    Person.Builder person = Person.newBuilder();
    person.setId(1);
    person.setName("zhangsan");

    // Write the new address book back to disk.
    FileOutputStream output = new FileOutputStream(FILE);
    person.build().writeTo(output);
    output.close();
  }

  static void read() throws IOException {
    Person person = Person.parseFrom(new FileInputStream(FILE));
    System.out.println(JSON.toString(person));
  }
}