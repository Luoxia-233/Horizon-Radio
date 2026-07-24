# Horizon Radio Lab

一个用于学习 Agent 协作开发、Java/JavaFX、软件工程实践、架构设计、重构、测试以及开发流程的实验仓库。

**定位**：实验与学习，不作为最终产品。

**本项目不是**：自动生成软件的框架、软件工程知识教程、通用 AI Agent 开发框架。它只是个人开发者使用 AI 辅助开发时的一套约束模板。

**实验载体**：模拟《极限竞速：地平线》系列电台听感体验的本地音乐播放器——在切歌时自动插入电台主持人语音。

---

## 当前实验内容

基于 Java 17 + JavaFX + Gradle 已完成的 MVP：

- 本地音乐目录扫描与加载
- 播放列表显示（带当前播放高亮）
- 播放 / 暂停 / 下一首 / 上一首 / 随机播放
- 电台语音素材管理
- 事件驱动的 Radio Engine
- 状态机控制的播放流程
- 19 个源码文件，70 条单元测试

---

## 快速开始

### 环境要求

- JDK 17 (LTS)
- （Gradle 通过 Wrapper 自动下载）

### 构建

```bash
./gradlew build
```

### 运行

```bash
./gradlew run
```

### 测试

```bash
./gradlew test
```

---

## 项目文档

- [ARCHITECTURE.md](./ARCHITECTURE.md) — 当前实验架构
- [ROADMAP.md](./ROADMAP.md) — 实验路线图
- [TODO.md](./TODO.md) — 实验任务
- [CHANGELOG.md](./CHANGELOG.md) — 变更记录
- [BUGS.md](./BUGS.md) — 已知问题
- [IDEAS.md](./IDEAS.md) — 灵感与想法
- [AGENTS.md](./AGENTS.md) — AI 辅助开发指南

---

## 技术栈（当前实验）

| 组件 | 选型 |
|------|------|
| 语言 | Java 17 |
| UI | JavaFX |
| 构建 | Gradle |
| 音频 | JavaFX MediaPlayer |

技术选型决策记录：[ADR-001](./docs/adr/001-tech-stack.md)

---

## 后续

本仓库后续将继续用于 Agent 协作实验，可能包括：

- 架构重构实验
- 新增音频特性实验（Fade / Ducking）
- 测试策略实验
- 跨技术栈对比实验

正式产品版本将在未来独立仓库中开发，不要求与当前 Lab 代码兼容。

---

## 许可证

MIT
