package com.example.rxjava;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Mock
    List<User> userArrayList;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void getUserData() {

        TestObserver<List<User>> testSubscriber = TestObserver.create();
        Observable<List<User>> userObservable = RestApi.getInstance().getUserLists();
        userArrayList = new ArrayList<>();
        userObservable.subscribe(testSubscriber);
        testSubscriber.assertSubscribed();
        testSubscriber.awaitCount(1);
        testSubscriber.assertValueCount(1);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
    }
}