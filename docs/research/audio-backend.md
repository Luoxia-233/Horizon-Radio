# Audio Backend 技术调研

> 日期: 2026-07-20
> 关联: Issue #004, ADR-001

---

## 调研目标

确定 Horizon Radio 在 JDK 17 + JavaFX 技术栈下的音频播放方案。

核心需求层级:
1. **MVP:** MP3/WAV 播放、音量控制、播放结束回调
2. **v0.2:** 音量平滑过渡 (Fade)
3. **v0.3:** 双音轨同时播放 + 独立音量控制 (Ducking)
4. **v0.4+:** 无额外音频需求（事件系统属于应用层）

---

## 候选方案

### 方案 A: JavaFX MediaPlayer (推荐)

| 维度 | 评估 |
|------|------|
| 依赖 | 零额外依赖（JavaFX 内置） |
| 格式支持 | MP3 ✓ / WAV ✓ / FLAC △ (依赖平台 codec) / AAC △ |
| 播放控制 | play / pause / stop / seek |
| 音量控制 | setVolume(0.0–1.0)，支持平滑过渡 |
| 播放结束 | onEndOfMedia 回调 (Runnable) |
| 多实例 | 支持多个 MediaPlayer 同时存在 |
| 双音轨 | 两个 MediaPlayer 独立控制音量 |
| Fade | JavaFX Timeline 驱动 volume 属性动画 |
| Ducking | 编程式音量切换（降低 music player → 播放 voice player → 恢复） |
| 均衡器 | AudioEqualizer 内建 |
| Windows | 良好支持 |
| 文档 | Oracle 官方文档，社区资料丰富 |
| 许可证 | GPL v2 + Classpath Exception |

**双音轨实现思路:**

```java
// 音乐播放器
MediaPlayer musicPlayer = new MediaPlayer(musicMedia);
musicPlayer.setVolume(1.0);

// 语音播放器
MediaPlayer voicePlayer = new MediaPlayer(voiceMedia);
voicePlayer.setVolume(1.0);

// Ducking: 音乐降低 + 语音播放
musicPlayer.setVolume(0.3);
voicePlayer.play();

// 语音结束后恢复
voicePlayer.setOnEndOfMedia(() -> {
    musicPlayer.setVolume(1.0);
});
```

**Fade 实现思路:**

```java
Timeline fadeOut = new Timeline(
    new KeyFrame(Duration.seconds(2),
        new KeyValue(musicPlayer.volumeProperty(), 0.0)
    )
);
fadeOut.play();
```

**关键限制:**
- MediaPlayer 创建后不可更换 Media 实例（需要重新创建播放器来切换歌曲）
- 格式支持依赖操作系统安装的 codec
- FLAC/AAC 在部分 Windows 环境下可能不可用

---

### 方案 B: VLCJ

| 维度 | 评估 |
|------|------|
| 依赖 | vlcj (uk.co.caprica:vlcj:4.8.x) + VLC 原生运行时 |
| 格式支持 | 几乎所有格式 (VLC 支持的格式) |
| 播放控制 | play / pause / stop / seek / 倍速 |
| 音量控制 | setVolume(0–200)，支持平滑过渡 |
| 播放结束 | finished 事件回调 |
| 多实例 | 支持多个 MediaPlayer |
| 双音轨 | 天然支持 |
| Fade | 需手动实现（定时器调节音量） |
| Ducking | 编程式音量切换 |
| 均衡器 | VLC 内建均衡器 |
| Windows | 需要额外安装 VLC |
| 文档 | README + 示例项目 + javadoc.io |
| 许可证 | GPL v3（Java 库）/ LGPL（VLC 本身） |

**关键限制:**
- 用户必须安装 VLC（分发复杂性）
- 线程模型复杂：不得在 Native 回调线程中调用 LibVLC
- vlcj-5.x 需要 VLC 4.x（尚未正式发布）；vlcj-4.x 需要 VLC 3.x（稳定）
- GPL v3 许可证约束
- 对象生命周期管理严格，需防止 GC 导致 JVM crash

---

### 方案 C: Java Sound API + SPI

| 维度 | 评估 |
|------|------|
| 依赖 | JDK 内置 + mp3spi / JLayer |
| 格式支持 | WAV ✓ / MP3 △ (需 SPI) / FLAC △ (需额外 SPI) |
| 播放控制 | Clip / SourceDataLine（底层 API） |
| 音量控制 | FloatControl（系统混音器级别） |
| 播放结束 | LineListener 事件 |
| 多实例 | 支持（但所有音频进入系统混音器） |
| 双音轨 | 可以，但混音控制精度低 |
| Fade | 需手动逐样本处理 |
| Ducking | 需手动实现（对样本做增益处理） |
| 文档 | 官方 API 文档，但示例较少 |
| 许可证 | JDK 内置 |

**关键限制:**
- 极度底层，手写 Fade/Ducking 需要大量样板代码
- MP3 解码依赖第三方 SPI（JLayer / mp3spi / Tritonus）
- 无内建播放进度、无内建 seek
- 双音轨混音需自行管理 buffer

---

## 对比总结

| 需求 | JavaFX MediaPlayer | VLCJ | Java Sound |
|------|:---:|:---:|:---:|
| MP3 播放 | ✓ | ✓ | △ |
| WAV 播放 | ✓ | ✓ | ✓ |
| FLAC 播放 | △ | ✓ | △ |
| 音量控制 | ✓ | ✓ | △ |
| 多音轨 | ✓ | ✓ | △ |
| Fade 平滑度 | ✓ | △ | ✗ |
| Ducking | ✓ | ✓ | ✗ |
| 零额外依赖 | ✓ | ✗ | ✓ |
| Windows 分发 | 简单 | 需捆绑 VLC | 简单 |
| 开发复杂度 | 低 | 中 | 高 |

---

## 推荐方案

**JavaFX MediaPlayer** 作为首选方案。

### 理由

1. **零额外依赖** —— 项目已使用 JavaFX 做 UI，MediaPlayer 属于同一生态
2. **API 匹配** —— volume 属性 + Timeline 动画天然适合 Fade/Ducking
3. **分发简单** —— 无需用户安装额外运行时
4. **架构允许替换** —— Audio Engine 通过 interface 暴露，若后续格式支持不足，可切换 VLCJ 方案

### 已知风险

- FLAC 格式支持需在 Windows 实测验证
- 若测试发现关键格式不支持，备选 VLCJ

### 备选

若 JavaFX MediaPlayer 在以下场景失败:
- FLAC/AAC 无法播放
- 双音轨性能不足
- 音频延迟不可接受

则切换到 **VLCJ (vlcj-4.8.x + VLC 3.x)**。

---

## POC 验证（待 #005 执行）

在实际编码时应先验证:

- [ ] MP3 文件播放 + 音量控制
- [ ] WAV 文件播放
- [ ] 两个 MediaPlayer 同时播放
- [ ] Timeline 驱动的 Fade Out / Fade In
- [ ] 语音文件播完后 onEndOfMedia 正确触发
