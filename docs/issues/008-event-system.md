# #008 实现事件系统

## Background

所有广播行为需要事件驱动。需要基础事件系统。

## Goal

实现事件总线，定义核心事件类型。

## Scope

### 包含

- EventBus 基础实现
- SongChanged 事件
- PlaylistStarted 事件
- PlaylistEnded 事件
- ManualSkip 事件
- PlayPause 事件

### 不包含

- LongListening（v0.4）
- 事件持久化
- 复杂事件过滤

## Technical Notes

使用 Java 原生机制实现（接口 + 回调 / Observable 模式）。

## Acceptance Criteria

- [ ] EventBus 可以注册和触发事件
- [ ] 6 种核心事件定义完成
- [ ] 多个订阅者可同时接收事件
- [ ] 有对应的单元测试

## Related

- ARCHITECTURE.md (事件系统)
- ROADMAP v0.1
