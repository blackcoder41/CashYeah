package com.raketlabs.uhack.services.connection.controller;

import com.raketlabs.uhack.BuildConfig;
import com.raketlabs.uhack.services.connection.models.APIResponse;
import com.raketlabs.uhack.services.connection.models.ErrorData;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public abstract class BaseController<T extends Object> {
    private Retrofit retrofit;

    public T createService(Class clazz) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        // .addHeader("x-ibm-client-id", BuildConfig.IBM_CLIENT_ID)
                        // .addHeader("x-ibm-client-secret", BuildConfig.IBM_CLIENT_SECRET)
                        .addHeader("Content-Type", "application/json")
                        .build();

                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.ENVIRONMENT)
                .client(client)
                .build();

        return (T) retrofit.create(clazz);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    protected ErrorData isInvalidToken(retrofit2.Response<? extends APIResponse> response) {
        if (response != null && response.errorBody() != null) {
            Response rawResponse = response.raw();

            return new ErrorData(rawResponse.message());
        }

        return null;
    }
}
