package validator;

import com.mospan.railway.model.Station;

import com.mospan.railway.validator.Validator;

import org.junit.Assert;

import org.junit.Test;


public class ValidatorTest {

    @Test
    public void shouldValidateIfThereIsNoStationWithSuchName() {

        Station station = new Station();
        station.setName("Test Station");
        Assert.assertTrue(new Validator().validateStations(station));

    }

}
