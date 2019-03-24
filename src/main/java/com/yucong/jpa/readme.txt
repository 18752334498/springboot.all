在@Query中编写JPQL语句进行update或者delete时，必须使用@Modifying注解，以通知SpringData这是一个update或者delete操作。

在update或者delete操作时，需要使用事务；此时需要在Service实现类的方法上声明事务@Transactional。

Spring Data 提供了默认的事务处理方式，即所有的查询均声明为只读事务。

对于自定义的方法，如需改变 Spring Data 提供的事务默认方式，可以在方法上注解 @Transactional 声明 。

进行多个 Repository 操作时，也应该使它们在同一个事务中处理，按照分层架构的思想，这部分属于业务逻辑层，因此，需要在 Service 层实现对多个 Repository 的调用，并在相应的方法上声明事务。
