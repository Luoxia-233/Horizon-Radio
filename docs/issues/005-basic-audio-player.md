# #005 实现基础音频播放器

## Background

需要一个基础音频播放器来播放音乐文件。音频后端方案已在 Issue #004 中确定。

## Goal

实现 AudioEngine 基础版本，支持单文件播放控制。

## Scope

### 包含

- 播放指定音频文件
- 暂停 / 恢复
- 停止
- 获取当前播放进度
- IAudioEngine 接口定义

### 不包含

- 多音轨
- Fade / Ducking
- 语音播放
- 音量控制（基础音量除外）

## Technical Notes

仅修改 src/audio/ 相关文件。通过接口暴露，不暴露实现细节。

## Acceptance Criteria

- [ ] 可以播放音频文件
- [ ] 支持暂停 / 恢复
- [ ] 支持停止
- [ ] IAudioEngine 接口定义清晰
- [ ] 有对应的单元测试

## Related

- Issue #004 (音频后端调研)
- ROADMAP v0.1
