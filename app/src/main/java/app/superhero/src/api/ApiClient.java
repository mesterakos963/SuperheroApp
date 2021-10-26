package app.superhero.src.api;

import com.squareup.moshi.Moshi;

import org.androidannotations.annotations.EBean;

import app.superhero.src.interfaces.SuperheroesService;
import app.superhero.src.utils.ZeroWhenNullAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@EBean(scope = EBean.Scope.Singleton)
public class ApiClient {
    private final String URL = "https://www.superheroapi.com/api.php/2915390945376495/";
    SuperheroesService superheroesService;
    private Retrofit retrofit;

    public Retrofit getClient() {
        if (retrofit == null) {

            Moshi moshi = new Moshi.Builder().add(new ZeroWhenNullAdapter()).build();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public SuperheroesService getSuperheroesService() {
        if (superheroesService == null) {
            return superheroesService = this.getClient().create(SuperheroesService.class);
        } else {
            return superheroesService;
        }
    }
}
