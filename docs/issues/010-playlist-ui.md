# #010 实现播放列表 UI

## Background

需要基础 UI 展示歌曲列表和播放状态。

## Goal

实现播放列表界面，支持基础交互。

## Scope

### 包含

- 歌曲列表显示（标题、艺术家）
- 当前播放歌曲高亮
- 播放状态显示
- 基础播放控制按钮（播放/暂停、下一首、上一首）
- 随机播放开关
- 目录选择按钮

### 不包含

- 精美 UI 设计
- 专辑封面
- 拖拽排序
- Mini Player

## Technical Notes

JavaFX 实现。UI 层不得包含业务逻辑，仅调用 Player Controller 接口。

## Acceptance Criteria

- [ ] 歌曲列表正确显示
- [ ] 当前播放歌曲高亮
- [ ] 播放/暂停按钮正常工作
- [ ] 下一首/上一首按钮正常工作
- [ ] 随机播放开关正常工作
- [ ] 选择音乐目录后列表更新

## Related

- Issue #003 (MusicLibrary)
- Issue #006 (播放队列)
- Issue #007 (状态机)
- ROADMAP v0.1
