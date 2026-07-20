# ADR-001: 技术栈选型

## 状态

已采纳

## 日期

2026-07-20

## 决策

| 组件 | 选型 |
|------|------|
| 语言 | Java 17 |
| UI 框架 | JavaFX |
| 构建工具 | Gradle |
| 音频后端 | 待定（见 Issue #004） |

## 背景

Horizon Radio 是一个 Windows-first 的本地音乐播放器项目。需要在 MVP 阶段开始前冻结技术栈。

## 评估方案

### 方案 A: C# + WPF + NAudio

- **优势:** 原生 Windows 体验、音频生态成熟（NAudio）、桌面 UI 一流
- **劣势:** 仅 Windows、学习成本（如不熟悉 C#）
- **结论:** 最适合「只需做出产品」的场景

### 方案 B: Java 17 + JavaFX

- **优势:** 已有知识储备、学习闭环（与 CS61B 互补）、LTS 版本、生态稳定
- **劣势:** Java 音频生态不如 C# 统一、JavaFX 打包较繁琐
- **结论:** 最适合「第一次完整项目开发经验」的场景

### 方案 C: Electron / Tauri

- **优势:** 生态最丰富、UI 灵活
- **劣势:** 体积大（Electron）、学习曲线陡（Tauri/Rust）
- **结论:** 不适合本项目的简化目标

## 决定

选择 **Java 17 + JavaFX**。

## 理由

1. 项目目标不仅是做出播放器，更是完整体验一次软件工程流程
2. 已有 Java 知识可直接转化为生产力
3. 架构分层设计使得未来替换 UI 或音频后端成为可能
4. 与 CS61B 学习形成闭环

## 代价

- 音频后端需要额外技术调研（Java Sound API / JavaFX MediaPlayer / VLCJ / FFmpeg wrapper）
- Windows 原生整合不如 WPF
- JavaFX 分发需要 JRE 或 jlink 打包

## 参考

- ROADMAP v0.1
- Issue #004 (Audio Backend Research)
