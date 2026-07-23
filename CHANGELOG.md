# 变更日志

> 本记录属于 Horizon Radio Lab 阶段。未来正式产品将从新仓库开始。

## [Unreleased]

### 2026-07-23 — 修复：Review 问题修复

- 修复无语音素材时状态机卡死问题
- 修复语音播放中手动切歌循环问题
- 串联 RadioEngine.onVoiceEnded 回调
- 抽取 AudioFormat 工具类消除重复代码
- VoiceManager 改用 ThreadLocalRandom
- 更新 .gitignore、BUGS.md

### 2026-07-20 — 实验：工程初始化

- Issue #001 完成 — Gradle 项目建立、包结构创建、JavaFX 入口
- 文档体系建立 (README / ARCHITECTURE / ROADMAP / AGENTS / TODO / CHANGELOG / BUGS / IDEAS)
- Issue 拆分 (001–011)
- ADR-001: 技术栈冻结 — Java 17 + JavaFX + Gradle
- ADR-002: 音频后端选型 — JavaFX MediaPlayer
- MVP 核心流程设计 (docs/design/MVP_FLOW.md)

### v0.1 (MVP) — 已完成

- [x] #001 初始化工程
- [x] #002 创建 Song 数据模型
- [x] #003 实现 MusicLibrary（递归扫描 + 格式过滤）
- [x] #004 音频后端技术调研
- [x] #005 IAudioEngine 接口 + JavaFX MediaPlayer 实现
- [x] #006 PlaylistQueue（顺序/随机/循环/模式切换不中断当前）
- [x] #007 PlayerState + StateMachine（5 状态 + 转移验证）
- [x] #008 泛型 EventBus + 4 种事件类型
- [x] #009 RadioEngine + VoiceManager（事件驱动语音插播）
- [x] #010 MainWindow UI + PlayerController 接口契约
- [x] #011 PlayerControllerImpl 多模块集成 + 端到端流程
