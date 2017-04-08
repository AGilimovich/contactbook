package com.itechart.web.service.scheduler;

/**
 * Interface of service for scheduling jobs.
 */
public interface AbstractSchedulingService {
    void startScheduler(int hours, int minutes);
}
