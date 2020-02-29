package me.ledis.schedule;

import me.ledis.data.LedisData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {
    @Scheduled(fixedDelay = 1000)
    public void removeExpiredKey() {
        if (LedisData.hasNoExpireTimeSet()) {
            return;
        }
        for (String key : LedisData.getAllKeyWithTimeout()) {
            LocalDateTime triggerTime = LocalDateTime.now();
            LocalDateTime expireTime = LedisData.getExpireTime(key);
            if (triggerTime.isEqual(expireTime) || triggerTime.isAfter(expireTime)) {
                LedisData.remove(key);
                LedisData.removeExpireTime(key);
            }
        }
    }
}
