package nextstep.ladder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {
    @Test
    void create() {
        assertThat(new Line(Spork.of(true, false, true))).isNotNull();
    }

    @DisplayName("오른쪽에 발판이 존재하는지 알 수 있다")
    @Test
    void hasSporkRightSide() {
        assertThat(new Line(Spork.of(true, false, true)).hasSporkRightSide(Position.of(0))).isTrue();
    }

    @DisplayName("발판은 갯수로 생성할 수 있다.")
    @Test
    void createSpokeFromCount() {
        assertThat(Spork.fromCount(4)).isNotNull();
    }

    /**
     * |-----|     |-----| 의 경우 첫번째는 1~2 구간에는 발판이 있고 2~3구간에는 발판이 없고 3~4 구간에는 발판이 있다.
     * 이를 Spork.of(true, false, true) 로 표현한다
     */
    private static class Spork {
        private final List<Boolean> list = new ArrayList<>();
        private static final Random RANDOM = new Random();
        public static Spork of(boolean... existsSpoke) {
            Spork spork = new Spork();
            for (boolean value : existsSpoke) {
                spork.add(value);
            }
            return spork;
        }

        public static Spork fromCount(int count) {
            return IntStream.of(count)
                    .mapToObj(number -> nextBoolean())
                    .collect(Spork::new, Spork::add, Spork::addAll);
        }

        private static boolean nextBoolean() {
            return RANDOM.nextBoolean();
        }

        private static void addAll(Spork right, Spork left) {
            throw new UnsupportedOperationException("병렬처리는 지원하지 않습니다.");
        }

        private void add(Boolean next) {
            if (list.isEmpty()) {
                list.add(next);
                return;
            }

            Boolean previousBoolean = last();
            if (previousBoolean) {
                list.add(Boolean.FALSE);
                return;
            }

            list.add(next);
        }

        private Boolean last() {
            return list.get(list.size() - 1);
        }
    }

    private static class Line {
        public Line(Spork spork) {
        }

        public boolean hasSporkRightSide(Position position) {
            return true;
        }
    }

    private static class Position {
        public static Position of(int position) {
            return null;
        }
    }
}
