package test;

import com.example.bhushan.data_lib.core.model.Clock;
import com.example.bhushan.data_lib.core.model.WeatherInteractor;
import com.example.bhushan.data_lib.core.model.WeatherInteractorImpl;
import com.example.bhushan.data_lib.core.services.WeatherApiServices;
import com.example.bhushan.data_lib.core.util.CacheProvider;
import com.example.bhushan.data_lib.core.util.Constants;
import com.example.bhushan.data_lib.core.util.SchedulerProvider;
import com.example.bhushan.data_lib.model.CurrentWeather;
import com.example.bhushan.data_lib.model.WeatherForecast;
import com.example.bhushan.data_lib.model.WeatherOverall;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherInteractorTest {
    private WeatherInteractor weatherInteractor;
    private WeatherApiServices weatherApiService;
    private CacheProvider cacheProvider;
    private SchedulerProvider schedulerProvider;
    private Clock clock;
    private WeatherOverall expectedResult;
    private WeatherOverall nonExpectedResult;
    private WeatherOverall newExpectedResult;

    @Before
    public void setup() {
        weatherApiService = mock(WeatherApiServices.class);
        cacheProvider = mock(CacheProvider.class);
        schedulerProvider = mock(SchedulerProvider.class);
        clock = mock(Clock.class);

        expectedResult = new WeatherOverall(new CurrentWeather(), new WeatherForecast());
        expectedResult.getmCurrentWeather().setName("Berlin");
        expectedResult.getmCurrentWeather().setDt(0L);

        nonExpectedResult = new WeatherOverall(new CurrentWeather(), new WeatherForecast());
        nonExpectedResult.getmCurrentWeather().setName("Tehran");
        nonExpectedResult.getmCurrentWeather().setDt(0L);

        newExpectedResult = new WeatherOverall(new CurrentWeather(), new WeatherForecast());
        newExpectedResult.getmCurrentWeather().setName("Berlin");
        newExpectedResult.getmCurrentWeather().setDt(Constants.STALE_MS + 1);

        when(cacheProvider.saveWeather(any())).thenReturn(Observable.just(null));

        when(schedulerProvider.mainThread()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.backgroundThread()).thenReturn(Schedulers.immediate());

        weatherInteractor = new WeatherInteractorImpl(cacheProvider, weatherApiService, schedulerProvider, clock);
        weatherInteractor.clearMemoryAndDiskCache();
    }

    @Test
    public void testHitsMemoryCache() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherOverall> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(cacheProvider.getWeather()).thenReturn(Observable.just(nonExpectedResult));
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Memory before checking cache or Network, cause the answer is there
        TestSubscriber<WeatherOverall> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

    @Test
    public void testHitsDiskCache() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherOverall> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        weatherInteractor.clearMemoryCache();
        when(cacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Cache after checking Memory which is null
        TestSubscriber<WeatherOverall> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

    @Test
    public void testCacheExpiry() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(newExpectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(clock.millis()).thenReturn(0L);

        // load weather from Cache but is not expired yet
        TestSubscriber<WeatherOverall> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS - 1);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherOverall> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherOverall> testSubscriberThird = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberThird);
        testSubscriberThird.assertNoErrors();
        testSubscriberThird.assertReceivedOnNext(Collections.singletonList(newExpectedResult));
    }

}
