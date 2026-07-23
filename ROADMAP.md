# 实验路线图

> 本路线图面向实验与学习，非产品发布计划。

---

## Phase 1: JavaFX MVP — 已完成

> 目标：用 Java 17 + JavaFX 验证完整的播放器 + 电台插播交互流程

- [x] 本地音乐加载与播放
- [x] 播放列表 UI + 控制按钮
- [x] 状态机管理播放状态
- [x] 事件驱动的 Radio Engine
- [x] 语音素材管理与插播
- [x] Agent 协作流程验证（Issue → 设计 → 编码 → Review）

**收获**：验证了分层架构、事件驱动、接口隔离在 Java 桌面应用中的可行性。

---

## Phase 2: Audio Fade Experiment

> 目标：验证音频淡入淡出方案

- [ ] Fade Out 实现（JavaFX Timeline + volumeProperty）
- [ ] Fade In 实现
- [ ] 语音 + 音乐衔接时序验证

**实验对象**：JavaFX MediaPlayer 的多实例 + Timeline 能力

---

## Phase 3: Audio Ducking Experiment

> 目标：验证双音轨独立音量控制

- [ ] 语音播放时音乐降低音量
- [ ] 语音结束后恢复原音量
- [ ] 平滑过渡效果

**实验对象**：双 MediaPlayer 实例 + 音频混音策略

---

## Phase 4: Architecture Refactor Experiment

> 目标：验证架构的可维护性和可测试性

- [ ] 尝试替换 Audio Engine 实现（如 VLCJ）
- [ ] 尝试分离 RadioEngine 规则配置
- [ ] 增加特定场景的集成测试

---

## 未来实验方向

- [ ] 跨技术栈对比实验（Flutter / Compose Multiplatform 等）
- [ ] 测试策略实验
- [ ] 持久化配置实验
