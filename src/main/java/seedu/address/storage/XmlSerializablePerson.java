package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An Immutable Person that is serializable to XML format
 */
@XmlRootElement(name = "person")
public class XmlSerializablePerson {

    @XmlElement
    private XmlAdaptedPerson person;

    /**
     * Creates an empty XmlSerializablePerson.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializablePerson() {
    }

    /**
     * Conversion
     */
    public XmlSerializablePerson(ReadOnlyPerson src) {
        this();
        person = new XmlAdaptedPerson(src);
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public Person toModelType() throws IllegalValueException {
        Person person = this.person.toModelType();

        return person;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializablePerson)) {
            return false;
        }

        XmlSerializablePerson xmlSerializablePerson = (XmlSerializablePerson) other;
        return person.equals(xmlSerializablePerson.person);
    }
}
