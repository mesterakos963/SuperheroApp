package app.superhero.src.api;

import com.squareup.moshi.Moshi;

import org.androidannotations.annotations.EBean;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@EBean(scope = EBean.Scope.Singleton)
public class ApiClient {
    private Retrofit retrofit;

    public Retrofit getClient() {
        if (retrofit == null) {

            Moshi moshi = new Moshi.Builder().add(new ZeroWhenNullAdapter()).build();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://superheroapi.com/api/2915390945376495/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
