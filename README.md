# JPA N+1 문제 학습 프로젝트

이 프로젝트는 JPA 사용 시 빈번하게 발생하는 **N+1 문제**의 원인을 이해하고, 이를 해결하기 위한 다양한 전략들을 실습하기 위해 구성되었습니다.

---

## 💡 N+1 학습을 위해 반드시 알아야 할 핵심 주제

### 1. JPA 핵심 동작 원리
N+1 문제는 JPA의 기본 동작 방식과 밀접하게 연관되어 있습니다.

*   **영속성 컨텍스트 (Persistence Context)**: JPA가 엔티티를 관리하는 환경입니다. 1차 캐시, 쓰기 지연, 변경 감지(Dirty Checking) 등을 통해 데이터베이스 접근을 최적화하지만, 연관 관계 로딩 시점과 맞물려 의도치 않은 쿼리를 발생시키기도 합니다.
    *   [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-persistence)
*   **프록시와 지연 로딩 (Proxy & Lazy Loading)**: JPA는 연관된 엔티티를 실제 사용하는 시점에 로딩하기 위해 '프록시' 객체를 사용합니다. 지연 로딩 설정 시, 연관 엔티티를 조회할 때마다 추가 쿼리가 발생하는 것이 N+1 문제의 핵심입니다.

### 2. N+1 문제 개요
*   **정의**: 1번의 쿼리로 N개의 데이터를 조회했는데, 연관된 데이터를 가져오기 위해 N번의 추가 조회가 발생하는 현상입니다.
*   **발생 지점**: 주로 `@OneToMany`나 `@ManyToOne`과 같은 연관 관계에서 데이터를 순회하며 사용할 때 발생합니다.

### 3. Fetch 전략과 N+1
*   **Fetch Type (EAGER vs LAZY)**:
    *   `EAGER` (즉시 로딩): 엔티티를 조회할 때 연관된 엔티티도 즉시 함께 조회합니다. JPA 구현체에 따라 Join을 쓰기도 하지만, JPQL 실행 시에는 N+1이 발생하기 쉽습니다.
    *   `LAZY` (지연 로딩): 연관된 엔티티를 실제 사용할 때 조회합니다. 실무에서는 가급적 모든 연관 관계를 `LAZY`로 설정할 것을 권장합니다.
    *   [Spring Data JPA - Fetching Strategies](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation)

### 4. N+1 해결 전략
*   **Fetch Join**: JPQL에서 `join fetch` 키워드를 사용하여 연관된 엔티티를 한 번의 Join 쿼리로 함께 조회하는 방식입니다. 가장 많이 사용되는 해결책입니다.
*   **@EntityGraph**: 어노테이션을 통해 특정 쿼리 메서드에서 연관된 엔티티를 함께 로딩(EAGER)하도록 지정하는 방식입니다.
    *   [Spring Data JPA - EntityGraph](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-graph)
*   **Batch Size**: `hibernate.default_batch_fetch_size` 설정을 통해 연관된 엔티티를 조회할 때 지정된 크기만큼 `where in` 절을 사용하여 묶어서 조회합니다. N번 발생할 쿼리를 (N/BatchSize)번으로 줄일 수 있습니다.
    *   [Hibernate User Guide - Batch Fetching](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#fetching-batch)

---

## 🛠 실습 단계
1.  `EAGER` 전략에서 발생하는 N+1 확인
2.  `LAZY` 전략과 프록시 객체의 동작 확인
3.  `Fetch Join`을 이용한 해결
4.  `@EntityGraph`를 이용한 해결
5.  `Batch Size` 설정을 통한 최적화
