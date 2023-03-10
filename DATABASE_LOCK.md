* synchronized로 해결한 경우의 문제점 : 서버 1대라면 상관없지만, 서버 여러대라면 해결이 불가하다.

* DB Lock으로 해결해보자.
  * Pessimistic Lock (로우나 테이블 단위로 락)
    * 실제로 데이터에 Lock을 걸어서 정합성을 맞추는 방법. 
    * exclusive lock을 걸게되면 다른 트랜잭션에서는 lock이 해제되기전에 데이터를 가져갈 수 없게됨.
    * 데드락이 걸릴 수 있으므로 주의하여 사용해야 함.
    * 충돌이 빈번하다면 Optimistic Lock 보다 성능이 좋을 수 있음.
  * Optimistic Lock
    * 실제로 Lock을 이용하지 않고 버전을 이용함으로써 정합성을 맞추는 방법.
    * 먼저 데이터를 읽은 후에 update를 수행할 때 현재 내가 읽은 버전이 맞는지 확인하여 업데이트 함.
    * 내가 읽은 버전에서 수정사항이 생겼을 경우에는 application에서 다시 읽은후에 작업을 수행해야 함.
    * 장점 : 별도의 락을 잡지 않으므로 성능이 좋음.
    * 단점 : 실패 시 재시도 로직을 개발자가 직접 작성해야 함 (Facade 만든거처럼) 또한 충돌이 빈번하다면 성능이 떨어질 수 있음.
  * Named Lock (메타데이터에 락(Pessimistic Lock과 차이점))
    * 이름을 가진 metadata locking. 이름을 가진 락을 획득한 후 해제할때까지 다른 세션은 이 락을 획득할 수 없도록 함
    * 주의할점으로는 트랜잭션이 종료될 때 락이 자동으로 해제되지 않음.
    * 별도의 명령어로 해제해주거나 선점시간이 끝나야 해제됨.
    * 자원 선점이 가능하고 + 사용중엔 접근이 불가능 = 데드락 주의해야함.
    * 아무튼 별도의 datasource를 쓰는게 추천되므로 (커넥션 풀 때문에) 그럴꺼면 레디스쓰지 느낌.