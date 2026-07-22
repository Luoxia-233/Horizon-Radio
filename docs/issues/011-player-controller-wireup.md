# #011 Player Controller 组装

## Background

所有独立模块需要协调工作。需要 Player Controller 将各部分串联起来。

## Goal

实现 Player Controller，连接 MusicLibrary、PlaylistQueue、AudioEngine、RadioEngine、EventBus。

## Scope

### 包含

- PlayerController 协调所有模块
- 完整播放流程：选择目录 → 加载 → 播放 → 切歌 → 语音插播 → 下一首
- 状态机正确驱动
- 事件正确分发
- IAudioEngine 扩展：增加 `onEnded` 回调（#005 Review 发现遗漏，播放结束无法通知 PlayerController）

### 不包含

- Fade / Ducking
- 多电台频道
- 配置持久化

## Technical Notes

这是 MVP 的最后一块拼图。完成后应可端到端运行。参考 docs/design/MVP_FLOW.md。

## Acceptance Criteria

- [ ] 端到端流程正常：加载音乐 → 播放 → 语音插播 → 下一首
- [ ] 手动切歌触发语音插播
- [ ] 随机播放开关生效
- [ ] 状态机状态变化正确
- [ ] 不崩溃

## Related

- 所有前置 Issue (#001-#010)
- docs/design/MVP_FLOW.md
- ROADMAP v0.1 (MVP 完成)
