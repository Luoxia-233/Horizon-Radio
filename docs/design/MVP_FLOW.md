# MVP 核心流程

> 这是 Horizon Radio v0.1 的端到端数据流图。作为 Agent 开发时的核心参考。

---

## 启动流程

```
用户启动程序
      │
      ▼
UI 初始化 (JavaFX)
      │
      ▼
PlayerController.init()
      │
      ├── 创建 AudioEngine 实例
      ├── 创建 MusicLibrary 实例
      ├── 创建 PlaylistQueue 实例
      ├── 创建 EventBus 实例
      └── 创建 RadioEngine 实例
                │
                ▼
          等待用户选择音乐目录
```

---

## 加载音乐流程

```
用户选择音乐目录
      │
      ▼
MusicLibrary.scan(path)
      │
      ▼
返回 List<Song>
      │
      ▼
PlaylistQueue.setSongs(songs)
      │
      ▼
触发 PlaylistLoaded 事件
      │
      ▼
UI 刷新歌曲列表
```

---

## 播放流程

```
用户点击播放 / 双击歌曲
      │
      ▼
PlayerController.play(song)
      │
      ▼
PlayerState → PLAYING
      │
      ▼
AudioEngine.play(song.filePath)
      │
      ▼
触发 PlaylistStarted 事件
      │
      ▼
UI 更新: 高亮当前歌曲 + 播放状态
```

---

## 切歌流程 (核心)

```
歌曲自然结束 / 用户点击下一首 / 上一首
      │
      ▼
PlayerState → TRANSITION
      │
      ▼
触发 SongChanged 事件
      │
      ▼
┌─ RadioEngine 收到事件 ─────────────────┐
│                                        │
│  判断是否插播?                          │
│  (MVP: 每次切换都播放)                   │
│     │                                  │
│     ▼                                  │
│  VoiceSelector.select()                │
│     │                                  │
│     ▼                                  │
│  从 assets/voice/ 随机选取一段语音       │
│     │                                  │
│     ▼                                  │
│  AudioEngine.playVoice(voiceFile)      │
│     │                                  │
│     ▼                                  │
│  PlayerState → VOICE_PLAYING           │
│     │                                  │
│     ▼                                  │
│  UI 更新: 显示 "主持语音播放中..."       │
│     │                                  │
│     ▼ (语音播放完毕)                     │
│  AudioEngine.onVoiceEnded              │
│     │                                  │
│     ▼                                  │
│  PlayerController.dispatchNextSong()   │
└────────────────────────────────────────┘
      │
      ▼
PlayerState → PLAYING
      │
      ▼
AudioEngine.play(nextSong.filePath)
      │
      ▼
UI 更新: 高亮下一首歌曲
```

---

## 手动切歌 vs 自动切歌

```
自动切歌 (歌曲播放完毕):
  AudioEngine.onMusicEnded
    → SongChanged 事件
    → RadioEngine 插播
    → 下一首

手动切歌 (用户点击按钮):
  PlayerController.next() / previous()
    → SongChanged 事件 (带 ManualSkip)
    → RadioEngine 插播
    → 下一首
```

---

## 暂停 / 恢复

```
用户点击暂停
      │
      ▼
PlayerController.pause()
      │
      ├── PlayerState → PAUSED
      │       (仅在 PLAYING 或 VOICE_PLAYING 状态时允许)
      │
      └── AudioEngine.pause()
            │
            ▼
      UI 更新: 暂停图标
```

---

## 随机播放

```
用户切换随机播放开关
      │
      ▼
PlaylistQueue.setShuffle(true/false)
      │
      ▼
触发 ShuffleToggled 事件
      │
      ▼
UI 更新: 开关状态
      │
      ▼
下次切歌时队列按新规则返回歌曲
```

---

## 状态机约束 (MVP)

```
允许的转移:
  IDLE       → PLAYING        (首次播放)
  PLAYING    → TRANSITION     (歌曲结束/手动切歌)
  PLAYING    → PAUSED         (暂停)
  TRANSITION → VOICE_PLAYING  (语音准备好)
  VOICE_PLAYING → PAUSED      (暂停)
  VOICE_PLAYING → PLAYING     (语音结束 → 下一首)
  PAUSED     → PLAYING        (恢复)
  PLAYING    → IDLE           (播放列表结束)

禁止的转移:
  IDLE       → PAUSED         (没在播放不能暂停)
  TRANSITION → PLAYING        (必须经过 VOICE_PLAYING)
  Playing 状态下直接切歌      (必须经过 TRANSITION)
```
