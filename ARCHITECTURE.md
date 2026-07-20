# 架构设计

## 分层架构

```
┌─────────────────────────────────┐
│              UI                 │  展示播放状态、响应用户操作
├─────────────────────────────────┤
│        Player Controller        │  播放控制协调层
├─────────────────────────────────┤
│         Radio Engine            │  事件驱动电台规则系统
├─────────────────────────────────┤
│         Audio Engine            │  多音轨管理、音量控制
├─────────────────────────────────┤
│         Audio Output            │  系统音频输出
└─────────────────────────────────┘
```

## 模块职责

### UI

- 展示播放列表、当前歌曲、播放状态
- 响应用户操作（播放/暂停/切歌/随机播放）
- 调用 Player Controller 接口
- **不包含任何业务逻辑**

### Player Controller

- 管理播放器状态机：`Idle → Playing → Transition → VoicePlaying → Paused`
- 解析本地音乐目录、维护播放列表
- 提供播放控制接口（play / pause / next / prev / shuffle）
- 触发事件给 Radio Engine
- **不直接控制音频输出**

### Radio Engine

- 事件驱动架构
- 监听事件（SongChanged, PlaylistStarted, PlaylistEnded, ManualSkip 等）
- 决定是否播放主持语音及播放哪段素材
- 管理广播规则与素材池
- **不知道播放器内部实现细节**

### Audio Engine

- 音乐播放与语音播放的底层控制
- 多音轨管理（music track + voice track）
- 音量控制、Fade In/Out、Audio Ducking
- 可替换实现，不影响上层模块

## 事件系统

| 事件 | 触发条件 |
|------|----------|
| `SongChanged` | 歌曲切换（自动/手动） |
| `PlaylistStarted` | 播放列表开始播放 |
| `PlaylistEnded` | 播放列表播放完毕 |
| `LongListening` | 长时间连续播放（如 3-5 首） |
| `ManualSkip` | 用户手动切歌 |
| `PlayPause` | 播放/暂停切换 |
| `ShuffleToggled` | 随机播放开关 |

## 素材管理

```
assets/
  music/          ← 用户自己的音乐（或用户指定目录）
  voice/
    intro/        ← 开场引导语
    transition/   ← 切歌转场语
    night/        ← 夜间专属
    weather/      ← 天气相关（扩展）
    station_id/   ← 电台呼号
  metadata/       ← 素材元数据
```

## 状态机

```
              ┌──────────┐
              │   Idle   │
              └────┬─────┘
                   │ play
              ┌────▼─────┐
       ┌──────│ Playing  │◄──────────────┐
       │      └────┬─────┘               │
       │ pause     │ song ends           │
       │      ┌────▼──────┐   voice      │
       │      │ Transition │──► ends ─────┘
       │      └────┬───────┘
       │           │ voice ready
       │      ┌────▼────────┐
       │      │ VoicePlaying │
       │      └────┬────────┘
       │           │ voice ends → Playing
  ┌────▼─────┐     │
  │  Paused  │◄────┘ (pause during voice)
  └──────────┘
```

## Java 模块结构

```
horizon-radio/
├── src/main/java/com/horizonradio/
│   ├── app/          ← JavaFX Application 入口 + 窗口
│   │   └── Main.java
│   ├── core/         ← 共享模型 (Song, Playlist, Event, PlayerState)
│   ├── player/       ← Player Controller (MusicLibrary, PlaylistQueue, StateMachine)
│   ├── audio/        ← Audio Engine (IAudioEngine 接口 + 实现)
│   ├── radio/        ← Radio Engine (RadioEngine, VoiceSelector, VoiceManager)
│   └── ui/           ← JavaFX UI 组件 (MainWindow, PlaylistView, PlayerControl)
├── src/test/java/com/horizonradio/
├── build.gradle
├── settings.gradle
└── gradle.properties
```

### 模块依赖方向

```
   app  ─────────────→  core
   │                     ↑
   ui  ─────────────────┤
                        │
   player ─────────────┤
   │                    │
   radio ──────────────┤
   │                    │
   audio ──────────────┘

禁止:
  core → audio    (核心不应依赖实现)
  core → radio    (核心不应依赖实现)
  core → player   (核心不应依赖实现)
  audio → radio   (音频不应依赖电台)
  radio → player  (电台不应依赖播放器实现)
```

## 核心原则

1. **面向接口编程** — 模块之间通过抽象接口通信，不依赖具体实现
2. **事件驱动** — 所有广播行为由事件触发，不在播放器中硬编码
3. **可替换性** — Audio Engine 可替换，不影响 Radio Engine
4. **渐进增强** — MVP 先验证流程，后续版本逐步加入 Fade/Ducking/规则系统
