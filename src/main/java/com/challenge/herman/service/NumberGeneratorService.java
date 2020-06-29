package com.challenge.herman.service;



import com.challenge.herman.exception.NumberGeneratorException;
import com.challenge.herman.model.*;
import com.challenge.herman.repository.RandomNumberRepository;
import com.challenge.herman.util.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.challenge.herman.util.Constants.*;
import static java.util.logging.Level.*;

@ApplicationScoped
public class NumberGeneratorService {

    private static final Logger LOGGER = Logger.getLogger(NumberGeneratorService.class.getName());

    @Inject
    private RandomNumberRepository randomNumberRepository;

    public void requestRandomNumber(String xMaxWait) {
        ThreadPoolExecutor executorPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(randomNumberRepository.getThreadPoolSize());
        executorPool.execute(() -> processNumberGeneration(xMaxWait));
        executorPool.shutdown();
    }

    private void processNumberGeneration(String xMaxWait) {
        long startTime = System.currentTimeMillis();

        LOGGER.log(INFO, "Random number generation process initiated... ");
        LOGGER.log(INFO, "please wait {0} seconds", randomNumberRepository.getMinRequestTime());

        String requestId = UUID.randomUUID().toString();

        RandomNumber randomNumber = new RandomNumber();
        randomNumber.setRequestId(requestId);
        randomNumber.setSubmissionDate(new Date());
        randomNumber.setRequestState(PENDING);
        randomNumberRepository.add(randomNumber);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                randomNumberRepository.getByRequestId(requestId).setProcessingTime(System.currentTimeMillis() - startTime);
                if (xMaxWait != null) {
                    try {
                        if (Long.parseLong(xMaxWait) >= 31) {
                            long xMaxWaitInMillis = Long.parseLong(xMaxWait) * 1000;
                            if (xMaxWaitInMillis <= (System.currentTimeMillis() - startTime)) {
                                //cancel request if it reaches the defined X-Max-Wait
                                cancelRequest(requestId);
                                this.cancel();
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.log(SEVERE, "An error occurred when parsing X-Max-Wait value");
                        throw new NumberGeneratorException("An error occurred when parsing X-Max-Wait value", e);
                    }
                }
            }
        }, 0, 1000);

        try {
            TimeUnit.SECONDS.sleep(randomNumberRepository.getMinRequestTime());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }


        //validate if request is not cancelled
        validateRequest(requestId);

        long elapsedTime = System.currentTimeMillis() - startTime;
        timer.cancel();
        randomNumberRepository.getByRequestId(requestId).setProcessingTime(elapsedTime);

    }

    private void validateRequest(String requestId) {
        if (randomNumberRepository.getByRequestId(requestId).getRequestState().equals(PENDING)) {

            //Generate 128bit random number
            long generatedNumber = 0;
            Random random = new SecureRandom();
            byte[] aesKey = new byte[16]; // 16 bytes = 128 bits
            random.nextBytes(aesKey);
            while (generatedNumber <= 3) {
                generatedNumber = random.nextLong();
            }

            randomNumberRepository.getByRequestId(requestId).setRequestState(COMPLETED);
            randomNumberRepository.getByRequestId(requestId).setGeneratedNumber(new BigInteger("" + generatedNumber));

            LOGGER.log(INFO, "Random number generation process finished... ");
            LOGGER.log(INFO, "Generated number: {0}", generatedNumber);
        } else {
            LOGGER.log(WARNING, "Random Number Request was cancelled");
        }
    }


    public List<HistoryResponse> getHistory() {
        List<HistoryResponse> historyResponseList = new ArrayList<>();
        randomNumberRepository.listAll().stream().filter(r -> r.getRequestState().equals(COMPLETED)).forEach(randomNumber -> {
            historyResponseList.add(new HistoryResponse(randomNumber.getGeneratedNumber(), randomNumber.getFormattedProcessingTime()));
        });

        return historyResponseList;
    }


    public GenericResponse cancelRequest(String requestId) {
        RandomNumber randomNumber = randomNumberRepository.getByRequestId(requestId);
        if (randomNumber == null) {
            LOGGER.log(SEVERE, "Request with ID {0} not found", requestId);
            return new GenericResponse(404, NOT_FOUND, String.format("Request with ID %s not found", requestId));
        }
        if (!randomNumber.getRequestState().equals(PENDING)) {
            LOGGER.log(SEVERE, "Request with ID {0} is not pending anymore", requestId);
            return new GenericResponse(500, ERROR, String.format("Request with ID %s is not pending anymore", requestId));
        }
        randomNumber.setRequestState(CANCELED);
        return new GenericResponse(200, SUCCESS, String.format("Request with ID %s was canceled successfully", requestId));
    }


    public StatsResponse getStats() {
        RandomNumber maxWaitingTime = randomNumberRepository.listAll()
                .stream()
                .filter(randomNumber -> randomNumber.getRequestState().equals(COMPLETED))
                .max(Comparator.comparing(RandomNumber::getProcessingTime))
                .orElseThrow(NoSuchElementException::new);

        RandomNumber minWaitingTime = randomNumberRepository.listAll()
                .stream()
                .filter(randomNumber -> randomNumber.getRequestState().equals(COMPLETED))
                .min(Comparator.comparing(RandomNumber::getProcessingTime))
                .orElseThrow(NoSuchElementException::new);

        long pendingRequests = randomNumberRepository.listAll().stream().filter(randomNumber -> randomNumber.getRequestState().equals("pending")).count();

        return new StatsResponse(maxWaitingTime.getFormattedProcessingTime(), minWaitingTime.getFormattedProcessingTime(), pendingRequests);
    }


    public List<PendingResponse> getPending() {
        List<PendingResponse> pendingResponseList = new ArrayList<>();
        randomNumberRepository.listAll()
                .stream()
                .filter(r -> r.getRequestState().equals(PENDING)).forEach(number -> {
            pendingResponseList.add(new PendingResponse(number.getRequestId(), number.getSubmissionDate(), number.getFormattedProcessingTime()));
        });

        return pendingResponseList;
    }

    public GenericResponse setThreadSize(int size) {
        randomNumberRepository.setThreadPoolSize(size);
        return new GenericResponse(200, SUCCESS, String.format("Thread size was successfully changed to %s", size));
    }

    public GenericResponse setMinRequestDuration(long time) {
        randomNumberRepository.setMinRequestTime(time);
        return new GenericResponse(200, SUCCESS, String.format("Minimum random number generation request durantion was successfully changed to %s", time));
    }


    public String formatTime(long elapsedTime) {
        return String.format("%d min, %d sec, %d millis", TimeUnit.MILLISECONDS.toMinutes(elapsedTime), TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)), elapsedTime);
    }

}