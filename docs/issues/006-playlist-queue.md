# #006 实现播放队列

## Background

需要管理歌曲播放顺序，支持顺序播放和随机播放。

## Goal

实现播放队列（PlaylistQueue），管理歌曲播放顺序。

## Scope

### 包含

- 从 Song 列表创建队列
- 顺序播放模式
- 随机播放模式
- 切换播放模式（不中断当前歌曲）
- 获取下一首 / 上一首
- 队列用完时自动循环

### 不包含

- UI 显示
- 拖拽排序
- 保存/加载队列

## Technical Notes

仅修改 src/player/ 相关文件。

## Acceptance Criteria

- [ ] 顺序播放：next 返回下一首，prev 返回上一首
- [ ] 随机播放：队列内部打乱，不重复
- [ ] 模式切换后队列正确更新
- [ ] 有对应的单元测试

## Related

- Issue #002 (Song 数据模型)
- Issue #005 (基础播放器)
- ROADMAP v0.1
