package com.challenge.herman.repository;


import com.challenge.herman.model.RandomNumber;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.challenge.herman.util.Constants.COMPLETED;


@ApplicationScoped
public class RandomNumberRepository {

    private List<RandomNumber> randomNumberList = new ArrayList<>();
    private int threadPoolSize;
    private long minRequestTime;

    {
        //Default configuration values
        threadPoolSize = 1;
        minRequestTime = 30;

        randomNumberList.add(new RandomNumber(UUID.randomUUID().toString(), new BigInteger("334556547"), new Date(), COMPLETED, 33000));
        randomNumberList.add(new RandomNumber(UUID.randomUUID().toString(), new BigInteger("34232423423423"), new Date(), COMPLETED, 31000));
        randomNumberList.add(new RandomNumber(UUID.randomUUID().toString(), new BigInteger("5464567675668"), new Date(), COMPLETED, 42000));
    }

    public List<RandomNumber> listAll() {
        return randomNumberList;
    }

    public void add(RandomNumber randomNumber) {
        randomNumberList.add(randomNumber);
    }

    public RandomNumber getByRequestId(String requestID) {
        return randomNumberList.stream().filter(randomNumber -> randomNumber.getRequestId().equals(requestID))
                .findFirst().orElse(null);
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public long getMinRequestTime() {
        return minRequestTime;
    }

    public void setMinRequestTime(long minRequestTime) {
        this.minRequestTime = minRequestTime;
    }
}