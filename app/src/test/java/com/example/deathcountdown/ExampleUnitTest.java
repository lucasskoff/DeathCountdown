package com.example.deathcountdown;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private LocalDateTime startDate;
    private StartUpScreen screen;

    @Before
    public void setUp(){
        screen = new StartUpScreen();
        startDate = LocalDateTime.of(2020, 6, 25, 12, 00, 0);
    }

    @Test
    public void calculates0DaysLeft() {
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 25, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("00:00:00:00", days);
    }

    @Test
    public void calculates5DaysLeft() {
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 30, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("05:00:00:00", days);
    }

    @Test
    public void calculates365DaysLeft() {
        LocalDateTime endDate = LocalDateTime.of(2021, 6, 25, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("365:00:00:00", days);
    }

    @Test
    public void calculatesDaysLeftForYear2099() {
        LocalDateTime endDate = LocalDateTime.of(2099, 6, 25, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("28854:00:00:00", days);
    }

    @Test
    public void calculatesDaysLeftForYear2100() {
        LocalDateTime endDate = LocalDateTime.of(2100, 6, 25, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("29219:00:00:00", days);
    }

    @Test
    public void calculatesDaysLeftForYear2111() {
        LocalDateTime endDate = LocalDateTime.of(2111, 6, 25, 12, 00, 0);
        String days = screen.calculateRemainingTime(startDate, endDate);
        assertEquals("33236:00:00:00", days);
    }
}