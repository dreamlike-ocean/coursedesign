package com.course.service.score;

import java.util.List;

public interface UnstableScoreStrategy {
    List<Integer> types();

    long validateTimestamp();
}
