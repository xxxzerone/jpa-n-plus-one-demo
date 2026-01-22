# Project Context
## Goal
- JPA N+1 문제 학습 및 해결 방법 실습.
- 도메인 복잡도를 최소화하여 N+1 문제의 원인과 해결책에 집중.

## Domain Model
- **Simple Structure**: N+1 이해를 돕기 위해 1:N, N:1 관계를 가진 간단한 엔티티 구성 (예: Member - Team, Post - Comment 등).

## Tech Stack
- **Framework**: Spring Boot 4.0.1
- **Language**: Java 17
- **Core Modules**: Spring Web, Spring Data JPA
- **Database**: H2 Database (Embedded)
- **Tooling**: Lombok

## Architecture
- **Layered Architecture**:
  - `Controller`: API 엔드포인트 처리.
  - `Service`: 비즈니스 로직 및 트랜잭션 관리.
  - `Repository`: Spring Data JPA를 이용한 데이터 접근.

## Guidelines
1. **Clear Demonstration**: N+1 문제가 발생하는 시나리오를 명확히 코드로 작성하고, 이를 해결하는 과정을 단계별로 보여준다.
2. **Simple Implementation**: 불필요한 DTO 변환이나 복잡한 검증 로직은 최소화한다.
3. **Testing**: 테스트 코드를 통해 쿼리 실행 횟수를 검증하거나 로그를 확인하는 방법을 포함한다.