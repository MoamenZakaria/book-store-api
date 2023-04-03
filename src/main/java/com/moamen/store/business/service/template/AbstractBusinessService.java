package com.moamen.store.business.service.template;

import com.moamen.store.exceptions.BookStoreException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBusinessService<Request, Response> {

    public final Response process(Request request) {
        String serviceName = getServiceName();
        long startTime = System.currentTimeMillis();
        validate(request);
        try {
            Response response = execute(request);
            log(request, response);
            log.info("Service: {} took : {} Millisecond", serviceName, System.currentTimeMillis() - startTime);
            return response;
        } catch (BookStoreException exp) {
            handleFailure(exp, request);
            throw exp;
        }
    }

    protected abstract String getServiceName();

    protected abstract void validate(Request request);

    protected abstract Response execute(Request request);

    protected void log(Request request, Response response) {
        log.info("Request: {} \n Response: {}", request, response);
    }

    protected void handleFailure(BookStoreException exp, Request request) {
        log.error("Error: {} happened in service {}", exp.getMessage(), getServiceName());
    }
}

