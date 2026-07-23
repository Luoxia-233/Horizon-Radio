# AI 辅助开发指南

本文档为 AI 编程助手（如 Claude、GPT、Copilot 等）提供项目上下文与编码规范。

## 项目概述

Horizon Radio Lab 是一个实验与学习仓库，用于验证 Agent 协作开发、Java/JavaFX、软件架构和工程实践。不作为最终产品。

> **所有开发任务均需遵循 [`docs/agent/LEARNING_MODE.md`](docs/agent/LEARNING_MODE.md) 的协作规范。**

> **所有 Issue 进入编码前需完成 [`docs/agent/ISSUE_WORKFLOW.md`](docs/agent/ISSUE_WORKFLOW.md) 中 Step 0-3 的讨论与确认流程。**

---

## Lab First Principle

对于 Lab 仓库，优先验证技术路线、架构设计和 Agent 协作方式，而不是追求功能完整性或产品完成度。

- 允许实验、重构和推翻已有实现
- 保留清晰的文档和 Git 历史以记录决策过程和学习成果
- 判断标准："这个实验让我学到了什么？"而非"这个功能完整吗？"

---

## Agent 角色定义

Agent 承担以下角色：

- 实现助手 — 按 Issue 规范编写代码
- 设计评审 — 提出候选方案供用户决定
- 技术解释 — 按 LEARNING_MODE.md 要求逐层讲解

Agent **不替代**以下决策：

- 产品决策（做什么功能、不做功能）
- 架构决策（模块如何划分、依赖方向）
- 范围决策（当前 Issue 做多少、不做多少）

不确定时，先问再实现。

---

## Agent 权限分级

### Level 1 — 自动允许

以下操作 Agent 可直接执行，无需确认：

- 在所属模块内新增实现类
- 新增测试文件
- 修复明显的局部 Bug（如空指针判断反了）
- 补充文档（README 运行说明、API 文档等）
- 遵循现有模式的代码重构（提取方法、重命名局部变量等）

### Level 2 — 需先说明方案

以下操作 Agent 必须在执行前输出方案并等待确认：

- 添加第三方依赖（NuGet / npm / Cargo 等）
- 修改已有公共接口（interface / abstract class / public API）
- 修改或创建配置结构（settings.json / playlist.json / metadata.db 等）
- 移动文件或改变目录结构
- 一次修改超过 3 个模块

### Level 3 — 必须询问

以下操作 Agent 不得自主决定：

- 改变架构（例如删除 RadioEngine 层、合并模块）
- 替换技术栈（例如 WPF → Electron）
- 大规模重构（超过 5 个文件、超过 300 行）
- 删除大量代码（超过 50 行连续删除）
- 修改 ARCHITECTURE.md / ROADMAP.md / README 中的项目目标

---

## 任务工作流

### 上下文同步协议

每个新 Session 的第一条消息建议固定为：

```
请先阅读：

README.md
ARCHITECTURE.md
AGENTS.md
docs/issues/ 目录下当前正在进行的 Issue

阅读完成后，请总结：
1. 当前项目目标
2. 当前架构
3. 你的开发约束

确认无误后等待我的任务。
```

### 任务范围

每项任务必须明确范围：

```
允许修改：
  src/xxx/

禁止修改：
  src/yyy/
  ARCHITECTURE.md
```

### 提案模式

对于复杂任务（涉及架构决策、多个模块、新增依赖），优先使用提案模式：

1. Agent 输出设计方案（修改哪些模块、新增哪些类、数据流变化、风险）
2. 用户确认方案
3. Agent 实现

### 停机条件

任务完成后必须停止，不得：

- "顺便优化"其他模块
- 实现当前 Issue 范围外的功能
- 重构无关代码

完成后输出格式：

```
## 修改摘要

### 修改的文件
- xxx

### 设计决策
- 为什么采用这个方案

### 验证方式
- 如何测试
```

---

## 技术栈约束

| 组件 | 选型 |
|------|------|
| 语言 | Java 17 |
| UI | JavaFX |
| 构建 | Gradle |
| 测试 | JUnit 5 |

### Java 版本约束

- 目标版本：Java 17（LTS）
- 不得使用 Preview Feature
- 不得使用 Java 21+ 专有 API
- Gradle toolchain 应指定 `JavaLanguageVersion.of(17)`

### Java 编码约定

- 使用标准 Java 命名（包名小写、类名 PascalCase）
- 优先不可变对象（final 字段、构造注入）
- 优先组合而非继承
- JavaFX 代码仅存在于 `app/` 和 `ui/` 包
- Core 模块不得依赖 JavaFX

### 依赖管理

- Gradle 管理依赖
- 新增第三方库必须经过 Level 2 审批流程
- 音频后端方案已确定（JavaFX MediaPlayer，见 ADR-002）

### 环境配置约束

- Gradle User Home: `D:\Computer\Java\Gradle`（环境变量 `GRADLE_USER_HOME`）
- 所有项目需要额外下载的组件、程序、SDK 等，在确保正常使用的前提下均安装至 `D:\Computer\Java\`
- 不得将开发环境组件安装至 C 盘

### 常用命令

```bash
# 构建
./gradlew build

# 运行
./gradlew run

# 测试
./gradlew test

# Lint
./gradlew check
```

---

## 编码约定

### 命名规范

- 文件名: PascalCase（组件）、kebab-case（普通文件）
- 类名: PascalCase
- 函数/方法: camelCase
- 常量: UPPER_SNAKE_CASE
- 私有变量: _camelCase 或 camelCase（按语言惯例）

### 代码风格

- **不添加注释**，除非代码本身无法表达意图
- 优先使用有意义的变量名和函数名代替注释
- 遵循各语言社区主流代码风格
- 一个类一个职责
- 避免 static 全局状态
- 避免提前抽象（仅有单个实现时不需要 interface）
- 避免过度设计

### 抽象引入原则

仅在以下情况引入抽象（interface / abstract class）：

- 存在至少两个实现
- 或未来版本已明确规划需要多态

### 架构约束

1. **UI 层不得包含业务逻辑**
2. **Radio Engine 不得依赖 Player 实现**
3. **Audio Engine 通过接口暴露，不暴露实现细节**
4. **所有电台行为必须通过事件驱动**

---

## Definition of Done

每个 Issue 完成必须满足：

- [ ] 能正常编译运行
- [ ] 无重要 Warning
- [ ] 已完成测试（如有测试框架）
- [ ] 更新 CHANGELOG.md
- [ ] 更新 TODO.md（将对应项标记完成）
- [ ] 提交 Git Commit
- [ ] 不遗留 TODO / FIXME 注释
- [ ] 未超出 Issue 定义的范围

---

## Git 提交规范

- 一个功能完成 → 一次 Commit
- 格式: `type: description`
- Type: `feat` `fix` `refactor` `docs` `style` `test` `chore`
- 示例:
  - `feat: add local music loading`
  - `fix: voice playback not triggered on manual skip`
  - `refactor: extract AudioEngine interface`

---

## 项目文件结构（建议）

```
Horizon Radio/
  src/
    ui/              ← UI 组件
    player/          ← Player Controller
    radio/           ← Radio Engine
    audio/           ← Audio Engine
    shared/          ← 共享类型、工具
  assets/
    music/
    voice/
  docs/
    issues/          ← Issue 文档
    decisions/       ← 设计决策记录
    devlog/          ← 开发日志
    research/        ← 技术调研记录
  tests/
```

---

## 禁止事项

- 不要在 UI 中编写业务逻辑
- 不要在播放器中硬编码电台规则
- 不要提交编译产物和依赖包
- 不要提交包含密钥或敏感信息的文件
- 不要为了当前需求提前实现未来功能
- 不要一次修改整个项目
- 先读全文再编辑，不要猜测文件内容
