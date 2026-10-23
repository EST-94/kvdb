package kvdb;

import java.util.Optional;

/**
 * 키-값 저장소의 공통 인터페이스.
 * 1주차 InMemoryStore, 2주차 LogFileStore, 3주차 LsmStore가 모두 이 인터페이스를 구현한다.
 * 테스트는 이 인터페이스만 보고 작성하므로, 구현을 바꿔도 같은 테스트로 검증할 수 있다.
 */
public interface KeyValueStore extends AutoCloseable { // [임시] close()는 2주차 파일 대비 (미확정)

    /** 저장소의 기본 크기 제한: 512MB (생성자에서 변경 가능). */
    int DEFAULT_MAX_SIZE = 512 * 1024 * 1024;

    /**
     * 키에 값을 저장한다. 같은 키가 있으면 덮어쓴다.
     * [임시] 반환값 없음(void) - LSM에서 이전 값 조회 비용을 피하기 위함 (미확정)
     *
     * @throws IllegalArgumentException key나 value가 null이거나 크기 제한을 넘을 때
     */
    void put(byte[] key, byte[] value);

    /**
     * 키에 해당하는 값을 조회한다.
     *
     * @return 값이 있으면 Optional.of(값의 복사본), 없으면 Optional.empty()
     * @throws IllegalArgumentException key가 null이거나 크기 제한을 넘을 때
     */
    Optional<byte[]> get(byte[] key);

    /**
     * 키를 삭제한다. 없는 키를 삭제해도 에러를 내지 않는다.
     *
     * @return 실제로 지웠으면 true, 원래 없었으면 false
     * @throws IllegalArgumentException key가 null이거나 크기 제한을 넘을 때
     */
    boolean delete(byte[] key);

    /** 1주차 구현에서는 할 일이 없다. 2주차부터 열린 파일을 닫는다. */
    @Override
    void close();
}
