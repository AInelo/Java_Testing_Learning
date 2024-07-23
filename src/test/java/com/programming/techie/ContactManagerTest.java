package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;

import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {

    ContactManager contactManager;
    //-------To mean that, is executed before all methode in this test class------//
    @BeforeAll
    public static void setupAll(){
        System.out.println("Should Print Before All Tests");
    }


    //-----To mean that Before the exécution of eachmethode, the ContactManager Class have to be instancied one time----//
    @BeforeEach
    public void setup(){
        contactManager = new ContactManager();
    }


    @Test
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                                    contact.getLastName().equals("Doe") &&
                                    contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create When Last Name is Null")
    public void  shouldThrowRuntimeExceptionWhenLastNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact("Doe", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create When Phone Number is Null")
    public void  shouldThrowRuntimeExceptionWhenPhoneNumberIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @AfterEach
    public void tearDown(){
        System.out.println("Should Executed After Each Test");
    }

    @AfterAll
    public static void tearDownAll(){
        System.out.println("Should be executed at the end of the test class");
    }


//----- Disable Test on a Operating Systme-------//
    @Test
    @DisplayName("Should Not work on Windows")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable on Windows OS")
    public void shouldCreateContactOnMacOs() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    //---------------------Understanding Junit Test LifeCycle-------------------//
    /**
    * {@code @BeforeAll*}
    * @BeforeEach
    * {@code @AfterEach}
    * {@code @AfterAll}
     */
    //--------------- Assumptions on JAVA Tests-----------------//





    //--------------- Repeated Tests annotations-----------------//
    @DisplayName("Should Not Create When Last Name is Null")
    @RepeatedTest(value = 5,
    name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
    public void  shouldTestContactCreationRepeatedly(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact("Doe", null, "0123456789");
        });
    }




    //-------Nested Classes-----//
    @Nested
    class RepeatedNestedTest{

    }


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class RepeatedParametizedNestTest{
        //--------------- Parameterized Tests -----------------//
        /** //-------Parameterized Tests : Test are executed with different set of input Substitues @Test
         Data is provided using different annotations
         @ValueSource, @CsvSource etc
         @MethodeSource, validation
         */

        @DisplayName("Should Not Create When Last Name is Null")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789","0123456789", "0123456789"})
        public void  shouldTestContactCreationValueSource(String phoneNumber){
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("Should Not Create When Last Name is Null")
        @ParameterizedTest
        @CsvSource({"0123456789","0123456789", "0123456789"})
        public void  shouldTestContactCreationCsvSource(String phoneNumber){
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("Should Not Create When Last Name is Null")
        @ParameterizedTest
        @CsvFileSource(resources = "/test.csv")
        public void  shouldTestContactCreationCsvFileSource(String phoneNumber){
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("Should Not Create When Last Name is Null")
        @ParameterizedTest
        @MethodSource("phoneNumber")
        public void  shouldTestContactCreationMethodeSource(String phoneNumber){
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        private List<String> phoneNumber() {
            return Arrays.asList("0123456789", "0123456789", "0123456789");
        }
    }






}