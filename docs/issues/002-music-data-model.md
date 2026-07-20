# #002 创建 Song 数据模型

## Background

需要抽象的数据结构来表示一首歌曲。

## Goal

创建 Song 数据模型及相关类型。

## Scope

### 包含

- Song 类（title, artist, album, filePath, duration）
- Song 工厂方法（从文件路径解析元数据）
- 相关枚举（如需要）

### 不包含

- 实际的音频解码
- 播放功能

## Technical Notes

取决于技术栈，可能是 POCO / data class / struct。

## Acceptance Criteria

- [ ] Song 类定义完成
- [ ] 可以从文件路径创建 Song
- [ ] 元数据解析正常
- [ ] 有对应的单元测试

## Related

- ROADMAP v0.1
