package kvdb;

import java.util.Arrays;
import java.util.HexFormat;

/**
 * byte[]를 맵의 키로 쓰기 위한 래퍼.
 * 배열은 equals/hashCode가 객체 주소 기준이라 그대로 키로 쓰면 조회가 실패한다.
 * 이 클래스는 (1) 내용 기준으로 비교하고 (2) 방어적 복사로 키가 바뀌지 않게 지킨다.
 * 구현 내부에서만 쓰고, 인터페이스 바깥에는 노출하지 않는다.
 */
record Key(byte[] bytes) {

    /** 생성 시 복사본을 저장해서 호출한 쪽이 원본 배열을 바꿔도 영향을 받지 않는다. */
    Key {
        if (bytes == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        bytes = bytes.clone();
    }

    /** 내부 배열을 그대로 내주면 바깥에서 바꿀 수 있으므로 복사본을 돌려준다. */
    @Override
    public byte[] bytes() {
        return bytes.clone();
    }

    /** record 기본 equals는 배열을 주소로 비교하므로 내용 비교로 재정의한다. */
    @Override
    public boolean equals(Object o) {
        return o instanceof Key other && Arrays.equals(bytes, other.bytes);
    }

    /** equals와 짝을 맞춰 내용으로 해시값을 계산한다. */
    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "Key[" + HexFormat.of().formatHex(bytes) + "]";
    }
}
