# chenxing1992
###### /libs/README-EXT.txt
``` txt
This directory contains third-party jar files that are required
by CalendarFX. The framework can not work without these.

- controlsfx-xxx.jar

  Custom controls developed as part of the open source project ControlsFX.

- fontawesomefx-commons-xxx.jar

  Common support code for web fonts in JavaFX.

- fontawesomefx-fontawesome-xxx.jar

  The fontawesome font for JavaFX.

- license4j-1.4.0.jar

  Support for licensing keys.
```
###### /build.gradle
``` gradle
plugins {
    id 'java'
    id 'jacoco'
    id 'checkstyle'
    id "com.github.kt3k.coveralls" version "2.4.0"
    id "com.github.johnrengelman.shadow" version '1.2.3'
    id 'org.asciidoctor.convert' version '1.5.6'
    id 'application'
}

// Specifies the entry point of the application
mainClassName = 'seedu.address.MainApp'

sourceCompatibility = JavaVersion.VERSION_1_8
targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}

checkstyle {
    toolVersion = '8.1'
}
project.ext {
    controlsFxVersion = '8.40.11'
    guavaVersion = '19.0'
    jacksonVersion = '2.7.0'
    jacksonDataTypeVersion = '2.7.4'
    junitVersion = '4.12'
    testFxVersion = '4.0.7-alpha'
    monocleVersion = '1.8.0_20'
    checkstyleVersion = '8.1'

    libDir = 'lib'
}
jacocoTestReport {
    reports {
        xml.enabled false
        csv.enabled false
        html.destination "${buildDir}/jacocoHtml"
    }
}

dependencies {
    String testFxVersion = '4.0.7-alpha'
```
###### /build.gradle
``` gradle
    compile fileTree(dir: 'libs', include: '*.jar')
    compile group: 'org.fxmisc.easybind', name: 'easybind', version: '1.0.3'
    compile group: 'org.controlsfx', name: 'controlsfx', version: '8.40.11'
    compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.7.0'
    compile group: 'com.fasterxml.jackson.datatype', name: 'jackson-datatype-jsr310', version: '2.7.4'
    compile group: 'com.google.guava', name: 'guava', version: '19.0'
    compile group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1'
    compile "com.joestelmach:natty:0.11"

    testCompile group: 'junit', name: 'junit', version: '4.12'
    testCompile group: 'org.testfx', name: 'testfx-core', version: testFxVersion
    testCompile group: 'org.testfx', name: 'testfx-junit', version: testFxVersion
    testCompile group: 'org.testfx', name: 'testfx-legacy', version: testFxVersion, {
        exclude group: 'junit', module: 'junit'
    }
    testCompile group: 'org.testfx', name: 'openjfx-monocle', version: '1.8.0_20'
}

shadowJar {
    archiveName = "addressbook.jar"

    destinationDir = file("${buildDir}/jar/")
}

task wrapper(type: Wrapper) {
    gradleVersion = '4.6'
}

task coverage(type: JacocoReport) {
    sourceDirectories = files(allprojects.sourceSets.main.allSource.srcDirs)
    classDirectories = files(allprojects.sourceSets.main.output)
    executionData = files(allprojects.jacocoTestReport.executionData)
    afterEvaluate {
        classDirectories = files(classDirectories.files.collect {
            fileTree(dir: it, exclude: ['**/*.jar'])
        })
    }
    reports {
        html.enabled = true
        xml.enabled = true
    }
}

coveralls {
    sourceDirs = allprojects.sourceSets.main.allSource.srcDirs.flatten()
    jacocoReportPath = "${buildDir}/reports/jacoco/coverage/coverage.xml"
}

tasks.coveralls {
    dependsOn coverage
    onlyIf { System.env.'CI' }
}

task(guiTests)
task(nonGuiTests)

// Run `test` task if `guiTests` or `nonGuiTests` is specified
guiTests.dependsOn test
nonGuiTests.dependsOn test

task(allTests)

// `allTests` implies both `guiTests` and `nonGuiTests`
allTests.dependsOn guiTests
allTests.dependsOn nonGuiTests

test {
    systemProperty 'testfx.setup.timeout', '60000'

    /*
     * Prints the currently running test's name in the CI's build log,
     * so that we can check if tests are being silently skipped or
     * stalling the build.
     */
    if (System.env.'CI') {
        beforeTest { descriptor ->
            logger.lifecycle('Running test: ' + descriptor)
        }
    }

    jacoco {
        destinationFile = new File("${buildDir}/jacoco/test.exec")
    }

    doFirst {
        boolean runGuiTests = gradle.taskGraph.hasTask(guiTests)
        boolean runNonGuiTests = gradle.taskGraph.hasTask(nonGuiTests)

        if (!runGuiTests && !runNonGuiTests) {
            runGuiTests = true;
            runNonGuiTests = true;
        }

        if (runNonGuiTests) {
            test.include 'seedu/address/**'
        }

        if (runGuiTests) {
            test.include 'systemtests/**'
            test.include 'seedu/address/ui/**'
        }

        if (!runGuiTests) {
            test.exclude 'seedu/address/ui/**'
        }
    }
}

task headless << {
    println "Setting headless mode properties."
    test {
        systemProperty 'java.awt.robot', 'true'
        systemProperty 'testfx.robot', 'glass'
        systemProperty 'testfx.headless', 'true'
        systemProperty 'prism.order', 'sw'
        systemProperty 'prism.text', 't2k'
    }
}

// Makes sure that headless properties are set before running tests
test.mustRunAfter headless

asciidoctor {
    backends 'html5'
    sourceDir 'docs'
    outputDir "${buildDir}/docs"

    attributes linkcss: true,
            stylesheet: 'gh-pages.css',
            'source-highlighter': 'coderay',
            icons: 'font',
            experimental: true,
            sectlinks: true,
            idprefix: '',  // for compatibility with GitHub preview
            idseparator: '-'
}

/*
 * Copies stylesheets into the directory containing generated HTML files as
 * Asciidoctor does not copy linked CSS files to the output directory when rendering.
 * This is needed for linked stylesheets and embedded stylesheets which import other files.
 */
task copyStylesheets(type: Copy) {
    from "${asciidoctor.sourceDir}/stylesheets"
    into "${asciidoctor.outputDir}/html5/stylesheets"
}
asciidoctor.dependsOn copyStylesheets

task deployOfflineDocs(type: Copy) {
    into('src/main/resources/docs')

    from("${asciidoctor.outputDir}/html5") {
        include 'stylesheets/*'
        include 'images/*'
        include 'UserGuide.html'
    }
}

task copyDummySearchPage(type: Copy) {
    from 'docs/DummySearchPage.html'
    into "${buildDir}/docs/html5"
}

deployOfflineDocs.dependsOn asciidoctor
processResources.dependsOn deployOfflineDocs

defaultTasks 'clean', 'headless', 'allTests', 'coverage', 'asciidoctor'
```
###### /src/main/resources/view/CalendarDisplay.fxml
``` fxml
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>

<StackPane fx:id="placeHolder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
  <TextArea fx:id="resultDisplay" editable="false" prefHeight="416.0" prefWidth="796.0" style="-fx-background-color: yellow;" styleClass="result-display" />
</StackPane>
```
###### /src/main/java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
```
###### /src/main/java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### /src/main/java/seedu/address/model/person/ReadOnlyPerson.java
``` java
/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Timetable> timeTableProperty();
    Timetable getTimetable();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    //ObjectProperty<AppointmentList> appointmentProperty();
    // List<Appointment> getAppointments();
    /**Same state detected will return true.
     */
    default boolean isSameStateAs(ReadOnlyPerson rp) {
        return rp == this // short circuit if same object
                || (rp != null // this is first to avoid NPE below
                && rp.getName().equals(this.getName()) // state checks here onwards
                && rp.getPhone().equals(this.getPhone())
                && rp.getEmail().equals(this.getEmail())
                && rp.getAddress().equals(this.getAddress())
                && rp.getTimetable().equals((this.getTimetable())));
    }

```
###### /src/main/java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    /**
     * Show all contact in detail as text
     */
    default String getAsText() {
        final StringBuilder b = new StringBuilder();
        b.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" TimeTable: ")
                .append(getTimetable())
                .append(" Tags: ");
        getTags().forEach(b::append);
        return b.toString();
    }

}

```
###### /src/main/java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

```
###### /src/main/java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * @return the list as an unmodifiable list and sorted by name in ascending order
     */
    public ObservableList<ReadOnlyPerson> asObservableListSortedByNameAsc() {
        internalList.sort((o1, o2) -> {
            int output = (o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName) >= 0) ? 1 : -1;
            return output;
        });
        return FXCollections.unmodifiableObservableList(mappedList);
    }

```
###### /src/main/java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * @return a unmodifiable list and will be sorted by name in descending order
     */
    public ObservableList<ReadOnlyPerson> asObservableListSortedByNameDsc() {
        internalList.sort((o1, o2) -> {
            int op = (o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName) <= 0) ? 1 : -1;
            return op;
        });
        return FXCollections.unmodifiableObservableList(mappedList);
    }
```
###### /src/main/java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * @return list is reversed
     */
    public ObservableList<ReadOnlyPerson> asObservableListReversed() {
        FXCollections.reverse(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }


    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    /**
     * Util method to extract person out from a list
     */
    private Person getPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        requireNonNull(target);
        for (Person person : internalList) {
            if (person.equals(target)) {
                return person;
            }
        }
        throw new PersonNotFoundException();
    }

}

```
###### /src/main/java/seedu/address/model/person/Person.java
``` java
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


```
###### /src/main/java/seedu/address/model/person/Person.java
``` java
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Timetable timetable, Set<Tag> tags) {

        requireAllNonNull(name, phone, email, address, timetable, tags);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.timetable = new SimpleObjectProperty<>(timetable);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

    }

```
###### /src/main/java/seedu/address/model/person/Person.java
``` java
    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getTimetable(), source.getTags());
    }
    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setTimetable(Timetable timetable) {
        this.timetable.set(requireNonNull(timetable));
    }

    @Override
    public ObjectProperty<Timetable> timeTableProperty() {
        return timetable;
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

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
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
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, timetable, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}

```
###### /src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     *
     * Help to remove unwanted tags
     */
    public void removeUnusedTags(Set<Tag> tagToRemove) {
        Set<Tag> newTags = getTagsExcluding(tagToRemove);
        tags.setTags(newTags);
        syncMasterTagListWith(persons);
    }

```
###### /src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     *
     * Help to exclude unwanted tags
     */
    public Set<Tag> getTagsExcluding(Set<Tag> tagsToExclude) {
        Set<Tag> output = tags.toSet();
        for (Tag tagExcluded : tagsToExclude) {
            output.remove(tagExcluded);
        }
        return output;
    }

```
###### /src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     * Make sure that these people:
     * - appear in the master list {@link #tags}
     * - Tag objects are pointed in the master list
     *
     * @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

```
###### /src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     * Make sure this person:
     * - Appear in the master list {@link #tags}
     * - Tag object is pointed in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList Tags = new UniqueTagList(person.getTags());
        tags.mergeFrom(Tags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> mainTagObjects = new HashMap<>();
        tags.forEach(tag -> mainTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        Tags.forEach(tag -> correctTagReferences.add(mainTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private ReadOnlyPerson syncWithMasterTagList(ReadOnlyPerson person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTimetable(),
                correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(person.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Person newPerson =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getTimetable(), newTags);

        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Removing a person's tags should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    /**
     * Removes {@code tag} from all persons in this {@code AddressBook}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is obtained from the address book.");
        }
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
```
