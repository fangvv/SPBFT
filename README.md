# SPBFT

[![GitHub](https://img.shields.io/badge/Project_Homepage-181717?logo=github)](https://github.com/fangvv/SPBFT) — [https://github.com/fangvv/SPBFT](https://github.com/fangvv/SPBFT)

本项目分享的是以下论文的核心代码（完整的版本貌似弄丢了...）：

## 程序结构

```
论文代码/
└── 论文代码/
    └── 代码/
        └── SHPbft2/                         # Maven 项目根目录
            ├── pom.xml                      # Maven 构建配置（Java 1.8）
            ├── src/main/java/com/pbft/      # 核心源代码
            │   ├── PbftMain.java            # 经典 PBFT 启动入口
            │   ├── Pbft.java                # 经典 PBFT 共识算法实现
            │   ├── PbftMsg.java             # 经典 PBFT 消息模型
            │   ├── HQMain.java              # 优化版 PBFT (SHPBFT) 启动入口
            │   ├── HQ.java                  # 优化版 PBFT 共识算法实现
            │   ├── HQMsg.java               # 优化版 PBFT 消息模型
            │   └── TimerManager.java        # 异步定时调度工具
            └── src/main/resources/
                └── logback.xml              # 日志配置
```

## 程序功能

该项目实现了两种 PBFT 共识算法：

### 1. 经典 PBFT（Pbft.java）
- 标准的 PBFT 三阶段共识流程：预准备 (Pre-Prepare) → 准备 (Prepare) → 提交 (Commit)
- 支持视图变更 (View Change) 和节点宕机恢复
- 容错能力：`f = (n-1)/3`，需要 `2f+1` 票达成一致

### 2. 优化版 SHPBFT（HQ.java）
- 引入**信用机制**：共识正常的节点信用增加，拜占庭行为的节点信用减半
- 基于信用值筛选**高质量节点 (HQ)** 参与主共识流程，降低通信复杂度
- 当 HQ 节点间共识失败时，降级回退到传统 PBFT 流程，保证安全性
- 新增优化共识流程：HREQ → HPP → HBA → HCON → HCOM

### 3. 消息模型
- `PbftMsg` / `HQMsg`：定义节点间通信的消息结构，包含类型、节点 ID、视图编号、序列号、时间戳和数据等字段

### 4. 定时调度
- `TimerManager`：基于 `ScheduledExecutorService` 的异步延时执行工具，用于超时检测和定时任务

### 5. 实验数据
```
实验结果/
├── 不存在拜占庭/     # 无拜占庭节点场景
│   ├── tps/           # 吞吐量 (TPS) 数据与图表
│   ├── 共识时间图/    # 共识耗时数据与图表
│   └── 节点间通信量/  # 通信开销数据与图表
└── 存在拜占庭/       # 有拜占庭节点场景
    ├── tps/
    ├── 共识时间图/
    └── 节点间通信量/
```
实验数据包含 TPS（吞吐量）、共识时间、节点间通信量三类指标，每种指标均提供原始数据（`.txt` / `.xlsx`）和 MATLAB 绘图脚本（`.m`），可直接复现论文中的对比图表。

## 构建与运行

### 环境要求
- JDK 1.8+
- Maven 3.x

### 构建项目
```bash
cd "论文代码\论文代码\代码\SHPbft2"
mvn clean compile
```

### 运行经典 PBFT 模拟
```bash
mvn exec:java -Dexec.mainClass="com.pbft.PbftMain"
```
或直接在 IDE 中运行 `PbftMain.java` 的 `main` 方法。

### 运行优化版 SHPBFT 模拟
```bash
mvn exec:java -Dexec.mainClass="com.pbft.HQMain"
```
或直接在 IDE 中运行 `HQMain.java` 的 `main` 方法。

### 参数调整
- **节点数量**：在 `PbftMain.java` 或 `HQMain.java` 中修改 `size` 变量（PBFT 默认 4，SHPBFT 默认 22）
- **拜占庭节点**：在 `HQMain.java` 中通过 `nodes.get(id).setByzt()` 设置
- **网络时延**：在启动入口的 `net[]` 数组中调整节点间延迟范围（默认 10~60ms）
- **超时时间**：在 `checkTimer()` / `checkHTimer()` 方法中调整（投票超时默认 1000ms，请求超时默认 600ms）

程序为控制台应用，运行后输出节点间消息交互的日志，可用于观察共识流程和统计性能指标。

## ⭐ Star

**If you find this work useful for your research, please consider giving this repository a ⭐ star. Your support is greatly appreciated!**

方维维，王子岳，宋慧丽，王云鹏，丁毅. 一种面向区块链的优化PBFT共识算法[J]. 北京交通大学学报, 2019, 43(5): 58-64.
FANG Weiwei, WANG Ziyue, SONG Huili, WANG Yunpeng, DING Yi. An optimized PBFT consensus algorithm for blockchain. Beijing Jiaotong University, 2019, 43(5): 58-64.

    @article{方维维2019一种面向区块链的优化,
    title={一种面向区块链的优化 PBFT 共识算法},
    author={方维维 and 王子岳 and 宋慧丽 and 王云鹏 and 丁毅},
    journal={北京交通大学学报},
    volume={43},
    number={5},
    pages={58},
    year={2019}
  }

仅供相关领域的研究人员参考。不足之处欢迎大家批评指正！

如有疑问，请联系论文二作：1042039220@qq.com

> Please note that the open source code in this repository was mainly completed by the graduate student author during his master's degree study. Since the author did not continue to engage in scientific research work after graduation, it is difficult to continue to maintain and update these codes. We sincerely apologize that these codes are for reference only.
