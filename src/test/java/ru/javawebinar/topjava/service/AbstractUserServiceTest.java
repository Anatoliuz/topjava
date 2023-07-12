package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Bean
    @Profile({Profiles.JPA, Profiles.DATAJPA})
    public  JpaUtil jpaUtil() {
        return new JpaUtil();
    }

    @Bean
    @Profile(Profiles.JDBC)
    public JpaUtil jpaUtilNull() {
        return null;
    }

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
        if (!isJdbc()) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, admin, guest, user);
    }

    @Test
    public void createWithException() throws Exception {
        //   Assume.assumeFalse(isJdbc());

        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())));
    }

    @Test
    public void updateByAddingRole() {
        User user = getUpdatedByAddingRole();
        service.update(user);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdatedByAddingRole());
    }

    @Test
    public void updateByDeletingRoles() {
        User user = getUpdatedByDeletingRoles();
        service.update(user);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdatedByDeletingRoles());
    }

    @Test
    public void updateBySettingRole() {
        User user = getUpdatedBySettingRole();
        service.update(user);
        USER_MATCHER.assertMatch(service.get(GUEST_ID), getUpdatedBySettingRole());
    }

}