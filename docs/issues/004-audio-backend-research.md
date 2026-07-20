# #004 音频后端技术调研

## Background

Java 音频生态不如 C# 统一。需要在编写 AudioEngine 前确定音频播放方案。

## Goal

确定 Java 平台播放 MP3/WAV/FLAC/AAC 的最佳方案。

## Scope

### 包含

- 评估 Java 音频播放候选方案
- 验证各方案的播放能力
- 验证音量控制能力
- 验证多音轨能力（未来 v0.3 Ducking 需要）
- 验证 Fade In/Out 能力（未来 v0.2 需要）

### 不包含

- 正式实现 AudioEngine
- UI 集成
- Radio Engine 集成

## 候选方案

| 方案 | 说明 | 依赖 |
|------|------|------|
| Java Sound API (javax.sound) | JDK 内置 | 无 |
| JavaFX MediaPlayer | JavaFX 内置 | JavaFX |
| VLCJ | LibVLC Java 绑定 | VLC 安装 |
| JLayer / MP3SPI | MP3 解码库 | 外部 jar |

## 评估维度

- [ ] MP3 播放能力
- [ ] WAV 播放能力
- [ ] FLAC 播放能力
- [ ] AAC 播放能力
- [ ] 播放进度获取
- [ ] 播放结束回调
- [ ] 音量控制
- [ ] 多音轨同时播放
- [ ] Fade In/Out 可行性
- [ ] Windows 兼容性

## Acceptance Criteria

- [ ] 至少一种方案通过所有评估维度
- [ ] 输出调研报告（方案对比表 + 推荐方案 + POC 代码）
- [ ] POC 代码能够播放一首 MP3

## Related

- ADR-001 (技术栈选型)
- Issue #005 (基础播放器)
- ROADMAP v0.1
