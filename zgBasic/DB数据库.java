数据库 国内面试 基础知识点

索引的作用？和它的优点缺点是什么？使用索引查询一定能提高查询的性能吗？为什么？
索引是什么，怎么实现的，为什么能达到log级别

        作用,优点:  提升检索效率 (索引只建立在经常用到的条件字段)
        缺点: 它所生成的索引文件会占用大量的磁盘空间，并且在对SELECT以外的操作时会相对的降低了效率
              索引在 提高查询速度 的同时，降低了增删改三者的执行效率

        Mysql 索引种类

            INDEX 普通索引: 这是最基本的索引类型，而且它没有唯一性之类的限制。
                //CREATE INDEX indexName ON mytable(username(length));
                //ALTER mytable ADD INDEX [indexName] ON (username(length)) ;
                //DROP INDEX [indexName] ON mytable;

            UNIQUE 唯一索引: 这种索引和前面的“普通索引”基本相同，但有一个区别：索引列的所有值都只能出现一次，即必须唯一。
                //CREATE UNIQUE INDEX indexName ON mytable(username(length))

            PRIMARY KEY 主键索引: 主键是一种唯一性索引，每个表只能有一个主键
                //一般是在建表的时候同时创建主键索引：

            组合索引（较特殊）:会一次检索n条索引，直到找出相应的数据返回
                //ALTER TABLE mytable ADD INDEX name_city_age (name(10),city,age);

Mysql数据库两种索引的区别 (索引的实现)

        B+树在查找单条记录的速度虽然比不上Hash索引，但是因为更适合排序等操作
        Hash：Hash 索引仅仅能满足"=","IN"和"<=>"查询，不能使用范围查询

        利用Hash需要把数据全部加载到内存中，如果数据量大，是一件很消耗内存的事，而采用B+树，是基于按照节点分段加载，由此减少内存消耗。
        对于唯一查找（查找一个值），Hash确实更快，但数据库中经常查询多条数据，这时候由于B+数据的有序性，与叶子节点又有链表相连，他的查询效率会比Hash快的多

对什么建立索引（何时被使用）
        一般来说，在WHERE和JOIN中出现的列需要建立索引，但也不完全如此，
        因为MysqL只对<，<=，=，>，>=，BETWEEN，IN，以及某些时候的LIKE才会使用索引.


B 与 B+树区别

        B-tree树即B树，B即Balanced，平衡的意思,是多叉的 不是2叉

        In a B-tree you can store both keys and data in the internal and leaf nodes, 
        but in a B+ tree you have to store the data in the leaf nodes only.

        B+树中只有叶子节点会带有指向记录的指针（ROW ID）,
        B+树中所有叶子节点都是通过指针连接在一起，而B树不会。（底部的叶子结点是链表形式, 因此也可以实现更方便的顺序遍历）
        

        B+树改进了B树, 让内结点只作索引使用, 去掉了其中指向data record的指针, 使得每个结点中能够存放更多的key, 因此能有更大的出度. 
        这有什么用? 这样就意味着存放同样多的key, 树的层高能进一步被压缩, 使得检索的时间更短.


        B+树的优点
        1、B+树的层级更少：相较于B树B+每个非叶子节点存储的关键字数更多，树的层级更少所以查询数据更快；
        2、B+树查询速度更稳定：B+所有关键字数据地址都存在叶子节点上，所以每次查找的次数都相同所以查询速度要比B树更稳定;
        3、B+树天然具备排序功能：B+树所有的叶子节点数据构成了一个有序链表，在查询大小区间的数据时候更方便，数据紧密性很高，缓存的命中率也会比B树高。
        4、B+树全节点遍历更快：B+树遍历整棵树只需要遍历所有的叶子节点即可，，而不需要像B树一样需要对每一层进行遍历，这有利于数据库做全表扫描。

        B树相对于B+树的优点是，如果经常访问的数据离 根节点 很近，而B树的非叶子节点本身存有关键字其数据的地址，所以这种数据检索的时候会要比B+树快。



什么是主键?  什么是外键?
        主键是数据表的唯一索引
            是一个列或多列的组合，其值能唯一地标识表中的每一行(e,g. 身份证号)，通过它可强制表的实体完整性。 
            主键主要是用于其他表的外键关联，以及本记录的修改与删除。

        外键用于与另一张表的关联。是能确定另一张表记录的字段，用于保持数据的一致性。
            比如，A表中的一个字段，是B表的主键，那他就可以是A表的外键。



Mysql存储引擎是？(多存储引擎机制)
        存储引擎说白了就是   如何存储数据 + 如何为存储的数据建立索引 + 如何更新,查询数据   等技术的实现方法。
        因为在关系数据库中数据的存储是以 表 的形式存储的, 所以存储引擎也可以称为表类型（即存储和操作此表的类型.

        默认的存储引擎: InnoDB
            1.更新密集的表。                       InnoDB存储引擎特别适合处理多重并发的更新请求。
            2.事务。                             InnoDB存储引擎是支持事务的标准MySQL存储引擎。
            3.自动灾难恢复。与其它存储引擎不同       InnoDB表能够自动从灾难中恢复。
            4.外键约束。                         MySQL支持外键的存储引擎只有InnoDB。
            5.支持自动增加列AUTO_INCREMENT属性。

        InnoDB 引擎支持事务的概念, 多用于web网站后台等实时的中小型事务处理后台。

        MyISAM my-z[ei]m
            MyISAM表是独立于操作系统的，这说明可以轻松地将其从Windows服务器移植到Linux服务器
            每当我们建立一个MyISAM引擎的表时，就会在本地磁盘上建立三个文件，文件名就是表名。
                例如，我建立了一个MyISAM引擎的tb_Demo表，那么就会生成以下三个文件：
                    tb_demo.frm，存储表定义。
                    tb_demo.MYD，存储数据。
                    tb_demo.MYI，存储索引。

        MyISAM多用于
            选择密集型的表, MyISAM存储引擎在 筛选大量数据时  非常迅速，这是它最突出的优点。
            插入密集型的表, MyISAM的 并发插入特性 允许同时选择和插入数据。
                例如：MyISAM存储引擎很适合管理 邮件或Web服务器日志数据。

        MyISAM 引擎不支持事务的概念, 多用于数据仓库这样 查询多而事务少 的情况, 速度较快。


        Oracle没有引擎的概念, oracle有OLTP和OLAP模式的区分,两者的差别不大,只有参数设置上的不同。
        Oracle无论哪种模式都是 支持事务概念的, oracle是一个不允许读脏的数据库系统.


什么是事务

        指访问并可能更新数据库中各种数据项的一个程序执行单元(unit)
        一个数据库事务通常包含对数据库进行读或写的一个操作序列
        事务存在的意义
            1.为数据库操作提供了一个从失败中恢复到正常状态的方法 + 同时提供了数据库即使在异常状态下仍能保持一致性的方法。
            2.当多个应用程序在 并发访问 数据库时, 可以在这些应用程序之间提供一个 隔离方法 , 以防止彼此的操作互相干扰

        当一个事务被提交给了DBMS（数据库管理系统），则DBMS需要确保该事务中的所有操作都成功完成且其结果被永久保存在数据库中，
        如果事务中有的操作没有成功完成，则事务中的所有操作都需要被回滚，回到事务执行前的状态（要么全执行，要么全都不执行）;
        同时，该事务对数据库或者其他事务的执行无影响，所有的事务都好像在独立的运行。

事务四大特性是什么？
        ACID
        原子性（ Atomicity ）、一致性（ Consistency ）、隔离性（ Isolation ）和持久性（ Durability ）。 
        原子性（Atomicity）:
            事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行。
        一致性（Consistency）:
            事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致状态的含义是数据库中的数据应满足完整性约束。
        隔离性（Isolation）:
            多个事务并发执行时，一个事务的执行不应影响其他事务的执行。
        持久性（Durability）:
            一个事务一旦提交，他对数据库的修改应该永久保存在数据库中。


Mysql 事务的 隔离级别 有哪些？
        //transaction-isolation = {READ-UNCOMMITTED | READ-COMMITTED | REPEATABLE-READ | SERIALIZABLE}
        //SET [SESSION | GLOBAL] TRANSACTION ISOLATION LEVEL {READ UNCOMMITTED | READ COMMITTED | REPEATABLE READ | SERIALIZABLE}

        隔离级别                    脏读（Dirty Read）          不可重复读（NonRepeatable Read）     幻读（Phantom Read） 
        未提交读（Read uncommitted）        可能                            可能                       可能

        已提交读（Read committed）          不可能                          可能                        可能

        可重复读（Repeatable read）          不可能                          不可能                     可能

        可串行化（Serializable ）            不可能                          不可能                     不可能


        ·未提交读(Read Uncommitted): 允许脏读，也就是可能读取到 其他会话中 未提交事务 修改的数据.
        ·提交读(Read Committed):    只能读取到已经提交的数据。  Oracle等多数数据库默认都是该级别 (不重复读)
        ·可重复读(Repeated Read):   可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读
        ·串行读(Serializable):     完全串行化的读，每次读都需要 获得表级 共享锁，读写 相互都会阻塞.

        ① 脏读: 脏读就是指当一个事务正在访问数据，并且对数据进行了修改write，而这种修改还没有提交 not commit 到数据库中，
                这时，另外一个事务也访问这个数据，然后使用read了这个数据。
        ② 不可重复读: 是指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。
                那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。
        ④ 幻读: 第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。
                那么，以后就会发生操作第一个事务的用户发现表中 还有没有修改的数据行，就好象发生了幻觉一样。


数据库完整性

        数据库完整性约束用于保证数据的正确性。
        系统在更新、插入或删除等操作时都要检查数据的完整性，核实其约束条件

        关系模型中有 4类 完整性约束:
        实体完整性
            仅有一个主键，每个主键值必须唯一，而且不允许为“空”或重复。

        参照完整性
            参照完整性属于表间规则。(有关联的表 不能只改一个，要都改)
            对于永久关系的相关表，在更新、插入或者删除记录时，如果只改其一，就会影响数据的完整性。
            关系之间的联系是通过公共属性实现的。
            这个公共属性经常是一个表的主键，同时是另一个表的外键
        域完整性
            数据库表中的列必须满足某种 特定的数据类型 

        用户定义完整性


如何维护数据库的完整性和一致性？
        维护完整性
        尽可能的使用约束,  比如 check 主键、外键、非空字段来约束
        再是 使用触发器 trigger。

        维护一致性
        为了实现将数据库状态恢复到一致状态的功能(维护一致性)，
            DBMS通常需要 维护事务日志 以追踪事务中所有影响数据库数据的操作, 看是否需要回滚


数据库的乐观锁和悲观锁是什么？数据库的悲观锁和乐观锁是啥有什么区别
        乐观并发控制(乐观锁)和悲观并发控制 Pessimistic Concurrency Control（悲观锁）是并发控制主要采用的技术手段。

        悲观:
            如果一个事务执行的操作都某行数据应用了锁，那只有当这个事务把锁释放，其他事务才能够执行与该锁冲突的操作。
            悲观并发控制主要用于 数据争用激烈的环境 + 以及 发生并发冲突时 使用锁保护数据的成本 < 回滚事务的成本 的环境中。
            往往依靠数据库提供的锁机制 

            用 select…for update 来实现的

                //0.开始事务
                begin;/begin work;/start transaction; (三者选一就可以)
                //1.查询出商品信息
                select status from t_goods where id=1 for update;
                //2.根据商品信息生成订单
                insert into t_orders (id,goods_id) values (null,1);
                //3.修改商品status为2
                update t_goods set status=2;
                //4.提交事务
                commit;/commit work;

        乐观:    
            乐观锁假设认为数据一般情况下不会造成冲突，所以在数据进行 提交更新 的时候，才会正式对数据的冲突与否进行检测，
            如果发现冲突了，则让返回用户错误的信息，让用户决定如何去做。

            实现数据版本有两种方式，第一种是使用版本号，第二种是使用时间戳。
                使用版本号时，可以在数据初始化时指定一个版本号，每次对数据的更新操作都对 版本号执行+1 version=version+1 操作。
                并判断当前版本号是不是该数据的最新的版本号。



对一个投入使用的在线事务处理表格有过多索引需要有什么样的性能考虑?
            对一个表格的索引越多，数据库引擎用来更新、插入或者删除数据所需要的时间就越多，
            因为在数据操控发生的时候索引也必须要维护。
            要求的存储空间越多


什么是数据模型？什么是规范化？
        数据模型是一种标识实体类型及其实体间联系的模型。典
            型的数据模型有网状模型、层次模型和关系模型。
        从关系数据库的表中，除去冗余数据的过程称为规范化。
            包括：精简数据库的结构，从表中删除冗余的列，标识所有依赖于其它数据的数据

谈谈数据库设计的三范式
        1NF  : 强调的是列的原子性，即列 不能够再分成其他几列
        2NF  : 一是表必须有一个主键,
               二是没有包含在主键中的列必须完全依赖于主键, 而不能只依赖于主键的一部分。 
                如果存在 不完全依赖（只依赖一部分），那么这个属性和主关键字的这一部分应该分离出来形成一个新的实体，新实体与原实体之间是一对多的关系。

                e.g. 
                【OrderDetail】（OrderID，ProductID，UnitPrice，Discount，Quantity，ProductName） 
                    因为我们知道在一个订单中可以订购多种产品，所以单单一个 OrderID 是不足以成为主键的，主键应该是（OrderID，ProductID）。
                        显而易见 Discount（折扣），Quantity（数量）完全依赖（取决）于主键（OderID，ProductID）
                        而 UnitPrice，ProductName 只依赖于 ProductID。所以 OrderDetail 表不符合 2NF。不符合 2NF 的设计容易产生冗余数据。 
                    可以把【OrderDetail】表拆分为【OrderDetail】（OrderID，ProductID，Discount，Quantity）
                                            和【Product】（ProductID，UnitPrice，ProductName）来消除原订单表中UnitPrice，ProductName多次重复的情况。
        3NF  : 任何非主属性不依赖于其它非主属性[在2NF基础上消除传递依赖]

            考虑一个订单表【Order】（OrderID，OrderDate，CustomerID，CustomerName，CustomerAddr，CustomerCity）主键是（OrderID）。 
            其中 OrderDate，CustomerID，CustomerName，CustomerAddr，CustomerCity 等非主键列都完全依赖于主键（OrderID），所以符合 2NF。
            不过问题是 CustomerName，CustomerAddr，CustomerCity 直接依赖的是 CustomerID（非主键列），而不是直接依赖于主键，它是通过传递才依赖于主键，所以不符合 3NF。 
            通过拆分【Order】为【Order】（OrderID，OrderDate，CustomerID）和【Customer】（CustomerID，CustomerName，CustomerAddr，CustomerCity）从而达到 3NF。







Redis
        
        https://www.jianshu.com/p/36a646cef11a
        
        为什么要用Redis缓存 : 因为普通的关系型数据库 支持不了瞬间大量的查询, 比如 购物网站秒杀, 可能1s 20w条数据 DB就崩了
        key-value 型数据库  就可以支持 快速的查询



MangoDB









 

