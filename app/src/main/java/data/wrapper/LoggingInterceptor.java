package data.wrapper;

import androidx.annotation.NonNull;

import java.io.IOException;

import java.util.logging.Logger;


import okhttp3.Interceptor;

import okhttp3.Request;

import okhttp3.Response;


public class LoggingInterceptor implements Interceptor {


    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());


    @NonNull
    @Override

    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        logger.info("Sending request: " + request.method() + " " + request.url());


        Response response = chain.proceed(request);

        logger.info("Received response: " + response.code() + " " + response.message());

        return response;

    }

}