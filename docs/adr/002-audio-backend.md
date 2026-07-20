# ADR-002: 音频后端选型

## 状态

已采纳

## 日期

2026-07-20

## 决策

| 组件 | 选型 |
|------|------|
| 音频后端 | JavaFX MediaPlayer |
| 备选方案 | VLCJ (vlcj-4.8.x + VLC 3.x) |

## 背景

Horizon Radio 需要音频播放能力，核心需求从 MVP 的基础播放逐步升级到 v0.3 的双音轨 Ducking。

## 评估方案

详见 [docs/research/audio-backend.md](../research/audio-backend.md)。

| 方案 | 核心优势 | 核心劣势 |
|------|----------|----------|
| JavaFX MediaPlayer | 零依赖、API 简洁、Fade 天然支持 | FLAC/AAC 支持有限 |
| VLCJ | 万能格式、功能全面 | 需捆绑 VLC、线程模型复杂、GPL |
| Java Sound | 零依赖 | 过于底层、手写 Fade/Ducking 不现实 |

## 决定

选择 **JavaFX MediaPlayer**。

## 理由

1. 项目已依赖 JavaFX（UI），MediaPlayer 是同一 ecosystem 的组成部分
2. `volumeProperty` + `Timeline` 组合天然适合 Fade In/Out 和 Ducking
3. 分布式零额外依赖——用户无需安装 VLC
4. Audio Engine 通过 `IAudioEngine` 接口暴露，架构设计允许未来替换为 VLCJ

## 代价

- FLAC / AAC 格式支持依赖 Windows codec，需实测验证
- 不支持高级 DSP 处理（本项目不需要）
- Media 实例绑定不可变——切歌时需新建 MediaPlayer

## 触发重审条件

- FLAC 或 AAC 在 Windows 10/11 上不可用
- 双 MediaPlayer 实例出现资源竞争或延迟问题
- 需要 JavaFX MediaPlayer 无法提供的能力

满足任一条件时，评估切换到 VLCJ。

## 参考

- [docs/research/audio-backend.md](../research/audio-backend.md)
- ADR-001 (技术栈选型)
- Issue #004 (音频后端调研)
- Issue #005 (基础播放器实现)
