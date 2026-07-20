# #003 实现 MusicLibrary

## Background

需要扫描本地音乐目录并返回歌曲列表。

## Goal

实现 MusicLibrary 类，负责音乐资源管理。

## Scope

### 包含

- 扫描指定目录（支持递归）
- 过滤支持的音频格式（mp3, wav, flac, aac 等）
- 返回 Song 列表
- 支持增量扫描

### 不包含

- 播放功能
- UI 显示
- 元数据编辑

## Technical Notes

仅修改 src/player/ 或 src/shared/ 相关文件。不得调用 Audio Engine。

## Acceptance Criteria

- [ ] 扫描指定目录返回歌曲列表
- [ ] 支持递归扫描
- [ ] 过滤非音频文件
- [ ] 有对应的单元测试

## Related

- Issue #002 (Song 数据模型)
- ROADMAP v0.1
