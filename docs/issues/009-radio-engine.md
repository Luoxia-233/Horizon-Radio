# #009 实现 RadioEngine 框架

## Background

Radio Engine 是核心逻辑层，负责监听事件、决定是否播放主持语音。

## Goal

实现 RadioEngine 框架，连接事件系统与语音素材。

## Scope

### 包含

- RadioEngine 监听 SongChanged / ManualSkip 事件
- 决定是否播放语音（可先定为"每次切歌都播放"）
- 从素材池随机选取语音
- 通知 Audio Engine 播放语音
- VoiceManager 类（扫描 voice 目录、按类别管理素材）

### 不包含

- 复杂的广播规则（v0.4）
- Fade / Ducking
- 素材元数据编辑

## Technical Notes

RadioEngine 不得依赖 Player 实现，仅依赖事件和接口。

## Acceptance Criteria

- [ ] 收到 SongChanged 事件时触发语音播放流程
- [ ] 语音素材随机选取
- [ ] 语音播放完毕后触发下一首歌曲播放
- [ ] VoiceManager 正确扫描素材目录
- [ ] 有对应的单元测试

## Related

- Issue #008 (事件系统)
- Issue #005 (基础播放器)
- ROADMAP v0.1
