# Horizon Radio

模拟《极限竞速：地平线》系列电台听感体验的 Windows 本地音乐播放器。

不只是播放音乐，而是在切歌时自动插入电台主持人语音，打造沉浸式个人音乐电台。

## 当前版本

**v0.1 (MVP)** — 基础播放 + 主持语音插播

## 快速开始

> 开发环境与构建指南将在 MVP 开发阶段补充。

## 项目文档

- [ARCHITECTURE.md](./ARCHITECTURE.md) — 系统架构与模块设计
- [ROADMAP.md](./ROADMAP.md) — 版本规划与里程碑
- [TODO.md](./TODO.md) — 当前任务清单
- [CHANGELOG.md](./CHANGELOG.md) — 版本变更记录
- [BUGS.md](./BUGS.md) — 已知问题
- [IDEAS.md](./IDEAS.md) — 灵感与未来想法
- [AGENTS.md](./AGENTS.md) — AI 辅助开发指南

## 设计原则

1. **播放器不是核心** — 播放器只负责播放与控制，不承担电台逻辑
2. **Radio Engine 独立** — 事件驱动的电台规则系统
3. **Audio Engine 独立** — 多音轨管理、音量控制、Fade/Ducking
4. **UI 与业务逻辑分离** — UI 仅展示状态与响应用户操作

## 技术栈

| 组件 | 选型 |
|------|------|
| 语言 | Java 17 |
| UI | JavaFX |
| 构建 | Gradle |
| 音频 | 待定（调研中） |

技术选型决策记录：[ADR-001](./docs/adr/001-tech-stack.md)

## 平台

- Windows 10/11

## 许可证

MIT
