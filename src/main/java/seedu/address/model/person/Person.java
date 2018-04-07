package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author chenxing1992
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Timetable> timetable;
    private ObjectProperty<UniqueTagList> tags;


    //@@author chenxing1992
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Timetable timetable, Set<Tag> tags) {

        requireNonNull(name);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.timetable = new SimpleObjectProperty<>(timetable);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

    }

    //@@author chenxing1992
    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getTimetable(), source.getTags());
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public ObjectProperty<Timetable> timeTableProperty() {
        return timetable;
    }

    @Override
    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    @Override
    public Timetable getTimetable() {
        return timetable.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public boolean hasTag(Tag tag) {
        return tags.get().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.equals((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, timetable, tags);
    }

    @Override
    public String toString() {
        return getName().toString();
    }

}

