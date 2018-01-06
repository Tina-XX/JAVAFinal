# 葫芦娃大作业
## 一、运行界面
### 1、进入主界面后按下空格键将自动开打，按下L键将加载复盘文件并自动播放
#### 准备开始
![Alt text](https://github.com/Tina-XX/JAVAFinal/blob/master/screenshot/start.png)
#### 胜利！
![Alt text](https://github.com/Tina-XX/JAVAFinal/blob/master/screenshot/win.png)
#### 加载复盘文件
![Alt text](https://github.com/Tina-XX/JAVAFinal/blob/master/screenshot/load.png)

## 二、对战机制
### 1、攻击力
  葫芦娃在最开始的时候攻击力并不高，仅能勉强打败小罗喽，碰到蛇精和蝎子精只有选择死亡的份；但当他们的爷爷被打死后，葫芦娃会进入暴走状态，此时任何敌人都能打败。
  爷爷的攻击力最低，基本碰到敌人就死。
  小罗喽的攻击力与葫芦娃相当。
  蛇精和蝎子精的攻击力非常高。
### 2、移动速度
  葫芦娃和小罗喽的移动速度适中，爷爷的移动速度最慢，蝎子精和蛇精的移动速度相当高。
  爷爷和小罗喽只能向前移动攻击。
  葫芦娃与蛇精、蝎子精都会主动寻找存活的敌人，并攻击敌人直到场上没有敌方。
### 3、攻击冷却时间
  每当生物进行一次攻击之后，都需要冷却攻击时间，此时该生物不能移动和攻击。葫芦娃和小罗喽的攻击冷却时间适中，爷爷的攻击冷却时间最短，蛇精和蝎子精在做出一次攻击后要冷却相当长的一段时间。
  
## 三、类结构
### 1、抽象类Creature
  葫芦娃Brothers、爷爷Grandpa、小罗喽Monsters、蛇精Shejing、蝎子精Xiezijing均继承自该类，同时Creature类implement自Runnable，在子类中具体实现各自的run方法。
### 2、接口Layout
  实现move方法，使生物作出一次移动。
### 3、Map类
  生成一个15*15的战场，在每一次行动之后保存战场信息。
### 4、LoadRecord类
  实现复盘文件的读取和自动播放。打开Finder选择文件，并限定文件的后缀必须为.txt。
  
## 四、多线程
  通过线程池将每个生物体创建为一个新的线程，通过判断游戏是否结束来决定要不要进入wait（）状态。当该生物体自身状态为存活时，可以开始行动，首先将自身锁起来，判断周围是否有敌人；如果有存活的敌人，则将敌人也锁起来，开打，战斗结束后释放敌人，进入战后冷却sleep；判断移动方向，如果即将前往的Position的valid为true，即该Position没有生物，则决定移动，将该Position锁起来，防止两个生物同时踩在这块Position，进行移动后修改前后Position的valid和调用生物体方法SetXY修改其位置信息。
  为了使回放时候过程速度便于观看，将回放也创建为一个线程，每进行一次行动将sleep一会儿。

## 五、面向对象和设计原则
### 1、定义抽象类Creature和接口Layout
  将有共同性质的Object从同一基类中继承。
### 2、封装和数据安全性
  将内部数据定义为private，通过GetX方法获取、SetX方法修改，防止外部对数据的直接访问。将多个子类的共用数据定义为static类型（如boolean类型的标志），防止数据不一致导致的错误。
### 3、单一职责原则
  每个子类仅实现一项功能，只有一个原因将引起类的变化。
### 4、RTTI与范型
  使用范型<T extern Creature>便于在不确定参数具体类型时接受参数。
  在复盘功能中创建Creature对象，运行时再根据文件读取内容确定具体的实类，调用具体对应的方法。
  
