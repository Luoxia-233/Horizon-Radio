# #007 实现播放器状态机

## Background

播放器需要明确的状态管理，避免大量布尔变量。

## Goal

实现 Player State Machine，管理播放器状态。

## Scope

### 包含

- 状态定义: Idle / Playing / Transition / VoicePlaying / Paused
- 状态转移逻辑
- 暴露当前状态供 UI 订阅
- PlayerStateChanged 事件

### 不包含

- 实际的音频控制
- Radio Engine 逻辑
- UI 实现

## Technical Notes

状态转移规则参考 ARCHITECTURE.md 中的状态机图。

## Acceptance Criteria

- [ ] 5 种状态定义完成
- [ ] 状态转移逻辑正确
- [ ] 非法转移被拒绝（如 Playing → Idle 不经停顿）
- [ ] 状态变化时触发事件
- [ ] 有对应的单元测试

## Related

- ARCHITECTURE.md (状态机图)
- docs/design/MVP_FLOW.md (状态转移约束)
- Issue #005 (基础播放器)
- ROADMAP v0.1
