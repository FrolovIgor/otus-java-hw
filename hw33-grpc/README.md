# Grpc client-server

### Порядок запуска
- Сервер: запустить файл `NumberSequenceServer`
- Клиент: запустить файл `NumberSequenceClient`

### Пример вывода в консоль работы клиента

```
08:56:13.324 [main] INFO  r.o.grpc.client.NumberSequenceClient - Client starting, wait information ...
08:56:13.438 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 1
08:56:13.609 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 1

08:56:14.444 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 3
08:56:15.445 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 4
08:56:15.601 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 2

08:56:16.446 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 7
08:56:17.449 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 8
08:56:17.605 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 3

08:56:18.455 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 12
08:56:19.459 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 13
08:56:19.601 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 4

08:56:20.465 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 18
08:56:21.471 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 19
08:56:21.608 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 5

08:56:22.476 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 25
08:56:23.480 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 26
08:56:23.606 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 6

08:56:24.486 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 33
08:56:25.488 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 34
08:56:25.601 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 7

08:56:26.492 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 42
08:56:27.497 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 43
08:56:27.608 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 8

08:56:28.499 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 52
08:56:29.505 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 53
08:56:29.607 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 9

08:56:30.507 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 63
08:56:31.513 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 64
08:56:31.606 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 10

08:56:32.516 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 75
08:56:33.518 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 76
08:56:33.604 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 11

08:56:34.519 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 88
08:56:35.522 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 89
08:56:35.601 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 12

08:56:36.526 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 102
08:56:37.531 [main] INFO  r.o.grpc.client.NumberSequenceClient - currentValue: 103
08:56:37.602 [grpc-default-executor-0] INFO  r.o.g.c.SequenceResponseObserver - Number from server: sequenceValue: 13
```