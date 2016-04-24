# 任务清单
    如果你有时间和精力投入开源事业，欢迎通过email等方式联系我
    Email：<alafighting@163.com>

**说明：** 实现类放在对应包的impl子包下【如: disk.storage.impl/FileStorage.java】

## - core    数据存储，负责数据的读取和写入，不关心线程等
### - disk
#### disk.storage       磁盘存储
- FileStorage

#### disk.journal       磁盘日志
- FIFOJournal           先进先出算法
- LFUJournal            最近最少使用算法
- LRUJournal            最久未使用算法
- NMRUJournal           非最近使用算法

#### disk.converter     数据转换器
- SerializableConverter
- ParcelableConverter
- KryoConverter
- JsonConverter

#### disk.sink          数据槽
- FileSink
- StringSink
- ByteArraySink

#### disk.source        数据源
- FileSource
- StringSource
- ByteArraySource

### - memory
#### memory.storage     内存存储
- FileStorage

#### memory.journal     内存日志
- FIFOJournal           先进先出算法
- LFUJournal            最近最少使用算法
- LRUJournal            最久未使用算法
- NMRUJournal           非最近使用算法
