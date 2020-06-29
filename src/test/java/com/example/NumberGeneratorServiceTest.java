package com.example;


import com.challenge.herman.exception.NumberGeneratorException;
import com.challenge.herman.model.*;
import com.challenge.herman.repository.RandomNumberRepository;
import com.challenge.herman.service.NumberGeneratorService;
import com.challenge.herman.util.Constants;
import com.challenge.herman.util.DelayExecution;
import com.challenge.herman.util.DelayMe;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.challenge.herman.util.Constants.PENDING;


@RunWith(Arquillian.class)
public class NumberGeneratorServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(NumberGeneratorService.class)
                .addClass(RandomNumberRepository.class)
                .addClass(NumberGeneratorException.class)
                .addClass(RandomNumber.class)
                .addClass(GenericResponse.class)
                .addClass(HistoryResponse.class)
                .addClass(PendingResponse.class)
                .addClass(StatsResponse.class)
                .addClass(Constants.class)
                .addClass(DelayExecution.class)
                .addClass(DelayMe.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private NumberGeneratorService numberGeneratorService;

    @Inject
    private RandomNumberRepository randomNumberRepository;


    @Test
    public void requestRandomNumberTest() {
        numberGeneratorService.requestRandomNumber(null);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(1, randomNumberRepository.listAll().stream().filter(randomNumber -> randomNumber.getRequestState().equals(PENDING)).count());
    }

    @Test
    public void getPendingTest() {
        //RandomNumberRepository is initiated with 3 generated random numbers
        Assert.assertNotNull(numberGeneratorService.getPending());
    }

    @Test
    public void getHistoryTest() {
        //RandomNumberRepository is initiated with 3 generated random numbers
        Assert.assertNotNull(numberGeneratorService.getHistory());
        Assert.assertEquals(3, numberGeneratorService.getHistory().size());
    }

    @Test
    public void cancelRequestTest() {
        randomNumberRepository.add(new RandomNumber("0dc4972b-4209-4a8c-b386-5380ff63a575", new BigInteger("9879867685765765"), new Date(), PENDING, 4000));
        Assert.assertEquals(200, numberGeneratorService.cancelRequest("0dc4972b-4209-4a8c-b386-5380ff63a575").getCode());
    }

    @Test
    public void getStatsTest() {
        Assert.assertNotNull(numberGeneratorService.getStats());
    }

    @Test
    public void setThreadSizeTest() {
        Assert.assertEquals(200, numberGeneratorService.setThreadSize(5).getCode());
    }


    @Test
    public void setMinRequestDurationTest() {
        Assert.assertEquals(200, numberGeneratorService.setMinRequestDuration(32).getCode());
    }

}