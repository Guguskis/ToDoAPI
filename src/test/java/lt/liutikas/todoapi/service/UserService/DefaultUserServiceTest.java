package lt.liutikas.todoapi.service.UserService;

import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultUserService.class)
@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @MockBean
    private UserRepository repository;
    @Autowired
    private UserService service;


    @Test
    void verify_CorrectCredentials_ReturnsTrue() {
        String username = "John";
        String password = "admin";
        when(repository.findByUsername(username))
                .thenAnswer((Answer<User>) invocationOnMock ->
                        new User(username, password));

        boolean actual = service.verify(new User(username, password));

        assertTrue(actual);
    }

    @Test
    void verify_IncorrectPassword_ReturnsFalse() {
        String username = "John";
        String correctPassword = "admin";
        String incorrectPassword = "incorrect";
        when(repository.findByUsername(username))
                .thenAnswer((Answer<User>) invocationOnMock ->
                        new User(username, correctPassword));

        boolean actual = service.verify(new User(username, incorrectPassword));

        assertFalse(actual);
    }

    @Test
    void verify_NonexistentUser_ReturnsFalse() {
        String username = "John";
        String password = "admin";
        when(repository.findByUsername(username))
                .thenAnswer(invocationOnMock -> null);

        boolean actual = service.verify(new User(username, password));

        assertFalse(actual);
    }

}