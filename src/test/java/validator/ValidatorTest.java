package validator;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.Station;

import com.mospan.railway.model.User;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.UserService;
import com.mospan.railway.validator.Validator;

import org.junit.*;


public class ValidatorTest {
    static Station dbStation = new Station();
    static User dbUser = new User();

    @BeforeClass
    public static void setUp() {
        //inserting station
        dbStation.setName("Test Station");
        new StationService().insert(dbStation);

        //inserting user
        Detail detailUpd = new Detail();
        detailUpd.setEmail("email@mail.com");
        detailUpd.setFirstName("Name");
        detailUpd.setLastName("Surname");
        dbUser.setLogin("test_login");
        dbUser.setPassword("password");
        dbUser.setRole(Role.CLIENT);
        dbUser.setDetails(detailUpd);
        new UserService().insert(dbUser);

    }

    @Test
    public void shouldValidateIfThereIsNoStationWithSuchName() {
        Station station = new Station();
        station.setName("Test_Station");
        Assert.assertTrue(new Validator().validateStations(station));
    }

    @Test
    public void shouldReturnFalseIfThereIsAStationWithSuchName() {
        Station station = new Station();
        station.setName("Test Station");
        new StationService().insert(station);
        Assert.assertFalse(new Validator().validateStations(dbStation));
    }


    @Test
    public void shouldReturnNoChange() {
        User user = new User();
        Detail detail = new Detail();
        detail.setEmail("email@mail.com");
        detail.setFirstName("Name");
        detail.setLastName("Surname");
        user.setLogin("login");
        user.setDetails(detail);

        User userUpd = new User();
        Detail detailUpd = new Detail();
        detailUpd.setEmail("email@mail.com");
        detailUpd.setFirstName("Name");
        detailUpd.setLastName("Surname");
        userUpd.setLogin("login");
        userUpd.setDetails(detailUpd);

        Assert.assertEquals("no_change", new Validator().validateUser(user, userUpd));
    }

    @Test
    public void shouldReturnFalseIfEmailIsUsed() {
        User user = new User();
        Detail detail = new Detail();
        detail.setEmail("email@mail.com");
        user.setLogin("login");
        user.setDetails(detail);

        Assert.assertEquals("false", new Validator().validateUser(user, dbUser));
    }

    @Test
    public void shouldReturnFalseIfLoginIsUsed() {
        User user = new User();
        Detail detail = new Detail();
        detail.setEmail("mail@mail.com");
        user.setLogin("test_login");
        user.setDetails(detail);

        Assert.assertEquals("false", new Validator().validateUser(user, dbUser));
    }

    @Test
    public void shouldReturnTrueIfThereIsNoSuchData() {
        User user = new User();
        Detail detail = new Detail();
        detail.setEmail("newmail@mail.com");
        user.setLogin("test_new_login");
        user.setDetails(detail);

        Assert.assertEquals("true", new Validator().validateUser(dbUser, user));
    }

    @AfterClass
    public static  void deleteFromDb() {
        new StationService().delete(new StationService().find("Test Station"));
        new UserService().delete(new UserService().find("test_login"));
    }


}
